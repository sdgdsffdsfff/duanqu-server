package com.duanqu.manager.service.editor;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquSecurity;
import com.duanqu.common.model.ActiveContentModel;
import com.duanqu.common.model.ActiveInfoModel;
import com.duanqu.common.model.BannerInfoModel;
import com.duanqu.common.model.ContentModel;
import com.duanqu.common.model.EditorTagModel;
import com.duanqu.common.model.EditorTalentInfoModel;
import com.duanqu.common.model.EditorTalentModel;
import com.duanqu.common.model.FilterWordModel;
import com.duanqu.common.model.UserAdminModel;
import com.duanqu.common.model.UserModel;
import com.duanqu.common.submit.MContentSubmit;
import com.duanqu.common.submit.MUserSubmit;
import com.duanqu.common.vo.SimpleUserForm;
import com.duanqu.manager.dao.UserAdminMapper;
import com.duanqu.manager.service.content.IManagerContentService;
import com.duanqu.manager.service.filter.IManagerFilterService;
import com.duanqu.manager.service.hot.IManagerHotService;
import com.duanqu.manager.service.tag.IManagerTagService;
import com.duanqu.manager.service.user.IManagerUserService;
import com.duanqu.manager.submit.ManagerMessageSubmit;
import com.duanqu.redis.service.user.IRedisRelationshipService;
import com.duanqu.redis.service.user.IRedisUserService;

public class TestManagerEditorServiceImpl extends BaseTestServiceImpl {
	
	
	static Logger logger = Logger.getLogger(TestManagerEditorServiceImpl.class);
	
	@Resource
	private IManagerEditorService managerEditorService;
	
	@Resource
	private IManagerUserService managerUserService;
	
	@Resource
	private IManagerContentService managerContentService;
	@Resource
	private IManagerHotService managerHotService;
	
	@Resource
	private IRedisUserService redisUserService;
	
	@Resource
	private UserAdminMapper userAdminMapper;
	@Resource
	private IManagerFilterService managerFilterService;
	@Resource
	private IManagerTagService managerTagService;
	
	@Resource
	private IRedisRelationshipService redisRelationshipService;
	

	
	@Rollback(false)
	public void testInsertActiveInfo(){
		ActiveInfoModel activeInfoModel=new ActiveInfoModel();
		activeInfoModel.setActiveTitle("搞笑活动排名");
		activeInfoModel.setBeginTime(System.currentTimeMillis());
		activeInfoModel.setEndTime(System.currentTimeMillis());
		activeInfoModel.setCreateTime(System.currentTimeMillis());
		activeInfoModel.setFlag(0);
		managerEditorService.insertActiveInfo(activeInfoModel, 2);
	}
	
	@Rollback(false)
	
	public void testInsertEditorTag(){
		EditorTagModel editorTagModel=new EditorTagModel();
		editorTagModel.setTagText("图片");
		editorTagModel.setTagImage("images");
		editorTagModel.setCreateTime(System.currentTimeMillis());
		editorTagModel.setOrderNum(2);
		managerEditorService.insertEditorTag(editorTagModel);

	}
	@Rollback(false)
	//@Test
	public void testInsertEditorTalent(){
		EditorTalentModel editorTalentModel=new EditorTalentModel();
		editorTalentModel.setUid(2);
		editorTalentModel.setInfoId(1);
		editorTalentModel.setComment("幽默");
		editorTalentModel.setCreateTime(System.currentTimeMillis());
		managerEditorService.insertEditorTalent(editorTalentModel);
	}
	//@Test
	@Rollback(false)
	public void testInsertEditorTalentInfo(){
		EditorTalentInfoModel editorTalentInfoModel=new EditorTalentInfoModel();
		editorTalentInfoModel.setTitle("达人榜");
		editorTalentInfoModel.setCreateTime(System.currentTimeMillis());
		editorTalentInfoModel.setImgUrl("cc");
		managerEditorService.insertEditorTalentInfo(editorTalentInfoModel);
		
	}
	
	
	@Rollback(false)
	
	public void testInsertBannerInfo(){
		BannerInfoModel bannerInfoModel=new BannerInfoModel();
		bannerInfoModel.setTitle("搞笑");
		bannerInfoModel.setDescription("测试");
		bannerInfoModel.setImgUrl("images");

	//	bannerInfoModel.setBannerType(1);

		bannerInfoModel.setBannerType("1");

		bannerInfoModel.setCreateTime(System.currentTimeMillis());
		bannerInfoModel.setInnerUrl("show.do");
		managerEditorService.insertBannerInfo(bannerInfoModel);
	}
	@Rollback(false)
	
	public void testuUdateActiveContent(){
		ActiveContentModel activeContentModel=new ActiveContentModel();
		activeContentModel.setActiveId(1);
		activeContentModel.setOrderNum(2);
		managerEditorService.updateActiveContent(activeContentModel);
	}
	@Rollback(false)
	
