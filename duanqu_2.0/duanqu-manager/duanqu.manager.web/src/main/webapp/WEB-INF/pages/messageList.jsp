<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<c:if test="${message!=null}">
    alert("${message}");
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
		form1.action="user.do?method=messageList";
		form1.submit();	
	}


function del(id,uid){
	if(confirm("你确定删除该条私信吗?")){
	form1.action="user.do?method=deleteMessage&messageId="+id+"&uid="+uid;
	form1.submit();
	}
}

function empty(){
document.form1.messageText.value="";
document.form1.createTimeQ.value="";
document.form1.createTimeZ.value="";

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
        <div class="tab"> <a href="content.do?method=dqjList">内容管理</a> <a href="user.do?method=messageList" class="on">私信管理</a> </div>
        <div class="searcharea">
          <form  method="post" name="form1">
           <label for="keyword">按关键字:</label>
            <input type="text" class="input-text" name="messageText" id="messageText" value="${vo.messageText}"/>
            <label for="timebegin">按时间：</label>
            <input class="Wdate" type="text"  name="createTimeQ" value="${vo.createTimeQ}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
            -
            <input class="Wdate" type="text" name="createTimeZ" value ="${vo.createTimeZ}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
            <input type="button" value="搜索" class="button" name="search" onclick="query()"/>
			<input type="button" value="清空" class="button" name="search" onclick="empty()"/>
        </div>
          <div class="table-list">
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="50">&nbsp;</th>
                  <th width="98"><div class="w80">头像</div></th>
                  <th width="138"><div class="w120">用户名</div></th>
                  <th width="178"><div class="w160">发送时间</div></th>
                  <th width="178"><div class="w160">私信内容</div></th>
                  <th width="366" align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.id}"/>" id="newslist-<c:out value="${item.id}"/>" name="idList"></td>
                  <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatar_url}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatar_url}"></span></td>
                  <td align="center">${item.nick_name}</td>
                  <td align="left" class="time">${fn:substring(item.fssj,0,19)}</td>
                  <td>${item.message_text}</td>
                  <td align="center">
                  <c:if test="${item.uid!=1}">
                  <a href="###" onClick="showdiv('回复',300,200,'user.do?method=enterMessage&fslx=1&uid=${item.uid}&messageId=${item.id}')">回复</a>
                  </c:if>
                  &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="" onclick="del('${item.id}','${item.uid}')">删除</a>
                  
                  &nbsp;&nbsp;&nbsp;&nbsp;<a href="#"class="showtagcontent" data-role="user.do?method=messageDetail&uid=${item.uid}&recUid=${item.rec_uid}">详情</a>
                  </td>
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
