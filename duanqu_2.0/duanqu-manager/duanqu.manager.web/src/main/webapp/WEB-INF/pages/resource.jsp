<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<%@ page import="com.duanqu.common.DateUtil" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css"/>
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
	form1.action="resource.do?method=list";
	form1.submit();	
}

function del(id){
	if(confirm("你确定删除该条数据吗?")){
		form1.action="resource.do?method=deleteBannerInfo&id="+id;
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
            <input name="" type="button" value="上传资源"  class="button" onClick="showdiv('上传资源',300,200,'resource.do?method=enter')"/>
            
           </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
               
                  <th width="108">描述</th>
                  <th width="108">图片</th>
                  <th width="108">类别</th>
                  <th width="343" align="center">操作</th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${vo.objList}" var="item">
                <tr>
				  <td align="center"><span class="time">${item.description}</span></td>
                  <td align="center"><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.resourceIconUrl}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunSystemImagesDomain()%>${item.resourceIconUrl}"></span></td>
				  <td align="center"><span class="time">
				  
				  <c:choose>
				  <c:when test="${item.type==1}">背景音乐</c:when>
				  <c:when test="${item.type==2}">表情</c:when>
				  <c:otherwise>贴纸</c:otherwise>
				  </c:choose>

				  </span></td>
				  <td align="center"> <a href="#" class="" onclick="del('${item.id}')">删除</a></td>

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
