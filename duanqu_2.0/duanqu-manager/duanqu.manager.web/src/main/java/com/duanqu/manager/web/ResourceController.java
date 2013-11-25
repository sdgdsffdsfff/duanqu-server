package com.duanqu.manager.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.manager.service.resource.IManagerResourceService;
import com.duanqu.manager.submit.ManagerSysResourceSubmit;

@Controller
@RequestMapping("resource.do")
public class ResourceController {
	
	
	@Resource
	private IManagerResourceService managerResourceService;
	
	
	@RequestMapping(params = "method=list", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,ManagerSysResourceSubmit managerSysResourceSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("resource");
		managerResourceService.querySysResourceList(managerSysResourceSubmit);
		mav.addObject("vo", managerSysResourceSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=enter", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enter(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView("resource-add");
		return mav;
	}
	
	@RequestMapping(params = "method=saveUpload", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveUpload(HttpServletRequest request,HttpServletResponse response,ManagerSysResourceSubmit managerSysResourceSubmit ){
		ModelAndView mav=new ModelAndView("resource");
		String message="";
		try {
			managerResourceService.insertSysResourceModel(managerSysResourceSubmit);
			message="添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="添加失败";
		}
		managerResourceService.querySysResourceList(managerSysResourceSubmit);
		mav.addObject("vo", managerSysResourceSubmit);
		mav.addObject("message", message);
		return mav;
	}
	@RequestMapping(params = "method=deleteBannerInfo", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteBannerInfo(HttpServletRequest request,HttpServletResponse response,ManagerSysResourceSubmit managerSysResourceSubmit  ){
		ModelAndView mav=new ModelAndView("resource");
		String message="";
		try {
			managerResourceService.deleteSysResource(managerSysResourceSubmit.getId());
			message="删除成功";
		} catch (Exception e) {
			message="删除失败";
			e.printStackTrace();
		}
		managerResourceService.querySysResourceList(managerSysResourceSubmit);
		mav.addObject("vo", managerSysResourceSubmit);
		mav.addObject("message", message);
		return mav;
	}
}
