package com.duanqu.client.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.duanqu.client.search.IESSearcher;
import com.duanqu.client.service.comment.BaseTestServiceImpl;
import com.duanqu.client.service.comment.IClientCommentService;
import com.duanqu.client.service.forwardcontent.IClientForwardContentService;
import com.duanqu.client.service.info.IClientContentService;
import com.duanqu.client.service.likecontent.IClientLikeContentService;
import com.duanqu.client.service.user.IClientUserService;
import com.duanqu.common.index.IIndexBuilder;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.vo.ActionForm;
import com.duanqu.common.vo.EditorTagForm;
import com.duanqu.common.vo.SimpleUserForm;
import com.duanqu.redis.service.IRedisTestService;
import com.duanqu.redis.service.comment.IRedisCommentService;
import com.duanqu.redis.service.content.IRedisContentService;
import com.duanqu.redis.service.hot.IRedisHotService;
import com.duanqu.redis.service.message.IRedisMessageService;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;

public class TestHotService extends BaseTestServiceImpl {
	
	@Resource
	IClientContentService clientContentService;

	@Resource
	IRedisHotService redisHotService;
	@Resource
	IRedisRelationshipService redisRelationshipService;
	@Resource
	IRedisTestService redisTestService;
	
	@Resource
	IRedisUserService redisUserService;
	
	@Resource
	IRedisContentService redisContentService;
	@Resource
	IIndexBuilder indexBuilder;
	@Resource
	IESSearcher esSearcher;
	@Resource
	IClientLikeContentService clientLikeContentService;
	@Resource
	IClientForwardContentService clientForwardContentService;
	
	@Resource
	IRedisCommentService redisCommentService;
	
	@Resource
	IClientCommentService clientCommentService;
	@Resource
	IClientUserService clientUserService;
	@Resource
	IRedisMessageService redisMessageService;
	
	/*
	@Test
	public void accessMessage(){
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user : users){
			List<MessageForm> messages = redisMessageService.loadMessages(user.getUid(), 1, 0, -1);
			for (MessageForm message : messages){
				System.out.println(message);
			}
		}
	}*/
	
	
	/*@Test
	public void sysUserDate(){
		List<UserModel> users = redisUserService.loadAllUser();
		int count = 0;
		for (UserModel user : users){
			count++;
			UserModel duser = clientUserService.getUser(user.getUid());
//			System.out.println(count);
			if (duser == null){
				
				System.out.println((count) +"||" + user);
				BindModel bind = redisUserService.getBindInfo(user.getUid(), 1);
				if (bind == null || bind.getUid() == 0){
					bind = redisUserService.getBindInfo(user.getUid(), 2);
				}
				if (bind != null && bind.getUid()>0){
					user.setNickName(EmojiUtils.filterEmoji(user.getNickName()));
					user.setSignature(EmojiUtils.filterEmoji(user.getSignature()));
					bind.setOpenNickName(EmojiUtils.filterEmoji(bind.getOpenNickName()));
					try{
						clientUserService.insertRegisterTh(user, bind);
					}catch (Exception e) {
						System.out.println("=================="+e.getMessage());
//						e.printStackTrace();
						user.setNickName("<code>"+URLEncoder.encode(user.getNickName())+"</code>");
						user.setSignature("<code>"+URLEncoder.encode(user.getSignature())+"</code>");
						bind.setOpenNickName("<code>"+URLEncoder.encode(bind.getOpenNickName())+"</code>");
						try{
							clientUserService.insertRegisterTh(user, bind);
						}catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
//		System.out.println(redisUserService.getUser(391));
	}*/
	
	
	
	/*@Test
	public void initNickName(){
		List<UserModel> users = redisUserService.loadAllUser();
		int count = 0;
		for (UserModel user : users){
			System.out.println((count ++) +"||" + user);
			redisUserService.addNickName(user.getNickName(), user.getUid());
		}
	}*/
	
	/*@Test
	public void settingUpdate(){
		List<UserModel> users = redisUserService.loadAllUser();
		int count = 0;
		for (UserModel user : users){
			SettingModel setting = redisUserService.getUserSetting(user.getUid());
			if (setting != null && setting.getIsCamera() == 1){
				System.out.println((count ++) +"||" + user);
				setting.setIsCamera(0);
				redisUserService.setUserSetting(user.getUid(), setting);
			}
		}
	}*/
	
