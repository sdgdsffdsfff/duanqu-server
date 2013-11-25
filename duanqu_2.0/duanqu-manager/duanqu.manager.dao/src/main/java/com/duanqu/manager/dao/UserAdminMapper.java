package com.duanqu.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.FriendModel;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.MessagePushModel;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.SetUserModel;
import com.duanqu.common.model.UserAdminModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.model.UserRecommendModel;
import com.duanqu.common.submit.MFeedBackSubmit;
import com.duanqu.common.submit.MMessageSubmit;
import com.duanqu.common.submit.MPushHistorySubmit;
import com.duanqu.common.submit.MUserSubmit;
import com.duanqu.manager.submit.ManagerUserForm;
import com.duanqu.manager.submit.ManagerUserSubmit;

public interface UserAdminMapper {
	UserAdminModel selectUserAdminModel(UserAdminModel userAdminModel);
	List<ManagerUserForm> queryUserList(MUserSubmit mUserSubmit);//第三方平台
	long queryUserListCount(MUserSubmit mUserSubmit);
	
	List<Map<String, Object>> queryUserListM(MUserSubmit mUserSubmit);//关联手机
	long queryUserListMCount(MUserSubmit mUserSubmit);
	
	void insertUserreCommend(UserRecommendModel userRecommendModel);//插入编辑推存好友
	
	List<Long> selectHotUserList(long time);//查询热门用户列表
	
	List<BindModel> queryBindModelList();//获取绑定表里的用户信息
	void inserOpenFriend(OpenFriend openFriend);//插入第三方平台好友
	void updateThfriend(Map<String,Object> map);//调用存储过程匹配好友 map 中方的参数类型 key="p_exectype" value=1：程序调用；2：作业调用
    //key="p_functype" 第三方平台类型 0:第三方平台，1：手机号码  key="p_uid"  用户id  
	OpenFriend selectOpenFriend(OpenFriend openFriend);//查询第三方平台好友是不是已经存在账号关系表
	List<OpenFriend> queryOpenFriends();//获取最新匹配的数据
	void updateOpenFriend();//取出最新匹配的数据后，更新匹配成功标志为旧数据
	
	void insertUserMessage(MessageModel messageModel);
	void updateUserJy(ManagerUserSubmit managerUserSubmit);//用户禁言
	void updateUserNormal(long uid);//手动解除用户禁言
	void updateUserNormalAll(long endTime);//通过定时器自动解除禁言时间到期的用户
	
	/**
	 * @return
	 * 查询禁言用户列表
	 */
	List<Long> queryUserJyList(long endTime);
	
	
	/**
	 * @param mUserSubmit
	 * @return
	 * 查询马甲用户列表
	 */
	List<ManagerUserForm> queryMjUserList(MUserSubmit mUserSubmit);
	
	/**
	 * @param mUserSubmit
	 * @return
	 * 统计马甲总数
	 */
	long queryMjUserListCount(MUserSubmit mUserSubmit);
	
	/**
	 * @param uid
	 * @return
	 * 通过uid查询用户实体
	 */
	UserModel getUserModelByUid(long uid);
	
	/**
	 * @param userModel
	 * 插入用户基本信息表
	 */
	void insertUserInfo(UserModel userModel);
	
	/**
	 * @return
	 * 获取绑定手机号的用户列表
	 */
	List<Long> queryMobilesList();
	
	/**
	 * @param uid
	 * @return
	 *获取存储过程执行后，刚刚匹配成功的数据
	 */
	List<OpenFriend> queryMobiles();
	/**
	 * @param uid
	 * 取出最新匹配的数据后，更新匹配成功标志为旧数据
	 */
	void updateMobile();
	
	/**
	 * @param id
	 * 更新该马甲用户id下的所有新评论为旧评论
	 */
	void updateComment(long id);
	
	
	/**
	 * @param mMessageSubmit
	 * 获取跟短趣君有关的私信列表
	 */
	List<Map<String, Object>> queryMessageList(MMessageSubmit mMessageSubmit);
	
	/**
	 * @param mMessageSubmit
	 * @return
	 * 统计跟短趣君有关的私信总数
	 */
	long queryMessageListCount(MMessageSubmit mMessageSubmit);
	
	/**
	 * @param messageId
	 * 更新该条私信为已回复状态
	 */
	void updateMessage(long messageId);
	
	
	/**
	 * @param managerMessageSubmit
	 * 发送者删除私信
	 */
	void deleteMessage(long messageId);
	
	
	/**
	 * @param managerMessageSubmit
	 * 接受者删除私信
	 */
	void deleteMessageRec(long messageId);
	
	/**
	 * @return
	 * 获取用户列表
	 */
	List<UserModel> queryUserModels(Map<String, Object> map);
	
	/**
	 * @return
	 * 获取用户列表总数
	 */
	long queryUserModelsCount();
	
	
	/**
	 * @param mFeedBackSubmit
	 * @return
	 * 获取反馈列表,参数无意义防止以后加查询条件
	 */
	List<Map<String, Object>> queryFeedBackModels(MFeedBackSubmit mFeedBackSubmit);
	
	/**
	 * @return
	 * 获取反馈列表总数,参数无意义防止以后加查询条件
	 */
	long queryFeedBackModelsCount(MFeedBackSubmit mFeedBackSubmit);
	
	
	/**
	 * @param feedBackModel
	 * 更新反馈内容状态
	 */
	void updateFeedBackModel(FeedBackModel feedBackModel);
	
