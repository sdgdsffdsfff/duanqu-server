package com.duanqu.client.test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.duanqu.client.search.IESSearcher;
import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.common.dao.user.FriendMapper;
import com.duanqu.common.index.IIndexBuilder;
import com.duanqu.common.model.IndexOpenUserModel;
import com.duanqu.common.model.OpenFriend;

public class TestIndexService extends BaseTestServiceImpl{
	
	private static int pageSize = 100;
	
	@Resource
	FriendMapper friendMapper;
	@Resource
	IIndexBuilder indexBuilder;
	@Resource
	IESSearcher esSearcher;
//	@Test
	public void buildOpenUser(){
		int count = friendMapper.queryOpenFriendListCount();
		int pageCount = (count - 1) / pageSize + 1;
		System.out.println("总共有 " + pageCount + " 页数据需要索引");
		for (int i = 1;i<= pageCount;i++){
			System.out.println("正在索引第 "+i+" 页数据！");
			List<OpenFriend> friends = friendMapper.queryOpenFriendList((i - 1)*pageSize, pageSize);
			for (OpenFriend friend:friends){
				IndexOpenUserModel indexOpenUser = new IndexOpenUserModel();
				indexOpenUser.setId(friend.getOpenUserId()+"|"+friend.getUid());
				indexOpenUser.setOpenNickName(friend.getOpenNickName());
				indexOpenUser.setOpenType(friend.getOpenType());
				indexOpenUser.setOpenUserId(friend.getOpenUserId());
				indexOpenUser.setUid(friend.getUid());
				try{
					System.out.println("|");
					indexBuilder.buildOpenUserIndex(indexOpenUser);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("");
		}
	}
	
	@Test
	public void buildMobile(){
		int count = friendMapper.queryUserMobilesListCount();
		int pageCount = (count - 1) / pageSize + 1;
		System.out.println("总共有 "+pageCount+" 页数据需要索引");
		for (int i = 1;i<= pageCount;i++){
			System.out.println("正在索引第 "+i+" 页数据！");
			List<OpenFriend> friends = friendMapper.queryUserMobilesList((i - 1)*pageSize, pageSize);
			for (OpenFriend friend:friends){
				IndexOpenUserModel indexOpenUser = new IndexOpenUserModel();
				indexOpenUser.setId(friend.getOpenUserId()+"|"+friend.getUid());
				indexOpenUser.setOpenNickName(friend.getOpenNickName());
				indexOpenUser.setOpenType(friend.getOpenType());
				indexOpenUser.setOpenUserId(friend.getOpenUserId());
				indexOpenUser.setUid(friend.getUid());
				try{
					System.out.print("|");
					indexBuilder.buildOpenUserIndex(indexOpenUser);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	public void search(){
		Map map = esSearcher.searchOpenUser(3, "王海华", 3, 0, 20);
		System.out.println(map.get("count"));
		List<OpenFriend> friends = (List<OpenFriend>)map.get("result");
		System.out.println(friends.size());
		for (OpenFriend friend : friends){
			System.out.println(friend);
		}
	}
	

}
