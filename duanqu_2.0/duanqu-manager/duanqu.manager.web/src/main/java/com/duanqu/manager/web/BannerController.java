package com.duanqu.manager.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.manager.service.banner.IManagerBannerService;
import com.duanqu.manager.submit.ManagerBannerSubmit;

@Controller
@RequestMapping("banner.do")
public class BannerController {
	
	@Resource
	IManagerBannerService managerBannerService;
	
	@RequestMapping(params = "method=list", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,ManagerBannerSubmit managerBannerSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("banner");
		managerBannerService.queryBannerModelsList(managerBannerSubmit);
		mav.addObject("vo", managerBannerSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=enter", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enter(HttpServletRequest request,HttpServletResponse response ) throws Exception{
		ModelAndView mav=new ModelAndView("banner-add");
		return mav;
	}
	
	@RequestMapping(params = "method=saveUpload", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveUpload(HttpServletRequest request,HttpServletResponse response,ManagerBannerSubmit managerBannerSubmit ){
		ModelAndView mav=new ModelAndView("banner");
		String message="";
		try {
			managerBannerService.insertBannerInfo(managerBannerSubmit);
			message="添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="添加失败";
		}
		managerBannerService.queryBannerModelsList(managerBannerSubmit);
		mav.addObject("vo", managerBannerSubmit);
		mav.addObject("message", message);
		return mav;
	}
	@RequestMapping(params = "method=deleteBannerInfo", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteBannerInfo(HttpServletRequest request,HttpServletResponse response,ManagerBannerSubmit managerBannerSubmit ){
		ModelAndView mav=new ModelAndView("banner");
		String message="";
		try {
			managerBannerService.deleteBannerInfo(managerBannerSubmit.getBid());
			message="删除成功";
		} catch (Exception e) {
			message="删除失败";
			e.printStackTrace();
		}
		managerBannerService.queryBannerModelsList(managerBannerSubmit);
		mav.addObject("vo", managerBannerSubmit);
		mav.addObject("message", message);
		return mav;
	}
	@RequestMapping(params = "method=fbBannerInfo", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView fbBannerInfo(HttpServletRequest request,HttpServletResponse response,ManagerBannerSubmit managerBannerSubmit ){
		ModelAndView mav=new ModelAndView("banner");
		String message="";
		try {
			managerBannerService.editBannerInfo(managerBannerSubmit);
			message="发布成功";
		} catch (Exception e) {
			message="发布失败";
			e.printStackTrace();
		}
		managerBannerService.queryBannerModelsList(managerBannerSubmit);
		mav.addObject("vo", managerBannerSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	
	
}
