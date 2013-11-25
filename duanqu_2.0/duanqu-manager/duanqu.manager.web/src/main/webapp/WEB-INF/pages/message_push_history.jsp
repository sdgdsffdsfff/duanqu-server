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
var ts="${message}";
<c:if test="${message!=null}">
art.dialog(ts,function(){}).time(0.5);
</c:if>



function deleteTs(id){
	if(confirm("你确定取消该条推送吗?")){
		form1.action="user.do?method=deletePushMessage&id="+id;
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
	form1.action="user.do?method=pushMessageHistory";
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
      <div class="position">当前位置： 用户管理 > 用户列表</div>
      <div class="content">
        <div class="tab"> <a href="user.do?method=list" >用户列表</a> <a href="user.do?method=tsList"  >推荐用户列表</a> <a href="user.do?method=tsHotList">推荐热门列表</a><a href="user.do?method=pushMessageHistory" class="on">推送历史记录</a> </div>
         <div class="searcharea">
           <form  method="post" name="form1">
            <label for="select">按推送状态：</label>
            <select name="isShow" id="select">
              <option value="">请选择</option>
              <option value="0" <c:if test="${vo.isShow=='0'}">selected</c:if> >未推送</option>
              <option value="1" <c:if test="${vo.isShow=='1'}">selected</c:if>>已推送</option> 
            </select>
		   <input type="button" value="搜索" class="button"  name="search" onclick="query()"/>
		    </div>
          <div class="table-list">
            <div class="pd8"></div>
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="50">&nbsp;</th>
                  <th width="98"><div class="w80">类型</div></th>
                  <th width="98"><div class="w80">参数</div></th>
                  <th width="178"><div class="w160">推送说明</div></th>
                  <th width="178"><div class="w160">推送状态</div></th>
                  <th width="98" align="center"><div class="w160"><a href="#">推送时间▲</a></div></th>
                  <th width="366" align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.id}"/>" id="newslist-<c:out value="${item.id}"/>" name="uidList"></td>
                  <td align="center"><span class="time">
				  <c:if test="${item.type=='MESSAGE_DIALOG'}">
                  私信对话列表
				  </c:if>
				  <c:if test="${item.type=='MESSAGE_AT'}">
                  私信AT消息
				  </c:if>
				  <c:if test="${item.type=='MESSAGE_COMMENT'}">
                  消息评论列表
				  </c:if>
				  <c:if test="${item.type=='INDEX'}">
                  首页
				  </c:if>
				  <c:if test="${item.type=='HOT_CONTENT'}">
                  热门内容榜
				  </c:if>
				  <c:if test="${item.type=='HOT_TALENT'}">
                  热门达人榜
				  </c:if>
				  <c:if test="${item.type=='HOT_CHANNEL'}">
                  热门频道
				  </c:if>
				  <c:if test="${item.type=='USER_INDEX'}">
                  用户个人中心
				  </c:if>
				  <c:if test="${item.type=='CONTENT_DETAIL'}">
                  单条内容
				  </c:if>
				  <c:if test="${item.type=='SEARCH_KEY'}">
                  搜索结果页面
				  </c:if>
				  <c:if test="${item.type=='WAP_PAGE'}">
                  WAP页面
				  </c:if>
				  <c:if test="${item.type=='TAG'}">
                  标签内容列表页面
				  </c:if>
				  <c:if test="${item.type=='TOPIC_INDEX'}">
                  话题列表页面
				  </c:if>
				  <c:if test="${item.type=='TOPIC_LIST'}">
                  话题内容列表页
				  </c:if>
				  </span></td>
                  <td align="center"><span class="time">${item.innerParam}</span></td>
                  <td align="center"><span class="time">${item.messageText}</span></td>
                  <td align="center"><span class="time">
				  <c:if test="${item.isShow=='0'}">
                  未推送
				  </c:if>
				  <c:if test="${item.isShow=='1'}">
                  已推送
				  </c:if>
				  </span></td>
                  <td align="center"><span class="time">${fn:substring(item.createTimeStr,0,19)}</span></td>
                  <td align="center">
                  <c:if test="${item.isShow=='0'}">
                  <a href="###" onclick="deleteTs(${item.id})">取消推送</a>
                  </c:if>
                  </td>
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
