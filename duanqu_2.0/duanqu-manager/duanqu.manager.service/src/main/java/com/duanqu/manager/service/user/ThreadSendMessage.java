package com.duanqu.manager.service.user;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javapns.notification.PayloadPerDevice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.duanqu.common.DateUtil;
import com.duanqu.common.PushSender;
import com.duanqu.common.model.MessageModel;
import com.duanqu.manager.dao.UserAdminMapper;
import com.duanqu.redis.service.syn.message.IMessageSynService;


public class ThreadSendMessage {
	Log logger = LogFactory.getLog(ThreadSendMessage.class);
	long num;
	UserAdminMapper userAdminMapper;
	IMessageSynService messageSynService;
	String messageText;
	BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<Long>(2000);
	static LinkedList<List<Long>> linkedList=new LinkedList<List<Long>>();
	final int pageSize=200;
	final static int CONSUMER_NUM = 5;
	long logNum;
	static long pushNum=1;
	
	boolean flag=false;
	
	public void send() {
		//借用线程池
		ExecutorService service = Executors.newCachedThreadPool();
		ThreadSendMessage threadSendMessage=new ThreadSendMessage();
		//生产者
		Producer producer=new Producer();
		service.execute(producer);
		//消费者
		for (int i = 0; i < CONSUMER_NUM; i++) {
			service.execute(new Consumer());
		}
		service.execute(new PushMessage(threadSendMessage));
		service.execute(new PushMessage(threadSendMessage));
		service.execute(new PushMessage(threadSendMessage));
		service.shutdown();
	}
	
	public synchronized List<Long> poll(){
		return linkedList.poll();
	}
	
	public ThreadSendMessage(){
		
	}
	public ThreadSendMessage(long num, UserAdminMapper userAdminMapper,
			IMessageSynService messageSynService, String messageText) {
		this.num = num;
		this.userAdminMapper = userAdminMapper;
		this.messageSynService = messageSynService;
		this.messageText = messageText;
	}
	
	// 消费者线程
	class Consumer implements Runnable {
		Long recUid;
		@Override
		public void run() {
			while(true){
				try {
					recUid=blockingQueue.poll();
					if(recUid!=null){
					MessageModel mm = new MessageModel();
					mm.setUid(1);
					mm.setMessageText(messageText);
					mm.setRecUid(recUid);	
					mm.setCreateTime(System.currentTimeMillis());
					messageSynService.synMessageSendBatchs(mm);
					/*Map<String, Object> map=new HashMap<String, Object>();
					map.put("uid", recUid);
					map.put("type", 2);
					userAdminMapper.insertTmp(map);*/
				    logNum++;
					if(logNum==10000){
						logger.error("已经发送了一万用户========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
					}
					if(logNum==20000){
						logger.error("已经发送了2万用户用户========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
					}
					if(logNum==50000){
						logger.error("已经发送了5万用户========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
					}
					if(logNum==80000){
						logger.error("已经发送了8万用户========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
					}
				//	TimeUnit.SECONDS.sleep(3);	
					}else{
						if(flag){
							logger.error("全部用户已经发送完毕========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
							break;
						}
					}
				} catch (Exception e) {
					logger.error("发送消息失败的用户ID:"+recUid+"错误信息:"+e);
				}	
		}
		}
	}
	//生产者线程
	class Producer implements Runnable {
		@Override
		public void run() {
			while(true){
			if(num>0){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("pageStart", (num-1)*pageSize);
				map.put("pageSize", pageSize);
				List<Long> list=userAdminMapper.getUserList(map);
				linkedList.add(list);
				for(Long n:list){
					try {
						blockingQueue.put(n);
					/*	Map<String, Object> map2=new HashMap<String, Object>();
						map2.put("uid", n);
						map2.put("type", 1);
						userAdminMapper.insertTmp(map2);*/
					} catch (InterruptedException e) {
						e.printStackTrace();
						logger.error("添加到队列失败的用户ID"+n+"错误信息:"+e);
					}
				}
				num--;
			}else{
				logger.error("生产者线程执行完毕========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
				flag=true;
				break;
			}
		}
		}
	}
	
	class PushMessage implements Runnable{
		ThreadSendMessage threadSendMessage;
		@Override
		public void run(){
			while(true){
				List<Long> list=threadSendMessage.poll();
				if(list!=null){
					List<PayloadPerDevice> listPay=messageSynService.toList(list);
					PushSender.batchsendMessage(listPay);	
					logger.error("已经推送========================="+pushNum*1000+"用户"+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
					System.out.println(pushNum);
					pushNum++;
					
				}else{
					if(flag){
						logger.error("消息推送完毕========================="+DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
						break;
					}
				}
			}
		}
		public PushMessage(ThreadSendMessage threadSendMessage){
			this.threadSendMessage=threadSendMessage;
		}
	}
}





