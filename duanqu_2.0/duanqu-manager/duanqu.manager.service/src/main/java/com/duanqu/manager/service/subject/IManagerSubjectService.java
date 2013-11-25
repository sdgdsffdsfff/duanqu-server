package com.duanqu.manager.service.subject;

import com.duanqu.manager.submit.ManagerSubjectSubmit;

public interface IManagerSubjectService {
	
	/**添加话题
	 * @param managerSubjectSubmit
	 */
	String insertSubject(ManagerSubjectSubmit managerSubjectSubmit);
	
	/**查询话题列表
	 * @param managerSubjectSubmit
	 * 
	 */
	void querySubjectList(ManagerSubjectSubmit managerSubjectSubmit);
	
	/**删除话题
	 * @param sid
	 */
	void deleteSubject(int sid);

}
