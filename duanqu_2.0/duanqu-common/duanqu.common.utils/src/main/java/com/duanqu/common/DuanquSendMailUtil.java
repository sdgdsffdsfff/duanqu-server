package com.duanqu.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DuanquSendMailUtil {

	// 设置服务器
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_SMTP = "smtp.qq.com";
	// 服务器验证
	private static String KEY_PROPS = "mail.smtp.auth";
	// 发件人用户名、密码
	private static String SEND_USER = "911679374@qq.com";
	private static String SEND_PWD = "1988125116";

	// 以上配置信息可以考虑从配置文件读取

	public static void sendMail(String toUser, String yzm) {
		Properties properties = System.getProperties();
		properties.put(KEY_SMTP, VALUE_SMTP);// 设置smtp服务器地址
		// properties.put(KEY_PROPS, true);//身份验证
		// 用这种方式既然报错，说验证没有打开，不清楚跟下面这个方式有何区别
		properties.setProperty(KEY_PROPS, "true");
		Authenticator authenticator = new duanquAuthenticator(SEND_USER,
				SEND_PWD);
		Session session = Session.getInstance(properties, authenticator); // 获得一个带有authenticator的session实例
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(SEND_USER));// 设置发送方
			message.setRecipient(RecipientType.BCC, new InternetAddress(toUser));// 设置接收方
			message.setSubject("短趣");
			message.setText("你好：你的验证码是" + yzm + "");
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		sendMail("ydm3053652@163.com", "12345655");
	}

}

class duanquAuthenticator extends Authenticator {
	private String userName;
	private String userPwd;

	public duanquAuthenticator(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, userPwd);
	}

}
