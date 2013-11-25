package com.duanqu.redis.service;

public interface IRedisTestService {
	
	public void insertTestTimeline(String id,String value);
	
	public void insertTestFans(String value);
	
	public void loadTimeline(int start,int count);

}
