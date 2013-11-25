package com.duanqu.upload.web;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquErrorCode;
import com.duanqu.common.DuanquSecurity;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.EmojiUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.bean.ContentBean;
import com.duanqu.common.model.ActionModel.Action;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.submit.ContentSubmit;
import com.duanqu.common.vo.ActionForm;
import com.duanqu.common.vo.ContentForm;
import com.duanqu.common.vo.SimpleUserForm;
import com.duanqu.redis.pubsub.IRedisPublisher;
import com.duanqu.redis.service.badword.IBadwordService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.jms.IRedisJMSMessageService;
import com.duanqu.redis.service.user.IRedisUserService;

/**
 * 内容相关
 * 
 * @author wanghaihua
 * 
 */
@Controller
public class UploadController extends BaseController {

	@Resource
	IRedisUserService redisUserService;
	
	@Resource
	IRedisContentService redisContentService;
	
	@Resource(name="duanquPublisher")
	IRedisPublisher duanquPublisher;
	@Resource
	IBadwordService badwordService;
	@Resource
	IRedisJMSMessageService redisJMSMessageService;
	
	@RequestMapping(value = "/content/upload", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result uploadContent(@ModelAttribute ContentSubmit contentSubmit,@RequestParam("token")String token) {
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
			String md5 ="";
			try{
				//DuanquUtils.writeFile(video, contentSubmit.getVideo().getBytes()); // 写本地文件
				videoPath = AliyunUploadUtils.uploadHDVideo(contentSubmit.getVideo()
						.getInputStream(),
						contentSubmit.getVideo().getBytes().length,
						contentSubmit.getVideo().getContentType());
				//取文件的MD5值
				md5 = DuanquSecurity.getHash(contentSubmit.getVideo().getInputStream());
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
			model.setMd5(md5);
			model.setKey(DuanquUtils.md5(System.currentTimeMillis()+""));
			model.setUid(uid);
			model.setThumbnailsUrl(thumbnailsPath);
			model.setVideoUrlHD(videoPath);
			model.setVideoUrl(videoPath);//处理前高清流畅使用同一个文件
			
			model.setOriginalDesc(contentSubmit.getDescription());
			try{
				model.setDescription(EmojiUtils.encodeEmoji(new String(contentSubmit.getDescription().getBytes("UTF-8"))));//替换成<e></e>格式
				//编码Emoji
			}catch(Exception e){
				model.setDescription(contentSubmit.getDescription());
			}
			model.setDescription(badwordService.filterBadword(contentSubmit.getDescription()));
			redisContentService.insertContent(model);
			
			ActionForm action = new ActionForm();
			ContentForm form = model.asContentFormForClient();
			form.setUser(user.asSimpleUserForm());
			form.setLike(false);
			form.setForward(false);
			form.setOptUsers(new ArrayList<SimpleUserForm>());
			action.setContent(form);
			action.setActionType(Action.CREATE.getMark());
			action.setActionUser(user.asSimpleUserForm());
			action.setTime(System.currentTimeMillis());
			
			result.setCode(200);
			result.setData(action);
			result.setMessage("上传成功！");
			result.setTime(System.currentTimeMillis());
			
			ContentBean bean = contentSubmit.asContentBean();
			bean.setThumbnailsUrl(thumbnailsPath);
			bean.setVideoUrl(videoPath);
			bean.setVideoUrlHD(videoPath);
			bean.setCid(model.getCid());
			bean.setUid(uid);
			bean.setKey(model.getKey());
			bean.setMd5(model.getMd5());
			bean.setDescription(EmojiUtils.encodeEmoji(bean.getDescription()));//替换成<e></e>格式
			try{
				redisJMSMessageService.insertAddContentMessageQueue(bean);
				redisJMSMessageService.insertConvertMessageQueue(bean);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.CONTENT_ADD));
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.CONVERT2GIF));
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
	}
	
	
	@RequestMapping(value = "/user/upload", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result uploadUserIntroduction(@ModelAttribute ContentSubmit contentSubmit,@RequestParam("token")String token) {
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
			String videoPath = null;
			try{
				videoPath = AliyunUploadUtils.uploadUserVideo(contentSubmit.getVideo().getInputStream(),
						contentSubmit.getVideo().getBytes().length,
						contentSubmit.getVideo().getContentType());
			}catch(Exception e){
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData(0);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage()+"上传自我介绍视频错误！"+e.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
			String videoFaceUrl = "";
			try{
				videoFaceUrl = AliyunUploadUtils.uploadUserVideoFace(
						contentSubmit.getThumbnails().getInputStream(),
						contentSubmit.getThumbnails().getBytes().length,
						contentSubmit.getThumbnails().getContentType());
			}catch(Exception e){
				result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
				result.setData(0);
				result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage()+"上传自我介绍封面错误！"+e.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
			user.setVideoFaceUrl(videoFaceUrl);
			user.setVideoUrl(videoPath);
			redisUserService.update(user);
			result.setCode(200);
			result.setData("");
			result.setMessage("上传成功！");
			
			if (result.getCode() == 200){
				//发送同步信息
				redisJMSMessageService.insertUserEditQueue(user);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.USER_EDIT));
			}
			
			return result;
		}
	}
	
	@RequestMapping(value = "/content/upload/test", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result deleteContent() {
		Result result = new Result();
		result.setCode(200);
		result.setData("");
		result.setMessage("test");
		result.setTime(System.currentTimeMillis());
		return result;
	}

}
