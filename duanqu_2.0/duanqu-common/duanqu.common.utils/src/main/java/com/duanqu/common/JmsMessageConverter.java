package com.duanqu.common;

import java.io.ObjectInputStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.util.ByteArrayInputStream;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class JmsMessageConverter implements MessageConverter {

	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		return null;

	}

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		Object object = null;
		if (message instanceof ObjectMessage) {
			// 两次强转，获得消息中的主体对象字节数组流
			byte[] obj = (byte[]) ((ObjectMessage) message).getObject();
			// 读取字节数组中为字节数组流
			ByteArrayInputStream bis = new ByteArrayInputStream(obj);
			try {
				// 读字节数组流为对象输出流
				ObjectInputStream ois = new ObjectInputStream(bis);
				// 从对象输出流中取出对象 并强转
				object = ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return object;
	}

}
