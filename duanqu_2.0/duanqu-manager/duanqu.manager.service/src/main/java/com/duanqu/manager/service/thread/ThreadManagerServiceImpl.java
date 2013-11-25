package com.duanqu.manager.service.thread;


public class ThreadManagerServiceImpl implements IThreadManagerService {
	
	Runnable showTimesThread;//播放次数更新线程
	Runnable relationShipThread;//好友数，粉丝数，关注数更新线程

	@Override
	public void startThreads() {
		System.out.println("----启动播放次数同步线程----");
		Thread t1 = new Thread(showTimesThread);
		t1.start();
		System.out.println("----启动用户好友数，粉丝数，关注数同步线程----");
		Thread t2 = new Thread(relationShipThread);
		t2.start();
	}

	public void setShowTimesThread(Runnable showTimesThread) {
		this.showTimesThread = showTimesThread;
	}

	public void setRelationShipThread(Runnable relationShipThread) {
		this.relationShipThread = relationShipThread;
	}
}
