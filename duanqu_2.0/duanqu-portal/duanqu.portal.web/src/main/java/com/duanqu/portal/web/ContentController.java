package com.duanqu.portal.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.duanqu.client.service.info.IClientContentService;
import com.duanqu.common.DuanquConfig;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.LogShowModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.user.IRedisUserService;

@Controller
public class ContentController {

	@Resource
	IRedisContentService redisContentService;

	@Resource
	IRedisUserService redisUserService;
	
	@Resource
	IClientContentService clientContentService;
	
	
	/*@RequestMapping(value = "/v/show", method = RequestMethod.GET)
	public String showVideo(
			@RequestParam(value = "cid", required = true, defaultValue = "0") long cid,
			ModelMap model,HttpServletRequest req) {
 
		ContentModel cModel = redisContentService.getContent(cid);
		if (cModel != null && cModel.getCid() > 0) {
			long uid = cModel.getUid();
			UserModel uModel = redisUserService.getUser(uid);
			String thumbnail = cModel.getThumbnailsUrl();
			if (thumbnail != null && cModel.getThumbnailsUrl().indexOf("http://") < 0){
				cModel.setThumbnailsUrl(DuanquConfig.getAliyunThumbnailDomain()
						+ cModel.getThumbnailsUrl());
			}
			if (cModel.getVideoUrl() != null && cModel.getVideoUrl().indexOf("http://") < 0){
				cModel.setVideoUrl(DuanquConfig.getAliyunVideoDomain()
						+ cModel.getVideoUrl());
			}
			if (cModel.getVideoUrlHD() != null && cModel.getVideoUrlHD().indexOf("http://") < 0){
				cModel.setVideoUrlHD(DuanquConfig.getAliyunHDVideoDomain()
						+ cModel.getVideoUrlHD());
			}
			model.put("content", cModel);
			model.put("user", uModel.asUserForm());
		}

		String clientType = this.getUs(req);
		System.out.println("==============="+clientType);
		if (clientType.equals("iPhone"))
			return "view_iphone";
		if (clientType.equals("Android"))
			return "view_android";
		return "view";
	}*/
	
	@RequestMapping(value = "/v/{key}", method = RequestMethod.GET)
	public String showVideoByKey(
			@PathVariable String key,
			@RequestParam(value="from",required=false) String from,
			ModelMap model) {
		long cid = redisContentService.getCid(key);
		if (cid > 0){
			ContentModel cModel = redisContentService.getContent(cid);
			if (cModel != null && cModel.getCid() > 0) {
				long uid = cModel.getUid();
				UserModel uModel = redisUserService.getUser(uid);
				String thumbnail = cModel.getThumbnailsUrl();
				if (thumbnail != null && cModel.getThumbnailsUrl().indexOf("http://") < 0){
					cModel.setThumbnailsUrl(DuanquConfig.getAliyunThumbnailDomain()
							+ cModel.getThumbnailsUrl());
				}
				if (cModel.getVideoUrl() != null && cModel.getVideoUrl().indexOf("http://") < 0){
					cModel.setVideoUrl(DuanquConfig.getAliyunVideoDomain()
							+ cModel.getVideoUrl());
				}
				if (cModel.getVideoUrlHD() != null && cModel.getVideoUrlHD().indexOf("http://") < 0){
					cModel.setVideoUrlHD(DuanquConfig.getAliyunHDVideoDomain()
							+ cModel.getVideoUrlHD());
				}
				
				if (StringUtils.hasText(cModel.getGifUrl()) && !cModel.getGifUrl().equalsIgnoreCase("null")){
					cModel.setGifUrl(DuanquConfig.getAliyunThumbnailDomain() + cModel.getGifUrl());
				}else{
					cModel.setGifUrl("");
				}
				
				model.put("content", cModel);
				model.put("user", uModel.asUserForm());
				model.put("signature", uModel.getSignature());
			}
			//TODO 插入日志表
			LogShowModel showLog = new LogShowModel();
			
			showLog.setCid(cid);
			showLog.setCreateTime(System.currentTimeMillis());
			if (from != null && from.equals("sina")){
				showLog.setType(2);
			}else{
				showLog.setType(1);
			}
			clientContentService.insertShowContent(showLog);
//			String clientType = this.getUs(req);
//			System.out.println("==============="+clientType);
//			if (clientType.equals("iPhone"))
//				return "view_iphone";
//			if (clientType.equals("Android"))
//				return "view_android";
			return "play";
		}else{
			return "404";
		}
	}
	
	private String getUs(HttpServletRequest req){
		String ua = req.getHeader("User-Agent");
		if (ua == null){
			return "web";
		}
		if (ua.contains("Android")){
			return "Android";
		}
		if (ua.contains("iPhone") || ua.contains("iPad")){
			return "iPhone";
		}
		return "web";
	}

}
