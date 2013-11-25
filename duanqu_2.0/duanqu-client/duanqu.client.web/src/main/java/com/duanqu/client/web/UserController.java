package com.duanqu.client.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquConfig;
import com.duanqu.common.DuanquErrorCode;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.model.SettingModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.submit.Pager;
import com.duanqu.common.vo.ActionForm;
import com.duanqu.common.vo.UserDetailForm;
import com.duanqu.redis.service.badword.IBadwordService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.message.IRedisMessageService;
import com.duanqu.redis.service.timeline.IRedisTimelineService;
import com.duanqu.redis.service.user.IRedisRelationshipService;

/**
 * 用户个人中心相关操作
 * 
 * @author wanghaihua
 * 
 */
@Controller
public class UserController extends BaseController {
	
	private static int PAGE_SIZE = 20;
	
	@Resource
	IRedisTimelineService redisTimelineService;

	@Resource
	IRedisContentService redisContentService;
	
	@Resource
	IRedisRelationshipService redisRelationshipService;
	
	@Resource
	IRedisMessageService redisMessageService;
	@Resource
	IBadwordService badwordService;
	

	/**
	 * 取得用户个人中心我创建的内容
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/center/my", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result myUploadContent(
			@RequestParam(value="uid",required=false,defaultValue="0") long uid,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("token") String token) {
		
		long curUid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		
		if (uid == 0){
			uid = curUid;
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setPageSize(PAGE_SIZE);
		List<ActionForm> actions = redisContentService.loadUserContents(uid, curUid, pager);
		
		result.setCode(200);
		result.setData(actions);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		result.setPages((redisContentService.countUserContents(uid) - 1)
				/ PAGE_SIZE + 1);
		return result;
	}
	
	
	
	/**
	 * 我喜欢的内容列表
	 * @param uid
	 * @param page
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/center/like", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result myLikeContent(
			@RequestParam(value="uid",required=false,defaultValue="0") long uid,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("token") String token) {
		
		long curUid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		
		if (uid == 0){
			uid = curUid;
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setPageSize(PAGE_SIZE);
		List<ActionForm> actions = redisContentService.loadUserLikeContents(uid, curUid, pager);
		
		result.setCode(200);
		result.setData(actions);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		result.setPages((redisContentService.countUserLikeContents(uid) - 1)
				/ PAGE_SIZE + 1);
		return result;
	}
	
	/**
	 * 我转发的内容列表
	 * @param uid
	 * @param page
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/center/forward", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result myForwardContent(
			@RequestParam(value="uid",required=false,defaultValue="0") long uid,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("token") String token) {
		
		long curUid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		
		if (uid == 0){
			uid = curUid;
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setPageSize(PAGE_SIZE);
		List<ActionForm> actions = redisContentService.loadUserForwardContents(uid, curUid, pager);
		
		result.setCode(200);
		result.setData(actions);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		result.setPages((redisContentService.countUserForwardContents(uid) - 1)
				/ PAGE_SIZE + 1);
		return result;
	}
	
	
	/**
	 * 取得用户个人中心我创建的内容
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/center/all", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result myAllContent(
			@RequestParam(value="uid",required=false,defaultValue="0") long uid,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("token") String token) {
		
		long curUid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		
		if (uid == 0){
			uid = curUid;
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setPageSize(PAGE_SIZE);
		List<ActionForm> actions = redisContentService.loadUserAllContents(uid, curUid, pager);
		result.setCode(200);
		result.setData(actions);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		result.setPages((redisContentService.countUserAllContents(uid) - 1)
				/ PAGE_SIZE + 1);
		return result;
	}
	
	/**
	 * 取得用户个人中心用户信息
	 * @param uid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/center", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result userMain(@RequestParam(value="uid",required=false,defaultValue="0") long uid,
			@RequestParam("token") String token) {
		long curUid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		if (curUid == 0){
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		UserDetailForm user = null;
		if (curUid == uid || uid == 0){//访问自己的主页
			UserModel model = redisUserService.getUser(curUid);
			user = model.asDetailUserForm();
			user.setFansCount(redisRelationshipService.countFans(curUid));
			user.setFollowsCount(redisRelationshipService.countFollows(curUid));
			user.setFriendsCount(redisRelationshipService.countFriends(curUid));
			int privateContentCount = redisContentService.countUserPrivateContents(curUid);
			int publicContentCount = redisContentService.countUserPublicContents(curUid);
			user.setContentsCount(privateContentCount + publicContentCount);
			user.setIsFans(0);
			user.setIsFollow(0);
			user.setIsBlack(0);
		} 
		
		if (uid != 0 && uid != curUid){//访问别人的个人中心
			UserModel model = redisUserService.getUser(uid);
			user = model.asDetailUserForm();
			if (uid == 1){//趣拍官方用户隐藏 粉丝数，关注数，好友数
				user.setFansCount(0);
				user.setFollowsCount(0);
				user.setFriendsCount(0);
				user.setContentsCount(redisContentService.countUserPublicContents(uid));
			}else{
				user.setFansCount(redisRelationshipService.countFans(uid));
				user.setFollowsCount(redisRelationshipService.countFollows(uid));
				user.setFriendsCount(redisRelationshipService.countFriends(uid));
				user.setContentsCount(redisContentService.countUserPublicContents(uid));
			}
			boolean hasRight = redisMessageService.hasMessageRight(curUid);//当前登陆用户是否有私信特权
			user.setIsFans((hasRight || redisRelationshipService.isFollowed(uid, curUid)) ? 1 : 0);
			user.setIsFollow(redisRelationshipService.isFollowed(curUid, uid) ? 1 : 0);
			user.setIsBlack(redisRelationshipService.isBlackUser(curUid, uid)?1:0);
		}

		result.setCode(200);
		result.setData(user);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		
		return result;
	}
	
	
	/**
	 * 取得用户首页动态页面
	 * 
	 * @param page 页码
	 * @param token
	 * @param type 列表类型 （all全部，forward 转发，create 创建，friend 好友,private 仅自己）
	 * @return
	 */
	@RequestMapping(value = "/user/timeline", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result timeLine(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("token") String token,
			@RequestParam(value="type",required=false,defaultValue="all") String type) {
		
		long uid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		if (uid == 0){
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setPageSize(PAGE_SIZE);
		List<ActionForm> actions = redisTimelineService.loadUserTimeline(uid, pager,type);
		int count = 0 ;
		if ("private".equals(type)){
			count = redisTimelineService.countUserPrivateContents(uid);
		}else{
			count = redisTimelineService.countUserTimeline(uid);
		}
		result.setCode(200);
		result.setData(actions);
		result.setPages( (count -1) / PAGE_SIZE + 1 );
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		
		return result;
	}
	
	
	/**
	 * 取得用户首页动态页面
	 * 
	 * @param page 页码
	 * @param token
	 * @param type 列表类型 （all全部，forward 转发，create 创建）
	 * @return
	 */
	@RequestMapping(value = "/user/timeline/new", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result timeLineNew(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam("token") String token) {
		
		long uid = super.getUid(token);// 当前登录用户ID
		Result result = new Result();
		if (uid == 0){
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setPageSize(PAGE_SIZE);
		List<ActionForm> actions = redisTimelineService.loadUserNewestTime(uid);

		int count = redisTimelineService.countUserTimeline(uid);
		result.setCode(200);
		result.setData(actions);
		result.setPages( (count -1) / PAGE_SIZE + 1 );
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		
		return result;
	}
	
	
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result editUser(@RequestParam(value="avatar",required=false) MultipartFile avatar,
			@RequestParam("nickName")String nickName,
			@RequestParam("signature")String signature,
			@RequestParam("token") String token) {
		Result result = valid(nickName,signature);
		if (result != null){
			return result;
		}
		result = new Result();
		UserModel user = getUser(token);
		if (user != null && user.getStatus() == 0){
			result = new Result();
			result.setCode(DuanquErrorCode.USER_FORBID.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.USER_FORBID.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		if (avatar != null && avatar.getSize() > 0) {
			// 直接上传aliyun
			String contentType = avatar.getContentType();
			try {
				if (DuanquConfig.getImageContentType().contains(contentType)){
					String key = AliyunUploadUtils.uploadAvatar(avatar
							.getInputStream(), avatar.getBytes().length, avatar
							.getContentType());
					//更新用户昵称成不为“趣拍”，趣拍官方
					if (nickName != null && nickName.contains("趣拍")){
						nickName.replaceAll("趣拍", "");
						if (nickName.trim().length() < 2){
							nickName += DuanquUtils.getRamdCode();
						}
					}
					user.setAvatarUrl(key);
					user.setNickName(nickName);
					user.setSignature(signature);
					redisUserService.update(user);
					result.setCode(200);
					result.setData("");
					result.setPages(0);
					result.setMessage("更新成功！");
					result.setTime(System.currentTimeMillis());
				}else{
					result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
					result.setData("文件格式不支持！");
					result.setPages(0);
					result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
					result.setTime(System.currentTimeMillis());
				}
			} catch (Exception e) {
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData(e.getMessage());
				result.setPages(0);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		} else {
			user.setNickName(nickName);
			user.setSignature(signature);
			redisUserService.update(user);
			result.setCode(200);
			result.setData("");
			result.setPages(0);
			result.setMessage("更新成功！");
			result.setTime(System.currentTimeMillis());
		}
		if (result.getCode() == 200){
			//发送同步信息
			redisJMSMessageService.insertUserEditQueue(user);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.USER_EDIT));
		}
		return result;
	}
	
	/**
	 * 删除自我介绍视频
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/video/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result deleteUserVideo(@RequestParam("token")String token){
		Result result = new Result();
		UserModel user = this.getUser(token);
		if (user != null){
			user.setVideoFaceUrl("");
			user.setVideoUrl("");
			try{
				redisUserService.update(user);
				result.setCode(200);
				result.setData("");
				result.setMessage("删除成功！");
				result.setTime(System.currentTimeMillis());
			}catch(Exception e){
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData("系统错误！e"+e);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		}else{
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("参数错误！");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
			
	
	private Result valid(String nickName,String signature){
		Result result = null;
		if (!StringUtils.hasText(nickName)){
			result = new Result();
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("昵称不能为空！");
			result.setPages(0);
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		String badWord = badwordService.hasBadWord(nickName);
		if (StringUtils.hasText(badWord)){
			result = new Result();
			result.setCode(DuanquErrorCode.SENSITIVE.getCode());
			result.setData("昵称包含敏感词："+badWord);
			result.setPages(0);
			result.setMessage("昵称"+DuanquErrorCode.SENSITIVE.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		badWord = badwordService.hasBadWord(signature);
		if (StringUtils.hasText(badWord)){
			result = new Result();
			result.setCode(DuanquErrorCode.SENSITIVE.getCode());
			result.setData("签名包含敏感词："+badWord);
			result.setPages(0);
			result.setMessage("签名"+DuanquErrorCode.SENSITIVE.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		return result;
	}
	
	/**
	 * 获取用户的设置信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/setting/get", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result getSetting(
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		SettingModel setting = redisUserService.getUserSetting(uid);
		if (setting == null){
			setting = new SettingModel();
		}
		result.setCode(200);
		result.setData(setting);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	/**
	 * 喜欢分享设置
	 * @param like
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/setting/like", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result settingUserLikeShare(@RequestParam("like")int like, //0：关闭，1：开启
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		SettingModel setting = redisUserService.getUserSetting(uid);
		if (setting == null){
			setting = new SettingModel();
		}
		setting.setShare(like);
		redisUserService.setUserSetting(uid, setting);
		
		result.setCode(200);
		result.setData(setting);
		result.setMessage("设置成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	/**
	 * 是否进入拍摄设置
	 * @param like
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/setting/camera", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result settingIsCamera(@RequestParam("isCamera")int isCamera,
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		SettingModel setting = redisUserService.getUserSetting(uid);
		if (setting == null){
			setting = new SettingModel();
		}
		setting.setIsCamera(isCamera);
		redisUserService.setUserSetting(uid, setting);
		
		result.setCode(200);
		result.setData(setting);
		result.setMessage("设置成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	/**
	 * 编辑推荐设置
	 * @param recommend
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/setting/recommend", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result settingUserRecommend(@RequestParam("recommend")int recommend,
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		SettingModel setting = redisUserService.getUserSetting(uid);
		if (setting == null){
			setting = new SettingModel();
		}
		setting.setRecommend(recommend);
		redisUserService.setUserSetting(uid, setting);
		
		result.setCode(200);
		result.setData(setting);
		result.setMessage("设置成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	/**
	 * 消息推送设置
	 * @param recommend
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/setting/message", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result settingUserRecommend(@RequestParam(value="atMessage",required=false,defaultValue="1")int atMessage,
			@RequestParam(value="likeMessage",required=false,defaultValue="0")int likeMessage,
			@RequestParam(value="commentMessage",required=false,defaultValue="1")int commentMessage,
			@RequestParam(value="message",required=false,defaultValue="1")int message,
			@RequestParam(value="forwardMessage",required=false,defaultValue="0")int forwardMessage,
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		SettingModel setting = redisUserService.getUserSetting(uid);
		if (setting == null){
			setting = new SettingModel();
		}
		setting.setAtMessage(atMessage);
		setting.setLikeMessage(likeMessage);
		setting.setCommentMessage(commentMessage);
		setting.setMessage(message);
		setting.setForwardMessage(forwardMessage);
		redisUserService.setUserSetting(uid, setting);
		result.setCode(200);
		result.setData(setting);
		result.setMessage("设置成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	@RequestMapping(value = "/setting/banner", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result settingUserBanner(@RequestParam("banner") MultipartFile banner,
			@RequestParam("token") String token) {
		Result result = new Result();
		UserModel user = this.getUser(token);
		if (banner != null && banner.getSize() > 0) {
			// 直接上传aliyun
			String contentType = banner.getContentType();
			try {
				if (DuanquConfig.getImageContentType().contains(contentType)){
					String key = AliyunUploadUtils.uploadUserBanner(banner
							.getInputStream(), banner.getBytes().length, banner
							.getContentType());
					redisUserService.updateBanner(key, user.getUid());
					result.setCode(200);
					result.setData("");
					result.setPages(0);
					result.setMessage("更新成功！");
					result.setTime(System.currentTimeMillis());
				}else{
					result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
					result.setData("文件格式不支持！");
					result.setPages(0);
					result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
					result.setTime(System.currentTimeMillis());
				}
			} catch (Exception e) {
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData(e.getMessage());
				result.setPages(0);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		} else {
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("背景图片为空!");
			result.setPages(0);
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
}
