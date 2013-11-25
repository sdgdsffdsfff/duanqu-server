package com.duanqu.redis.service.badword;

import java.util.Set;

import com.duanqu.common.DuanquUtils;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.utils.key.SystemKeyManager;

public class BadwordServiceImpl extends BaseRedisService implements
		IBadwordService {

	@Override
	public String filterBadword(String string) {
		this.loadBadword();
		return DuanquUtils.filter(string, DuanquUtils.badwordMap);
	}

	@Override
	public String hasBadWord(String string) {
		this.loadBadword();
		return DuanquUtils.hasSensitiveWord(string, DuanquUtils.badwordMap);
	}
	
	@SuppressWarnings("unchecked")
	public void loadBadword(){
		Set wordSet = jmsTemplate.boundSetOps(SystemKeyManager.getBadwordsKey()).members();
		DuanquUtils.wordSetToMap(wordSet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNewVersionTips() {
		Object obj = jmsTemplate.boundValueOps(SystemKeyManager.getNewVersionTipsKey()).get();
		if (obj != null){
			return (String) obj;
		} else {
			return "新版本上线，赶紧更新吧！";
		}
	}

}
