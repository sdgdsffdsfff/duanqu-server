package com.duanqu.manager.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.manager.service.content.IManagerContentService;
import com.duanqu.manager.submit.ManagerContentSubmit;


@Controller
@RequestMapping("content.do")
public class ContentController {
	
	@Resource
	IManagerContentService managerContentService;
	
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 * 内容列表
	 */
	@RequestMapping(params = "method=list", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("home");
		String nickName=mContentSubmit.getNickName()==null?"":mContentSubmit.getNickName();
		int cxrk=mContentSubmit.getCxrk();
		if(!"".equals(nickName)&&cxrk==3){
			mContentSubmit.setNickName(new String(nickName.getBytes("ISO-8859-1"),"utf-8"));
		}
		managerContentService.queryContentForms(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 * 推送列表
	 */
	@RequestMapping(params = "method=tsList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView tsList(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("push");
		managerContentService.queryTsContentForms(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 * 短趣君列表
	 */
	@RequestMapping(params = "method=dqjList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView dqjList(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("dqj");
		managerContentService.queryContentFormsByDqj(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		return mav;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * 进入推送页面
	 */
	@RequestMapping(params = "method=enterTs", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterTs(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit){
		ModelAndView mav=new ModelAndView("tsContent");
		mav.addObject("mContentSubmit", mContentSubmit);
		return mav;
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * 进入转发页面
	 */
	@RequestMapping(params = "method=enterForward", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterForward(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit){
		ModelAndView mav=new ModelAndView("forward");
		mav.addObject("mContentSubmit", mContentSubmit);
		mav.addObject("listPublishUser", managerContentService.queryPublishUserList());
		return mav;
	}
	
	
	
	
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 * 保存推送信息
	 */
	@RequestMapping(params = "method=tsrecommend", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView tsrecommend(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("home");
		String message="";
		try {
			managerContentService.insertRecommendModel(mContentSubmit);
			message="success";
		} catch (Exception e) {
			message="fail";
			e.printStackTrace();
		}
		managerContentService.queryContentForms(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("message", message);
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param cid
	 * @param mContentSubmit
	 * @param sStatus
	 * @return
	 * @throws Exception
	 * 内容状态操作
	 */
	@RequestMapping(params = "method=deleteContent", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteContent(HttpServletRequest request,HttpServletResponse response,@RequestParam("cid") long cid,MContentSubmit mContentSubmit,@RequestParam("sStatus") int sStatus,@RequestParam("dqj") int dqj) throws Exception{
		ModelAndView mav=new ModelAndView("home");
		ContentModel contentModel=new ContentModel();
		contentModel.setCid(cid);
		contentModel.setcStatus(sStatus);
		String ts="";
		try {
			managerContentService.updateContent(contentModel);
			if(sStatus==2){
				ts="2Success";
			}else if(sStatus==3){
				ts="3Succes";
			}
		} catch (Exception e) {
			if(sStatus==2){
				ts="2Fail";
			}else if(sStatus==3){
				ts="3Fail";
			}
			e.printStackTrace();
		}
		managerContentService.queryContentForms(mContentSubmit);
		if(dqj==1){
			mav=new ModelAndView("dqj");
			managerContentService.queryContentFormsByDqj(mContentSubmit);
		}
		mav.addObject("vo", mContentSubmit);
		mav.addObject("ts", ts);
	    return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param cid
	 * @return
	 * 进入评论页面
	 */
	@RequestMapping(params = "method=comment", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView comment(HttpServletRequest request,HttpServletResponse response,@RequestParam("cid") long cid){
		ModelAndView mav=new ModelAndView("comment-add");
		mav.addObject("cid", cid);
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param commentModel
	 * @return
	 * 保存评论
	 */
	@RequestMapping(params = "method=saveComment", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveComment(HttpServletRequest request,HttpServletResponse response,CommentModel commentModel){
		ModelAndView mav=new ModelAndView("comment-add");
		mav.addObject("message", managerContentService.insertComment(commentModel));
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * 进入上传界面
	 */
	@RequestMapping(params = "method=enterUpload", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterUpload(HttpServletRequest request,HttpServletResponse response,@RequestParam("dqj") int dqj){
		ModelAndView mav=new ModelAndView("upvideo");
		mav.addObject("dqj", dqj);
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param managerContentSubmit
	 * @return
	 * 保存上传信息
	 */
	@RequestMapping(params = "method=saveUpload", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView saveUpload(HttpServletRequest request,HttpServletResponse response,ManagerContentSubmit managerContentSubmit,@RequestParam("dqj") int dqj){
		ModelAndView mav=new ModelAndView("upvideo");
		mav.addObject("message", managerContentService.insertContentInfo(managerContentSubmit));
		mav.addObject("dqj", dqj);
		return mav;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param cid
	 * @param type
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 * 取消推送
	 */
	@RequestMapping(params = "method=deleteTs", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteTs(HttpServletRequest request,HttpServletResponse response,@RequestParam("cid") long cid,@RequestParam("typeT") int typeT,MContentSubmit mContentSubmit) throws Exception{
		ModelAndView mav=new ModelAndView("push");
		String message="";
		try {
			managerContentService.deleteRecommendModel(cid, typeT);
			message="取消成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="取消失败";
		}
		managerContentService.queryTsContentForms(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("message", message);
		return mav;
	}
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 * 举报列表
	 */
	@RequestMapping(params = "method=reportList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView reportList(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("jubao");
		managerContentService.queryReportList(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		return mav;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=enterSinaQuan", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView enterSinaQuan(HttpServletRequest request,HttpServletResponse response,@RequestParam("type") int type,@RequestParam("cid") long cid ) throws Exception{
		ModelAndView mav=new ModelAndView("content-sinaquan");
		Map<String, Object> map=managerContentService.getContentSinaQuanNum(cid);
		mav.addAllObjects(map);
		mav.addObject("cid", cid);
		mav.addObject("type", type);
		return mav;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param cid
	 * @param mContentSubmit
	 * @param sStatus
	 * @return
	 * @throws Exception
	 * 举报信息操作同内容列表
	 */
	@RequestMapping(params = "method=deleteReport", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteReport(HttpServletRequest request,HttpServletResponse response,@RequestParam("cid") long cid,MContentSubmit mContentSubmit,@RequestParam("sStatus") int sStatus) throws Exception{
		ModelAndView mav=new ModelAndView("jubao");
		ContentModel contentModel=new ContentModel();
		contentModel.setCid(cid);
		contentModel.setcStatus(sStatus);
		String ts="";
		try {
			managerContentService.updateContent(contentModel);
			if(sStatus==2){
				ts="2Success";
			}else if(sStatus==3){
				ts="3Succes";
			}
		} catch (Exception e) {
			if(sStatus==2){
				ts="2Fail";
			}else if(sStatus==3){
				ts="3Fail";
			}
			e.printStackTrace();
		}
		managerContentService.queryReportList(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("ts", ts);
	    return mav;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param mContentSubmit
	 * @param jbid
	 * @return
	 * @throws Exception
	 * 驳回举报
	 */
	@RequestMapping(params = "method=updateReport", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView updateReport(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit,@RequestParam("jbid") long jbid ) throws Exception{
		ModelAndView mav=new ModelAndView("jubao");
		String message="";
		try {
			managerContentService.updateReport(jbid);
			message="驳回成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="驳回失败";
		}
		managerContentService.queryReportList(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("message", message);
		return mav;
	}
	@RequestMapping(params = "method=setList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView setList(HttpServletRequest request,HttpServletResponse response,@RequestParam("cid") long cid,@RequestParam("type") int type){
		ModelAndView mav=new ModelAndView("setList");
		mav.addAllObjects(managerContentService.getSetContentList(cid,type));
		mav.addObject("cid", cid);
		mav.addObject("type", type);
		return mav;
	}
	
	
	@RequestMapping(params = "method=tsHotList", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView tsHotList(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit ) throws Exception{
		ModelAndView mav=new ModelAndView("push-hot");
		managerContentService.queryTsHotContent(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		
		return mav;
	}
	
	@RequestMapping(params = "method=deleteHotContent", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView deleteHotContent(HttpServletRequest request,HttpServletResponse response,MContentSubmit mContentSubmit,@RequestParam("type") int type ) throws Exception{
		ModelAndView mav=new ModelAndView("push-hot");
		String message="";
		try {
			managerContentService.deleteTsHotContent(Long.parseLong(mContentSubmit.getCid()),type);
			message="取消成功";
		} catch (Exception e) {
			e.printStackTrace();
			message="取消失败";
		}
		managerContentService.queryTsHotContent(mContentSubmit);
		mav.addObject("vo", mContentSubmit);
		mav.addObject("message", message);
		return mav;
	}
	
	
	
	
}
