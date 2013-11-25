package com.duanqu.compress.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.oss.model.OSSObject;
import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquConfig;
import com.duanqu.common.VideoTools;
import com.duanqu.common.bean.ContentBean;
import com.duanqu.redis.service.BaseRedisService;
import com.duanqu.redis.utils.key.ContentKeyManager;
import com.duanqu.redis.utils.key.JMSKeyManager;

public class VideoCompressServiceImpl extends BaseRedisService implements IVideoCompressService {

	@SuppressWarnings("unchecked")
	@Override
	public void convert2Gif() {
		String submitJson = null;
		ContentBean bean = null;
		do {
			String tempFolderName = UUID.randomUUID().toString();
			submitJson = (String) jmsTemplate.boundListOps(JMSKeyManager.getConvertMqKey()).rightPop();
			bean = JSON.parseObject(submitJson, ContentBean.class);
			if (bean != null) {
				try {
					OSSObject video = AliyunUploadUtils.getVideo(bean
							.getVideoUrlHD());
					if (video != null){
						InputStream in = video.getObjectContent();
						// 文件写在一个统一的文件夹内，方便转化完成后统一删除；
						// 下载视频文件写本地
						File videoFile = new File(
								DuanquConfig.getConvertResultPath()
										+ tempFolderName + File.separator
										+ bean.getVideoUrlHD());
						
						//创建文件夹
						if(!videoFile.exists()){
							videoFile.getParentFile().mkdirs();
						}
						OutputStream out = new FileOutputStream(videoFile);
						int length = 0;
						byte[] buf = new byte[1024];
						while ((length = in.read(buf)) != -1) {
							out.write(buf, 0, length);
						}
						in.close();
						out.close();
						long beginTime = System.currentTimeMillis();
						VideoTools.convert2Gif(videoFile, tempFolderName, tempFolderName+".gif");
						long endTime = System.currentTimeMillis();
						
						//System.out.println("===转化耗时："+(endTime - beginTime));
						File gifFile = new File(DuanquConfig.getConvertResultPath()+tempFolderName+".gif");
						InputStream gifIn = new FileInputStream(gifFile);
						String key = AliyunUploadUtils.uploadThumbnail(gifIn, gifFile.length(), "image/gif");
						//删除文件
						gifFile.delete();
						contentTemplate.boundHashOps(ContentKeyManager.getContentInfoKey(bean.getCid()+"")).put("gifUrl", key);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} while (submitJson != null);
	}

}
