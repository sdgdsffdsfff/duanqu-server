package com.duanqu.common.share;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

import com.duanqu.common.AliyunUploadUtils;
import com.duanqu.common.DuanquConfig;
import com.duanqu.common.model.OpenFriend;
import com.duanqu.common.model.BindModel.OpenType;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.qzone.Share;
import com.qq.connect.api.weibo.UserInfo;
import com.qq.connect.api.weibo.Weibo;
import com.qq.connect.javabeans.GeneralResultBean;
import com.qq.connect.javabeans.weibo.FansIdolsBean;
import com.qq.connect.javabeans.weibo.SingleFanIdolBean;
import com.qq.connect.javabeans.weibo.WeiboBean;

public class ShareServiceImpl implements IShareService {
	Log logger = LogFactory.getLog(ShareServiceImpl.class);

	@Override
	public List<OpenFriend> loadSinaFollows(String openUserId, String accessToken) {
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		UserWapper users = null;
		List<OpenFriend> friends = new ArrayList<OpenFriend>();
		try {
			do{
				users = fm.getFriendsByID(openUserId, 200, (users == null ? 0:Integer.parseInt(users.getNextCursor()+"")));
				for (User u : users.getUsers()) {
					OpenFriend friend = new OpenFriend(OpenType.SINA.getMark());
					friend.setOpenNickName(u.getScreenName());
					friend.setOpenUserId(u.getId());
					friend.setOpenUserName(u.getName());
					friend.setAvatarUrl(u.getAvatarLarge());
					friend.setOpenType(OpenType.SINA.getMark());
					friends.add(friend);
				}
			}while (users.getNextCursor() > 0);
		} catch (WeiboException e) {
			logger.error("shareToSina Error.code=" + e.getErrorCode()
					+ "statusCode=" + e.getStatusCode() + ",Message="
					+ e.getMessage() + ";[params:accessToken=" + accessToken);
		}
		return friends;

	}

