package com.duanqu.redis.service.syn.system;

import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.utils.key.SystemKeyManager;

public class SystemSynServiceImpl extends BaseRedisService implements ISystemSynService {

	@SuppressWarnings("unchecked")
	@Override
	public void addBadword(String badword) {
		jmsTemplate.boundSetOps(SystemKeyManager.getBadwordsKey()).add(badword.trim());
		//重建
		//Set wordSet = jmsTemplate.boundSetOps(SystemKeyManager.getBadwordsKey()).members();
		//DuanquUtils.wordSetToMap(wordSet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteBadword(String badword) {
		jmsTemplate.boundSetOps(SystemKeyManager.getBadwordsKey()).remove(badword.trim());
		//重建
		//Set wordSet = jmsTemplate.boundSetOps(SystemKeyManager.getBadwordsKey()).members();
		//DuanquUtils.wordSetToMap(wordSet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void synNewVersionTips(String tips) {
		jmsTemplate.boundValueOps(SystemKeyManager.getNewVersionTipsKey()).set(tips);
		
	}

}
