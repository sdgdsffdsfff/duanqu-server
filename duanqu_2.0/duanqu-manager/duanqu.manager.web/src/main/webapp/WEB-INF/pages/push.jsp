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
</head>
<body>
<script type="text/javascript">	

<c:if test="${message!=null}">
 alert("${message}");
</c:if>



function deleteTs(cid,typeValue){
	if(confirm("你确定取消该条推送内容吗?")){
		form1.action="content.do?method=deleteTs&cid="+cid+"&typeT="+typeValue;
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
	form1.action="content.do?method=tsList";
	form1.submit();	
}

</script>


<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a>
    <p><b class="s1">皮肤1</b><b class="s2">皮肤1</b><b class="s3">皮肤1</b><b class="s4">皮肤1</b></p>
    </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
     <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 内容列表</div>
      <div class="content">
        <div class="tab"> <a href="content.do?method=list&cxrk=1"  >所有内容</a> <a href="content.do?method=tsList"  class="on">推送内容</a><a href="content.do?method=tsHotList" >热门推送</a> </div>
        <div class="searcharea">
           <form  method="post" name="form1">
            <label for="tag">按关键字：</label>
            <input type="text" class="input-text" name="key" id="tag" value="${vo.key}">
            <label for="tag">按发布者：</label>
            <input type="text" class="input-text" name="nickName" id="tag" value="${vo.nickName}">
            <label for="push">按推送位置：</label>
            <label for="select"></label>
            <select name="type" id="select">
              <option value="">请选择</option>
              <option value="0" <c:if test="${vo.type=='0'}"> selected </c:if>  >首页编辑推荐</option>
              <option value="1" <c:if test="${vo.type=='1'}"> selected </c:if>  >发现编辑推荐</option>
            </select>
            <input type="button" value="搜索" class="button" name="search" onclick="query()">
          
        </div>
          <div class="table-list">
            <div class="pd8"></div>
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="50">&nbsp;</th>
                  <th width="98"><div class="w80">视频图片</div></th>
                  <th width="98"><div class="w80">发布者</div></th>
                  <th width="178"><div class="w160">描述</div></th>
                  <th width="178"><div class="w160">
                      <DIV id="u2811">
                        <DIV id="u2811_rtf">
                          <p>推送位置</p>
                        </DIV>
                      </DIV>
                    </div></th>
                  <th width="98" align="center"><div class="w160"><a href="#">推送时间▲</a></div></th>
                  <th width="366" align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.cid}"/>" id="newslist-<c:out value="${item.cid}"/>" name="cidList"></td>
                  <td  align="center"><span class="imgpreview" ><img src="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" width="35" class="preview videopic" bimg="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" data-role="<%=DuanquConfig.getAliyunHDVideoDomain()%>${item.videoUrlHd}"></span></td>
                  <td  align="center"><a href="#" charset="hz-4006606-1000631" target="_blank">${item.nickName}</a></td>
                  <td align="center">${item.description}</td>
                  <td align="center"><c:if test="${item.type==0}">首页编辑推荐</c:if><c:if test="${item.type==1}">发现编辑推荐</c:if></td>
                  <td align="center"><span class="time">${fn:substring(item.createTime,0,19)} </span></td>
                  <td align="center"><a href="#" onclick="deleteTs(${item.cid},${item.type})" >取消推荐</a></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="pd8 clearfix">
                <%@ include file="page.jsp"%>       
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
