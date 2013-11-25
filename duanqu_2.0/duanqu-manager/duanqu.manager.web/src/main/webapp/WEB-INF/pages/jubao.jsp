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
	
	<c:if test="${message!=null}">
	alert("${message}");
	</c:if>
	
	
function deleteContent(cid,sStatus){
		var str;
		if(sStatus=="3"){
			str="你确定屏蔽该条内容吗?";
		}else if(sStatus=="2"){
			str="你确定删除该条内容吗?";
		}
		if(confirm(str)){
			form1.action="content.do?method=deleteReport&sStatus="+sStatus+"&cid="+cid;
			form1.submit();
		}
	}
	
	function updateReport(jbid){
		if(confirm("你确定驳回该条举报信息?")){
			form1.action="content.do?method=updateReport&jbid="+jbid;
			form1.submit();
		}
	}
	function query(index){
	if(index!=undefined){
		var patrn=/^[1-9][0-9]*$/;
		if(!patrn.exec(index)){
			form1.page.value=1;
		}else{
			form1.page.value=index;
		}
	}
	form1.action="content.do?method=reportList";
	form1.submit();	
}
	
	

</script>
</head>

<body>
<script src="js/jquery.imagePreview.js" ></script>
<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a>
  	 <p><b class="s1">皮肤1</b><b class="s2">皮肤1</b><b class="s3">皮肤1</b><b class="s4">皮肤1</b></p>
  	</span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
     <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 举报管理</div>
      <div class="content">
         <form  method="post" name="form1">
          <div class="table-list">
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th ><div class="w320">被举报内容（视频图片）</div></th>
                  <th ><div class="w320">内容描述</div></th>
                  <th><div class="w120">举报人</div></th>
                  <th><div class="w120">被举报人</div></th>
                  <th><div class="w120"><a href="#">举报时间▲</a></div></th>
                  <th align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              <c:choose>
              <c:when test="${vo.objList== null || fn:length(vo.objList) == 0}">	
              	<tr>
              	<td align="center" colspan="7">	
              	没有查询记录
                </td>
              	</tr>
              </c:when>
              <c:otherwise>	
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.cid}"/>" id="newslist-<c:out value="${item.cid}"/>" name="cidList"></td>
                  <td  align="center"><span class="imgpreview" ><img src="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" width="35" class="preview videopic" bimg="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" data-role="<%=DuanquConfig.getAliyunHDVideoDomain()%>${item.videoUrlHd}"></span></td>
                  <td align="left">${item.description}</td>
                  <td align="center">${item.jbrName}</td>
                  <td align="center">${item.bjbrName}</td>
                  <td align="center"><span class="time">${fn:substring(item.jbsj,0,19)}</span></td>
                  <td align="center"><a href="#" onclick="deleteContent(${item.cid},3)">屏蔽</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteContent(${item.cid},2)">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="updateReport(${item.jbid})">驳回</a></td>
                </tr>
               </c:forEach> 
              </c:otherwise> 
               </c:choose>	
              </tbody>
            </table>
            <div class="pd8  clearfix">
            	<c:if test="${(vo.objList)!= null && fn:length(vo.objList) > 0}">

                <%@ include file="page.jsp"%>    
              </c:if>            
            </div>
            
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
