package com.duanqu.manager.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.common.model.OtherTagModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.submit.MTagSubmit;
import com.duanqu.manager.service.content.IManagerContentService;
import com.duanqu.manager.service.tag.IManagerTagService;
@Controller
@RequestMapping("tag.do")
public class TagController {
	@Resource
	IManagerTagService managerTagService;
	
	@Resource
	IManagerContentService managerContentService;
	
	@RequestMapping(params = "method=editerTagList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView editerTagList(HttpServletRequest request,HttpServletResponse response,MTagSubmit mTagSubmit){
		ModelAndView mav=new ModelAndView("tag-edit");
		managerTagService.queryTagList(mTagSubmit);
		mav.addObject("vo", mTagSubmit);
		return mav;
	}
	@RequestMapping(params = "method=hotList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView hotList(HttpServletRequest request,HttpServletResponse response,MTagSubmit mTagSubmit){
		ModelAndView mav=new ModelAndView("tag-rec");
		managerTagService.queryTagHotList(mTagSubmit);
		mav.addObject("vo", mTagSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=hotImageList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView hotImageList(HttpServletRequest request,HttpServletResponse response,MTagSubmit mTagSubmit){
		ModelAndView mav=new ModelAndView("tag-rec-image");
		managerTagService.queryTagHotImageList(mTagSubmit);
		mav.addObject("vo", mTagSubmit);
		return mav;
	}
	@RequestMapping(params = "method=enterOtherTag", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterOtherTag(HttpServletRequest request,HttpServletResponse response,@RequestParam("tagType") int tagType){
		ModelAndView mav=new ModelAndView("tag-add");
		mav.addObject("otherTag", managerTagService.selectOtherTagModel(tagType));
		mav.addObject("tagType", tagType);
		return mav;
	}
	@RequestMapping(params = "method=saveOtherTag", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveOtherTag(HttpServletRequest request,HttpServletResponse response,OtherTagModel otherTagModel){
		ModelAndView mav=new ModelAndView("tag-add");
		String message="";
		try {
			managerTagService.insertOtherTag(otherTagModel);
			message="添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="添加失败";
		}
		mav.addObject("message", message);
		return mav;
	}
	@RequestMapping(params = "method=enterTag", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterTag(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit){
		ModelAndView mav=new ModelAndView("tag-add-content");
		mav.addObject("contentModel", managerContentService.getContentModel(Long.parseLong(mContentSubmit.getCid())));
		mav.addObject("mContentSubmit", mContentSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=enterHotTag", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterHotTag(HttpServletRequest request,HttpServletResponse response,@RequestParam("tid") long tid){
		ModelAndView mav=new ModelAndView("tag-add-hot");
		
		mav.addObject("tid", tid);
		return mav;
	}
	@RequestMapping(params = "method=saveHotTag", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveHotTag(HttpServletRequest request,HttpServletResponse response,MTagSubmit mTagSubmit){
		ModelAndView mav=new ModelAndView("tag-edit");
		String message="";
		try {
			managerTagService.insertTagHotImage(mTagSubmit);
			message="保存成功";
		} catch (Exception e) {
			message="保存失败";
			e.printStackTrace();
		}
		managerTagService.queryTagList(mTagSubmit);
		mav.addObject("vo", mTagSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(params = "method=saveTag", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> saveTag(HttpServletRequest request,HttpServletResponse response,@RequestParam("tid")long tid,@RequestParam("cid") long cid,@RequestParam("tagText") String tagText){
		String message="";
		Map<String,Object> map=new HashMap<String, Object>();
		TagModel tagModel=new TagModel();
		tagModel.setTagText(tagText);
		tagModel.setTid(tid);
		try {
			map=managerTagService.updateTagModel(tagModel,cid);
			message="success";
		} catch (Exception e) {
			e.printStackTrace();
			message="fail";
		}
		map.put("message", message);
		return map;
	}
	
	@RequestMapping(params = "method=deleteTsHot", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteTsHot(HttpServletRequest request,HttpServletResponse response,MTagSubmit mTagSubmit){
		ModelAndView mav=new ModelAndView("tag-rec");
		int type=mTagSubmit.getType();
		String message="";
		try {
			managerTagService.deleteTagHot(mTagSubmit);
			message="取消成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="取消失败";
		}
		if(type==1){
			managerTagService.queryTagHotList(mTagSubmit);
		}else{
			mav=new ModelAndView("tag-rec-image");
			managerTagService.queryTagHotImageList(mTagSubmit);
		}
		mav.addObject("message", message);
		mav.addObject("vo", mTagSubmit);
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param mTagSubmit
	 * @return刷新到缓存
	 */
	@RequestMapping(params = "method=fb",method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView fb(HttpServletRequest request,HttpServletResponse response,MTagSubmit mTagSubmit){
		ModelAndView mav=new ModelAndView("tag-rec-image");
		String message="";
		try {
			managerTagService.insertRedis(mTagSubmit);
			message="发布成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="发布失败";
		}
		managerTagService.queryTagHotImageList(mTagSubmit);
		mav.addObject("vo", mTagSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
}