	/**
	 * @param cid
	 * 更新短趣君某一条内容下的所有评论为旧评论
	 */
	void updateCommentDqj(long cid);
	
	UserRecommendModel getUserRecommend(long uid);
	
	void updateUserRecommend(UserRecommendModel userRecommendModel);
	
    void insertSendAllMessage(MessageModel messageModel);
    
    /**
     * @param mUserSubmit
     * @return
     * 更加内容数进行排序查询
     */
    List<ManagerUserForm> queryUserListNrs(MUserSubmit mUserSubmit);
    
    
    
    
    /**
     * @param friendModel
     * 添加假粉丝
     */
    void insertFalseFriend(FriendModel friendModel);
    
    /**
	 * @param map
	 * 更新关注数
	 */
	void updateUserGzs(Map<String, Object> map);
	
	/**
	 * @param map
	 * 更新假粉丝数
	 */
	void updateUserJfss(Map<String, Object> map);
	
	/**
	 * @param map
	 * 更新好友数
	 */
	void updateUserHys(Map<String, Object> map);
	
	/**
	 * @return
	 * 添加假粉丝时获取的可用马甲列表
	 */
	List<Long> queryMjByJfs(long uid);
	
	/**
	 * @param uid
	 * @return
	 * 查询随机取的马甲跟当前用户关系
	 */
	FriendModel getFriendModel(FriendModel friendModel);
	
	void updateIsFriend(FriendModel friendModel);
	
	
	/**
	 * @param mUserSubmit
	 * @return
	 * 查询推荐用户列表
	 */
	List<Map<String, Object>> queryTsUserList(MUserSubmit mUserSubmit);
	
	/**
	 * @param mUserSubmit
	 * @return
	 * 统计列表总数
	 */
	long queryTsUserListCount(MUserSubmit mUserSubmit);
	
	void deleteUserRecommend(Map<String, Object> map);
	
	/**
	 * @param messageModel
	 * @return
	 * 查询对话详情
	 */
	List<Map<String, Object>> queryMessageDetail(MessageModel messageModel);
	
	/**
	 * @param messageModel
	 * 更新私信为旧私信
	 */
	void updateMessageIsOld(MessageModel messageModel);
	
	/**
	 * 统一更新用户的好友数，关注数，粉丝数
	 * @param uid
	 */
	void updateUserRelationshipCount(@Param("uid") long uid,
			@Param("fansCount") int fansCount, @Param("followsCount") int followsCount,
			@Param("friendsCount") int friendsCount);
	
	/**
	 * @param userModel
	 * 限制取消限制用户排行
	 */
	void  updateUserStatus(UserModel userModel);
	
	
	
	/**
	 * @param setUserModel
	 * 设置排行位置
	 */
	void insertSetUser(SetUserModel setUserModel);
	
	/**
	 * @param setUserModel
	 * 位置更新
	 */
	void updateSetUser(SetUserModel setUserModel);
	
	/**
	 * @param uid
	 * @return
	 * 获取推送热门对象
	 */
	SetUserModel getSetUserModel(long uid);
	
	/**
	 * @return
	 * 获取推送热门的列表
	 */
	List<SetUserModel> querySetUserModels();
	
	/**
	 * @return
	 * 获取推送热门的列表
	 */
	List<Map<String, Object>> queryTsHotUserList(MUserSubmit mUserSubmit);
	
	/**
	 * @return
	 * 获取推送热门的总数
	 */
	long queryTsHotUserListCount(MUserSubmit mUserSubmit);
	
	/**
	 * @param cid
	 * 取消热门推送
	 */
	void deleteTsHotUser(long uid);
	
	List<Long> getList();
	
	void updateUserFans(Map<String, Object> map);
	
	/**
	 * @param uid
	 * @return
	 * 获取用户的关注数
	 */
	List<Long> selectFollows(long uid);
	
	/**
	 * @param uid
	 * @return
	 * 获取用户的粉丝数
	 */
	List<Long> selectFans(long uid);
	//群发私信分页取用户列表
	List<Long> getUserList(Map<String, Object> map);
	
	//测试用
	void insertTmp(Map<String, Object> map);
	
	
	/**
	 * 插入推送信息
	 * @param messagePushModel
	 */
	void insertMessagePush(MessagePushModel messagePushModel);
	
	/**
	 * 定时器扫描待推送的信息
	 * @param createTime
	 * @return
	 */
	List<MessagePushModel> queryMessagePushList(long createTime);
	
	/**信息推送后，更新其状态
	 * @param id
	 */
	void updateMessagePush(int id);
	
	/**用户认证后更新用户认证状态
	 * @param map
	 */
	void updateUserAuthentication(Map<String, Object> map);
	
	/**推送历史记录
	 * @param mPushHistory
	 * @return
	 */
	List<MessagePushModel> queryMessagePushHistoryList(MPushHistorySubmit mPushHistorySubmit);
	
	/**推送历史总数
	 * @param mPushHistory
	 * @return
	 */
	long queryMessagePushHistoryListCount(MPushHistorySubmit mPushHistorySubmit);
	
	/**删除推送信息
	 * @param mPushHistory
	 */
	void deleteMessagePush(MPushHistorySubmit mPushHistorySubmit);
	
	/**
	 * 获取认证理由
	 * @param uid
	 * @return
	 */
	String getAuthenticationReason(long uid);
	
	
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
