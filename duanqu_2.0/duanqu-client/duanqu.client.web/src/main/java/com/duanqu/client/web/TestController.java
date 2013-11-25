package com.duanqu.client.web;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanqu.common.DuanquSecurity;
import com.duanqu.common.Result;
import com.duanqu.common.dao.demo.UserMapper;
import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.service.ProducerService;
import com.duanqu.common.submit.UserSubmit;
import com.duanqu.common.vo.ActionForm;
import com.duanqu.common.vo.UserForm;
import com.duanqu.redis.service.IRedisTestService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.search.ISearcher;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;

@Controller
public class TestController extends BaseController{
	Log logger = LogFactory.getLog(TestController.class);
	
	@Resource
	IRedisUserService redisUserService;

	@Resource
	UserMapper userMapper;
	
	@Resource
	IRedisRelationshipService	userRelationService;
	
	@Resource
	ISearcher suggestSearcher;
	
	@Resource
	ISearcher contentSearcher;
	
	@Resource
	IRedisContentService redisContentService;
	
	@Resource
	IRedisTestService redisTestService;
	
	/*@Resource
	ProducerService producerService;
	
	@RequestMapping(value="/test/pulish",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result pulish(){
		
		//duanquPublisher.publish(new NoticeMessage("localhost","localhost",NoticeMessage.MessageType.TEST));
		UserModel user = new UserModel();
		user.setUid(123);
		user.setNickName("拔掉博雅71");
		producerService.sendMessage(user);
		Result result = new Result();
		result.setCode(200);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		
		return result;
	}
	*/
	
	
	@RequestMapping(value="/test/{uCount}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result insertData(@PathVariable int uCount){
		
		for (int i = 0;i<uCount;i++){
			redisTestService.insertTestFans(String.valueOf(i));
			for (int j =0 ;j< i * 10 ;j++){
				redisTestService.insertTestTimeline(String.valueOf(i), i+":1:"+j);
			}
		}
		
		
		Result result = new Result();
		result.setCode(200);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	@RequestMapping(value="/test/load/{start}/{count}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result loadData(@PathVariable int start,@PathVariable int count){
		try{
		redisTestService.loadTimeline(start,count);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setCode(200);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	@RequestMapping(value="/user/{uid}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody UserForm getUser(@PathVariable String uid, HttpServletRequest request){
		UserModel model = redisUserService.getUser(Long.valueOf(uid));
		return new UserForm(model);
	}
	
	@RequestMapping(value="/user/follow/{srcUid}/{targetUid}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result follow(@PathVariable String srcUid, @PathVariable String targetUid, HttpServletRequest request){
		Result result = new Result();
		result.setCode(200);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		long srcUidLong =  0;
		try{
			srcUidLong = Long.parseLong(srcUid);
		}catch (Exception e) {
			srcUidLong = 0;
		}
		
		UserModel srcUser = redisUserService.getUser(srcUidLong);
		
		long targetUidLong =  0;
		try{
			targetUidLong = Long.parseLong(targetUid);
		}catch (Exception e) {
			targetUidLong = 0;
		}
		
		UserModel targetUser = redisUserService.getUser(targetUidLong);
		
		if (targetUidLong == srcUidLong){
			result.setCode(600);
			result.setMessage("不能自己关注自己！");
		}
		
		if (targetUser != null && srcUser != null){
			userRelationService.follow(srcUidLong, targetUidLong);
		}
		
		/*List<User> users = new ArrayList<User>();
		Set<String> followsUid = userRelationService.loadFollowsUid(srcUidLong);
		for (String uid : followsUid){
			User user = cacheServiceDemo.getUser(Long.parseLong(uid));
			users.add(user);
		}*/
		result.setData(userRelationService.loadFans(srcUidLong, 0, 20,1));
		return result;
	}
	
	
	@RequestMapping(value="/user/follow/{uid}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result getFollows(@PathVariable String uid, HttpServletRequest request){
		
		Result result = new Result();
		
		result.setCode(200);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		long uidLong =  0;
		try{
			uidLong = Long.parseLong(uid);
		}catch (Exception e) {
			uidLong = 0;
		}
		
		
		
		result.setData(userRelationService.loadFollows(uidLong,0,20));
		return result;
	}
	
	
	
	
	@RequestMapping(value="/user/new/{userName}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody UserForm addDbUser(@PathVariable String userName, HttpServletRequest request){
		
		UserSubmit user = new UserSubmit();
		user.setLoginPassword(DuanquSecurity.encodePassword(userName));
		user.setNickName(userName);
		user.setAvatarUrl("default.jpg");
		user.setEmail("badboy4471@sina.com");
		user.setLatitude(100);
		user.setLongitude(100);
		user.setOpenType(1);
		user.setOpenUid("123456789");
		user.setRefreshToken("123456789");
		user.setAccessToken("");

		UserModel model = user.asUserModel();
		model.setToken(DuanquSecurity.encodeToken(user.getEmail()));
		redisUserService.insertUser(model);
		BindModel bind = user.asBindModel();
		bind.setUid(model.getUid());
		redisUserService.bindUserInfo(bind);
		
		//cacheServiceDemo.insertUser(user);
		//redisPublisher.publish(user);
		//userRedisPublisher.publish(user);
		return new UserForm(model);
	}
	
	
	/*@RequestMapping(value="/content/add/{tags}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Content addContent(@PathVariable String tags, HttpServletRequest request){
		
		Content content = new Content();
		content.setCommentNum(2);
		content.setCreatetime(System.currentTimeMillis());
		content.setDescription("基于java语言开发的轻量级的中文分词工具包");
		content.setLatilude(100.0f);
		content.setLongitude(100.0f);
		content.setPlayTimes(5);
		content.setTags(tags);
		content.setThumbnail("http://upyun-img.danqoo.com/common/20130326/201303261628275912114.jpg!picture.big0");
		content.setTime(3.5f);
		content.setTitle("黄山旅游");
		content.setVideoUrl("http://duanqu-video.b0.upaiyun.com/video/7-2.mp4");
		content.setUid(1l);
		content.setId(at.getAndIncrement());
		contentAddPublisher.publish(content);
		return content;
	}*/
	
	
	@RequestMapping(value="/suggest/{key}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result search(@PathVariable String key, HttpServletRequest request){
		Set<String> suggests = suggestSearcher.searchSuggest(key);
		Result result = new Result();
		result.setCode(200);
		result.setData(suggests);
		result.setMessage("成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	@RequestMapping(value="/content/{key}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody
	Result searchContent(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@PathVariable String key, HttpServletRequest request) {
		int pageSize = 20;
		List<ActionForm> actions = contentSearcher.searchTagContentsOrderByUploadTime(1,key,(page - 1)*pageSize, (page * pageSize -1));
		Result result = new Result();
		result.setCode(200);
		result.setData(actions);
		result.setMessage("成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	@RequestMapping(value="/content/add",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result addContent( HttpServletRequest request){
		ContentModel contentModel = new ContentModel();
		contentModel.setCommentNum(0);
		contentModel.setCreateTime(System.currentTimeMillis());
		contentModel.setcStatus(0);
		contentModel.setDescription("你好，西湖旅游，测试数据");
		contentModel.setForwardNum(0);
		contentModel.setHeight(600);
		contentModel.setWidth(600);
		contentModel.setIsPrivate(0);
		contentModel.setLatilude(100);
		contentModel.setLongitude(100);
		contentModel.setLikeNum(0);
		contentModel.setLocation("浙江杭州拱墅区祥园路38号浙报印务");
		contentModel.setPlayTime(8);
		contentModel.setShowTimes(0);
		contentModel.setThumbnailsUrl("test.jpg");
		contentModel.setTitle("测试旅游");
		contentModel.setUid(1);
		contentModel.setUploadTime(System.currentTimeMillis());
		contentModel.setVideoUrl("1.mp4");
		contentModel.setVideoUrlHD("2.mp4");
		
		redisContentService.insertContent(contentModel);
		Result result = new Result();
		
		result.setCode(200);
		result.setData(contentModel);
		result.setMessage("上传成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	@RequestMapping(value="/content/get/{cid}",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result getContent(@PathVariable String cid, HttpServletRequest request){
		ContentModel content = redisContentService.getContent(Long.valueOf(cid));
		Result result = new Result();
		
		result.setCode(200);
		result.setData(content);
		result.setMessage("上传成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	@RequestMapping(value="/report",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody Result report(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user:users){
			//int count = redisUserService.countContent(user.getUid());
			//user.setRoleId(0);
			//logger.error("|userName|"+user.getNickName()+"|Email|"+user.getEmail()+"|注册时间|"+sdf.format(new Date(user.getCreateTime())) + "|count|"+count);
		}
		
		Result result = new Result();
		result.setCode(200);
		result.setData(users);
		result.setMessage("上传成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	
}
