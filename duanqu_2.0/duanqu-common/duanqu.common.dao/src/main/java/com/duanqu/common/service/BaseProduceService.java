package com.duanqu.common.service;

import org.springframework.jms.core.JmsTemplate;

public class BaseProduceService {
	
	protected JmsTemplate jmsTemplate;


	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

}