	/*@Test
	public void addBanner(){
		List<BannerInfoModel> banners = new ArrayList<BannerInfoModel>();
		
		BannerInfoModel banner1 = new BannerInfoModel();
		banner1.setBannerType(BannerType.INVITE.getMark());
		banner1.setBid(1);
		banner1.setCreateTime(System.currentTimeMillis());
		banner1.setDescription("");
		banner1.setImgUrl("hot_1.png");
		banner1.setInnerUrl(null);
		banner1.setTitle("邀请");
		banners.add(banner1);
		
		BannerInfoModel banner2 = new BannerInfoModel();
		banner2.setBannerType(BannerType.HOT_TALENT.getMark());
		banner2.setBid(2);
		banner2.setCreateTime(System.currentTimeMillis());
		banner2.setDescription("");
		banner2.setImgUrl("hot_2.png");
		banner2.setInnerUrl(null);
		banner2.setTitle("达人榜");
		banners.add(banner2);
		
		redisHotService.addBanner(banners);
		
	}*/
	
	
	/*@Test
	public void synUserStatus(){
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user : users){
			//System.out.println(user.getUid()+","+user.getNickName());
			//redisUserService.updateUserStatus(user.getUid(), 1);
			//clientUserService.
			UserModel model = clientUserService.getUser(user.getUid());
			if (model == null){
				System.out.println(user);
			}
		}
	}*/
	
	
	/*@Test
	@Rollback(false)
	public void synData(){
		List<Long> cids = redisContentService.loadAllContentId();
		for (long cid : cids) {
			ContentModel content = redisContentService.getContent(cid);
			//假如数据库不存在；
			ContentModel model = clientContentService.getContentModel(cid);
			if (model == null){
				System.out.println("==="+content.getCid());
				content.setDescription(EmojiUtils.encodeEmoji(content.getDescription()));
				content.setOriginalDesc(EmojiUtils.encodeEmoji(content.getOriginalDesc()));
				content.setDescription(EmojiUtils.filterEmoji(content.getDescription()));
				content.setOriginalDesc(EmojiUtils.filterEmoji(content.getOriginalDesc()));
				try{
					//1、插入内容到数据库；
					clientContentService.insertContentInfo(content);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				//2、补充喜欢日志；
				Set likes = redisContentService.loadContentLikes(cid);
				if (likes != null && likes.size()>0){
					for (Object obj : likes){
						TypedTuple like = (TypedTuple)obj;
						String value = (String)like.getValue();
						long time = like.getScore().longValue();
						try{
							long uid = Long.parseLong(value.split(":")[0]);
							
							LikeContentModel likeContentModel = new LikeContentModel();
							likeContentModel.setCid(cid);
							likeContentModel.setUid(uid);
							likeContentModel.setCreateTime(time);
							clientLikeContentService.insertLikeContentModel(likeContentModel);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				//3、同步转发数据
				Set forwards = redisContentService.loadContentForwards(cid);
				for (Object obj : forwards){
					TypedTuple forward = (TypedTuple)obj;
					String value = (String)forward.getValue();
					long time = forward.getScore().longValue();
					try{
						long uid = Long.parseLong(value.split(":")[0]);
						//转发同步数据库
						ForwardContentModel forwardContentModel = new ForwardContentModel();
						forwardContentModel.setCid(cid);
						forwardContentModel.setUid(uid);
						forwardContentModel.setCreateTime(time);
						clientForwardContentService.insertForwardContentModel(forwardContentModel);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//4、同步评论
				List<CommentForm> comments = redisCommentService.loadComments(cid, 1, 100);
				if (comments != null && comments.size() > 0){
					try{
						for (CommentForm form : comments){
							//4.1 插入主评论
							CommentModel comment = new CommentModel();
							comment.setCid(cid);
							comment.setCommentText(EmojiUtils.encodeEmoji(form.getCommentText()));
							comment.setCommentUrl("");
							comment.setCreateTime(form.getCreateTime());
							comment.setId(form.getId());
							comment.setParentId(0);
							comment.setReplyUid(0);
							comment.setRootId(0);
							comment.setUid(form.getUser().getUid());
							clientCommentService.insertContentComment(comment);
							List<CommentForm> childs = form.getReplyComments();
							if (childs != null && childs.size() > 0){
								for (CommentForm child : childs){
									CommentModel childComment = new CommentModel();
									childComment.setCid(cid);
									childComment.setCommentText(EmojiUtils.encodeEmoji(child.getCommentText()));
									childComment.setCommentUrl("");
									childComment.setCreateTime(child.getCreateTime());
									childComment.setId(child.getId());
									childComment.setParentId(form.getId());
									childComment.setReplyUid(form.getUser().getUid());
									childComment.setRootId(form.getId());
									childComment.setUid(child.getUser().getUid());
									clientCommentService.insertContentComment(childComment);
								}
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}*/
	
/*	@Test
	public void search() {
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user : users){
			if (user.getNickName().indexOf("null")>=0){
				System.out.println(user);
			}
		}
	}*/
	
