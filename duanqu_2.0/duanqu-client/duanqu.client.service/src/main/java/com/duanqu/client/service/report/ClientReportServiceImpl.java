package com.duanqu.client.service.report;

import com.duanqu.client.dao.ClientReportMapper;
import com.duanqu.common.model.ReportModel;

public class ClientReportServiceImpl implements IClientReportService {

	ClientReportMapper clientReportMapper;
	
	@Override
	public void insertReportModel(ReportModel reportModel) {
		clientReportMapper.insertReportModel(reportModel);

	}

	public ClientReportMapper getClientReportMapper() {
		return clientReportMapper;
	}

	public void setClientReportMapper(ClientReportMapper clientReportMapper) {
		this.clientReportMapper = clientReportMapper;
	}
	
	
	
	

}
