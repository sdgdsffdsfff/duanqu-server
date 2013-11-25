<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script src="js/jquery.imagePreview.js" ></script>
</head>
<script type="text/javascript">

 

function del(id,uid){
DemoController.deleteMessage(id,uid,function(data){
if(data=='删除成功'){
$("#"+id).remove();
}
art.dialog(data,function(){}).time(0.5);
});



}

function save(){
var uid=${uid};
var messageText=$("#tagText").val();
DemoController.insertMessageDetail(uid,messageText,function(data){
if(data.message=='回复成功'){
var ts="<p id='"+data.id+"'><font color='#006400'>趣拍君&nbsp;&nbsp;"+data.time+"</font> <br>"+messageText+"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=\"del('"+data.id+"','"+uid+"')\">删除</a></p>";
$("#pid").append(ts);

}
art.dialog(data.message,function(){}).time(0.5);
});
}
  




	
</script>


<body style="background:#fff">
<div id="Main">
<div class="table-list">
 <form  method="post" name="form1">
 <div id="pid">
 <c:forEach items="${detailList}" var="item">
 <c:choose>
 <c:when test="${item.uid==1}">
 <p id="${item.id}"><font color="#006400">${item.nick_name}&nbsp;&nbsp;${fn:substring(item.fssj,0,19)}</font> <br>
 ${item.message_text} &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="" onclick="del('${item.id}','${item.uid}')">删除</a>
 </p>
 </c:when>
 <c:otherwise>
 
 <p id="${item.id}"><font color="#0000FF">${item.nick_name}&nbsp;&nbsp;${fn:substring(item.fssj,0,19)}</font> <br>
 ${item.message_text} &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="" onclick="del('${item.id}','${item.uid}')">删除</a>
 </p>
 </c:otherwise>
 </c:choose>
 </c:forEach>
 </div>
 <hr>
 <textarea name="tagText" id="tagText" cols="35" rows="5" class="textarea-text"></textarea>
 <input type="button" value="回复" class="button"  name="search" onclick="save()">
          
 </form>
  
</div>
</div>
</body>
</html>
