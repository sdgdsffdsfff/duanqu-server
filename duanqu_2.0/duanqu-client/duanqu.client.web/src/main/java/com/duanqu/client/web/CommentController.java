package com.duanqu.client.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanqu.common.DuanquErrorCode;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.submit.CommentSubmit;
import com.duanqu.common.vo.CommentForm;
import com.duanqu.redis.service.badword.IBadwordService;
import com.duanqu.redis.service.comment.IRedisCommentService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.user.IRedisRelationshipService;

@Controller
public class CommentController extends BaseController {
	private static int COMMENT_PAGE_SIZE = 10;
	@Resource
	IRedisCommentService redisCommentService;
	@Resource
	IRedisContentService redisContentService;
	@Resource
	IBadwordService badwordService;
	@Resource
	IRedisRelationshipService redisRelationshipService;
	
	@RequestMapping(value = "/comment/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result addComment(@ModelAttribute CommentSubmit comment,
			@RequestParam("token") String token) {
		long uid = getUid(token);
		Result result = this.valid(comment, uid);
		if (result != null){
			return result;
		}
		if (comment.getReplyUid() > 0 && comment.getParentId() > 0
				&& comment.getRootId() == 0) {
			CommentModel parentComment = redisCommentService.getComment(comment.getParentId());
			if (parentComment != null && parentComment.getId() > 0){
				if (parentComment.getParentId() == 0){
					comment.setRootId(parentComment.getId());
				}else{
					comment.setRootId(parentComment.getRootId());
				}
				
			}
		}
		result = new Result();
		CommentModel model = comment.asCommentModel();
		model.setUid(uid);
		redisCommentService.addComment(model);
		result.setCode(200);
		result.setData(model.getId());
		result.setMessage("评论成功！");
		result.setTime(System.currentTimeMillis());
		
		if (result.getCode() == 200){
			try{
				redisJMSMessageService.insertCommentMessageQueue(model);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.COMMENT));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Result valid(CommentSubmit comment,long uid){
		Result result = null;
		if (uid == 0){
			result = new Result();
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("token值不对");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		UserModel curUser = redisUserService.getUser(uid);
		if (curUser!= null && curUser.getStatus() == 0){//被禁言
			result = new Result();
			result.setCode(DuanquErrorCode.USER_FORBID.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.USER_FORBID.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		if (comment.getReplyUid() == uid){
			result = new Result();
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("自己不能回复自己");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		//父评论ID和回复用户ID都为0 或者 都不为0
		if (comment.getParentId() * comment.getReplyUid() == 0
				&& comment.getParentId() + comment.getReplyUid() > 0) {
			result = new Result();
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("回复评论，父评论ID和回复用户ID都不能为0。");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		if (!StringUtils.hasText(comment.getCommentText())){
			result = new Result();
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("评论内容不能为空。");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		// 敏感词
		String badword = badwordService.hasBadWord(comment.getCommentText());
		if (badword != null){
			result = new Result();
			result.setCode(DuanquErrorCode.SENSITIVE.getCode());
			result.setData(badword);
			result.setMessage("评论内容"+DuanquErrorCode.SENSITIVE.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		//内容ID是否存在
		ContentModel content = redisContentService.getContent(comment.getCid());
		if (content == null || content.getCid() == 0){
			result = new Result();
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("对应的内容不存在或者已经被删除！");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		if (content != null && content.getcStatus() != 0){
			result = new Result();
			result.setCode(DuanquErrorCode.CONTENT_DELETE.getCode());
			result.setData("对应的内容不存在或者已经被删除！");
			result.setMessage(DuanquErrorCode.CONTENT_DELETE.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		if (comment.getParentId()>0){
			CommentModel model = redisCommentService.getComment(comment.getParentId());
			if (model.getCid() != comment.getCid()){
				result = new Result();
				result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
				result.setData("上传的内容ID和父评论对应的ID不对应！");
				result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
			if (model.getUid() != comment.getReplyUid()){
				result = new Result();
				result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
				result.setData("对应的回复用户错误！");
				result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
		}
		//回复用户是否存在
		if (comment.getReplyUid() > 0){
			UserModel user = redisUserService.getUser(comment.getReplyUid());
			if (user == null || user.getUid() == 0){
				result = new Result();
				result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
				result.setData("回复用户不存在或已经删除！");
				result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
				return result;
			}
		}
		//评论黑名单验证
		boolean bool = redisRelationshipService.isBlackUser(content.getUid(), uid);
		if (bool){
			result = new Result();
			result.setCode(DuanquErrorCode.BLACKUSER.getCode());
			result.setData("黑名单用户无法评论！");
			result.setMessage(DuanquErrorCode.BLACKUSER.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		//回复黑名单验证
		boolean isBlack = redisRelationshipService.isBlackUser(comment.getReplyUid(), uid);
		if (isBlack){
			result = new Result();
			result.setCode(DuanquErrorCode.BLACKUSER.getCode());
			result.setData("黑名单用户无法回复！");
			result.setMessage(DuanquErrorCode.BLACKUSER.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		return result;
	}
	
	@RequestMapping(value = "/comment/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result loadComments(@RequestParam("cid")long cid,
			@RequestParam(value="page",required=false,defaultValue="1")int page,
			@RequestParam("token") String token) {
		Result result =new Result();
		List<CommentForm> comments = redisCommentService.loadComments(cid, page, COMMENT_PAGE_SIZE);
		int count = redisCommentService.countComments(cid);
		result.setCode(200);
		result.setData(comments);
		result.setPages((count - 1) / COMMENT_PAGE_SIZE + 1);
		result.setMessage("评论成功！");
		result.setTime(System.currentTimeMillis());
		result.setTotal(count);
		return result;
	}
	
	@RequestMapping(value = "/comment/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result deleteComment(@RequestParam("id")long id,
			@RequestParam("token") String token) {
		Result result =new Result();
		long uid = getUid(token);
		if (uid == 0){
			result.setCode(DuanquErrorCode.NO_LOGIN.getCode());
			result.setData("token值不对");
			result.setMessage(DuanquErrorCode.NO_LOGIN.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		CommentModel comment = redisCommentService.getComment(id);
		if (comment == null || comment.getId() == 0){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("删除的评论不存在！id="+id);
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			ContentModel content = redisContentService.getContent(comment.getCid());
			
			if ((content != null && content.getUid() == uid) || comment.getUid() == uid){
//				int count = redisCommentService.deleteComment(comment);
				result.setCode(200);
				result.setData(1);
				result.setMessage("删除成功！");
				result.setTime(System.currentTimeMillis());
			}else{
				result.setCode(DuanquErrorCode.RIGHT_ERROR.getCode());
				result.setData("不是自己的评论不能删除。uid"+uid);
				result.setMessage(DuanquErrorCode.RIGHT_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		}
		
		if (result.getCode() == 200){
			try{
				NoticeMessage message = new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.DELETE_COMMENT);
				message.setObjId(id);
				duanquPublisher.publish(message);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
}
