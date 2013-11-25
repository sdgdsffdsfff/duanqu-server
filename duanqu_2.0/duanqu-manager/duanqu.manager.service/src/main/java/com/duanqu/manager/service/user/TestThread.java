package com.duanqu.manager.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*//ThreadSendMessage threadSendMessage=new ThreadSendMessage(50);
		Thread thread=new Thread(threadSendMessage);
		thread.start();*/
		
		BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<Long>(5);
		
		blockingQueue.add(Long.valueOf(2));
		blockingQueue.add(Long.valueOf(3));
		
		List<Long> list=new ArrayList<Long>();
		list.add(Long.valueOf(4));
		list.add(Long.valueOf(6));
		list.add(Long.valueOf(7));
		list.add(Long.valueOf(8));
		
		blockingQueue.addAll(list);
		System.out.println(blockingQueue.toString());


	}

}
