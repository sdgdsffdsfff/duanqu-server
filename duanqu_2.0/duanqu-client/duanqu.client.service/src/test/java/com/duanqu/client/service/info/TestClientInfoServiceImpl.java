package com.duanqu.client.service.info;


import javax.annotation.Resource;

import org.apache.http.conn.params.ConnConnectionParamBean;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.LogShareModel;
import com.duanqu.common.model.LogShowModel;

  
public class TestClientInfoServiceImpl extends BaseTestServiceImpl {
	

	@Resource
	IClientContentService clientContentService;
	 
	
	
	@Rollback(false)
	//@Test
	public void testInsertContentInfo(){
		ContentModel contentModel=new ContentModel();
		contentModel.setCid(16);
		contentModel.setTitle("dm.yang测试");
		contentModel.setVideoUrlHD("D:\\");
		contentModel.setVideoUrl("D:\\");
		contentModel.setPlayTime(22);
		contentModel.setThumbnailsUrl("D:\\");
		contentModel.setDescription("我们都是好孩子###图片#，你想干什么#  军事##动漫##火爆#");
		contentModel.setCreateTime(System.currentTimeMillis());
		contentModel.setCreateTime(System.currentTimeMillis());
		contentModel.setUid(1);
		contentModel.setCommentNum(23);
		contentModel.setLikeNum(23);
		contentModel.setForwardNum(23);
		contentModel.setShowTimes(12);
		contentModel.setLatilude(23.4f);  
		contentModel.setLongitude(23.3f);
		contentModel.setLocation("北部软件园");
		contentModel.setHeight(43.5f);
		contentModel.setWidth(54.5f);
		contentModel.setcStatus(0);
		contentModel.setIsPrivate(2);
		clientContentService.insertContentInfo(contentModel);
		
	}
	//@Test
	@Rollback(false)
	public void testUpdateContent(){
		
		clientContentService.updateContentStatusToDelete(10);
	}
	//@Test
	@Rollback(false)
	public void testUpdateContentMd5(){
		ContentModel contentModel=new ContentModel();
		contentModel.setCid(1);
		contentModel.setMd5("222");
		clientContentService.updateContentMd5(contentModel);
	}
//	@Test
	@Rollback(false)
	public void testUpdateContentShowTimes(){
		clientContentService.updateContentShowTimes(1, 5);
	}
	//@Test
	public void testGetContentModel(){
		ContentModel contentModel=clientContentService.getContentModel(1);
		System.out.println(contentModel.toString());
	}
	
	//@Test
	@Rollback(false)
	public void testUpdateContentSina(){
		//clientContentService.updateContentShowTimes(1, 5);
		clientContentService.updateContentSinaShareNum(1, 5);
		clientContentService.updateContentQuanShareNum(1, 5);
	}
	@Test
	@Rollback(false)
	public void testInsertShareContent(){
		LogShareModel logShareModel=new LogShareModel();
		logShareModel.setCid(1);
		logShareModel.setUid(1);
		logShareModel.setFlag(1);
		logShareModel.setType(1);
		logShareModel.setCreateTime(System.currentTimeMillis());
		
		clientContentService.insertShareContent(logShareModel);
		
		LogShowModel logShowModel=new LogShowModel();
		logShowModel.setCid(2);
		logShowModel.setType(2);
		logShowModel.setCreateTime(System.currentTimeMillis());
		clientContentService.insertShowContent(logShowModel);
		
	}

}
