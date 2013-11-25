package com.duanqu.redis.service.user;

import java.util.List;
import java.util.Set;

import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.SettingModel;
import com.duanqu.common.model.UserModel;

public interface IRedisUserService {
	
	/**
	 * Redis中插入user HashMap
	 * @param user
	 */
	public void insertUser(UserModel userModel);
	
	/**
	 * 更新用户Token
	 * @param uid
	 * @param token
	 */
	public void updateDuanquToken(long uid,String token);
	
	
	
	
	/**
	 * 通过用户Id从缓存取出用户信息
	 * @param userid
	 * @return
	 */
	public UserModel getUser(long uid);
	
	/**
	 * 通过手机号码取出用户信息
	 * @param mobile
	 * @return
	 */
	public UserModel getUserByMobile(String mobile);
	
	/**
	 * 第三方平台登陆取用户信息
	 * @param openType
	 * @param openUid
	 * @return
	 */
	public UserModel getUser(int openType,String openUid);
	
	/**
	 * 通过Email取得用户信息
	 * @param email
	 * @return
	 */
	public UserModel getUser(String email);
	
	/**
	 * 通过Token取得用户ID
	 * @param token
	 * @return
	 */
	public long getUid(String token);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public void updateBanner(String banner,long uid);
	
	/**
	 * 更新用户状态
	 * @param uid
	 * @param status
	 */
	public void updateUserStatus(long uid,int status);
	
	
	/**
	 * 插入绑定信息
	 * @param model
	 */
	public void bindUserInfo(BindModel model);
	
	/**
	 * 取得绑定信息
	 * @param uid //短趣用户ID
	 * @param openType //开放平台类型
	 * @return
	 */
	public BindModel getBindInfo(long uid,int openType);
	
	/**
	 * 获取用户设置信息
	 * @param uid
	 * @return
	 */
	public SettingModel getUserSetting(long uid);

	/**
	 * 取得所有用户
	 */
	public List<UserModel> loadAllUser();
	
	public void update(UserModel model);
	
	public void bindMobile(long uid,String mobile);
	
	/**
	 * 判断手机号码是否已经判定
	 * @param mobile
	 * @return
	 */
	public boolean mobileIsBind(String mobile);
	
	/**
	 * 添加设置
	 * @param uid
	 * @param setting
	 */
	public void setUserSetting(long uid,SettingModel setting);
	
	/**
	 * 添加设备号
	 * @param uid
	 * @param deviceToken
	 */
	public void addUserDeviceToken(long uid,String deviceToken);
	
	/**
	 * 删除设备号
	 * @param uid
	 */
	public void deleteUserDeviceToken(long uid);
	
	/**
	 * 取得设备号
	 * @param uid
	 * @return
	 */
	public Set<String> getUserDeviceToken(long uid);
	
	/**
	 * 是否是名人
	 * @param uid
	 * @return
	 */
	public boolean isFamous(long uid);
	
	/**
	 * 获取用户全局ID
	 * @return
	 */
	public long getUserId();
	
	/**
	 * 统计每天用户喜欢同步数
	 * @param uid
	 * @return
	 */
	public int countLikeSynNumOneDay(long uid);
	
	/**
	 * 记录每天用户喜欢同步数
	 * @param uid
	 */
	public void addLikeSynMumOneDay(long uid);
	
	/**
	 * 通过用户昵称取出用户ID
	 * @param nickName
	 * @return
	 */
	public UserModel getUserByNickName(String nickName);
	
	/**
	 * 添加用户昵称和用户Id对应关系
	 * @param uid
	 */
	public void addNickName(String nickName,long uid);
	
	/**
	 * 取出所有设置信息
	 * @return
	 */
	public List<SettingModel> loadAllSettings();
	
	/**
	 * 更新用户达人信息
	 * @param uid 用户ID
	 * @param isTalent	是否是认证达人（0：否，1：是）
	 * @param talentDesc	认证描述
	 */
	public void updateUserTalentInfo(long uid,int isTalent,String talentDesc);
	
	/**
	 * @param uid 用户ID
	 * @param time最后一次访问时间
	 * @param function访问的功能点
	 */
	public void updateUserLastVist(long uid,long time,String function);
	
}
