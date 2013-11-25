package com.duanqu.client.test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.duanqu.client.search.IESSearcher;
import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.common.vo.FriendForm;

public class TestSearchService extends BaseTestServiceImpl {
	@Resource
	IESSearcher esSearcher;
	@Test
	public void testIndexUser(){
		Map reslteMap = esSearcher.searchUser(1, "test", 0, 20);
		List<FriendForm> sUsers  = (List)reslteMap.get("result");
		for(FriendForm friend:sUsers){
			System.out.println(friend);
		}
		
		//esSearcher.searchContent(1, "萌宠", 0, 20);
	}
	//@Test
	public void searchUser(){
		
	}
} 
