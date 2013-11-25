package com.duanqu.client.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duanqu.common.DuanquUtils;
import com.duanqu.common.NoticeMessage;
import com.duanqu.common.Result;
import com.duanqu.common.model.FeedBackModel;
import com.duanqu.common.model.ReportModel;
import com.duanqu.redis.service.badword.IBadwordService;

@Controller
public class FeedbackController extends BaseController {
	
	@Resource
	IBadwordService badwordService;
	
	@RequestMapping(value = "/feedback", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result feedback(@RequestParam("content")String content,
			@RequestParam("token") String token) {
		Result result = new Result();
		FeedBackModel feedBack = new FeedBackModel();
		feedBack.setCreateTime(System.currentTimeMillis());
		feedBack.setFeedbackText(content);
		feedBack.setIsCheck(0);
		feedBack.setUid(getUid(token));
		try{
			redisJMSMessageService.insertFeedBackQueue(feedBack);
			duanquPublisher.publish(new NoticeMessage(DuanquUtils.getIp(),DuanquUtils.getIp(),NoticeMessage.MessageType.FEEDBACK));
		}catch(Exception e){
			e.printStackTrace();
		}
		result.setCode(200);
		result.setData("");
		result.setMessage("提交成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
	
	@RequestMapping(value = "/version/tips", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result getNewVersionTips() {
		Result result = new Result();
		String tips = badwordService.getNewVersionTips();
		result.setCode(200);
		result.setData(tips);
		result.setMessage("提交成功！");
		result.setTime(System.currentTimeMillis());
		return result;
	}
}
