package com.duanqu.client.jms;

import javax.jms.Destination;

import com.duanqu.common.service.BaseProduceService;
import com.duanqu.common.service.ProducerService;

public class UserProducerServiceImpl extends BaseProduceService implements ProducerService {
	
	Destination destination;

	@Override
	public void sendMessage(Object user) {
		
		/*jmsTemplate.send(newUserDestination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createTextMessage(message);  
            }  
        });*/
		try{
			jmsTemplate.convertAndSend(destination, user);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	

}
