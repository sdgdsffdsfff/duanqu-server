package com.duanqu.redis.service.report;

import org.springframework.util.StringUtils;

import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.utils.key.ReportKeyManager;

public class RedisReportServiceImpl extends BaseRedisService implements
		IRedisReportService {

	@SuppressWarnings("unchecked")
	@Override
	public void addBiaoqingReport(long cid, String biaoqingNo) {
		reportTemplate.boundSetOps(ReportKeyManager.getBiaoqingKey(biaoqingNo)).add(String.valueOf(cid));

	}
	@SuppressWarnings("unchecked")
	@Override
	public void addFilterReport(long cid, String filters) {
		String[] filterNos = filters.split(",");
		for (String filterNo : filterNos){
			if (StringUtils.hasText(filterNo.trim())){
				reportTemplate.boundSetOps(ReportKeyManager.getFilterKey(filterNo.trim())).add(String.valueOf(cid));
			}
		}
		

	}
	@SuppressWarnings("unchecked")
	@Override
	public void addMusicReport(long cid, String musicNo) {
		reportTemplate.boundSetOps(ReportKeyManager.getMusicKey(musicNo)).add(String.valueOf(cid));

	}
	@SuppressWarnings("unchecked")
	@Override
	public void addTiezhiReport(long cid, String tiezhiNo) {
		reportTemplate.boundSetOps(ReportKeyManager.getTiezhiKey(tiezhiNo)).add(String.valueOf(cid));
	}

}
