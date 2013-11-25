package com.duanqu.manager.service.subject;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.model.SubjectModel;
import com.duanqu.manager.dao.SubjectMapper;
import com.duanqu.manager.submit.ManagerSubjectSubmit;
import com.duanqu.redis.service.hot.IRedisHotService;

public class ManagerSubjectServiceImpl implements IManagerSubjectService {

	Log logger = LogFactory.getLog(SubjectMapper.class);
	private SubjectMapper subjectMapper;
	private IRedisHotService redisHotService;

	@Override
	public void deleteSubject(int sid) {
		subjectMapper.deleteSubject(sid);
		try {
			//删除缓存
			redisHotService.deleteSubject(sid);
		} catch (Exception e) {
			logger.error("上传活动视频插入数据库失败:" + e.getMessage());
		}
	}

	@Override
	public String insertSubject(ManagerSubjectSubmit managerSubjectSubmit) {
		String message = "";
		//MultipartFile videoFile = managerSubjectSubmit.getVideoFile();
		//MultipartFile thumbnailFile=managerSubjectSubmit.getThumbnailsFile();
		MultipartFile imgFile = managerSubjectSubmit.getImgFile();
		MultipartFile explainFile = managerSubjectSubmit.getExplainFile();

		//String videoUrl = "";
	//	String thumbnailsUrl="";
		String contentType = "";
		String imgUrl = "";
		String explainUrl="";
/*		try {
			contentType = videoFile.getContentType();
			if (!"".equals(contentType) && !"video/mp4".equals(contentType)) {
				return message = "活动视频必须为MP4格式";
			}
			videoUrl = AliyunUploadUtils
					.uploadSystemHDVideo(videoFile.getInputStream(), videoFile
							.getBytes().length, contentType);
		} catch (Exception e) {
			logger.error("上传活动视频失败:" + e.getMessage());
		}
		try {
			contentType = thumbnailFile.getContentType();
			if (!"".equals(contentType) && !"image/jpeg".equals(contentType)) {
				return message = "活动视频首帧图必须为jpg格式";
			}
			thumbnailsUrl = AliyunUploadUtils.uploadSystemThumbnail(thumbnailFile
					.getInputStream(), thumbnailFile.getBytes().length, contentType);
		} catch (Exception e) {
			logger.error("上传活动视频首帧图失败:" + e.getMessage());
		}
		*/
		try {
			contentType = imgFile.getContentType();
			if (!"".equals(contentType) && !"image/jpeg".equals(contentType)) {
				return message = "活动图必须为jpg格式";
			}
			imgUrl = AliyunUploadUtils.uploadSystemThumbnail(imgFile
					.getInputStream(), imgFile.getBytes().length, contentType);
		} catch (Exception e) {
			logger.error("上传活动图片失败:" + e.getMessage());
		}
		try {
			contentType = explainFile.getContentType();
			if (!"".equals(contentType) && !"image/jpeg".equals(contentType)) {
				return message = "活动图必须为jpg格式";
			}
			explainUrl = AliyunUploadUtils.uploadSystemThumbnail(explainFile
					.getInputStream(), explainFile.getBytes().length, contentType);
		} catch (Exception e) {
			logger.error("上传活动图片失败:" + e.getMessage());
		}
		SubjectModel subjectModel = new SubjectModel();
		subjectModel.setTitle(managerSubjectSubmit.getTitle());
		subjectModel.setDescription(managerSubjectSubmit.getDescription());
		//subjectModel.setVideoUrl(videoUrl);
		subjectModel.setImgUrl(imgUrl);
		//subjectModel.setThumbnailsUrl(thumbnailsUrl);
		subjectModel.setExplainUrl(explainUrl);
		subjectModel.setInnerParam(managerSubjectSubmit.getInnerParam());
		subjectModel.setCreateTime(System.currentTimeMillis());
		try {
			subjectMapper.insertSubject(subjectModel);
			//插入缓存
			redisHotService.insertSubject(subjectModel);
			
			message = "上传成功";
		} catch (Exception e) {
			logger.error("上传活动视频插入数据库失败:" + e.getMessage());
			message = "上传失败";
		}
		return message;
	}

	@Override
	public void querySubjectList(ManagerSubjectSubmit managerSubjectSubmit) {
		long count = subjectMapper.querySubjectListCount(managerSubjectSubmit);
		managerSubjectSubmit.computerTotalPage(count);
		List<Map<String, Object>> list = subjectMapper
				.querySubjectList(managerSubjectSubmit);
		managerSubjectSubmit.setObjList(list);
	}

	public void setSubjectMapper(SubjectMapper subjectMapper) {
		this.subjectMapper = subjectMapper;
	}

	public void setRedisHotService(IRedisHotService redisHotService) {
		this.redisHotService = redisHotService;
	}

}
