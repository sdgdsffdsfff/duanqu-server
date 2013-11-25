
<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
<c:if test="${message!=null}">
	alert("${message}");
	window.location="content.do?method=list";
//	window.close();
</c:if>
	

function saveComment(){
  if(document.form2.commentText.value==""){
  alert("请输入评论内容！");
  return false;
  }
  DemoController.saveComment($("#cid").val(),$("#textarea").val(),function(data){
	  art.dialog(data,function(){}).time(0.5);
	  art.dialog.list['N3690'].close();
  });
  
  
  
  
}
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="content.do?method=saveComment">
<input type="hidden" name=cid id="cid" value="${cid}"/>
  <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="commentText" id="textarea" style="width:350px;height:100px"></textarea></td>
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="submit" type="button" class="button" id="button" value="添加" onclick="saveComment()" /></td>
    </tr>
  </table>
</form>
</body>
</html>