	@Override
	public boolean shareToSina(String imagePath,String content, String accessToken) {
		Timeline timeline = new Timeline();
		timeline.setToken(accessToken);
		if (imagePath == null){
			try {
				timeline.UpdateStatus(content);
				return true;
			} catch (WeiboException e) {
				logger.error("shareToSina Error.code=" + e.getErrorCode()
						+ "statusCode=" + e.getStatusCode() + ",Message="
						+ e.getMessage() + ";[params:imagePath=" + imagePath
						+ ",content=" + content + ",accessToken=" + accessToken);
			} 
		}else{
			byte[] image = getByteFromFile(imagePath);
			try {
				ImageItem item = new ImageItem(image);
				timeline.UploadStatus(URLEncoder.encode(content, "utf-8"), item);
				return true;
			} catch (WeiboException e) {
				logger.error("shareToSina Error.code=" + e.getErrorCode()
						+ "statusCode=" + e.getStatusCode() + ",Message="
						+ e.getMessage() + ";[params:imagePath=" + imagePath
						+ ",content=" + content + ",accessToken="
						+ accessToken);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage()+"content="+content);
			}
		}
		return false;
	}


	private byte[] getByteFromFile(String key) {
		return AliyunUploadUtils.getThumbnail(key);
	}

	@Override
	public List<OpenFriend> loadQQFollows(String openUserId,String token) {
		
		UserInfo user = new UserInfo(token, openUserId);
		
		List<OpenFriend> friends = new ArrayList<OpenFriend>();
		try {
			FansIdolsBean idols = null;
			do {
				idols = user.getIdolsList(30, (idols == null ? 0 : idols.getNextStartPos()), "install=0");
				for (SingleFanIdolBean idol : idols.getFanIdols()) {
					OpenFriend friend = new OpenFriend(OpenType.TENCENT.getMark());
					friend.setOpenNickName(idol.getNick());
					friend.setOpenUserId(idol.getOpenID());
					friend.setOpenUserName(idol.getName());
					friend.setAvatarUrl(idol.getAvatar().getAvatarURL100());
					friend.setOpenType(OpenType.TENCENT.getMark());
					friends.add(friend);
				}
			} while (idols.hasNext());

		} catch (QQConnectException e) {
			logger.error("loadQQFollows Error.code=" + e.getStatusCode() + ";"
					+ e.getMessage() + ";[params:token=" + token
					+ ",openUserId=" + openUserId + "]");
		}
		return friends;
	}

	/**
	 * java.lang.String title,
                                  java.lang.String url,
                                  java.lang.String site,
                                  java.lang.String fromUrl,
	 */
	@Override
	public void shareToQZone(String title,String content, String url, String playUrl,String imageUrl,String token, String openUserId) {
		Share share = new Share(token,openUserId);
		try {
			/*if (content.length()>36){
				title = content.substring(0,36);
			}else{
				title = content;
			}*/
			//title = "趣拍视频来一发，咩哈哈哈哈！戳这里，戳这里";
			String summary = "";
			if (StringUtils.hasText(content)){
				if (content.length()> 80){
					summary = content.substring(0,75)+"……";
				}else {
					summary = content;
				}
			}
			GeneralResultBean grb = share.addShare(title, url, "趣拍", DuanquConfig.getHost(),
			       // "comment=" + content ,
			        "summary=" + summary,
			        "images="+imageUrl,
			        //"type=5",
			        //"playurl="+playUrl,
			        "nswb=1");
			
			if (grb.getRet() != 0){
				logger.error(grb + ";[params:content:" + content + ",url:"
								+ url + ",playUrl:" + playUrl + ",imageUrl:"
								+ imageUrl + ",token:" + token + ",openUserId:"
								+ openUserId + "]");
			}
		} catch (QQConnectException e) {
			logger.error(e.getMessage() + ";[params:content:" + content + ",url:"
					+ url + ",playUrl:" + playUrl + ",imageUrl:"
					+ imageUrl + ",token:" + token + ",openUserId:"
					+ openUserId + "]");
		}
		
	}

	@Override
	public void shareToQQWeibo(String imagePath, String content, String token,
			String openUserId) {
		
		Weibo weibo = new Weibo(token,openUserId);
		if (imagePath != null){
			try {
				weibo.addPicWeibo(content, this.getByteFromFile(imagePath));
			} catch (QQConnectException e) {
				logger.error("shareToQQWeibo Error.code="+e.getStatusCode() + ";" + e.getMessage()
						+ ";[params:imagePath=" + imagePath + ",content=" + content
						+ ",token=" + token + ",openUserId="
						+ openUserId + "]");
				
			}
		}else{
			try{
				WeiboBean res = weibo.addWeibo(content);
				System.out.println(res);
			}catch (QQConnectException e) {
				logger.error("shareToQQWeibo Error.code="+e.getStatusCode() + ";" + e.getMessage()
						+ ";[params:imagePath=" + imagePath + ",content=" + content
						+ ",token=" + token + ",openUserId="
						+ openUserId + "]");
				
			}
		}
	}
	
	@Override
	public void sinaFollow(String accessToken) {
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		try {
			fm.createFriendshipsById("3511874221");
		} catch (WeiboException e) {
			logger.error("关注趣拍APP出错！Error:code=" + e.getErrorCode()
					+ ";statusCode=" + e.getStatusCode() + ";Messsage="
					+ e.getMessage() + "," + e.getError().toString());
		}		
	}
	
	public static void main(String[] args) {
		//D3893B93983B93CA5768DAEF9019C9F0
		//System.out.println("http://t.cn/zHfBoIe".length());
		//713A35ACF8359352E11120FA9E9404FB
		ShareServiceImpl test = new ShareServiceImpl();
		//test.loadQQFollows("D3893B93983B93CA5768DAEF9019C9F0", "713A35ACF8359352E11120FA9E9404FB");
		//test.loadSinaFollows("1421024527","2.0085TKYB0kAFJeb866bbcb760JctUW");
		//test.shareToSina("/duanqu2.0/resources/thumbnail/20130511/41368239873705.jpg","@沈欢英 中文测试","", "2.0085TKYB0kAFJeb866bbcb760JctUW");
		/*test
				.shareToQZone(
						"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈呵呵呵呵呵呵他",
						"http://share.danqoo.com/v/show.htm?cid=7304", "http://duanqu-video.b0.upaiyun.com/20130601/11370054895255.mp4",
						"http://duanqu.b0.upaiyun.com/20130601/11370060963729.jpg",
						"FF9FDAD6F84E5BA79DED709526F7BE93",
						"41167165994306673D0BF9C503EFFFE4");*/
		test.shareToQQWeibo(null,"中文测试" , "7E0B6FA47EEAC199C63CB64D43F654D3", "0CF22F1D05C5DC91DE36D6BACD7686A2");
//		test.sinaFollow("2.0085TKYB0kAFJeb866bbcb760JctUW");
	}

}
