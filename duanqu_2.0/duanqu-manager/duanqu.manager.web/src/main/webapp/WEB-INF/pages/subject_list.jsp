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
	form1.action="subject.do?method=list";
	form1.submit();	
}

function deleteTs(sid){
	if(confirm("你确定删除该条话题吗?")){
		form1.action="subject.do?method=deleteSubject&sid="+sid;
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
	  <div class="searcharea">
        <form action="" method="post" name="form1">
         <label for="keyword">按关键字:</label>
            <input type="text" class="input-text" name="title" id="keyword" value="${vo.title}"/>
			<input type="button" value="搜索" class="button" name="search" onclick="query()"/>
			</div>
          <div class="table-list">
           <div class="pd8">
            <input name="" type="button" value="添加"  class="button" onClick="showdiv('添加话题',400,300,'subject.do?method=enter')"/>
           </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="40">话题ID</th>
                  <th width="40">话题名</th>
                  <th width="108">介绍图片</th>
                  <th width="108">横幅图片</th>
                  <th width="300">话题介绍</th>
                  <th width="100">参数</th>
                   <th width="108">话题内容数</th>
                  <th width="100" align="center">操作</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center">
                  ${item.sid}
                  </td>
                  <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">${item.title}</a></td>
                  <td align="center"><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.explain_url}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.explain_url}"></span></td>
                  <td align="center"><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.img_url}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.img_url}"></span></td>
                  <td align="center">${item.description}</td>
				   <td align="center">${item.inner_param}</td>    
                  <td align="center">
                  <a href="content.do?method=list&cxrk=4&sid=${item.sid}">${item.zs}</a>
                  </td>
				  <td align="center">
                  <a href="###" onclick="deleteTs('${item.sid}')">删除</a>
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
