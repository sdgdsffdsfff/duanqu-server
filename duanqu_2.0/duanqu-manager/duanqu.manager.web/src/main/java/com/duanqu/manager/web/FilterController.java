package com.duanqu.manager.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.common.model.FilterWordModel;
import com.duanqu.common.submit.MFilterWordSubmit;
import com.duanqu.manager.service.filter.IManagerFilterService;


@Controller
@RequestMapping("filter.do")
public class FilterController {
	
	@Resource
	private IManagerFilterService managerFilterService;
	
	@RequestMapping(params = "method=list", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,MFilterWordSubmit mFilterWordSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("word");
		managerFilterService.queryFilterWordModels(mFilterWordSubmit);
		mav.addObject("vo", mFilterWordSubmit);
		return mav;
	}
	@RequestMapping(params = "method=enter", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enter(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView("word-add");
		return mav;
	}
	
	@RequestMapping(params = "method=saveWord", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveWord(HttpServletRequest request,HttpServletResponse response,FilterWordModel filterWordModel) throws Exception{
		ModelAndView mav=new ModelAndView("word-add");
		String message="";
		try {
			managerFilterService.insertFilterWordModel(filterWordModel);
			message="添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="添加失败";
		}
		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(params = "method=deleteWord", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteWord(HttpServletRequest request,HttpServletResponse response,@RequestParam("id") int id) throws Exception{
		ModelAndView mav=new ModelAndView("word");
		String message="";
		try {
			managerFilterService.deleteFilterWordModel(id);
			message="删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="删除失败";
		}
		MFilterWordSubmit mFilterWordSubmit=new MFilterWordSubmit();
		managerFilterService.queryFilterWordModels(mFilterWordSubmit);
		mav.addObject("vo",mFilterWordSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	
	
	
	

}