	public void testDeleteEditorTag(){
		managerEditorService.deleteEditorTag(1);
	}
	@Rollback(false)
	
	public void testDeleteEditorTalent(){
		managerEditorService.deleteEditorTalent(1);
	}
	
	public void testQueryBannerInfoModels(){
		List<BannerInfoModel> list=managerEditorService.queryBannerInfoModels();
		for(Iterator<BannerInfoModel> iterator=list.iterator();iterator.hasNext();){
			System.out.println(iterator.next().getBid());
		}
	}
	//@Test
	public void testQueryEditorTagModels(){
		List<EditorTagModel> list=managerEditorService.queryEditorTagModels();
		for(Iterator<EditorTagModel> iterator=list.iterator();iterator.hasNext();){
			System.out.println(iterator.next().getId());
		}
	}
	//@Test
	public void testQueryEditorTalentInfoModels(){
		List<EditorTalentInfoModel> list=managerEditorService.queryEditorTalentInfoModels();
		for(Iterator<EditorTalentInfoModel> iterator=list.iterator();iterator.hasNext();){
			System.out.println(iterator.next().getId());
		}
	}
	
	//@Test
	public void testQueryEditorTalentModels(){
		List<EditorTalentModel> list=managerEditorService.queryEditorTalentModels();
		for(Iterator<EditorTalentModel> iterator=list.iterator();iterator.hasNext();){
			System.out.println(iterator.next().getId());
		}
	}
	//@Test
	public void testQueryTagInfo(){
		List<Map<String, Object>> list=managerEditorService.queryTagInfo();
		for(Iterator<Map<String, Object>> iterator=list.iterator();iterator.hasNext();){
			Map<String,Object> map=iterator.next();
			System.out.println(map.get("clcountSum"));
			System.out.println(map.get("tag_text"));
		}
	}
	//@Test
	public void testCheckUserAdimin(){
		UserAdminModel userAdminModel2=new UserAdminModel();
		userAdminModel2.setUsername("admin");
		userAdminModel2.setPassword("1");
		UserAdminModel userAdminModel=managerUserService.checkUserAdimin(userAdminModel2);
		System.out.println(userAdminModel.getFullname());
	}
	//@Test 
	public void testQueryUserList(){
		MUserSubmit mUserSubmit=new MUserSubmit();
		mUserSubmit.setNickname("204");
		managerUserService.queryUserList(mUserSubmit);
		/*List<Map<String, Object>> list=mUserSubmit.getObjList();
		for(Iterator<Map<String, Object>> iterator=list.iterator();iterator.hasNext();){
			System.out.println(iterator.next());
		}*/
	}
	
