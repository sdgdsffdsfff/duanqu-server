package com.duanqu.manager.service.content;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquStringUtils;
import com.duanqu.common.DuanquUtils;
import com.duanqu.common.dao.content.ContentMapper;
import com.duanqu.common.model.CommentModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.ContentTagModel;
import com.duanqu.common.model.ForwardContentModel;
import com.duanqu.common.model.RecommendModel;
import com.duanqu.common.model.SetContentModel;
import com.duanqu.common.model.TagModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.vo.ManagerContentForm;
import com.duanqu.manager.dao.CommentMapper;
import com.duanqu.manager.dao.TagMapper;
import com.duanqu.manager.submit.ManagerContentSubmit;
import com.duanqu.redis.service.comment.IRedisCommentService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.hot.IRedisHotService;
import com.duanqu.redis.service.syn.comment.ICommentSynService;
import com.duanqu.redis.service.syn.content.IContentSynService;

public class ManagerContentServiceImpl implements IManagerContentService {

	Log log = LogFactory.getLog(ManagerContentServiceImpl.class);
	private ContentMapper contentMapper;

	private CommentMapper commentMapper;

	private IRedisCommentService redisCommentService;

	private IRedisContentService redisContentService;

	private IRedisHotService redisHotService;

	private IContentSynService contentSynService;

	private ICommentSynService commentSynService;

	private TagMapper tagMapper;

	@Override
	public void queryContentForms(MContentSubmit mContentSubmit)
			throws Exception {
		long count;
		List<ManagerContentForm> list = new ArrayList<ManagerContentForm>();
		int cxrk = mContentSubmit.getCxrk();
		if (mContentSubmit.getUploadTimeQ() != null
				&& mContentSubmit.getUploadTimeQ() != "") {
			mContentSubmit.setUploadTimeQL(mContentSubmit.toLong(mContentSubmit
					.getUploadTimeQ()));
		}
		if (mContentSubmit.getUploadTimeZ() != null
				&& mContentSubmit.getUploadTimeZ() != "") {
			mContentSubmit.setUploadTimeZL(mContentSubmit.toLong(mContentSubmit
					.getUploadTimeZ()));
		}
		if (cxrk == 2) {
			count = contentMapper.queryContentFormsTagCount(mContentSubmit);
			mContentSubmit.computerTotalPage(count);
			list = contentMapper.queryContentFormsTag(mContentSubmit);
		} else if (cxrk == 4) {
			count=contentMapper.queryContentFormsSubjectCount(mContentSubmit);
			mContentSubmit.computerTotalPage(count);
			list=contentMapper.queryContentFormsSubject(mContentSubmit);
		}
		else {
			count = contentMapper.queryContentFormsCount(mContentSubmit);
			mContentSubmit.computerTotalPage(count);
			list = contentMapper.queryContentForms(mContentSubmit);
		}
		List<ManagerContentForm> newList = new ArrayList<ManagerContentForm>();
		if (list != null && list.size() > 0) {
			for (Iterator<ManagerContentForm> iterator = list.iterator(); iterator
					.hasNext();) {
				ManagerContentForm contentForm = iterator.next();
				List<TagModel> list2 = contentMapper.queryTagModels(contentForm
						.getCid());
				contentForm.setList(list2);
				contentForm.setUploadTimeStr(contentForm.toDate(contentForm
						.getUploadTime()));
				newList.add(contentForm);
			}
		}
		mContentSubmit.setObjList(newList);
	}

	@Override
	public void queryTsContentForms(MContentSubmit mContentSubmit)
			throws Exception {
		long count = contentMapper.queryTsContentFormsCount(mContentSubmit);
		mContentSubmit.computerTotalPage(count);
		List<Map<String, Object>> list = contentMapper
				.queryTsContentForms(mContentSubmit);
		mContentSubmit.setObjList(list);
	}

