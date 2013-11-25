package com.duanqu.client.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquErrorCode;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.EmojiUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.bean.AtMessageBean;
import com.duanqu.common.bean.ContentBean;
import com.duanqu.common.bean.ShareBean;
import com.duanqu.common.model.ActionModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.ActionModel.Action;
import com.duanqu.common.model.ContentModel.Status;
import com.duanqu.common.model.ReportModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.submit.ContentSubmit;
import com.duanqu.common.vo.ActionForm;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.timeline.IRedisTimelineService;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;

/**
 * 内容相关
 * 
 * @author wanghaihua
 * 
 */
@Controller
public class ContentController extends BaseController {
	Log logger = LogFactory.getLog(ContentController.class);
	private static int PAGE_SIZE = 20;
	@Resource
	IRedisUserService redisUserService;
	
	@Resource
	IRedisContentService redisContentService;
	
	@Resource
	IRedisTimelineService redisTimelineService;
	
	@Resource
	IRedisRelationshipService redisRelationshipService;
	
	@RequestMapping(value = "/content/upload", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result saveContent(@ModelAttribute ContentSubmit contentSubmit,@RequestParam("token")String token) {
		Result result = new Result();
		UserModel user = super.getUser(token);
		if (user == null){
			result.setCode(DuanquErrorCode.TOKEN_ERROR.getCode());
			result.setData(token);
			result.setMessage(DuanquErrorCode.TOKEN_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		long uid = user.getUid();
		if (uid == 0) {
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData(0);
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		} else {
			if(!StringUtils.isEmpty(contentSubmit.getGroupNames())){
				contentSubmit.setIsPrivate(2);//组内分享
			}
			String videoPath = "";
			try{
				//DuanquUtils.writeFile(video, contentSubmit.getVideo().getBytes()); // 写本地文件
				videoPath = AliyunUploadUtils.uploadHDVideo(contentSubmit.getVideo()
						.getInputStream(),
						contentSubmit.getVideo().getBytes().length,
						contentSubmit.getVideo().getContentType());
			}catch(Exception e){
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData(0);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage()+"上传视频错误！"+e.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
			String thumbnailsPath = "";

			try{
				//DuanquUtils.writeFile(thumbnails, contentSubmit.getThumbnails().getBytes()); //写文件
				thumbnailsPath = AliyunUploadUtils.uploadThumbnail(
						contentSubmit.getThumbnails().getInputStream(),
						contentSubmit.getThumbnails().getBytes().length,
						contentSubmit.getThumbnails().getContentType());
				
			}catch(Exception e){
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData(0);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage()+"上传封面错误！"+e.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
			ContentModel model = contentSubmit.asContentModel();
			model.setKey(DuanquUtils.md5(System.currentTimeMillis()+""));
			model.setUid(uid);
			model.setThumbnailsUrl(thumbnailsPath);
			model.setVideoUrlHD(videoPath);
			model.setVideoUrl(videoPath);//处理前高清流畅使用同一个文件
			
			try{
				model.setDescription(EmojiUtils.encodeEmoji(new String(contentSubmit.getDescription().getBytes("UTF-8"))));//替换成<e></e>格式
				//编码Emoji
			}catch(Exception e){
				model.setDescription(contentSubmit.getDescription());
			}
			redisContentService.insertContent(model);
			result.setCode(200);
			result.setData(model.getCid());
			result.setMessage("上传成功！");
			result.setTime(System.currentTimeMillis());
			
			ContentBean bean = contentSubmit.asContentBean();
			bean.setThumbnailsUrl(thumbnailsPath);
			bean.setVideoUrl(videoPath);
			bean.setVideoUrlHD(videoPath);
			bean.setCid(model.getCid());
			bean.setUid(uid);
			bean.setKey(model.getKey());
			bean.setDescription(EmojiUtils.encodeEmoji(bean.getDescription()));//替换成<e></e>格式
			try{
				redisJMSMessageService.insertAddContentMessageQueue(bean);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.CONTENT_ADD));
			}catch(Exception e){
				logger.error("发送NoticeMessage.MessageType.CONTENT_ADD出错！"+e.getMessage()+","+e);
			}
			return result;
		}
	}
	
	
	@RequestMapping(value = "/content/detail", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result getContentDetail(
			@RequestParam(value = "cid", required = true, defaultValue = "0") long cid,
			@RequestParam("token") String token) {
		Result result = new Result();
		ActionModel action = new ActionModel();
		action.setAction(Action.CREATE.getMark());
		action.setCid(cid);
		action.setUid(0);
		ActionForm actionForm = redisTimelineService.getActionForm(action, super.getUid(token), 0,false);
		result.setCode(200);
		result.setData(actionForm);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	@RequestMapping(value = "/content/isdelete", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result isContentDelete(
			@RequestParam(value = "cid", required = true, defaultValue = "0") long cid,
			@RequestParam("token") String token) {
		Result result = new Result();
		ContentModel content = redisContentService.getContent(cid);
		if (content == null || content.getcStatus() != 0 || content.getCid() == 0){
			result.setCode(DuanquErrorCode.CONTENT_DELETE.getCode());
			result.setData(0);
			result.setMessage(DuanquErrorCode.CONTENT_DELETE.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(200);
			result.setData(0);
			result.setMessage("视频正常！");
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/content/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result deleteContent(
			@RequestParam(value = "cid", required = true, defaultValue = "0") long cid,
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		ContentModel content = redisContentService.getContent(cid);
		if (content == null || content.getCid() == 0){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData(0);
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		if (content.getUid() != uid){//判断是否是自己的内容
			result.setCode(DuanquErrorCode.RIGHT_ERROR.getCode());
			result.setData(0);
			result.setMessage(DuanquErrorCode.RIGHT_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		redisContentService.updateContentStatus(cid, Status.AUTHOR_DELETE.getMark());
		redisContentService.deleteContentFromList(uid, cid);
		try{
			NoticeMessage message = new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.CONTENT_DELETE);
			message.setObjId(cid);
			duanquPublisher.publish(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		result.setCode(200);
		result.setData("");
		result.setMessage("删除成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	@RequestMapping(value = "/content/new/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result loadNewContentList(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		int start = (page - 1) * PAGE_SIZE;
		int end = page * PAGE_SIZE - 1;
		List<ActionForm> contents = redisContentService.listContents(uid, start, end);
		result.setCode(200);
		result.setData(contents);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		result.setPages((redisContentService.countContentList() - 1)/PAGE_SIZE + 1);
		return result;
	}
	
	
	@RequestMapping(value = "/content/find/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result loadFindContentList(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		int start = (page - 1) * PAGE_SIZE;
		int end = page * PAGE_SIZE - 1;
		List<ActionForm> contents = redisContentService.loadFindContentList(uid, start, end);
		result.setCode(200);
		result.setData(contents);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		result.setPages((redisContentService.countFindContent() - 1)/PAGE_SIZE + 1);
		return result;
	}
	
	/**
	 * 分享
	 * @param cid
	 * @param openType
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/content/share", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result shareContent(@RequestParam("cid") long cid,
			@RequestParam(value="memo",required=false)String memo,
			@RequestParam("openType") int openType,
			@RequestParam("token") String token) {
		Result result = new Result();
		UserModel user = getUser(token);
		if (user != null && user.getStatus() == 0){
			result = new Result();
			result.setCode(DuanquErrorCode.USER_FORBID.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.USER_FORBID.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		long uid = user.getUid();
		ShareBean bean = new ShareBean();
		ContentModel content = redisContentService.getContent(cid);
		if (content != null && content.getcStatus() == 0){
			bean.setCid(cid);
			bean.setOpenType(openType);
			bean.setUid(uid);
			bean.setMemo(memo);
			//redisContentService.insertShareContentList(uid,cid);
			try{
				redisJMSMessageService.insertShareQueue(bean);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.SHARE));
			}catch(Exception e){
				logger.error("发送NoticeMessage.MessageType.SHARE出错！"+e.getMessage()+","+e);
			}
			result.setCode(200);
			result.setData("");
			result.setMessage("分享成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.CONTENT_DELETE.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.CONTENT_DELETE.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
	
	
	/**
	 * AT内容
	 * @param cid
	 * @param openType
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/content/at", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result atContent(@RequestParam("cid") long cid,
			@RequestParam(value="atDuanquUsers",required=false,defaultValue="")String atDuanquUsers,
			@RequestParam(value="atSinaUsers",required=false,defaultValue="")String atSinaUsers,
			@RequestParam(value="atTencentUsers",required=false,defaultValue="")String atTencentUsers,
			@RequestParam("token") String token) {
		Result result = new Result();
		UserModel user = getUser(token);
		if (user != null && user.getStatus() == 0){
			result = new Result();
			result.setCode(DuanquErrorCode.USER_FORBID.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.USER_FORBID.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		long uid = user.getUid();
		
		ContentModel content = redisContentService.getContent(cid);
		if (content == null || content.getCid() == 0){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData(0);
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		if (content.getIsPrivate() != 0 ){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData(0);
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage()+"私密内容不能At");
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		redisTimelineService.insertUserAtList(uid, cid);
		
		try{
			AtMessageBean bean = new AtMessageBean();
			bean.setAtDuanquUsers(atDuanquUsers);
			bean.setAtSinaUsers(atSinaUsers);
			bean.setAtTencentUsers(atTencentUsers);
			bean.setCid(cid);
			bean.setUid(uid);
			redisJMSMessageService.insertAtQueue(bean);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.AT_MESSAGE));
		}catch(Exception e){
			logger.error("发送NoticeMessage.MessageType.AT_MESSAGEE出错！"+e.getMessage()+","+e);
		}
		result.setCode(200);
		result.setData("");
		result.setMessage("AT成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	/**
	 * 举报
	 * @param cid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/content/report", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result reportContent(@RequestParam("cid") long cid,
			@RequestParam("token") String token) {
		UserModel user = this.getUser(token);
		Result result = new Result();
		if (user != null && user.getStatus() == 0){
			result = new Result();
			result.setCode(DuanquErrorCode.USER_FORBID.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.USER_FORBID.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		ReportModel report = new ReportModel();
		report.setCid(cid);
		report.setCreateTime(System.currentTimeMillis());
		report.setIsCheck(0);
		report.setUid(getUid(token));
		try{
			redisJMSMessageService.insertReportQueue(report);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.REPORT));
		}catch(Exception e){
			logger.error("发送NoticeMessage.MessageType.REPORT出错！"+e.getMessage()+","+e);
		}
		result.setCode(200);
		result.setData("");
		result.setMessage("举报成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}

}
