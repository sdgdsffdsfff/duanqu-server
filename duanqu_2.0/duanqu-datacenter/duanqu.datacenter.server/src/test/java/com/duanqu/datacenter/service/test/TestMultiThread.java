package com.duanqu.datacenter.service.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class TestMultiThread {

	long num;
	String messageText;
	BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<Integer>(5);
	final int pageSize = 1000;
	Integer logNum;
	boolean flag = false;

	public static void main(String[] string) {
		TestMultiThread test = new TestMultiThread();
		// 借用线程池
		ExecutorService service = Executors.newCachedThreadPool();
		// 生产者
		// Producer producer = ;
		service.execute(test.new Producer());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 消费者
		for (int i = 0; i < 3; i++) {
			service.execute(test.new Consumer());
		}
		service.shutdown();
	}

	// 消费者线程
	class Consumer implements Runnable {
		Integer recUid;

		@Override
		public void run() {
			while (true) {
				try {
					recUid = blockingQueue.poll();
					System.out.println(Thread.currentThread().getName()+"--------out " + recUid);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 生产者线程
	class Producer implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 20; i++) {
				try {
					blockingQueue.put(i);
					System.out.println("put " + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
