<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/jquery.imagePreview.js" ></script>
</head>
<body>
<script type="text/javascript">
	function query(index){
		if(index!=undefined){
			var patrn=/^[1-9][0-9]*$/;
			if(!patrn.exec(index)){
				form1.page.value=1;
			}else{
				form1.page.value=index;
			}
		}
		form1.action="user.do?method=mjlist";
		form1.submit();	
	}
</script>

<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a> </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>

<div id="Content">
 <%@ include file="left.jsp"%>
<div id="Right" class="clearfix">
<div class="position">当前位置： 用户管理 > 马甲管理</div>
<div class="content">
  <div class="searcharea">
<form action="" method="post" name="form1">

<label for="tag">按用户名：</label> <input type="text" class="input-text" name="nickname" id="tag">
<input type="button" value="搜索" class="button" name="search" onclick="query()">
</div>
<div class="table-list">
<div class="pd8"><input name="" type="button"  value="增加马甲" class="button" onClick="showdiv('添加马甲',400,300,'user.do?method=enterAddMj')">
</div>
<table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
        <thead>
          <tr>
            <th width="48">&nbsp;</th>
            <th width="100"><div class="w80">头像</div></th>
            <th width="100"><div class="w80">昵称</div></th>
            <th width="154"><div class="w120">注册时间</div></th>
            <th width="112"><div class="w50"><a href="#">关注数▲</a></div></th>
            <th width="80"><div class="w50"><a href="#">粉丝数▼</a></div></th>
            <th width="80" align="center"><div class="w50"><a href="#">好友数▼</a></div></th>
            <th width="296" align="center">新评论</th>
            <th width="296" align="center"><div class="w120">操作</div></th>
          </tr>
        </thead>
        <tbody>
         <c:forEach items="${vo.objList}" var="item">
          <tr>
            <td align="center"><input type="checkbox" value='<c:out value="${item.uid}"/>' id="newslist-<c:out value="${item.uid}"/>" name="uidList"></td>
            <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatarUrl}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatarUrl}"></span></td>
            <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">${item.nickName}</a></td>
            <td align="center"><span class="time">${item.createTime}</span></td>
            <td align="center">${item.followNum}</td>
            <td align="center">${item.fansNum} | ${item.fansFalseNum}</td>
            <td align="center">${item.friendNum }</td>
            <td align="center"><a href="#"class="showtagcontent" data-role="user.do?method=mjCommentList&uid=${item.uid}"><font color="red">${item.xpls}</font></a></td>
            <td align="center">
              <a href="#">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="###" onClick="del()"></a></td>
          </tr>
          </c:forEach>
          
          
          
        </tbody>
      </table>
<div class="pd8 clearfix">
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
