package com.duanqu.manager.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;

import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.common.model.UserRecommendModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.manager.service.content.IManagerContentService;
import com.duanqu.manager.service.dwr.IManagerDwrService;
import com.duanqu.manager.service.tag.IManagerTagService;
import com.duanqu.manager.service.user.IManagerUserService;
import com.duanqu.manager.submit.ManagerMessageSubmit;
import com.duanqu.manager.submit.ManagerUserSubmit;

@Controller
@RemoteProxy(name = "DemoController")
public class DemoController {

	@Resource
	private IManagerDwrService managerDwrService;
	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private IManagerTagService managerTagService;
	@Resource
	private IManagerContentService managerContentService;

	@RemoteMethod
	public Map<String, Object> checkIsPrivate(String cid,int type) {
		return managerDwrService.checkContentIsPrivate(cid,type);
	}
	
	@RemoteMethod
	public boolean checkUserIsRecommend(String uid,int type) {
		return managerDwrService.insertUserIsRecommend(uid,type);
	}
	@RemoteMethod
	public String insertRecommended(String uid,int type,String reason) {
		String message="";
		try {
			managerDwrService.insertRecommended(uid, type, reason);
			message="推荐成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="推荐失败";
		}
		return message;
	}

	/*@RemoteMethod
	public String inserContentLike(long cid) {
		return managerDwrService.insertLikeContent(cid);
	}*/

	@RemoteMethod
	public String updateFeedBack(int id) {
		String message = "";
		FeedBackModel feedBackModel = new FeedBackModel();
		feedBackModel.setId(id);
		feedBackModel.setIsCheck(1);
		try {
			managerUserService.updateFeedBack(feedBackModel);
			message = "处理成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "处理失败";
		}
		return message;
	}

	/**
	 * @param cid
	 * @return 更新短趣君评论
	 */
	@RemoteMethod
	public String updateCommentDqj(long cid) {
		String message = "";
		try {
			managerUserService.updateCommentDqj(cid);
			message = "评论更新成功,请关闭当前页面";
		} catch (Exception e) {
			e.printStackTrace();
			message = "评论更新失败";
		}
		return message;
	}

	@RemoteMethod
	public String updateComment(long uid) {
		String message = "";
		try {
			managerUserService.updateComment(uid);
			message = "评论更新成功,请关闭当前页面";
		} catch (Exception e) {
			e.printStackTrace();
			message = "评论更新失败";
		}
		return message;
	}

	@RemoteMethod
	public Map<String, Object> saveTag(long tid, long cid, String tagText,
			String type) {
		String message = "";
		Map<String, Object> map = new HashMap<String, Object>();
		TagModel tagModel = new TagModel();
		tagModel.setTagText(tagText);
		tagModel.setTid(tid);
		try {
			map = managerTagService.updateTagModel(tagModel, cid);
			message = "success";
		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";
		}
		map.put("message", message);
		map.put("tagText", tagText);
		map.put("type", type);
		return map;
	}

	@RemoteMethod
	public String saveTsrecommend(String tslx, String type, int recommend,
			String cid, String tssjStr) {
		String message = "";
		MContentSubmit ms = new MContentSubmit();
		ms.setCid(cid);
		ms.setType(type);
		ms.setRecommend(recommend);
		ms.setTssjStr(tssjStr);
		ms.setTslx(tslx);

		try {
			managerContentService.insertRecommendModel(ms);
			message = "推送成功";
		} catch (Exception e) {
			message = "推送失败";
			e.printStackTrace();
		}
		return message;
	}

	@RemoteMethod
	public String saveComment(long cid, String commentText) {
		CommentModel commentModel = new CommentModel();
		commentModel.setCid(cid);
		commentModel.setCommentText(commentText);
		String message = managerContentService.insertComment(commentModel);
		return message;
	}

	@RemoteMethod
	public Map<String, Object> updateContentDescription(long cid, String desc) {
		String message = "";
		ContentModel cModel = new ContentModel();
		Map<String, Object> map = new HashMap<String, Object>();
		cModel.setCid(cid);
		cModel.setDescription(desc);
		try {
			map = managerContentService.updateContentDescription(cModel);
			message = "succes";
		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";
		}
		map.put("message", message);
		return map;
	}

	@RemoteMethod
	public String tsHost(String tid) {
		String message = "";

		try {
			managerTagService.insertTagHot(tid);
			message = "推送成功";
		} catch (Exception e) {
			message = "推送失败";
			e.printStackTrace();
		}
		return message;
	}

	@RemoteMethod
	public String saveMessage(String uid, int fslx, int qflx, long messageId,
			int id, String messageText) {
		String message = "";
		try {
			ManagerMessageSubmit managerMessageSubmit = new ManagerMessageSubmit();
			managerMessageSubmit.setFslx(fslx);
			managerMessageSubmit.setUid(uid);
			managerMessageSubmit.setQflx(qflx);
			managerMessageSubmit.setId(id);
			managerMessageSubmit.setMessageId(messageId);
			managerMessageSubmit.setMessageText(messageText);
			managerUserService.insertMessage(managerMessageSubmit);
			message = "发送成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "发送失败";
		}
		return message;
	}
	@RemoteMethod
	public String saveMessagePush(String type, int tslx,String messageText,String innerParam,
			String createTime) {
		String message = "";
		try {
			managerUserService.insertMessagePush(type, tslx, messageText, innerParam, createTime);
			message = "发送成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "发送失败";
		}
		return message;
	}
	@RemoteMethod
	public Map<String, Object> saveAuthentication(long uid,int flag,String messageText) {
		Map<String, Object> map=new HashMap<String, Object>();
		String message="";
		boolean isTrue=true;
		try {
			
			managerUserService.updateUserAuthentication(uid,flag, messageText);
			if(flag==1){
				message="认证成功";
			}else{
				message="取消认证成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(flag==1){
				message="认证失败";
			}else{
				message="取消认证失败";
			}
			isTrue=false;
		}
		map.put("message", message);
		map.put("isTrue", isTrue);
		return map;
	}
	@RemoteMethod
	public String updateAuthentication(long uid,String messageText){
		String message="";
		try {
			managerUserService.updateAuthenticationReason(uid, messageText);
			message="修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="修改失败";
		}
		return message;
		
	}
	
