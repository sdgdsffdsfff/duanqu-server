package com.duanqu.manager.dao;

import java.util.List;
import java.util.Map;

import com.duanqu.common.model.SubjectModel;
import com.duanqu.manager.submit.ManagerSubjectSubmit;

public interface SubjectMapper {
	
	/**
	 * @param subjectModel
	 * 添加话题
	 */
	void insertSubject(SubjectModel subjectModel);
	
	List<Map<String, Object>> querySubjectList(ManagerSubjectSubmit managerSubjectSubmit);
	
	long querySubjectListCount(ManagerSubjectSubmit managerSubjectSubmit);
	
	void deleteSubject(int sid);

}
