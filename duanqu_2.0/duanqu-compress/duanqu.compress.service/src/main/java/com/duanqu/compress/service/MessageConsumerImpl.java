package com.duanqu.compress.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.alibaba.fastjson.JSON;
import com.duanqu.common.NoticeMessage;

public class MessageConsumerImpl implements MessageListener {
	
	IVideoCompressService videoCompressService;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {

		NoticeMessage notice = JSON.parseObject(message.getBody(),	NoticeMessage.class);
		if(notice.getMessageType().equals(NoticeMessage.MessageType.COMPRESS)){
			System.out.println("---------------------压缩视频-------------");
		}
		
		if (notice.getMessageType().equals(NoticeMessage.MessageType.CONVERT2GIF)){
			//System.out.println("=============生存GIF--------------");
			videoCompressService.convert2Gif();
		}
		
		
	}

	public void setVideoCompressService(IVideoCompressService videoCompressService) {
		this.videoCompressService = videoCompressService;
	}


	
	
}
