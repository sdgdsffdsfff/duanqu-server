<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<%@ page import="com.duanqu.common.DateUtil" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">	

var ts="${message}";
<c:if test="${message!=null}">
 alert(ts);
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
	form1.action="tag.do?method=hotList";
	form1.submit();	
}

function deleteTs(bid){
	if(confirm("你确定删除该条运营条吗?")){
		form1.action="banner.do?method=deleteBannerInfo&bid="+bid;
		form1.submit();
	}
}
function fb(){
	form1.action="banner.do?method=fbBannerInfo";
	form1.submit();
}



</script>
</head>

<body>
<script src="js/jquery.imagePreview.js" ></script>
<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a>  </span>
    <h2>短趣APP编辑管理中心</h2>
  </div>
  <div id="Content">
   <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 标签管理</div>
      <div class="content">        
        <form action="" method="post" name="form1">
         
          <div class="table-list">
           <div class="pd8">
            <input name="" type="button" value="添加"  class="button" onClick="showdiv('添加运营条',300,200,'banner.do?method=enter')">
           <input name="" type="button" value="发布"  class="button" onClick="fb()">
           </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th width="108">名称</th>
                  <th width="108">图片</th>
                  <th width="108">类别</th>
                   <th width="108">链接地址</th>
                  <th width="343" align="center">操作</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.bid}"/>" id="newslist-<c:out value="${item.bid}"/>"></td>
                  <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">${item.title}</a></td>
                  <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.imgUrl}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.imgUrl}"></span></td>
                  <td align="center">
				   <c:choose>
                  	<c:when test="${item.bannerType=='tag'}">频道</c:when>
                  	<c:when test="${item.bannerType=='user'}">达人</c:when>
                  	<c:when test="${item.bannerType=='hot_talent'}">达人榜</c:when> 
					<c:when test="${item.bannerType=='invite'}">激请好友</c:when> 
					<c:when test="${item.bannerType=='hot_content'}">热门内容</c:when>
					<c:when test="${item.bannerType=='search_key'}">搜索</c:when>
					<c:when test="${item.bannerType=='channel'}">热门频道</c:when>
					<c:when test="${item.bannerType=='wap'}">WAP页面</c:when>
                  </c:choose>
				  </td>
                  <td align="center">${item.innerUrl}</td>     
                  <td align="center">
                  <input type="text" size="3" name="numList" value="${item.orderNum}" />
                  <input type="hidden" name="bidList" value="${item.bid}"/>
                  <a href="#" onClick="deleteTs(${item.bid})">删除</a>
                  </td>
                </tr>
                </c:forEach>
               
              </tbody>
            </table>
            <div class="pd8 clearfix">
               <%@ include file="page.jsp"%> </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