	//@Test
	@Rollback(false)
	public void testInsertUserreCommend(){
		List<Long> list=new ArrayList<Long>();
		//list.add();
		MUserSubmit mUserSubmit=new MUserSubmit();
		mUserSubmit.setUidList(list);
		managerUserService.insertUserreCommend(mUserSubmit);
	}
	//@Test
	public void testQueryContentForms(){
		MContentSubmit mContentSubmit=new MContentSubmit();
		mContentSubmit.setUploadTimeQ("2012-08-07 09:17:49");
		try {
			/*List<ContentForm> list=managerContentService.queryContentForms(mContentSubmit);
			for(Iterator<ContentForm> iterator=list.iterator();iterator.hasNext();){
				System.out.println(iterator.next());
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@Test
	public void testInserHotList(){
		managerHotService.insertHotList();
	}
	//@Test
	@Rollback(false)
	public void  testInserRedisFromContent(){
		managerContentService.insertRedisFromContent();
	}
	
	//@Test
	@Rollback(false)
	public void testGetUserFriendList(){
		//managerUserService.getUserModeisList();
		//managerContentService.dsTsContent();
	}
	//@Test
	@Rollback(false)
	public void testQueryModel(){
		managerUserService.duanquUpdateUserAll();
	}
	//@Test
	@Rollback(false)
	public void testQueryMj(){
		String driver = "com.mysql.jdbc.Driver";
		String url="jdbc:mysql://112.124.41.242:9906/dev4ed4d1aac6f";
		String userName="hellotecbadboy";
		String passWord="badboy4badboy4";
		String sql="select * from user where property = 2  and id <41921 order by id DESC ";
		try {
			Class.forName(driver);
			Connection conn=DriverManager.getConnection(url, userName, passWord);
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				//System.out.println(rs.getString("nickName"));
				//System.out.println(rs.getString("head"));
				String head=rs.getString("head");
				String id=rs.getString("id");
				/*URL urlImage;
				try {
					 urlImage=new URL("http://upyun-img.danqoo.com/avatar/"+head);
					 URLConnection uc=urlImage.openConnection();
						InputStream is=uc.getInputStream();
						String sub=head.substring(10, head.length());
						String fileName=("E:\\Image\\"+sub);
						File file=new File(fileName);
						FileOutputStream fStream=new FileOutputStream(file);
						int i=0;
						while((i=is.read())!=-1){
						    fStream.write(i);
						}
						fStream.flush();
						fStream.close();
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(id);
				}*/
				try {
					String sub=head.substring(10, head.length());
					String type="image/"+head.substring(head.indexOf(".")+1, head.length());
					String fileName=("E:\\Image\\"+sub);
					File file=new File(fileName);
					FileInputStream fis=new FileInputStream(file);
					UserModel userModel=new UserModel();
					userModel.setUid(redisUserService.getUserId());
					String avatarUrlStr = AliyunUploadUtils.uploadAvatar(
							fis, (int)file.length(),type
							); 
					userModel.setAvatarUrl(avatarUrlStr);
					userModel.setNickName(rs.getString("nickName"));
					userModel.setCreateTime(System.currentTimeMillis());
					userModel.setRoleId(4);
					userModel.setToken(DuanquSecurity.encodeToken(null));
					userAdminMapper.insertUserInfo(userModel);
					redisUserService.insertUser(userModel);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(id);
					
				}
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//@Test
	@Rollback(false)
	public void testWord(){
		String driver = "com.mysql.jdbc.Driver";
		String url="jdbc:mysql://112.124.41.242:9906/dev4ed4d1aac6f";
		String userName="hellotecbadboy";
		String passWord="badboy4badboy4";
		String sql="SELECT * from badword";
		try {
			Class.forName(driver);
			Connection conn=DriverManager.getConnection(url, userName, passWord);
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				FilterWordModel filterWordModel=new FilterWordModel();
				filterWordModel.setFilterText(rs.getString("word"));
				managerFilterService.insertFilterWordModel(filterWordModel);
			}
		} catch (Exception e) {
			
		}	
	}
	
	//@Test
	@Rollback(false)
	public void testUpdateContentDescription(){
		ContentModel contentModel=new ContentModel();
		contentModel.setCid(3);
		contentModel.setDescription("#嘿嘿##杭州的天气测试啊##趁早#发酵罐svj#可乐#");
		managerContentService.updateContentDescription(contentModel);	
	}
	//@Test
	@Rollback(false)
	public void testInsertHotTag(){
		//managerTagService.insertTagHot("104");
	//	managerTagService.deleteTagHot(104);
		
	}
	//@Test
	@Rollback(false)
	public void testInsertFalseFriend(){
    List<Long> list=userAdminMapper.getList();
    for(Long uid:list){
    	int fans=redisRelationshipService.countFans(uid);
    	//当前用户的关注数
    	List<SimpleUserForm> loadFollows=redisRelationshipService.loadFollows(uid, 1, 2000);
    	
    	List<Long> listFollows=new ArrayList<Long>();
    	for(SimpleUserForm suf:loadFollows){
    		listFollows.add(suf.getUid());
    	}
    	//当前用户的粉丝数
    	List<SimpleUserForm> loadFans=redisRelationshipService.loadFans(uid, 1, 2000,4 );
    	List<Long> listFans=new ArrayList<Long>();
    	for(SimpleUserForm suForm:loadFans){
    		listFans.add(suForm.getUid());
    	}
    	
    	//数据关系表中显示的关注数
    	List<Long> dbListFollows=userAdminMapper.selectFollows(uid);
    	//数据关系表中显示的粉丝数
    	List<Long> dbListFans=userAdminMapper.selectFans(uid);
        
    	int num2=0;
    	for(Long uid2:dbListFollows){
        		if(listFollows!=null){
        			if(!listFollows.contains(uid2)){
        				System.out.println("用户ID为:"+uid+":关注数----db存在但redis里不存在的元素:"+uid2);
        				num2++;
        			}
        		}
        	}
    	System.out.println("用户ID为"+uid+":关注数---db和reids的差异总数为:"+num2);
    	
    	int num3=0;
    	for(Long uid3:dbListFans){
        		if(listFans!=null){
        			if(!listFans.contains(uid3)){
        				System.out.println("用户ID为"+uid+":粉丝数----db存在但redis里不存在的元素:"+uid3);
        				num2++;
        			}
        		}
        	}
    	System.out.println("用户ID为"+uid+":粉丝数---db和reids的差异总数为:"+num3);
    	
  
    	/*System.out.println(uid+"++++++++"+fans);
    	Map<String, Object> map=new HashMap<String, Object>();
    	map.put("uid", uid);
    	map.put("num", fans);*/
    //	userAdminMapper.updateUserFans(map);
    }
	

		
	}
	@Test
	@Rollback(false)
	public void testInsertMessage(){
		ManagerMessageSubmit managerMessageSubmit=new ManagerMessageSubmit();
		managerMessageSubmit.setQflx(1);
	
	    managerMessageSubmit.setMessageText("群发私信测试922");
 		managerUserService.insertMessage(managerMessageSubmit);
	}

}
