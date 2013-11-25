<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
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

function deleteTs(tid){
	if(confirm("你确定取消该条推荐标签吗?")){
		form1.action="tag.do?method=deleteTsHot&type=2&tid="+tid;
		form1.submit();
	}
}
function fb(){
	form1.action="tag.do?method=fb";
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
        <div class="tab"> <a href="tag.do?method=editerTagList"  >标签列表</a> <a href="tag.do?method=hotList" >推荐列表</a> <a href="tag.do?method=hotImageList" class="on">推荐图片标签列表</a> </div>
        <div class="searcharea">
        <form action="" method="post" name="form1">
		
            <label for="tag">按标签名：</label>
            <input type="text" class="input-text" name="tagText" id="tag" value="${vo.tagText}">
            <input type="button" value="搜索" class="button" name="search" onclick="query()">
        </div>
          <div class="table-list">
           <div class="pd8">
           <input name="" type="button" value="发布"  class="button" onClick="fb()">
           </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th width="108">标签名</th>
                  <th width="108">标签图片</th>
                  <th width="108">标签位置</th>
                  <th width="342" align="center">推送时间</th>
                  <th width="343" align="center">操作</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.tid}"/>" id="newslist-<c:out value="${item.tid}"/>"></td>
                  <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">${item.tag_text}</a></td>
                  <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.image_url}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.image_url}"></span></td>
                  <td>
                  <input type="text" size="3" name="numList" value="${item.order_num}"/>
                  <input type="hidden" name="tidList" value="${item.tid}"/>
                  </td>
                  <td align="center"><span class="time">${fn:substring(item.createTime,0,19)}</span></td>
                  <td align="center"><a href="#" onClick="deleteTs(${item.tid})">取消推送</a></td>
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
