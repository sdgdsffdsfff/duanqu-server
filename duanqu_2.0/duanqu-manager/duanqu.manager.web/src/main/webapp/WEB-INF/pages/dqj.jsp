<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
 <script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">	

<c:if test="${message=='success'}">
alert("推送成功");
</c:if>
<c:if test="${message=='fail'}">
alert("推送失败");
</c:if>
<c:if test="${ts=='2Success'}">
alert("删除成功");
</c:if>
<c:if test="${ts=='2Fail'}">
alert("删除失败");
</c:if>
	<c:if test="${ts=='3Success'}">
alert("屏蔽成功");
</c:if>
<c:if test="${ts=='3Fail'}">
alert("屏蔽失败");
</c:if>



function query(index){
		if(index!=undefined){
			var patrn=/^[1-9][0-9]*$/;
			if(!patrn.exec(index)){
				form1.page.value=1;
			}else{
				form1.page.value=index;
			}
		}
		form1.action="content.do?method=dqjList";
		form1.submit();	
	}
	
	
function deleteContent(cid,sStatus){
	var str;
	if(sStatus=="3"){
		str="你确定屏蔽该条内容吗?";
	}else if(sStatus=="2"){
		str="你确定删除该条内容吗?";
	}else if(sStatus=="0"){
		str="你确定取消屏蔽该条内容吗?";
	}
	if(confirm(str)){
		form1.action="content.do?method=deleteContent&dqj=1&sStatus="+sStatus+"&cid="+cid;
		form1.submit();
	}
}

	
</script>
</head>

<body>
<script src="js/jquery.imagePreview.js" ></script>
<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a>
    <p><b class="s0 cs" id="theme0">皮肤1</b><b class="s1  cs" id="theme1">皮肤1</b><b class="s2  cs"  id="theme2">皮肤1</b></p>
    </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
   <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 用户管理 > 短趣君</div>
      <div class="content">
        <div class="tab"> <a href="content.do?method=dqjList"  class="on">内容管理</a> <a href="user.do?method=messageList" >私信管理</a> </div>
        <div class="searcharea">
         <form action="" name="form1" method="post">
         
            <label for="keyword">按关键字:</label>
            <input type="text" class="input-text" name="key" id="keyword" value="${vo.key}">
            <input type="button" value="搜索" class="button" name="search" onclick="query()">          
        </div>
        <form  method="get">
          <div class="table-list">
            <div class="pd8">
             <input name="" type="button" value="上传视频"  class="button" onClick="showdiv('上传视频',500,250,'content.do?method=enterUpload&dqj=1')">
            </div>
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="50">&nbsp;</th>
                  <th width="98"><div class="w80">视频图片</div></th>
                  <th width="138"><div class="w120">发布时间</div></th>
                  <th width="178"><div class="w160">描述</div></th>
                  <th width="178"><div class="w160">标签</div></th>
                  <th width="98" align="center"><div class="w80">播放次数<a href="#" onClick="search_order_by('show_times','ASC')">▲</a><a href="#" onClick="search_order_by('show_times','DESC')">▼</a></div></th>
                  <th width="78"><div class="w80">喜欢数<a href="#" onClick="search_order_by('like_num','ASC')">▲</a><a href="#" onClick="search_order_by('like_num','DESC')">▼</a></div></th>
                  <th width="78"><div class="w80">评论数<a href="#" onClick="search_order_by('comment_num','ASC')">▲</a><a href="#" onClick="search_order_by('comment_num','DESC')">▼</a></div></th>
                  <th width="90"><div class="w50">新浪分享数<a href="#" onClick="search_order_by('sinashare_num','ASC')">▲</a><a href="#" onClick="search_order_by('sinashare_num','DESC')">▼</a></div></th>
                  <th width="90"><div class="w50">朋友圈分享数<a href="#" onClick="search_order_by('quanshare_num','ASC')">▲</a><a href="#" onClick="search_order_by('quanshare_num','DESC')">▼</a></div></th>
                  <th width="78"><div class="w50"><a href="#">评论</a></div></th>
                  <th width="366" align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.cid}"/>" id="newslist-<c:out value="${item.cid}"/>" name="cidList"></td>
                  <td  align="center"><span class="imgpreview" ><img src="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" width="35" class="preview videopic" bimg="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" data-role="<%=DuanquConfig.getAliyunHDVideoDomain()%>${item.videoUrlHd}"></span></td>
                  <td align="left" class="time">${item.uploadTimeStr}</td>
                   <td id="des${item.cid}">${item.description}</td>
                  <td align="center"><span  class="taglist" id="tag${item.cid}">
                  <c:forEach items="${item.list}" var="item2">
                  <a href="#">${item2.tagText}</a> 
                  </c:forEach>
                  </span>&nbsp;&nbsp;
                  
                  <span class="addtag taglist" id="tagd${item.cid}">
                  <a href="#" onClick="showdiv('修改描述信息',400,200,'tag.do?method=enterTag&cid=${item.cid}')" style="cursor:pointer">修改</a>
                  </span>

                  </td>
                  <td align="center">${item.showTimes} | ${item.falseShowTimes}</td>
                  <td align="center">${item.likeNum} | ${item.falseLikeNum}</td>
                  <td align="center">${item.commentNum} | ${item.falseCommentNum}</td>  
                  <td align="center">${item.sinashareNum} |<div id="sina${item.cid}"> ${item.sinashareFalseNum}</div> <a href="#" onClick="showdiv('修改新浪假分享数',400,200,'content.do?method=enterSinaQuan&type=1&cid=${item.cid}')" style="cursor:pointer">修改</a></td>
                  <td align="center">${item.quanshareNum} |<div id="quan${item.cid}"> ${item.quanshareFalseNum}</div><a href="#" onClick="showdiv('修改朋友圈假分享数',400,200,'content.do?method=enterSinaQuan&type=2&cid=${item.cid}')" style="cursor:pointer">修改</a></td>
                  <td align="center"><a href="#"class="showtagcontent" data-role="user.do?method=dqjCommentList&cid=${item.cid}"><font color="red">${item.xpls}</font></a></td>
                  <td align="center"><a href="#" class="" onclick="deleteContent(${item.cid},2)">删除</a></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="pd8 clearfix" >
             <%@ include file="page.jsp"%>          
             </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
