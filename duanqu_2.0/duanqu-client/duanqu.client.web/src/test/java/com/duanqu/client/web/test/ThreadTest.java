package com.duanqu.client.web.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
	public static void main(String[] args) {
		// 创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(1);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Thread t1 = new MyThread();
		t1.setName("1111111111");
		Thread t2 = new MyThread();
		t2.setName("211111111");
		Thread t3 = new MyThread();
		t3.setName("3111111111");
		Thread t4 = new MyThread();
		t4.setName("41111111111111");
		Thread t5 = new MyThread();
		t5.setName("511111111111");
		//将线程放入池中进行执行 
	
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		//关闭线程池
		pool.shutdown();
	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
	}
}
