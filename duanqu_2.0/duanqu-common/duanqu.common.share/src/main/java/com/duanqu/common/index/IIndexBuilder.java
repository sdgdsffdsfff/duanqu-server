package com.duanqu.common.index;

import com.duanqu.common.model.IndexContentModel;
import com.duanqu.common.model.IndexOpenUserModel;
import com.duanqu.common.model.IndexUserModel;

public interface IIndexBuilder {
	
void buildUserIndex(IndexUserModel user);
	
	void buildConentIndex(IndexContentModel content);
	
	void buildOpenUserIndex(IndexOpenUserModel openUser);
	
	void deleteUserIndex(long id);
	
	void deleteContentIndex(long id);

}