	/*@Test
	public void indexContent() {
		List<Long> cids = redisContentService.loadAllContentId();
		for (long cid : cids){
			ContentModel content = redisContentService.getContent(cid);
			IndexContentModel indexModel = new IndexContentModel();
			indexModel.setCid(content.getCid());
			indexModel.setDescription(content.getDescription());
			indexModel.setTags(DuanquStringUtils.getTagString(content.getDescription()));
			indexModel.setTime(content.getUploadTime());
			indexBuilder.buildConentIndex(indexModel);
		}
	}*/
	
	/*@Test
	public void indexUser() {
		List<UserModel> users = redisUserService.loadAllUser();
		for (UserModel user : users){
			IndexUserModel indexUser = new IndexUserModel();
			indexUser.setNickName(user.getNickName());
			indexUser.setSignature(user.getSignature());
			indexUser.setTime(user.getCreateTime());
			indexUser.setUid(user.getUid());
			indexBuilder.buildUserIndex(indexUser);
		}
	}*/
	/*@Test
	public void searchContent() {
		Map map = esSearcher.searchContent(1, "通天", 0, 20);
		System.out.println(map.get("count"));
		List<ActionForm> actions = (List<ActionForm>)map.get("result");
		System.out.println(actions.size());
		for (ActionForm action:actions){
			System.out.println(action);
		}
	}*/
	
/*	@Test
	public void searchUser() {
		Map map = esSearcher.searchUser(1, "蒲永东", 0, 20);
		System.out.println(map.get("count"));
		List<FriendForm> friends = (List<FriendForm>)map.get("result");
		System.out.println(friends.size());
		for (FriendForm friend:friends){
			System.out.println(friend);
		}
	}*/
	
	
/*	@Test
	@Rollback(false)
	public void updateContentStatus() {
		List<Long> cids = redisContentService.loadAllContentId();
		for (long cid : cids){
			ContentModel content = redisContentService.getContent(cid);
			try {
				if (content.getcStatus()!= 0){
					clientContentService.updateContentStatusToDelete(content.getCid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}*/
/*	
	@Test
	@Rollback(false)
	public void updateMD5() {
		List<Long> cids = redisContentService.loadAllContentId();
		for (long cid : cids){
			ContentModel content = redisContentService.getContent(cid);
			String key = content.getVideoUrlHD();
			OSSObject obj = AliyunUploadUtils.getVideo(key);
			try {
				String md5 = DuanquSecurity.getHash(obj.getObjectContent());
				ContentModel md5Content = new ContentModel();
				md5Content.setCid(content.getCid());
				md5Content.setMd5(md5);
				clientContentService.updateContentMd5(md5Content);
				redisContentService.updateContentMD5(content.getCid(), md5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}*/
	
	
	//@Test
	public void testContentStatus() {
		List<Long> ids = redisContentService.loadAllContentId();
		for (long cid : ids){
			ContentModel content = redisContentService.getContent(cid);
			if (content.getcStatus() == 1){
				redisContentService.updateContentStatus(cid, 0);
			}
			
			if (content.getcStatus() == 0){
				redisContentService.updateContentStatus(cid, 1);
			}
		}
	}
		
		
	//@Test
	public void testTags(){
		String tagStr = "旅行|卖萌|摄影|腐女|爱奇艺";
		redisHotService.insertPublishTag(tagStr);
		//redisHotService.insertSearchTag(tagStr);
	}
	
//	@Test
	public void testHotContent(){
		List<Long> contentIds = redisContentService.loadAllContentId();
		List<Long> cids = new ArrayList<Long>();
		int count = 0;
		for (Long cid : contentIds){
			count ++ ;
			if (count < 100){
				cids.add(cid);
			}else{
				break;
			}
		}
		redisHotService.insertHotContent(cids,0);
	}
	
//	@Test
	public void testHotUser(){
		List<UserModel> users = redisUserService.loadAllUser();
		List<Long> uids = new ArrayList<Long>();
		int count = 0;
		for (UserModel user : users){
			count ++ ;
			if (count < 100){
				System.out.println(user);
				uids.add(user.getUid());
			}else{
				break;
			}
			System.out.println(uids.size());
		}
		redisHotService.insertHotUser(uids);
	}
	
