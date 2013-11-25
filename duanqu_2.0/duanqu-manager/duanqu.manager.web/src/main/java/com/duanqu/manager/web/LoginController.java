package com.duanqu.manager.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.common.model.UserAdminModel;
import com.duanqu.manager.service.user.IManagerUserService;

@Controller
@RequestMapping("login.do")
public class LoginController {
	
	
	@Resource
	private IManagerUserService managerUserService;
	
	@RequestMapping(params = "method=login", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,UserAdminModel userAdminModel){
		
		ModelAndView mav=new ModelAndView();
		String message="";
		UserAdminModel userAdminModel2=managerUserService.checkUserAdimin(userAdminModel);
		if(userAdminModel2==null){
			mav.setViewName("login");
			message="fail";
			mav.addObject("message", message);
		}else{
			request.getSession().setAttribute("userSession", userAdminModel2);
			mav=new ModelAndView("redirect:/content.do?method=list&cxrk=1");
		}
		return mav;
	}
	
	
	
	

}
