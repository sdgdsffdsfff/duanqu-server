package com.duanqu.client.service.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.client.service.report.IClientReportService;
import com.duanqu.common.model.MessageModel;
import com.duanqu.common.model.ReportModel;

public class TestClientMessageServiceImpl extends BaseTestServiceImpl {
	
	@Resource
	IClientMessageService clientMessageService;
	@Resource
	IClientReportService clientReportService;
	
	//@Test
	@Rollback(false)
	public void testInsertUserMessage(){
		MessageModel messageModel=new MessageModel();
		messageModel.setUid(2);
		messageModel.setRecUid(3);
		messageModel.setIsNew(1);
		messageModel.setIsDelete(0);
		messageModel.setMessageText("dd");
		messageModel.setCreateTime(System.currentTimeMillis());
		clientMessageService.insertUserMessage(messageModel);
	}
	@Test
	@Rollback(false)
	public void testInsertReportModel(){
		ReportModel reportModel=new ReportModel();
		reportModel.setCid(36);
		reportModel.setUid(107);
		reportModel.setCreateTime(System.currentTimeMillis());
		reportModel.setMemo("11111");
		reportModel.setIsCheck(0);
		clientReportService.insertReportModel(reportModel);
	}
	
	
	

}
