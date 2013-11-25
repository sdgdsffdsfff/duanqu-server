package com.duanqu.manager.service.banner;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.model.BannerInfoModel;
import com.duanqu.manager.dao.BannerMapper;
import com.duanqu.manager.submit.ManagerBannerSubmit;
import com.duanqu.redis.service.hot.IRedisHotService;

public class ManagerBannerServiceImpl implements IManagerBannerService {

	private BannerMapper bannerMapper;
	
	private IRedisHotService redisHotService;
	
	@Override
	public void queryBannerModelsList(ManagerBannerSubmit managerBannerSubmit) {
		long count=bannerMapper.queryBannerInfoModelsCount(managerBannerSubmit);
		managerBannerSubmit.computerTotalPage(count);
		List<BannerInfoModel> list=bannerMapper.queryBannerInfoModels(managerBannerSubmit);
		managerBannerSubmit.setObjList(list);
	}

	@Override
	public void insertBannerInfo(ManagerBannerSubmit managerBannerSubmit) throws Exception {
		BannerInfoModel bannerInfoModel=new BannerInfoModel();
		MultipartFile imgUrl=managerBannerSubmit.getImgUrl();
		String imgUrlStr=AliyunUploadUtils.uploadSystemImages(
				imgUrl.getInputStream(), imgUrl.getBytes().length,
				imgUrl.getContentType());
		bannerInfoModel.setTitle(managerBannerSubmit.getTitle());
		bannerInfoModel.setImgUrl(imgUrlStr);
		bannerInfoModel.setBannerType(managerBannerSubmit.getBannerType());
		bannerInfoModel.setInnerUrl(managerBannerSubmit.getInnerParam());
		bannerInfoModel.setCreateTime(System.currentTimeMillis());
		bannerMapper.insertBannerInfo(bannerInfoModel);
	}

	@Override
	public void deleteBannerInfo(long bid) {
		bannerMapper.deleteBannerInfo(bid);

	}

	@Override
	public void editBannerInfo(ManagerBannerSubmit managerBannerSubmit) {
		List<Long> bidList=managerBannerSubmit.getBidList();
		List<Long> numList=managerBannerSubmit.getNumList();
		if(bidList!=null&&bidList.size()>0){
			for(int i=0;i<bidList.size();i++){
				Long bid=bidList.get(i);
				Long num=numList.get(i);
				if(num!=null){
					BannerInfoModel bannerInfoModel=new BannerInfoModel();
					bannerInfoModel.setBid(bid.intValue());
					bannerInfoModel.setOrderNum(num.intValue());
					bannerMapper.updateBannerInfoOrderNum(bannerInfoModel);
				}
			}	
		}
		
		List<BannerInfoModel> list=bannerMapper.queryBannerList();
		redisHotService.addBanner(list);

	}

	public void setBannerMapper(BannerMapper bannerMapper) {
		this.bannerMapper = bannerMapper;
	}

	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}
	
	
	

}
