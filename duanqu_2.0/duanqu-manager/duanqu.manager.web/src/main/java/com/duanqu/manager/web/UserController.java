package com.duanqu.manager.web;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.Flags.Flag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.EditorTalentInfoModel;
import com.duanqu.common.model.EditorTalentModel;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.UserRecommendModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.submit.MFeedBackSubmit;
import com.duanqu.common.submit.MMessageSubmit;
import com.duanqu.common.submit.MPushHistorySubmit;
import com.duanqu.common.submit.MUserSubmit;
import com.duanqu.common.submit.TalentSubmit;
import com.duanqu.manager.service.editor.IManagerEditorService;
import com.duanqu.manager.service.user.IManagerUserService;
import com.duanqu.manager.submit.ManagerMessageSubmit;
import com.duanqu.manager.submit.ManagerMjUserSubmit;
import com.duanqu.manager.submit.ManagerUserSubmit;

/**
 * @author xunboda2
 *
 */
@Controller
@RequestMapping("user.do")
public class UserController {

	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private IManagerEditorService managerEditorService;
	
	@RequestMapping(params = "method=list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("user");
		managerUserService.queryUserList(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=tsList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView tsList(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("user_push");
		managerUserService.queryTsUserList(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		return mav;
	}
	@RequestMapping(params = "method=enterRecommended", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterRecommended(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("uid") String uid,@RequestParam("type") int type) {
		ModelAndView mav = new ModelAndView("recommended");
		mav.addObject("uid", uid);
		mav.addObject("type", type);
		return mav;
	}

	
	
	

	@RequestMapping(params = "method=tsfriend", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView tsfriend(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("user");
		String message = "";
		try {
			managerUserService.insertUserreCommend(mUserSubmit);
			message = "推荐成功";
		} catch (Exception e) {
			message = "推荐失败";
			e.printStackTrace();
		}
		managerUserService.queryUserList(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	
	@RequestMapping(params = "method=deleteUserRecommend", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView deleteUserRecommend(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit,@RequestParam("type") int type) {
		ModelAndView mav = new ModelAndView("user_push");
		String message = "";
		try {
			managerUserService.deleteUserRecommend(mUserSubmit,type);
			message = "取消成功";
		} catch (Exception e) {
			message = "取消失败";
			e.printStackTrace();
		}
		managerUserService.queryTsUserList(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		mav.addObject("message", message);
		return mav;
	}
	

	@RequestMapping(params = "method=tstalent", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView tstalent(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("talentlistdetail");
		List<EditorTalentInfoModel> list = managerEditorService
				.queryEditorTalentInfoModels();
		mav.addObject("listTalentInfo", list);
		mav.addObject("mUserSubmit", mUserSubmit);
		return mav;
	}

	@RequestMapping(params = "method=saveTalent", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView saveTalent(HttpServletRequest request,
			HttpServletResponse response, TalentSubmit talentSubmit) {
		ModelAndView mav = new ModelAndView("talentlistdetail");
		List<Integer> list = talentSubmit.getUidList();
		EditorTalentModel editorTalentModel = new EditorTalentModel();
		editorTalentModel.setComment(talentSubmit.getComment());
		editorTalentModel.setInfoId(talentSubmit.getTalentid());
		String message = "";
		try {
			for (Iterator<Integer> iterator = list.iterator(); iterator
					.hasNext();) {
				editorTalentModel.setUid(iterator.next());
				editorTalentModel.setCreateTime(System.currentTimeMillis());
				managerEditorService.insertEditorTalent(editorTalentModel);
			}
			message = "success";
		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";
		}
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mUserSubmit
	 * @return 进入发送私信界面
	 */
	@RequestMapping(params = "method=enterMessage", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterMessage(HttpServletRequest request,
			HttpServletResponse response,
			ManagerMessageSubmit managerMessageSubmit) {
		ModelAndView mav = new ModelAndView("message");
		mav.addObject("managerMessageSubmit", managerMessageSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=enterMessagePush", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterMessagePush(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("message_push");
		return mav;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * 进入用户认证界面
	 */
	@RequestMapping(params = "method=enterAuthentication", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterAuthentication(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uid") long uid,@RequestParam("flag") int flag) {
		ModelAndView mav = new ModelAndView("authentication");
		mav.addObject("uid", uid);
		mav.addObject("flag", flag);
		return mav;
	}
	
	@RequestMapping(params = "method=updateAuthentication", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateAuthentication(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uid") long uid) {
		ModelAndView mav = new ModelAndView("authentication_update");
		mav.addObject("uid", uid);
		mav.addObject("messageText", managerUserService.getAuthenticationReason(uid));
		return mav;
	}
	

	/**
	 * @param request
	 * @param response
	 * @param managerMessageSubmit
	 * @return 发送私信，群发
	 */
	@RequestMapping(params = "method=saveMessage", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveMessage(HttpServletRequest request,
			HttpServletResponse response,
			ManagerMessageSubmit managerMessageSubmit) {
		ModelAndView mav = new ModelAndView("message");
		String message = "";
		try {
			managerUserService.insertMessage(managerMessageSubmit);
			message = "发送成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "发送失败";
		}
		mav.addObject("message", message);
		mav.addObject("managerMessageSubmit", managerMessageSubmit);
		return mav;
	}
	@RequestMapping(params = "method=initIndex", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void initIndex(){
		managerUserService.buildIndex();
		
	}
	
	

	/**
	 * @param request
	 * @param response
	 * @param uid
	 * @return 进入用户禁言页面
	 */
	@RequestMapping(params = "method=enterUserJy", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterUserJy(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("uid") long uid) {
		ModelAndView mav = new ModelAndView("user_jy");
		mav.addObject("uid", uid);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param managerUserSubmit
	 * @return 保存用户禁言信息
	 */
	@RequestMapping(params = "method=saveUserJy", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView saveUserJy(HttpServletRequest request,
			HttpServletResponse response, ManagerUserSubmit managerUserSubmit) {
		ModelAndView mav = new ModelAndView("user_jy");
		String message = "";
		try {
			managerUserService.updateUserJy(managerUserSubmit);
			message = "保存成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存失败";
		}
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mUserSubmit
	 * @return
	 * @throws Exception
	 *             手动接触用户禁言
	 */
	@RequestMapping(params = "method=updateUserNormal", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateUserNormal(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit)
			throws Exception {
		ModelAndView mav = new ModelAndView("user");
		String message = "";
		try {
			managerUserService.updateUserNormal(Long.parseLong(mUserSubmit
					.getUid()));
			message = "解除成功";
		} catch (Exception e) {
			message = "解除失败";
			e.printStackTrace();
		}
		managerUserService.queryUserList(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mUserSubmit
	 * @return 查询马甲用户控制方法
	 */
	@RequestMapping(params = "method=mjlist", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView mjlist(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("user-mj");
		managerUserService.queryMjUserList(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @return 添加马甲跳转
	 */
	@RequestMapping(params = "method=enterAddMj", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView enterAddMj(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("mj-add");
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param userModel
	 * @return 保存马甲
	 */
	@RequestMapping(params = "method=saveMj", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView saveMj(HttpServletRequest request,
			HttpServletResponse response,
			ManagerMjUserSubmit managerMjUserSubmit) {
		ModelAndView mav = new ModelAndView("mj-add");
		String message = "";
		try {
			managerUserService.insertMjUser(managerMjUserSubmit);
			message = "添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "添加失败";
		}
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return查询短趣君下的评论数
	 */
	@RequestMapping(params = "method=dqjCommentList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView dqjCommentList(HttpServletRequest request,
			HttpServletResponse response, MContentSubmit mContentSubmit) {
		ModelAndView mav = new ModelAndView("view-comment-dqj");
		managerUserService.queryDqjCommentList(mContentSubmit);
		// managerUserService.updateComment(mUserSubmit);
		mav.addObject("vo", mContentSubmit);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param commentModel
	 * @param page
	 * @return进入短趣君的评论回复界面
	 */
	@RequestMapping(params = "method=enterCommentDqj", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterCommentDqj(HttpServletRequest request,
			HttpServletResponse response, CommentModel commentModel) {
		ModelAndView mav = new ModelAndView("comment-add-dqj");
		mav.addObject("commentModel", commentModel);
		return mav;
	}

	@RequestMapping(params = "method=saveCommentDqj", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveCommentDqj(HttpServletRequest request,
			HttpServletResponse response, CommentModel commentModel) {
		ModelAndView mav = new ModelAndView("view-comment-dqj");
		String message = "";
		try {
			managerUserService.insertReplyComment(commentModel);
			message = "回复成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "回复失败";
		}
		MContentSubmit mContentSubmit = new MContentSubmit();
		mContentSubmit.setCid(commentModel.getCid() + "");
		managerUserService.queryDqjCommentList(mContentSubmit);
		mav.addObject("commentModel", commentModel);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("message", message);
		return mav;
	}

	@RequestMapping(params = "method=deleteCommentDqj", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView deleteCommentDqj(HttpServletRequest request,
			HttpServletResponse response, CommentModel commentModel,
			MContentSubmit mContentSubmit) {
		ModelAndView mav = new ModelAndView("view-comment-dqj");
		String message = "";
		try {
			managerUserService.deleteContentComment(commentModel);
			message = "删除成功";
		} catch (Exception e) {
			message = "删除失败";
			e.printStackTrace();
		}

		managerUserService.queryDqjCommentList(mContentSubmit);
		mav.addObject("commentModel", commentModel);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mUserSubmit
	 * @return 查询马甲下新评论列表
	 */
	@RequestMapping(params = "method=mjCommentList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView mjCommentList(HttpServletRequest request,
			HttpServletResponse response, MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("view-comment");
		managerUserService.queryMjCommentList(mUserSubmit);
		// managerUserService.updateComment(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param commentModel
	 * @param page
	 * @return 进入评论页面,马甲的评论界面，跟其他分开，因页面中有不同元素
	 */
	@RequestMapping(params = "method=enterComment", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterComment(HttpServletRequest request,
			HttpServletResponse response, CommentModel commentModel) {
		ModelAndView mav = new ModelAndView("comment-add2");

		// managerUserService.insertReplyComment(commentModel);
		mav.addObject("commentModel", commentModel);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param commentModel
	 * @param page
	 * @return 保存评论
	 */
	@RequestMapping(params = "method=saveComment", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView saveComment(HttpServletRequest request,
			HttpServletResponse response, CommentModel commentModel) {
		ModelAndView mav = new ModelAndView("view-comment");
		String message = "";
		try {
			managerUserService.insertReplyComment(commentModel);
			message = "回复成功";
		} catch (Exception e) {
			message = "回复失败";
			e.printStackTrace();
		}

		MUserSubmit mUserSubmit = new MUserSubmit();
		mUserSubmit.setUid(commentModel.getUid() + "");
		managerUserService.queryMjCommentList(mUserSubmit);
		mav.addObject("commentModel", commentModel);
		mav.addObject("vo", mUserSubmit);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param commentModel
	 * @param mUserSubmit
	 * @return 删除评论
	 */
	@RequestMapping(params = "method=deleteComment", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView deleteComment(HttpServletRequest request,
			HttpServletResponse response, CommentModel commentModel,
			MUserSubmit mUserSubmit) {
		ModelAndView mav = new ModelAndView("view-comment");
		String message = "";
		try {
			managerUserService.deleteContentComment(commentModel);
			message = "删除成功";
		} catch (Exception e) {
			message = "删除失败";
			e.printStackTrace();
		}

		managerUserService.queryMjCommentList(mUserSubmit);
		mav.addObject("commentModel", commentModel);
		mav.addObject("vo", mUserSubmit);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param uid
	 * @return 更新评论状态
	 */
	@RequestMapping(params = "method=updateComment", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView updateComment(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("uid") long uid) {
		ModelAndView mav = new ModelAndView("view-comment");
		String message = "";
		try {
			managerUserService.updateComment(uid);
			message = "评论更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "评论更新失败";
		}
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mMessageSubmit
	 * @return 短趣君私信list
	 * @throws Exception 
	 */
	@RequestMapping(params = "method=messageList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView messageList(HttpServletRequest request,
			HttpServletResponse response, MMessageSubmit mMessageSubmit) throws Exception {
		ModelAndView mav = new ModelAndView("messageList");
		managerUserService.queryMessageList(mMessageSubmit);
		mav.addObject("vo", mMessageSubmit);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param managerMessageSubmit
	 * @return 删除私信
	 * @throws Exception 
	 * @throws  
	 */
	@RequestMapping(params = "method=deleteMessage", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView deleteMessage(HttpServletRequest request,
			HttpServletResponse response,
			ManagerMessageSubmit managerMessageSubmit,
			MMessageSubmit mMessageSubmit) throws Exception  {
		ModelAndView mav = new ModelAndView("messageList");
		String message = "";
		try {
			managerUserService.deleteMessage(managerMessageSubmit);
			message = "删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败";
		}
		managerUserService.queryMessageList(mMessageSubmit);
		mav.addObject("vo", mMessageSubmit);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * @param request
	 * @param response
	 * @param mFeedBackSubmit
	 * @return 查询反馈列表
	 */
	@RequestMapping(params = "method=feedBackList", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView feedBackList(HttpServletRequest request,
			HttpServletResponse response, MFeedBackSubmit mFeedBackSubmit) {
		ModelAndView mav = new ModelAndView("feedback");
		managerUserService.queryFeedBackList(mFeedBackSubmit);
		mav.addObject("vo", mFeedBackSubmit);
		return mav;
	}

	@RequestMapping(params = "method=deleteFeedBack", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView deleteFeedBack(HttpServletRequest request,
			HttpServletResponse response, MFeedBackSubmit mFeedBackSubmit) {
		ModelAndView mav = new ModelAndView("feedback");
		String message = "";
		try {
			FeedBackModel feedBackModel = new FeedBackModel();
			feedBackModel.setId(Integer.parseInt(mFeedBackSubmit.getId()));
			feedBackModel.setIsCheck(2);
			managerUserService.updateFeedBack(feedBackModel);
			message = "删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败";
		}
		managerUserService.queryFeedBackList(mFeedBackSubmit);
		mav.addObject("vo", mFeedBackSubmit);
		mav.addObject("message", message);
		return mav;
	}
	@RequestMapping(params = "method=enterMj", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView enterMj(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("num") long num,@RequestParam("type") int type) {
		ModelAndView mav=new ModelAndView("user-content-mj");
		mav.addObject("mjCount", managerUserService.queryUserMj(num, type));
		mav.addObject("num", num);
		mav.addObject("type", type);
		return mav;
	}
	@RequestMapping(params = "method=messageDetail", method = {
			RequestMethod.GET, RequestMethod.POST })
	public ModelAndView messageDetail(HttpServletRequest request,
			HttpServletResponse response,MessageModel messageModel) {
		ModelAndView mav=new ModelAndView("message-detail");
	    mav.addObject("detailList", managerUserService.queryMessageDetail(messageModel));
	    mav.addObject("uid",managerUserService.updateMessageIsOld(messageModel)); 
		return mav;
	}
	
	@RequestMapping(params = "method=setList_user", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView setList_user(HttpServletRequest request,HttpServletResponse response,@RequestParam("uid") long uid){
		ModelAndView mav=new ModelAndView("setList_user");
		mav.addObject("setUserModel", managerUserService.getUserModel(uid));
		mav.addObject("uid", uid);
		return mav;
	}
	
	
	@RequestMapping(params = "method=tsHotList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView tsHotList(HttpServletRequest request,HttpServletResponse response,MUserSubmit mUserSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("user_push_hot");
		managerUserService.queryTsHotUser(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		
		return mav;
	}
	
	@RequestMapping(params = "method=deleteHotUser", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteHotUser(HttpServletRequest request,HttpServletResponse response,MUserSubmit mUserSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("user_push_hot");
		String message="";
		try {
            managerUserService.deleteTsHotUser(Long.parseLong(mUserSubmit.getUid()));
			message="取消成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="取消失败";
		}
		managerUserService.queryTsHotUser(mUserSubmit);
		mav.addObject("vo", mUserSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	
	/**
	 * 推送历史列表
	 * @param request
	 * @param response
	 * @param mPushHistorySubmit
	 * @return
	 */
	@RequestMapping(params = "method=pushMessageHistory", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView pushMessageHistory(HttpServletRequest request,HttpServletResponse response, MPushHistorySubmit mPushHistorySubmit )  {
		ModelAndView mav=new ModelAndView("message_push_history");
		managerUserService.queryPushMessageHistoryList(mPushHistorySubmit);
		mav.addObject("vo", mPushHistorySubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=deletePushMessage", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deletePushMessage(HttpServletRequest request,HttpServletResponse response, MPushHistorySubmit mPushHistorySubmit )  {
		ModelAndView mav=new ModelAndView("message_push_history");
		String message="";
		try {
			managerUserService.deletePushMessage(mPushHistorySubmit);
			message="删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="删除失败";
		}
		managerUserService.queryPushMessageHistoryList(mPushHistorySubmit);
		mav.addObject("vo", mPushHistorySubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(params = "method=editRecommendReason", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView editRecommendReason(HttpServletRequest request,HttpServletResponse response,UserRecommendModel userRecommendModel ) throws Exception{
		ModelAndView mav=new ModelAndView("edit_recommend_reason");
		mav.addObject("reason", managerUserService.getRecommendReason(userRecommendModel));
		mav.addObject("userRecommendModel", userRecommendModel);
		return mav;
	}
}