	@Override
	public void duanquUpdateContentShowTimes(long cid, int st) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("st", st);
		contentMapper.updateContentShowTimes(map);
	}

	@Override
	public void queryContentFormsByDqj(MContentSubmit mContentSubmit)
			throws Exception {
		long count = contentMapper.queryContentFormsDqjCount(mContentSubmit);
		mContentSubmit.computerTotalPage(count);
		List<ManagerContentForm> list = contentMapper
				.queryContentFormsDqj(mContentSubmit);
		List<ManagerContentForm> newList = new ArrayList<ManagerContentForm>();
		if (list != null && list.size() > 0) {
			for (Iterator<ManagerContentForm> iterator = list.iterator(); iterator
					.hasNext();) {
				ManagerContentForm contentForm = iterator.next();
				List<TagModel> list2 = contentMapper.queryTagModels(contentForm
						.getCid());
				contentForm.setList(list2);
				contentForm.setUploadTimeStr(contentForm.toDate(contentForm
						.getUploadTime()));
				newList.add(contentForm);
			}
		}
		mContentSubmit.setObjList(newList);
	}

	@Override
	public void insertRecommendModel(MContentSubmit mContentSubmit)
			throws Exception {
		String cidStr = mContentSubmit.getCid();
		if (cidStr.endsWith(",")) {
			cidStr = cidStr.substring(0, cidStr.length() - 1);
		}
		String[] cidArray = cidStr.split(",");
		RecommendModel recommendModel = new RecommendModel();
		int type = Integer.parseInt(mContentSubmit.getType());
		int recommend = mContentSubmit.getRecommend();
		String tslx = mContentSubmit.getTslx();
		recommendModel.setType(type);
		recommendModel.setIsShow(Integer.parseInt(tslx));
		recommendModel.setRecommend(recommend);
		if (cidArray != null && cidArray.length > 0) {
			if (type == 0) {// 推送到首页编辑
				if ("0".equals(tslx)) {// 立即推送
					for (int i = 0; i < cidArray.length; i++) {
						long cid = Long.parseLong(cidArray[i]);
						recommendModel.setCid(cid);
						recommendModel
								.setCreateTime(System.currentTimeMillis());
						RecommendModel rm = contentMapper
								.getRecommendModel(recommendModel);
						if (rm == null) {
							contentMapper.insertRecommendModel(recommendModel);
						} else {
							contentMapper
									.updateRecommendCreateTime(recommendModel);
						}
						redisContentService.insertEditorRecommend(cid);

					}
				} else {// 定时推送
					for (int i = 0; i < cidArray.length; i++) {
						long cid = Long.parseLong(cidArray[i]);
						long tssj = mContentSubmit.toLong(mContentSubmit
								.getTssjStr());
						recommendModel.setCid(cid);
						recommendModel.setCreateTime(tssj);
						RecommendModel rm = contentMapper
								.getRecommendModel(recommendModel);
						if (rm == null) {
							contentMapper.insertRecommendModel(recommendModel);
						} else {
							contentMapper
									.updateRecommendCreateTime(recommendModel);
						}
					}
				}
			} else {// 推送到发现
				if ("0".equals(tslx)) {// 立即推送
					if (recommend == 1) {
						for (int i = 0; i < cidArray.length; i++) {
							long cid = Long.parseLong(cidArray[i]);
							recommendModel.setCreateTime(System
									.currentTimeMillis());
							recommendModel.setCid(cid);
							RecommendModel rm = contentMapper
									.getRecommendModel(recommendModel);
							if (rm == null) {
								contentMapper
										.insertRecommendModel(recommendModel);
							} else {
								contentMapper
										.updateRecommendCreateTime(recommendModel);
							}
							redisContentService.insertContentFindList(cid, 1);// 插入发现缓存
						}
					} else {
						for (int i = 0; i < cidArray.length; i++) {
							long cid = Long.parseLong(cidArray[i]);
							recommendModel.setCreateTime(System
									.currentTimeMillis());
							recommendModel.setCid(cid);
							RecommendModel rm = contentMapper
									.getRecommendModel(recommendModel);
							if (rm == null) {
								contentMapper
										.insertRecommendModel(recommendModel);
							} else {
								contentMapper
										.updateRecommendCreateTime(recommendModel);
							}
							redisContentService.insertContentFindList(cid, 0);// 插入发现缓存
						}
					}

				} else {// 定时推送
					for (int i = 0; i < cidArray.length; i++) {
						long cid = Long.parseLong(cidArray[i]);
						long tssj = mContentSubmit.toLong(mContentSubmit
								.getTssjStr());
						recommendModel.setCreateTime(tssj);
						recommendModel.setCid(cid);
						RecommendModel rm = contentMapper
								.getRecommendModel(recommendModel);
						if (rm == null) {
							contentMapper.insertRecommendModel(recommendModel);
						} else {
							contentMapper
									.updateRecommendCreateTime(recommendModel);
						}
					}
				}
			}
		}
	}

	/*
	 * 
	 * job执行定时推送信息
	 */
	@Override
	public void insertDsTsContent() {
		List<RecommendModel> list = contentMapper.queryReList(System
				.currentTimeMillis());
		if (list != null && list.size() > 0) {
			for (RecommendModel recommendModel : list) {
				int type = recommendModel.getType();
				long cid = recommendModel.getCid();
				int recommend = recommendModel.getRecommend();
				if (type == 0) {
					redisContentService.insertEditorRecommend(cid);//
				} else {
					if (recommend == 1) {
						redisContentService.insertContentFindList(cid, 1);// 插入发现缓存
					} else {
						redisContentService.insertContentFindList(cid, 0);// 插入发现缓存
					}
				}
				contentMapper.updateRecommendShow(recommendModel);// 更新推送信息发送状态
			}
		}
	}

	@Override
	public Map<String, Object> updateContentDescription(
			ContentModel contentModel) {
		long cid = contentModel.getCid();
		List<TagModel> tagList = contentMapper.queryTagModels(cid);
		List<String> tagText = new ArrayList<String>();
		Set<String> set = DuanquStringUtils.getTags(contentModel
				.getDescription());
		Map<String, Object> map = new HashMap<String, Object>();
		String ss = "";
		ContentTagModel contentTagModel = new ContentTagModel();
		contentTagModel.setCid(cid);
		for (TagModel tagModel : tagList) {
			String tagValue = tagModel.getTagText();
			long tid = tagModel.getTid();
			if (!set.contains(tagValue)) {
				ContentTagModel ctm = new ContentTagModel();
				ctm.setTid(tid);
				ctm.setCid(cid);
				tagMapper.deleteContentTag(ctm);
			}
			tagText.add(tagValue);
		}
		for (String text : set) {
			if (!tagText.contains(text)) {
				TagModel tagModel = contentMapper.selectTagInfo(text);
				if (tagModel == null) {
					TagModel tag = new TagModel();
					tag.setTagText(text);
					contentMapper.insertTagInfo(tag);
					contentTagModel.setTid(tag.getTid());
				} else {
					contentTagModel.setTid(tagModel.getTid());
				}
				contentMapper.insertContentTag(contentTagModel);
			}
			ss += "<a href>" + text + "</a>";
		}

		map.put("newText", ss);
		map.put("desc", contentModel.getDescription());
		map.put("cid", cid);
		contentMapper.updateContentDescription(contentModel);
		contentSynService.synContentEdit(cid, contentModel.getDescription());
		return map;
	}

	@Override
	public ContentModel getContentModel(long cid) {
		ContentModel cm = contentMapper.getContentModel(cid);
		return cm;
	}

	@Override
	public String insertComment(CommentModel commentModel) {
		String message = "";
		long cid = commentModel.getCid();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", -1);
		List<Long> list = commentMapper.queryUserList(cid);
		if (list == null || list.size() <= 0) {
			message = "对不起，马甲用户已经分配完，请添加马甲用户";
		} else {
			Random random = new Random();
			long uid = list.get(random.nextInt(list.size()));
			CommentModel newCm = new CommentModel();
			newCm.setUid(uid);
			newCm.setCid(cid);
			newCm.setCommentText(commentModel.getCommentText());
			newCm.setReplyUid(0);
			newCm.setCreateTime(System.currentTimeMillis());
			newCm.setParentId(0);
			newCm.setRootId(0);
			try {
				newCm.setId(redisCommentService.getCommentId());// 从redis获取主键值
				commentSynService.synCommentAdd(newCm);// 插入redis
				commentMapper.insertContentComment(newCm);// 插入评论表
				commentMapper.updateContentInfoFalse(map);// 更新假评论数
				message = "评论成功";
			} catch (Exception e) {
				e.printStackTrace();
				message = "评论失败";
			}
		}
		return message;
	}

	@Override
	public void queryReportList(MContentSubmit mContentSubmit) {
		long count = contentMapper.queryReportListCount(mContentSubmit);
		mContentSubmit.computerTotalPage(count);
		List<Map<String, Object>> list = contentMapper
				.queryReportList(mContentSubmit);
		mContentSubmit.setObjList(list);
	}

	@Override
	public String insertContentInfo(ManagerContentSubmit managerContentSubmit) {
		String message = "";
		ContentModel contentModel = new ContentModel();
		MultipartFile videoFile = managerContentSubmit.getVideo();// 视频地址
		MultipartFile thumbnailsFile = managerContentSubmit.getThumbnails();// 首帧图片
		int isShow = managerContentSubmit.getIsShow();
		int isDqj = managerContentSubmit.getIsDqj();
		int top = managerContentSubmit.getTop();

		String videoUrlHD = "";
		String thumbnailsUrl = "";
		if (isDqj == 1) {
			contentModel.setUid(1);// 短趣君发布，直接把短趣君的ID进行赋值，这里举例用uid为1表示短趣君
		} else {
			List<Long> listLongs = contentMapper.queryUserList();
			if (listLongs == null || listLongs.size() <= 0) {
				message = "无可用马甲用户";
				return message;
			} else {
				Random random = new Random();
				contentModel.setUid(listLongs.get(random.nextInt(listLongs
						.size())));
			}
		}
		try {
			videoUrlHD = AliyunUploadUtils.uploadHDVideo(videoFile
					.getInputStream(), videoFile.getBytes().length, videoFile
					.getContentType());
			thumbnailsUrl = AliyunUploadUtils.uploadThumbnail(thumbnailsFile
					.getInputStream(), thumbnailsFile.getBytes().length,
					thumbnailsFile.getContentType());
		} catch (Exception e) {
			e.printStackTrace();
			message = "上传阿里云平台失败";
			return message;
		}
		contentModel.setVideoUrlHD(videoUrlHD);
		contentModel.setThumbnailsUrl(thumbnailsUrl);
		contentModel.setCreateTime(System.currentTimeMillis());
		contentModel.setDescription(managerContentSubmit.getDescription());
		contentModel.setHeight(600);
		contentModel.setWidth(600);
		contentModel.setLongitude(0);
		contentModel.setLatilude(0);
		contentModel.setPlayTime(8);// 暂时视频时长这里写死，随后会在表单中增加
		contentModel.setKey(DuanquUtils.md5(System.currentTimeMillis() + ""));
		contentModel.setCid(redisContentService.getContentId());// 从redis里获取内容的id
		contentModel.setTop(top);
		if (isShow == 0) {
			// 立即发送
			try {
				contentModel.setUploadTime(System.currentTimeMillis());
				contentModel.setIsShow(1);
				insertContentInfo(contentModel);
				// redisContentService.insertContent(contentModel);//插入缓存
				contentSynService.synContentAdd(contentModel);
				message = "上传成功";
			} catch (Exception e) {
				e.printStackTrace();
				message = "上传失败";
			}

		} else {
			// 定时发送
			try {
				contentModel.setUploadTime(managerContentSubmit
						.toLong(managerContentSubmit.getUploadTime()));
				contentModel.setIsShow(0);
				insertContentInfo(contentModel);// 插入数据库
				message = "上传成功";
			} catch (ParseException e) {
				e.printStackTrace();
				message = "上传失败";
			}
		}
		return message;

	}

	public void insertContentInfo(ContentModel contentModel) {
		contentMapper.insertContentInfo(contentModel);
		ContentTagModel contentTagModel = new ContentTagModel();
		contentTagModel.setCid(contentModel.getCid());
		String description = contentModel.getDescription();
		String substr = "";
		Set<String> set = DuanquStringUtils.getTags(description);
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			substr = iterator.next().replaceAll("#", "").trim();
			TagModel tagModel = contentMapper.selectTagInfo(substr);
			if (tagModel == null) {
				TagModel tag = new TagModel();
				tag.setTagText(substr);
				contentMapper.insertTagInfo(tag);
				contentTagModel.setTid(tag.getTid());
			} else {
				contentTagModel.setTid(tagModel.getTid());
			}
			contentMapper.insertContentTag(contentTagModel);
		}
	}

	@Override
	public void insertHotContentList() {
		/*
		 * long a=24*60*60*1000; long b=System.currentTimeMillis(); long c=b-a;
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long todayTime = calendar.getTimeInMillis();
		int a = 24 * 60 * 60 * 1000;
		long yesterdayTime = todayTime - a;// 昨天0点的毫秒数
		long afterTime = todayTime - a * 2;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("todayTime", todayTime);
		map.put("yesterdayTime", yesterdayTime);
		map.put("afterTime", afterTime);
		List<Long> list =contentMapper.totalHotContent();//调用存储过程统计
		//List<Long> list = contentMapper.selectHotContentList(map);
		List<SetContentModel> listSet = contentMapper.querySetContentModels();
		if (listSet != null) {
			for (SetContentModel set : listSet) {
				int order = set.getOrderNum();
				if (order > list.size()) {
					list.add(set.getCid());
				} else {
					list.add(set.getOrderNum() - 1, set.getCid());
				}
			}
		}
		if (list.size() > 501) {
			list = list.subList(0, 501);
		}
		// 王海华修改，第二个参数type = 0 表示原来热门榜单的列表
		redisHotService.insertHotContent(list, 0);
	}

	@Override
	public void allHotTop() {
		List<Long> list = contentMapper.selectNewAllHotContentList();
		List<SetContentModel> listSet = contentMapper
				.queryNewSetContentModels();
		if (listSet != null) {
			for (SetContentModel set : listSet) {
				int order = set.getOrderNum();
				if (order > list.size()) {
					list.add(set.getCid());
				} else {
					list.add(set.getOrderNum() - 1, set.getCid());
				}
			}
		}
		if (list.size() > 100) {
			list = list.subList(0, 100);
		}
		// 王海华修改，第二个参数type = 1 表示新版本总榜
		redisHotService.insertHotContent(list, 1);

	}

	@Override
	public void weekHotTop() {
		List<Long> list = contentMapper.selectNewWeekHotContentList(System
				.currentTimeMillis());
		redisHotService.insertHotContent(list, 2);
	}

	@Override
	public void queryTsHotContent(MContentSubmit mContentSubmit) {
		long count = contentMapper.queryTsHotContentListCount(mContentSubmit);
		mContentSubmit.computerTotalPage(count);
		List<Map<String, Object>> list = contentMapper
				.queryTsHotContentList(mContentSubmit);
		mContentSubmit.setObjList(list);
	}

	@Override
	public void deleteTsHotContent(long cid, int type) {
		contentMapper.deleteTsHotContent(cid, type);
	}

	@Override
	public void deleteRecommendModel(long cid, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("type", type);
		contentMapper.deleteRecommendModel(map);
		if (type == 0) {
			redisContentService.deleteEditorRecommend(cid);
		} else {
			redisContentService.deleteContentFindList(cid);
		}

	}

	@Override
	public void updateContent(ContentModel contentModel) {
		contentMapper.updateContent(contentModel);
		// redisContentService.updateContentStatus(contentModel.getCid(),
		// contentModel.getcStatus());//更新缓存
		if (contentModel != null && contentModel.getcStatus() != 4) {
			contentSynService.synContentDelete(contentModel.getCid(),
					contentModel.getcStatus());
		}

	}

	@Override
	public void insertSetContent(long cid, int order_num, int type) {
		SetContentModel setContentModel = new SetContentModel();
		setContentModel.setCid(cid);
		setContentModel.setOrderNum(order_num);
		setContentModel.setType(type);
		setContentModel.setCreateTime(System.currentTimeMillis());
		SetContentModel setContentModel2 = contentMapper.getSetContentModel(
				cid, type);
		if (setContentModel2 != null) {
			contentMapper.updateSetContent(setContentModel);
		} else {
			contentMapper.insertSetContent(setContentModel);
		}
	}

	@Override
	public List<SetContentModel> queryContentModels() {
		return contentMapper.querySetContentModels();
	}

	@Override
	public Map<String, Object> getSetContentList(long cid, int type) {

		return contentMapper.getSetContentList(cid, type);
	}

	@Override
	public Map<String, Object> getContentSinaQuanNum(long cid) {
		return contentMapper.getContentSinaQuan(cid);
	}

	@Override
	public void updateReport(long jbid) {
		contentMapper.updateReport(jbid);

	}

	@Override
	public void updateContentSinaQuanNum(long cid, int type, int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("num", num);
		if (type == 1) {// 新浪
			contentMapper.updateContentSinaNum(map);
			contentSynService.synContentSinaShareNum(cid, num);
		} else if (type == 3) {
			contentMapper.updateContentFalseShowTimes(map);
			contentSynService.synContentShowTimes(cid, num);
		} else {
			contentMapper.updateContentQuanNum(map);
			contentSynService.synContentFriendsShareNum(cid, num);
		}
	}

	@Override
	public void insertRedisFromContent() {
		List<ContentModel> list = contentMapper.selectContentModels(System
				.currentTimeMillis());
		if (list != null && list.size() > 0) {
			for (Iterator<ContentModel> iterator = list.iterator(); iterator
					.hasNext();) {
				ContentModel contentModel = iterator.next();
				// redisContentService.insertContent(contentModel);//插入缓存
				contentSynService.synContentAdd(contentModel);
				contentMapper.updateContentShow(contentModel.getCid());// 更新投放状态
			}
		}
	}

	@Override
	public List<Map<String, Object>> queryPublishUserList() {

		return contentMapper.queryPublishUserList();
	}

	@Override
	public String saveForward(long uid, String cid) {
		String message = "";
		if (cid.endsWith(",")) {
			cid = cid.substring(0, cid.length() - 1);
		}
		String[] cidArray = cid.split(",");
		for (int i = 0; i < cidArray.length; i++) {
			ForwardContentModel forward = contentMapper
					.getForwardContentModel(uid, Long.parseLong(cidArray[i]));
			if (forward != null) {
				message = "该用户已经转发过了你选中的该条内容：" + cidArray[i];
				return message;
			}
		}
		try {
			for (int j = 0; j < cidArray.length; j++) {
				long cidLong = Long.parseLong(cidArray[j]);
				ForwardContentModel forwardContentModel = new ForwardContentModel();
				forwardContentModel.setCid(cidLong);
				forwardContentModel.setUid(uid);
				forwardContentModel.setCreateTime(System.currentTimeMillis());

				// 更新缓存
				message = contentSynService.synContentForward(uid, cidLong);
			}
		} catch (Exception e) {
			message = "转发失败";
			log.error("转发失败:" + e.getMessage());

		}
		return message;
	}

	public ContentMapper getContentMapper() {
		return contentMapper;
	}

	public void setContentMapper(ContentMapper contentMapper) {
		this.contentMapper = contentMapper;
	}

	public CommentMapper getCommentMapper() {
		return commentMapper;
	}

	public void setCommentMapper(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	public IRedisCommentService getRedisCommentService() {
		return redisCommentService;
	}

	public void setRedisCommentService(IRedisCommentService redisCommentService) {
		this.redisCommentService = redisCommentService;
	}

	public IRedisContentService getRedisContentService() {
		return redisContentService;
	}

	public void setRedisContentService(IRedisContentService redisContentService) {
		this.redisContentService = redisContentService;
	}

	public IRedisHotService getRedisHotService() {
		return redisHotService;
	}

	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}

	public IContentSynService getContentSynService() {
		return contentSynService;
	}

	public void setContentSynService(IContentSynService contentSynService) {
		this.contentSynService = contentSynService;
	}

	public ICommentSynService getCommentSynService() {
		return commentSynService;
	}

	public void setCommentSynService(ICommentSynService commentSynService) {
		this.commentSynService = commentSynService;
	}

	public void setTagMapper(TagMapper tagMapper) {
		this.tagMapper = tagMapper;
	}

}
