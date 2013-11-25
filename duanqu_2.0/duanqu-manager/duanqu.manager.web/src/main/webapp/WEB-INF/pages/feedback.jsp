<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='/duanqu.manager/dwr/interface/DemoController.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/engine.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/util.js'></script>


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
		form1.action="user.do?method=feedBackList";
		form1.submit();	
	}

function upd(id){
	var a=$("#"+id).text();
	DemoController.updateFeedBack(id,function(data){
	if(data=='处理成功'){
     $("#"+id).text('已处理')
	}
	art.dialog(data,function(){}).time(0.5);
	});
}


function del(id){
	if(confirm("你确定删除该条反馈信息吗?")){
	form1.action="user.do?method=deleteFeedBack&id="+id;
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
      <div class="position">当前位置： 用户管理 > 用户反馈</div>
      <div class="content">
         <form action="" name="form1" method="post">
          
          <div class="table-list">
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th ><div class="w80">头像</div></th>
                  <th ><div class="w80">用户名</div></th>
                  <th ><div class="w320">反馈内容</div></th>
                  <th><div class="w120">反馈时间</div></th>
                  <th  align="center"><div class="w120">状态</div></th>
                  <th align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.id}"/>" id="newslist-<c:out value="${item.id}"/>" name="idList"></td>
                  <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatar_url}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatar_url}"></span></td>
                  <td align="center">${item.nick_name}</td>
				  <td align="left">${item.feedback_text}</td>

                  <td align="left" class="time">${fn:substring(item.fksj,0,19)}</td>
                  <td align="center" id="${item.id}">
                  <c:choose>
                  <c:when test="${item.is_check==0}">
                                                                <a href="#" onclick="upd('${item.id}')">未处理</a>
                  </c:when>
                  <c:otherwise>
                                                                已处理
                  </c:otherwise>
                  </c:choose>
                  </td>
                  <td align="center"> 
				  <a href="###" onClick="showdiv('回复',300,200,'user.do?method=enterMessage&fslx=2&uid=${item.uid}&id=${item.id}')">回复</a>
				  &nbsp;&nbsp;&nbsp;
				  <a href="#" onclick="del('${item.id}')"> 删除</a>  
				  </td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="pd8  clearfix">
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
