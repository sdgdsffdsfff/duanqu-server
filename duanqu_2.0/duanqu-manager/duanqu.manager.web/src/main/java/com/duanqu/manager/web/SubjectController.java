package com.duanqu.manager.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.manager.service.subject.IManagerSubjectService;
import com.duanqu.manager.submit.ManagerSubjectSubmit;

@Controller
@RequestMapping("subject.do")
public class SubjectController {
	
	
	@Resource
	private IManagerSubjectService managerSubjectService;
	
	
	@RequestMapping(params = "method=list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,ManagerSubjectSubmit managerSubjectSubmit){
		ModelAndView mav=new ModelAndView("subject_list");
		managerSubjectService.querySubjectList(managerSubjectSubmit);
		mav.addObject("vo", managerSubjectSubmit);
		return mav;
	}
	
	@RequestMapping(params = "method=saveSubject", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView saveSubject(HttpServletRequest request,HttpServletResponse response,ManagerSubjectSubmit managerSubjectSubmit){
		ModelAndView mav=new ModelAndView("subject_list");
		String message=managerSubjectService.insertSubject(managerSubjectSubmit);
		managerSubjectSubmit.setTitle("");
		managerSubjectService.querySubjectList(managerSubjectSubmit);
		mav.addObject("vo", managerSubjectSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(params = "method=enter", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView enter(HttpServletRequest request,HttpServletResponse response ){
		ModelAndView mav=new ModelAndView("subject_add");
		return mav;
	}
	
	@RequestMapping(params = "method=deleteSubject", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView deleteSubject(HttpServletRequest request,HttpServletResponse response,ManagerSubjectSubmit managerSubjectSubmit){
		ModelAndView mav=new ModelAndView("subject_list");
		String message="";
		try {
			managerSubjectService.deleteSubject(managerSubjectSubmit.getSid());
			message="删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="删除失败";
		}
		managerSubjectService.querySubjectList(managerSubjectSubmit);
		mav.addObject("vo", managerSubjectSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	

}
