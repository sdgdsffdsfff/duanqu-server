package com.duanqu.client.web;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquConfig;
import com.duanqu.common.DuanquErrorCode;
import com.duanqu.common.DuanquSecurity;
import com.duanqu.common.DuanquSendSmsUtil;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.EmojiUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.bean.MobileMessageBean;
import com.duanqu.common.model.BindModel;
import com.duanqu.common.model.BindModel.OpenType;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.service.ProducerService;
import com.duanqu.common.submit.BindSubmit;
import com.duanqu.common.submit.UserSubmit;
import com.duanqu.common.vo.BindForm;
import com.duanqu.common.vo.LoginForm;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;

@Controller
public class LoginController extends BaseController {
	
	Log logger = LogFactory.getLog(LoginController.class);
	@Resource
	IRedisUserService redisUserService;
	@Resource
	IRedisRelationshipService redisRelationshipService;
	//ProducerService producerService;
	
	
	/**
	 * 第三方平台登录，下一步邮箱绑定
	 * @param openType
	 * @param openUid
	 * @return
	 */
	@RequestMapping(value = "/user/open/login", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result openLogin(
			@RequestParam(value = "openType", required = true, defaultValue = "0") int openType,
			@RequestParam(value = "openUid", required = true, defaultValue = "") String openUid,
			@RequestParam(value = "accessToken" ,required = true)String accessToken,
			@RequestParam(value = "refreshToken" ,required = true)String refreshToken,
			@RequestParam(value = "expiresDate" ,required = true)String expiresDate) {
		
		UserModel model = redisUserService.getUser(openType, openUid);
		Result result = new Result();
		if (model != null && model.getUid() > 0){
			//更新accessToken
			BindModel newBindModel = new BindModel();
			newBindModel.setAccessToken(accessToken);
			newBindModel.setCreatetime(System.currentTimeMillis());
			newBindModel.setExpiresDate(expiresDate);
			newBindModel.setOpenType(openType);
			newBindModel.setOpenUid(openUid);
			newBindModel.setUid(model.getUid());
			newBindModel.setRefreshToken(refreshToken);
			redisUserService.bindUserInfo(newBindModel);
			LoginForm loginForm = new LoginForm();
			loginForm.setBindForms(this.loadBindForms(model.getUid()));
			loginForm.setUser(model.asUserForm());
			
			result.setCode(200);
			result.setData(loginForm);
			result.setMessage("登陆成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.USER_NOT_EXIST.getCode());
			result.setData("该用户不存在！");
			result.setMessage(DuanquErrorCode.USER_NOT_EXIST.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
	
	/**
	 * 短趣登录
	 * @param email
	 * @param loginPassword
	 * @return
	 */
	@RequestMapping(value = "/user/duanqu/login", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result openLogin(
			@RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "loginPassword", required = true, defaultValue = "") String loginPassword) {
		
		Result result = new Result();
		UserModel model = redisUserService.getUser(email);
		if(model != null && model.getUid()>0){
			String encodePassword = DuanquSecurity.encodePassword(loginPassword);
			if (!StringUtils.isBlank(model.getLoginPassward()) && model.getLoginPassward().equalsIgnoreCase(encodePassword)){
				LoginForm loginForm = new LoginForm();
				loginForm.setBindForms(this.loadBindForms(model.getUid()));
				loginForm.setUser(model.asUserForm());
				//登录成功
				result.setCode(200);
				result.setData(loginForm);
				result.setMessage("登录成功！");
				result.setTime(System.currentTimeMillis());
			}else{
				//登录失败
				result.setCode(DuanquErrorCode.LOGIN_ERROR.getCode());
				result.setData("密码不正确");
				result.setMessage(DuanquErrorCode.LOGIN_ERROR.getMessage());
				result.setTime(System.currentTimeMillis());
			}
		}else{
			result.setCode(DuanquErrorCode.USER_NOT_EXIST.getCode());
			result.setData("用户不存在");
			result.setMessage(DuanquErrorCode.USER_NOT_EXIST.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		return result;
	}
	
	
	/**
	 * 第三方平台直接登录
	 * @return
	 */
	@RequestMapping(value = "/user/open/direct/login", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result openDirectLogin(@ModelAttribute UserSubmit submit) {
		
		Result result = new Result();
		UserModel user = null;
		//判断该平台是否已经绑定
		user = redisUserService.getUser(submit.getOpenType(), submit.getOpenUid());
		if (user != null && user.getUid() > 0){
			UserModel model = submit.asUserModel();
			model.setUid(user.getUid());
			model.setNickName(user.getNickName());
			model.setAvatarUrl(user.getAvatarUrl());
			String newToken = DuanquSecurity.encodeToken(submit.getEmail());
			model.setToken(newToken);
			//更新短趣Token
			redisUserService.updateDuanquToken(user.getUid(),newToken);
			//更新Token 
			BindModel newBindModel = new BindModel();
			newBindModel.setAccessToken(submit.getAccessToken());
			newBindModel.setCreatetime(System.currentTimeMillis());
			newBindModel.setExpiresDate(submit.getExpiresDate());
			newBindModel.setOpenType(submit.getOpenType());
			newBindModel.setOpenUid(submit.getOpenUid());
			newBindModel.setUid(model.getUid());
			newBindModel.setRefreshToken(submit.getRefreshToken());
			if (StringUtils.isEmpty(submit.getNickName())){
				newBindModel.setOpenNickName(user.getNickName());
			}else{
				newBindModel.setOpenNickName(EmojiUtils.filterEmoji(submit.getNickName()));
			}
			redisUserService.bindUserInfo(newBindModel);
			//更新 deviceToken
			if (submit.getDeviceToken() != null && submit.getDeviceToken().trim().length() == 64){
				redisUserService.addUserDeviceToken(user.getUid(), submit.getDeviceToken());
			}else{
				logger.error("DeviceToken 为空！"+submit.getDeviceToken());
			}
			LoginForm loginForm = new LoginForm();
			loginForm.setBindForms(this.loadBindForms(model.getUid()));
			loginForm.setUser(model.asUserForm());
			
			result.setCode(200);
			result.setData(loginForm);
			result.setMessage("登陆成功！");
			result.setTime(System.currentTimeMillis());
			
			if (result.getCode() == 200){
				//发送消息
				redisJMSMessageService.insertUserLoginQueue(newBindModel);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.LOGIN));
			}
			return result;
		}
		
		//更新用户昵称成不为“趣拍”，趣拍官方
		String oldNickName = submit.getNickName();
		if (oldNickName != null && oldNickName.contains("趣拍")){
			submit.setNickName(oldNickName.replaceAll("趣拍", ""));
			if (submit.getNickName().trim().length() < 2){
				submit.setNickName(submit.getNickName()+DuanquUtils.getRamdCode());
			}
		}
		
		if (StringUtils.isBlank(oldNickName)){
			submit.setNickName(DuanquUtils.getRamdCode());
		}
		
		
		UserModel model = submit.asUserModel();
		if (submit.getAvatar() != null && submit.getAvatar().getSize() >0){
			//直接上传阿里云OSS
			try {
				//UploadUtils.uploadAvatar(folder+avatarName, submit.getAvatar().getBytes());
				MultipartFile avatar = submit.getAvatar();
				String key = AliyunUploadUtils.uploadAvatar(avatar.getInputStream(), avatar.getBytes().length, avatar.getContentType());
				model.setAvatarUrl(key);
			} catch (Exception e) {
				model.setAvatarUrl(DuanquConfig.getDefaultAvatar());//上传失败用默认头像替代
			}
		}else{
			model.setAvatarUrl(DuanquConfig.getDefaultAvatar());//上传失败用默认头像替代
		}
		model.setToken(DuanquSecurity.encodeToken(submit.getEmail()));
		model.setStatus(1);//正常
		model.setRoleId(3);//普通用户
		redisUserService.insertUser(model);
		//添加手机设备号
		if (!StringUtils.isEmpty(submit.getDeviceToken())){
			redisUserService.addUserDeviceToken(model.getUid(), submit.getDeviceToken());
		}else{
			logger.error("DeviceToken 为空！"+submit.getDeviceToken());
		}
		
		BindModel bind = submit.asBindModel();
		bind.setUid(model.getUid());
		redisUserService.bindUserInfo(bind);
		LoginForm loginForm = new LoginForm();
		loginForm.setBindForms(this.loadBindForms(model.getUid()));
		loginForm.setUser(model.asUserForm());
		loginForm.setIsNew(1);//新用户
		if (model != null && model.getUid() > 0){
			result.setCode(200);
			result.setData(loginForm);
			result.setMessage("绑定成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
			result.setData(null);
			result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		
		if (result.getCode() == 200){
			//发送消息
			redisJMSMessageService.insertNewUserMessageQueue(model);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.USER_ADD));
			//producerService.sendMessage(model);
		}
		
		return result;
	}
	
	
	/**
	 * 第三方平台绑定邮箱
	 * @return
	 */
	@RequestMapping(value = "/user/open/bind", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result openBind(@ModelAttribute UserSubmit submit) {
		
		Result result = new Result();
		UserModel user = null;
		//判断该平台是否已经绑定
		user = redisUserService.getUser(submit.getOpenType(), submit.getOpenUid());
		if (user != null && user.getUid() > 0){
			result.setCode(DuanquErrorCode.ACCOUNT_EXIST.getCode());
			result.setData("可以尝试退出重新登录！");
			result.setMessage(DuanquErrorCode.ACCOUNT_EXIST.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		if (StringUtils.isBlank(submit.getEmail())) {
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("邮箱不能为空！");
			result.setMessage(DuanquErrorCode.PARAMETER_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		//判断邮箱是否被绑定
		user = redisUserService.getUser(submit.getEmail().trim().toLowerCase());
		if (user != null && user.getUid() > 0){ 
			result.setCode(DuanquErrorCode.EMAIL_USED.getCode());
			result.setData("邮箱已经被注册！");
			result.setMessage(DuanquErrorCode.EMAIL_USED.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}
		
		UserModel model = submit.asUserModel();
		if (submit.getAvatar() != null && submit.getAvatar().getSize() >0){
			//直接上传upyun
			try {
				//UploadUtils.uploadAvatar(folder+avatarName, submit.getAvatar().getBytes());
				MultipartFile avatar = submit.getAvatar();
				String key = AliyunUploadUtils.uploadAvatar(avatar.getInputStream(), avatar.getBytes().length, avatar.getContentType());
				model.setAvatarUrl(key);
			} catch (Exception e) {
				model.setAvatarUrl(DuanquConfig.getDefaultAvatar());//上传失败用默认头像替代
			}
		}else{
			model.setAvatarUrl(DuanquConfig.getDefaultAvatar());//上传失败用默认头像替代
		}
		model.setToken(DuanquSecurity.encodeToken(submit.getEmail()));
		redisUserService.insertUser(model);
		//添加手机设备号
		redisUserService.addUserDeviceToken(model.getUid(), submit.getDeviceToken());
		BindModel bind = submit.asBindModel();
		bind.setUid(model.getUid());
		redisUserService.bindUserInfo(bind);
		
		LoginForm loginForm = new LoginForm();
		loginForm.setBindForms(this.loadBindForms(model.getUid()));
		loginForm.setUser(model.asUserForm());
		if (model != null && model.getUid() > 0){
			result.setCode(200);
			result.setData(loginForm);
			result.setMessage("绑定成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.SYSTEM_ERROR.getCode());
			result.setData(null);
			result.setMessage(DuanquErrorCode.SYSTEM_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		
		if (result.getCode() == 200){
			//发送消息
			redisJMSMessageService.insertNewUserMessageQueue(model);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.USER_ADD));
		}
		return result;
	}
	
	@RequestMapping(value = "/user/open/bind/other", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result openBindOther(@ModelAttribute BindSubmit submit,@RequestParam("token")String token) {
		Result result = new Result();
		long uid = redisUserService.getUid(token);
		submit.setOpenNickName(EmojiUtils.filterEmoji(submit.getOpenNickName()));
		BindModel bind = submit.asBindModel();
		if (uid > 0){
			
			//判断这个帐号有没有被其他用户绑定过
			UserModel user = redisUserService.getUser(submit.getOpenType(), submit.getOpenUid());
			if (user != null && user.getUid() > 0){
				if (user.getUid() != uid){
					result.setCode(DuanquErrorCode.ACCOUNT_EXIST.getCode());
					result.setData("");
					result.setMessage(DuanquErrorCode.ACCOUNT_EXIST.getMessage());
					result.setTime(System.currentTimeMillis());
					return result;
				}
			}
			//判断是否跟之前绑定的帐号一致
			BindModel bindinfo = redisUserService.getBindInfo(uid, submit.getOpenType());
			if (bindinfo != null && bindinfo.getUid() > 0){
				if (!bindinfo.getOpenUid().equalsIgnoreCase(submit.getOpenUid())){
					result.setCode(DuanquErrorCode.BIND_SAME.getCode());
					result.setData("");
					result.setMessage(DuanquErrorCode.BIND_SAME.getMessage());
					result.setTime(System.currentTimeMillis());
					return result;
				}
			}
			
			bind.setUid(uid);
			redisUserService.bindUserInfo(bind);
			result.setCode(200);
			result.setData("");
			result.setMessage("绑定成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.TOKEN_ERROR.getCode());
			result.setData(token);
			result.setMessage(DuanquErrorCode.TOKEN_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		
		if (result.getCode() == 200){
			//发送消息
			redisJMSMessageService.insertBindingMessageQueue(bind);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.BINDING));
		}
		
		return result;
	}
	/**
	 * 短趣注册
	 * @return
	 */
	@RequestMapping(value = "/user/duanqu/register", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result duanquRegister(@ModelAttribute UserSubmit submit) {
		Result result = new Result();
		UserModel user = redisUserService.getUser(submit.getEmail());
		if (user != null && user.getUid() > 0){
			result.setCode(DuanquErrorCode.EMAIL_USED.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.EMAIL_USED.getMessage());
			result.setTime(System.currentTimeMillis());
		}else{
			user = submit.asUserModel();
			user.setToken(DuanquSecurity.encodeToken(user.getEmail()));
			user.setLoginPassward(DuanquSecurity.encodePassword(submit.getLoginPassword()));
			user.setAvatarUrl(DuanquConfig.getDefaultAvatar());
			user.setStatus(1);
			redisUserService.insertUser(user);
			result.setCode(200);
			result.setData(user.asUserForm());
			result.setMessage("注册成功！");
			result.setTime(System.currentTimeMillis());
		}
		if (result.getCode() == 200){
			//发送消息
			redisJMSMessageService.insertNewUserMessageQueue(user);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.USER_ADD));
		}
		return result;
	}
	
	@RequestMapping(value = "/mobile/bind", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result bindMobile(@RequestParam("mobile")String mobile,
			@RequestParam("code")String code,
			@RequestParam("token")String token) {
		UserModel user = this.getUser(token);
		String code1 = redisJMSMessageService.getMobileCode(mobile);
		Result result = new Result();
		if (code != null && code.equals(code1)){
			redisUserService.bindMobile(user.getUid(), mobile);
			result.setCode(200);
			result.setData("");
			result.setMessage("绑定成功！");
			result.setTime(System.currentTimeMillis());
		}else{
			result.setCode(DuanquErrorCode.MOBILE_CODE_ERROR.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.MOBILE_CODE_ERROR.getMessage());
			result.setTime(System.currentTimeMillis());
		}
		
		if (result.getCode() == 200){
			BindModel bind = new BindModel();
			bind.setUid(user.getUid());
			bind.setOpenNickName(mobile);
			bind.setCreatetime(System.currentTimeMillis());
			bind.setOpenType(OpenType.MOBILE.getMark());
			//发送消息
			redisJMSMessageService.insertBindingMessageQueue(bind);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.BINDING));
		}
		
		return result;
	}
	
	@RequestMapping(value = "/mobile/code/get", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result getMobileCode(
			@RequestParam("mobile")String mobile,
			@RequestParam("token")String token) {
		Result result = new Result();
		//判断手机号码是否被绑定
		boolean binded = redisUserService.mobileIsBind(mobile);
		if (binded){
			result.setCode(DuanquErrorCode.MOBILE_USED.getCode());
			result.setData("");
			result.setMessage(DuanquErrorCode.MOBILE_USED.getMessage());
			result.setTime(System.currentTimeMillis());
			return result;
		}else{
			String code = redisJMSMessageService.getMobileCode(mobile);
			code = code == null?DuanquUtils.getRamdCode():code;
			
			redisJMSMessageService.addMobileCode(mobile, code);
			
			DuanquSendSmsUtil.sendSms(mobile, "趣拍验证码："+code, "2");
			
			result.setCode(200);
			result.setData(code);
			result.setMessage("发送成功！");
			result.setTime(System.currentTimeMillis());
			return result;
		}
	}
	
	@RequestMapping(value = "/contacts/up", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result uploadContacts(
			@RequestParam(value="contactsStr",defaultValue="",required=false) String contactsStr,
			@RequestParam("token")String token) {
		
		long uid = getUid(token);
		Result result = new Result();
		if(StringUtils.isBlank(contactsStr)){
			result.setCode(DuanquErrorCode.PARAMETER_ERROR.getCode());
			result.setData("");
			result.setMessage("通讯录为空！");
			result.setTime(System.currentTimeMillis());
			return result;
		}else{
			String[] records = contactsStr.split("\\|");
			List<OpenFriend> friends = new ArrayList<OpenFriend>();
			for (String record : records){
				String[] res = record.split(":");
				if (res.length == 2){
					String mobile = res[1];
					String name = res[0];
					OpenFriend friend = new OpenFriend();
					friend.setOpenNickName(name);
					friend.setOpenUserName(mobile);
					friend.setOpenType(OpenType.MOBILE.getMark());
					friend.setOpenUserId(mobile);
					friend.setUid(uid);
					friends.add(friend);
				}
			}
			redisRelationshipService.insertNoMatchFriends(uid, friends, OpenType.MOBILE.getMark());
			result.setCode(200);
			result.setData("");
			result.setMessage("上传成功！");
			result.setTime(System.currentTimeMillis());
			if (result.getCode() == 200){
				MobileMessageBean mobiles = new MobileMessageBean();
				mobiles.setUid(uid);
				mobiles.setMobileStr(contactsStr);
				//发送消息保存通讯录
				redisJMSMessageService.insertContactsUpQueue(mobiles);
				duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.MOBILE_UP));
			}
			
			return result;
		}
	}
	
	@RequestMapping(value = "/bind/get", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result getUserBinds(
			@RequestParam("token") String token) {
		Result result = new Result();
		long uid = getUid(token);
		List<BindForm> bindForms = loadBindForms(uid);
		result.setCode(200);
		result.setData(bindForms);
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	/**
	 * 用户退出登录
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result logout(
			@RequestParam("token") String token) {
		Result result = new Result();
		try{
			redisUserService.deleteUserDeviceToken(getUid(token));
		}catch (Exception e) {
			e.printStackTrace();
		}
		result.setCode(200);
		result.setData("");
		result.setMessage("获取成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	/**
	 * 发同步微博
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/syn/weibo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result synWeibo(@RequestParam("token") String token) {
		Result result = new Result();
		try {
			redisJMSMessageService.insertSynWeiboQueue(getUid(token));
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.SYN_WEIBO));
		} catch (Exception e) {
			logger.error("同步出错！");
		}
		result.setCode(200);
		result.setData("");
		result.setMessage("同步成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	
	/**
	 * 取得用户绑定列表
	 * @param uid
	 * @return
	 */
	private List<BindForm> loadBindForms(long uid){
		List<BindForm> bindForms = new ArrayList<BindForm>();
		
		BindModel qqModel = redisUserService.getBindInfo(uid, BindModel.OpenType.TENCENT.getMark());
		if (qqModel != null && qqModel.getUid() > 0){
			bindForms.add(qqModel.asBindForm());
		}
		
		BindModel sinaModel = redisUserService.getBindInfo(uid, BindModel.OpenType.SINA.getMark());
		if (sinaModel != null && sinaModel.getUid() > 0){
			bindForms.add(sinaModel.asBindForm());
		}
		
		
		UserModel user = redisUserService.getUser(uid);
		
		if (!StringUtils.isBlank(user.getMobile())){
			BindModel mobileModel = new BindModel();
			mobileModel.setAccessToken("");
			mobileModel.setCreatetime(user.getCreateTime());
			mobileModel.setExpiresDate("");
			mobileModel.setOpenNickName(user.getMobile());
			mobileModel.setOpenType(BindModel.OpenType.MOBILE.getMark());
			mobileModel.setOpenUid(user.getUid()+"");
			mobileModel.setUid(user.getUid());
			mobileModel.setRefreshToken("");
			bindForms.add(mobileModel.asBindForm());
		}
		
		return bindForms;
	}
	
/*	private void writeFile(String fileName,byte[] bytes) {
		try {
			File file = new File("/home/duanquimages/avatar/"+fileName+".jpg");
			DuanquUtils.writeFile(file, bytes);
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();

		}
	}*/
}