	//@Test
	public void testRecommendContent(){
		List<Long> ids = redisContentService.loadAllContentId();
		for (int i = 0;i<100 && i<ids.size();i++){
			redisContentService.insertContentFindList(ids.get(i),0);
		}
	}
	
	//@Test
	public void testLoadRecommendContent(){
		List<ActionForm> actions = redisContentService.loadFindContentList(1,0,20);
		for (ActionForm action:actions){
			System.out.println(action);
		}
	}
	
	//@Test
	public void testRecommendUser(){
		List<UserModel> users = redisUserService.loadAllUser();
		int count = 0;
		for (UserModel user : users){
			count ++ ;
			if (count < 80){
				System.out.println(user);
				//redisHotService.addRecommendUser(user.getUid());
			}else{
				break;
			}
			
		}
		//redisRelationshipService.insertSystemRecommendUsers(uid, users, openType)
	}
	//@Test
	public void testSystemRecommendUser(){
		/*List<OpenFriend> sinaUsers = new ArrayList<OpenFriend>();
		MatchedUserModel sinaUser = new MatchedUserModel();
		sinaUser.setMatchedTime(System.currentTimeMillis());
		sinaUser.setUid(2);
		sinaUser.setUserName("新浪测试用户");
		sinaUsers.add(sinaUser);
		redisRelationshipService.insertSystemRecommendUsers(1, sinaUsers, 1);
		
		List<MatchedUserModel> qqUsers = new ArrayList<MatchedUserModel>();
		MatchedUserModel qqUser = new MatchedUserModel();
		qqUser.setMatchedTime(System.currentTimeMillis());
		qqUser.setUid(3);
		qqUser.setUserName("qq测试用户");
		qqUsers.add(qqUser);
		redisRelationshipService.insertSystemRecommendUsers(1, qqUsers, 2);
		
		List<MatchedUserModel> mobileUsers = new ArrayList<MatchedUserModel>();
		MatchedUserModel mobileUser = new MatchedUserModel();
		mobileUser.setMatchedTime(System.currentTimeMillis());
		mobileUser.setUid(4);
		mobileUser.setUserName("手机测试用户");
		mobileUsers.add(mobileUser);
		redisRelationshipService.insertSystemRecommendUsers(1, mobileUsers, 3);*/
		
		
	}
	//@Test
	public void testHash(){
		redisTestService.loadTimeline(0, 10);
	}
	
	//@Test
	public void addHotTag(){
		EditorTagForm form = new EditorTagForm();
		form.setId(1);
		form.setOrderNum(1);
		form.setTagImage("http://system-images.oss.aliyuncs.com/hot_1.png");
		form.setTagText("拍建筑");
		form.setWidth(100f);
		form.setHeight(100f);
		form.setTitle("拍建筑");
		redisHotService.addMainHotTag(form);
		form.setId(2);
		form.setOrderNum(2);
		form.setTagImage("http://system-images.oss.aliyuncs.com/hot_2.png");
		form.setTagText("随手拍花花草草");
		form.setWidth(100f);
		form.setHeight(100f);
		form.setTitle("随手拍花花草草");
		redisHotService.addMainHotTag(form);
		form.setId(3);
		form.setOrderNum(3);
		form.setTagImage("http://system-images.oss.aliyuncs.com/hot_3.png");
		form.setTagText("旅游");
		form.setWidth(100f);
		form.setHeight(100f);
		form.setTitle("百度旅游");
		redisHotService.addMainHotTag(form);
		form.setId(4);
		form.setOrderNum(4);
		form.setTagImage("http://system-images.oss.aliyuncs.com/hot_4.png");
		form.setTagText("搜索");
		form.setWidth(100f);
		form.setHeight(100f);
		form.setTitle("狗狗搜索");
		redisHotService.addMainHotTag(form);
	}
	//@Test
	public void recommendUser(){
		for (int i =1;i<80;i++){
			//redisHotService.addRecommendUser(i);
		}
	}
//	@Test
	public void listRecommendUsers(){
		List<SimpleUserForm> users = redisHotService.loadRecommendUsers(2);
		for (SimpleUserForm user : users){
			System.out.println(user);
		}
	}
}
