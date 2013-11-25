package com.duanqu.manager.service.user;

import java.util.ArrayList;
import java.util.Date;
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
import com.duanqu.manager.dao.UserAdminMapper;
import com.duanqu.redis.service.syn.message.IMessageSynService;


public class ThreadSendPush {
	Log logger = LogFactory.getLog(ThreadSendMessage.class);
	long num;
	UserAdminMapper userAdminMapper;
	IMessageSynService messageSynService;
	String messageText;
	String type;
	String innerParam;
	BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<Long>(3000);
	static LinkedList<List<Long>> linkedList=new LinkedList<List<Long>>();
	final static int pageSize=1000;
	final static int CONSUMER_NUM = 2;
	long logNum;
	static long pushNum=1;
	
	boolean finish = false;
	
	public void send() {
		//借用线程池
		ExecutorService service = Executors.newCachedThreadPool();
		//生产者
		Producer producer=new Producer();
		service.execute(producer);
		//消费者
		for (int i = 0; i < CONSUMER_NUM; i++) {
			service.execute(new Consumer());
		}
		service.shutdown();
	}
	
	public ThreadSendPush(){
		
	}
	public ThreadSendPush(long num, UserAdminMapper userAdminMapper,
			IMessageSynService messageSynService, String messageText,String innerParam,String type) {
		this.num = num;
		this.userAdminMapper = userAdminMapper;
		this.messageSynService = messageSynService;
		this.messageText = messageText;
		this.type=type;
		this.innerParam=innerParam;
	}
	
	// 消费者线程
	class Consumer implements Runnable {
		Long recUid;
		private List<PayloadPerDevice> pushList = new ArrayList<PayloadPerDevice>();
		@Override
		public void run() {
			while (true) {
				try {
					recUid = blockingQueue.poll();
					if (recUid != null) {
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("uid", recUid);
						map2.put("type", 1);
						map2.put("createTime", System.currentTimeMillis());
						userAdminMapper.insertTmp(map2);
						PayloadPerDevice pay = messageSynService.praseMessage(recUid, messageText,innerParam,type);
						if (pay != null) {
							pushList.add(pay);
						}
						pay = null;
						if (pushList.size() >= 100) {
							System.out.println("============="
									+ Thread.currentThread().getName() + ","
									+ pushList.hashCode() + "----------size="
									+ pushList.size());
							try {
								PushSender.batchsendMessage(pushList);
							} catch (Exception e) {
								e.printStackTrace();
							}
							pushList.clear();
						}
					} else {
						if (finish) {
							try {
								PushSender.batchsendMessage(pushList);
							} catch (Exception e) {
								e.printStackTrace();
							}
							pushList.clear();
							logger.error("全部用户已经发送完毕===========" + DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
							break;
						}
					}
				} catch (Exception e) {
					logger.error("发送消息失败的用户ID:" + recUid + "错误信息:" + e);
				}
			}
		}
	}
	//生产者线程
	class Producer implements Runnable {
		@Override
		public void run() {
			while (true) {
				if (num > 0) {
					System.out.println("已经在发送第 " + num + "批了");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pageStart", (num - 1) * pageSize);
					map.put("pageSize", pageSize);
					List<Long> list = userAdminMapper.getUserList(map);
					for (Long n : list) {
						try {
							blockingQueue.put(n);
						} catch (InterruptedException e) {
							e.printStackTrace();
							logger.error("添加到队列失败的用户ID" + n + "错误信息:" + e);
						}
					}
					list = null;
					num--;
				} else {
					logger.error("生产者线程执行完毕============"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
					finish = true;
					break;
				}
			}
		}
	}
}





