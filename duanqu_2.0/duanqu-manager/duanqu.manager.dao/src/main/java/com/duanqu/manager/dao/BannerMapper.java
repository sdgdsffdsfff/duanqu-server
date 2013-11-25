package com.duanqu.manager.dao;

import java.util.List;

import com.duanqu.common.model.BannerInfoModel;
import com.duanqu.manager.submit.ManagerBannerSubmit;

public interface BannerMapper {
	
	
	/**
	 * @param managerBannerSubmit
	 * @return
	 * 查询运营条列表
	 */
	List<BannerInfoModel> queryBannerInfoModels(ManagerBannerSubmit managerBannerSubmit);
	
	/**
	 * @param managerBannerSubmit
	 * @return
	 * 统计总数
	 */
	long queryBannerInfoModelsCount(ManagerBannerSubmit managerBannerSubmit);
	
	/**
	 * @param bannerInfoModel
	 * 添加运营条
	 */
	void insertBannerInfo(BannerInfoModel bannerInfoModel);
	
	/**
	 * @param bannerInfoModel
	 * 修改运营条的排序位置
	 */
	void updateBannerInfoOrderNum(BannerInfoModel bannerInfoModel);
	
	/**
	 * @param bid
	 * 删除运营条
	 */
	void deleteBannerInfo(long bid);
	
	/**
	 * @return
	 * 刷新缓存
	 */
	List<BannerInfoModel> queryBannerList();

}
