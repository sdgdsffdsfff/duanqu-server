package com.duanqu.manager.service.user;


import java.util.List;
import java.util.Map;

import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.SetContentModel;
import com.duanqu.common.model.SetUserModel;
import com.duanqu.common.model.UserAdminModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.model.UserRecommendModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.submit.MFeedBackSubmit;
import com.duanqu.common.submit.MMessageSubmit;
import com.duanqu.common.submit.MPushHistorySubmit;
import com.duanqu.common.submit.MUserSubmit;
import com.duanqu.manager.submit.ManagerMessageSubmit;
import com.duanqu.manager.submit.ManagerMjUserSubmit;
import com.duanqu.manager.submit.ManagerUserSubmit;

public interface IManagerUserService {
	UserAdminModel checkUserAdimin(UserAdminModel userAdminModel);//检查用户是否合法
	void queryUserList(MUserSubmit mUserSubmit);
	void insertUserreCommend(MUserSubmit mUserSubmit);//插入编辑推存好友
	void inserHotUserList();//插入热门用户
	//void insertOpenFriend(long uid,List<OpenFriend> list);//第三方登录获取第三方我关注列表并插入数据库;
	void updateThfriend(Map<String,Object> map);//调用存储过程匹配好友
	void getUserFriendList();//定时获取第3放平台的好友，并插入到数据库
	void updateUser();//定时匹配用户并把匹配成功的数据同步到Redis//王海华加
	void getUserModeisList();//定时匹配手机，获取刚刚匹配好的数据更新到缓存
	void insertMessage(ManagerMessageSubmit managerMessageSubmit);//私信群发私信
	void updateUserJy(ManagerUserSubmit managerUserSubmit);//用户禁言
	void updateUserNormal(long uid);//手动解除用户禁言
	void updateUserNormalAll();//通过定时器自动解除禁言时间到期的用户
	
	/**
	 * @param mUserSubmit
	 * 查询马甲用户列表
	 */
	void queryMjUserList(MUserSubmit mUserSubmit);
	
	/**
	 * @param mUserSubmit
	 * 查询马甲下的新评论数
	 */
	void queryMjCommentList(MUserSubmit mUserSubmit);
	
	/**
	 * @param managerMjUserSubmit
	 * 增加马甲用户
	 */
	void insertMjUser(ManagerMjUserSubmit managerMjUserSubmit);
	
	/**
	 * @param commentModel
	 * 回复评论
	 */
	void insertReplyComment(CommentModel commentModel);
	
	/**
	 * @param commentModel
	 * 删除评论
	 */
	void deleteContentComment(CommentModel commentModel);
	
	/**
	 * @param uid
	 * 把新评论更新为旧评论
	 */
	void updateComment(long uid);
	
	
	/**
	 * @param mMessageSubmit
	 * 查询短趣君私信列表
	 * @throws Exception 
	 */
	void queryMessageList(MMessageSubmit mMessageSubmit) throws Exception;
	
	
	/**
	 * @param managerMessageSubmit
	 * 删除私信
	 */
	void deleteMessage(ManagerMessageSubmit managerMessageSubmit);
	
	
	/**
	 * @param mFeedBackSubmit
	 * 查询反馈列表
	 */
	void queryFeedBackList(MFeedBackSubmit mFeedBackSubmit);
	
	
	void updateFeedBack(FeedBackModel feedBackModel);
	
	/**
	 * @param mContentSubmit
	 * 查询短趣君用户列表
	 */
	void queryDqjCommentList(MContentSubmit mContentSubmit);
	
	void updateCommentDqj(long cid);
	
	/**
	 * @param uid
	 * 添加假粉丝数
	 */
	String insertFalseFriend(long uid,int addNum);
	
	/**
	 * 定时匹配用户并把匹配成功的数据同步到Redis，批量操作
	 */
	void duanquUpdateUserAll();
	
	
	/**
	 * @param mUserSubmit
	 * 查询推荐用户列表
	 */
	void queryTsUserList(MUserSubmit mUserSubmit);
	
	/**
	 * @param mUserSubmit
	 * 取消用户推荐
	 */
	void deleteUserRecommend(MUserSubmit mUserSubmit,int type);
	
	/**
	 * @param num
	 * @param type
	 * @return查询可用马甲数量
	 */
	int queryUserMj(long num,int type);
	
	/**
	 * @param messageModel
	 * @return
	 * 查询私信对话详情
	 */
	List<Map<String, Object>> queryMessageDetail(MessageModel messageModel);
	long updateMessageIsOld(MessageModel messageModel);
	
	/**
	 * 在私信详情界面回复私信
	 */
	Map<String, Object> insertMessageDetail(long uid,String messageText);
	
	void updateUserStatus(long uid,int status);
	
	SetUserModel getUserModel(long uid);
	
    void insertSetUser(long uid,int order_num);
	
	List<SetUserModel> queryUserModels();
	
	void queryTsHotUser(MUserSubmit mUserSubmit);
	
	void deleteTsHotUser(long uid);
	
	/**群发推送
	 * @param type
	 * @param tslx
	 * @param messageText
	 * @param innerParam
	 * @param createTime
	 */
	void insertMessagePush(String type,int tslx,String messageText,String innerParam,String createTime);
	
	/**
	 * 定时器扫描定时推送信息
	 */
	void duanquMessagePushFromDb();
	
	/**用户认证
	 * @param uid
	 * @param messageText
	 */
	void updateUserAuthentication(long uid,int flag,String messageText);
	
	/**
	 * 添加索引
	 */
	void buildIndex();
	
	
	/**查询推送历史列表
	 * @param mPushHistorySubmit
	 */
	void queryPushMessageHistoryList(MPushHistorySubmit mPushHistorySubmit);
	
	/**删除推送信息
	 * @param mPushHistorySubmit
	 */
	void deletePushMessage(MPushHistorySubmit mPushHistorySubmit);
	
	
	/**获取认证理由
	 * @param uid
	 * @return
	 */
	String getAuthenticationReason(long uid);
	
	/**修改认证理由
	 * @param uid
	 * @param messageText
	 */
	void updateAuthenticationReason(long uid,String messageText);
	
	/**
	 * 获取推荐理由
	 * @param userRecommendModel
	 * @return
	 */
	String getRecommendReason(UserRecommendModel userRecommendModel);
	
	/**
	 * 修改推荐理由
	 * @param userRecommendModel
	 */
	void updateRecommendReason(UserRecommendModel userRecommendModel);


}
