package com.duanqu.client.search;

import java.util.List;
import java.util.Map;

import com.duanqu.common.vo.ActionForm;

public interface IESSearcher {
	
	Map<String ,Object> searchUser(long uid,String key,int start,int size);
	
	Map<String ,Object> searchContent(long uid,String key,int start,int size);

	Map<String ,Object> searchOpenUser(long uid,String key,int openType,int start,int size);
}