	@RemoteMethod
	public String updateRecommendReason(long uid,int type,String reason){
		String message="";
		try {
			UserRecommendModel userRecommendModel=new UserRecommendModel();
			userRecommendModel.setReason(reason);
			userRecommendModel.setType(type);
			userRecommendModel.setUid(uid);
			userRecommendModel.setCreate_time(System.currentTimeMillis());
			managerUserService.updateRecommendReason(userRecommendModel);
			message="修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="修改失败";
		}
		return message;
	}
	

	@RemoteMethod
	public String saveUserJy(long uid, String banText, String banEndtimeStr) {
		String message = "";
		try {
			ManagerUserSubmit managerUserSubmit = new ManagerUserSubmit();
			managerUserSubmit.setBanEndtimeStr(banEndtimeStr);
			managerUserSubmit.setUid(uid);
			managerUserSubmit.setBanText(banText);
			managerUserService.updateUserJy(managerUserSubmit);
			message="禁言成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="禁言失败";
		}
		return message;
	}
	@RemoteMethod
	public String updateUserNormal(long uid){
		String message="";
		try {
			message="解除成功";
			managerUserService.updateUserNormal(uid);
		} catch (Exception e) {
			e.printStackTrace();
			message="解除失败";
		}
		return message;
	}
	@RemoteMethod
	public String updateContentSinaQuan(long cid,int type,int num){
		String message="";
		try {
			message="修改成功";
			managerContentService.updateContentSinaQuanNum(cid, type, num);
		} catch (Exception e) {
			message="修改失败";
			e.printStackTrace();
		}
		return message;
	}
	/*@RemoteMethod
	public String insertFalseFans(long uid){
		return managerUserService.insertFalseFriend(uid);
	}*/
	
	@RemoteMethod
	public Map<String, Object> updateLikeJfss(long num,int type,int addNum){
		String message="";
		boolean flag=false;
		if(type==1){
		message=managerDwrService.insertLikeContent(num, addNum);
		}else{
		message=managerUserService.insertFalseFriend(num, addNum);	
		}
		Map<String, Object> map=new HashMap<String, Object>();
		if("喜欢成功".equals(message) || "增加成功".equals(message)){
			flag=true;
		}
		map.put("flag", flag);
		map.put("message", message);
		return map;
	}
	@RemoteMethod
	public String deleteMessage(long id,long uid){
		String message="";
		ManagerMessageSubmit managerMessageSubmit=new ManagerMessageSubmit();
		managerMessageSubmit.setMessageId(id);
		managerMessageSubmit.setUid(uid+"");
		try {
			managerUserService.deleteMessage(managerMessageSubmit);
			message="删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="删除失败";
		}
		return message;
	}
	@RemoteMethod
	public Map<String, Object> insertMessageDetail(long uid,String messageText){
		String message="";
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=managerUserService.insertMessageDetail(uid, messageText);
			message="回复成功";
		} catch (Exception e) {
			message="回复失败";
		}
		map.put("message", message);
		return map;
	}
	@RemoteMethod
	public Map<String, Object> updateUserStatus(long uid,int status){
		String message="";
		Map<String, Object> map=new HashMap<String, Object>();
		boolean flag=true;
		try {
			managerUserService.updateUserStatus(uid, status);
			if(status==2){
				message="限制成功";
			}else{
				message="回复成功";
			}
		} catch (Exception e) {
			if(status==2){
				message="限制失败";
			}else{
				message="回复失败";
			}
			flag=false;
		}
		map.put("flag", flag);
		map.put("message", message);
		return map;
		
		
	}
	@RemoteMethod
	public Map<String, Object> mask(long cid,int sStatus){
		String message="";
		boolean flag=true;
		Map<String, Object> map=new HashMap<String, Object>();
		ContentModel contentModel=new ContentModel();
		contentModel.setCid(cid);
		contentModel.setcStatus(sStatus);
		try {
			managerContentService.updateContent(contentModel);
			if(sStatus==3){
				message="屏蔽成功";
			}else if(sStatus==4){
				message="限制排行";
			}else{
				message="恢复正常";
			}
		} catch (Exception e) {
			if(sStatus==3){
				message="屏蔽失败";
			}else if(sStatus==4){
				message="限制失败";
			}else{
				message="恢复失败";
			}
			flag=false;
			e.printStackTrace();
		}
		map.put("flag", flag);
		map.put("message", message);
		return map;
	}
	@RemoteMethod
	public String setList(long cid,int order_num,int type){
		String message="";
		try {
			managerContentService.insertSetContent(cid, order_num,type);
			message="设置成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="设置失败";
		}
		return message;
	}
	@RemoteMethod
	public String setList_user(long uid,int order_num){
		String message="";
		try {
			managerUserService.insertSetUser(uid, order_num);
			message="设置成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="设置失败";
		}
		return message;
	}
	
	@RemoteMethod
	public void buildIndex(){
		managerUserService.buildIndex();
	}
	@RemoteMethod
	public String saveForward(long uid,String cid){
		return managerContentService.saveForward(uid, cid);
	}
}
