package com.duanqu.manager.service.banner;

import com.duanqu.common.model.BannerInfoModel;
import com.duanqu.manager.submit.ManagerBannerSubmit;

public interface IManagerBannerService {
	
	/**
	 * @param managerBannerSubmit
	 * 运营列表查询
	 */
	void queryBannerModelsList(ManagerBannerSubmit managerBannerSubmit);
	
	/**
	 * @param bannerInfoModel
	 * 添加运营条
	 * @throws Exception 
	 */
	void insertBannerInfo(ManagerBannerSubmit managerBannerSubmit) throws Exception;
	
	/**
	 * @param bid
	 * 删除运营条
	 */
	void deleteBannerInfo(long bid);
	
	/**
	 * @param managerBannerSubmit
	 * 发布运营条
	 */
	void editBannerInfo(ManagerBannerSubmit managerBannerSubmit);
}
