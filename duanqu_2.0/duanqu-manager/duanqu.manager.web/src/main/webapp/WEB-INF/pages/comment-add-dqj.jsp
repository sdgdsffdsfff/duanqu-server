
<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
 <script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
<c:if test="${message!=null}">
	alert("${message}");
	window.location="content.do?method=list";
//	window.close();
</c:if>
	
	
/*function save(){
  if(document.form1.commentText.value==""){
  alert("请输入评论内容！");
  return false;
  }
	form1.submit();
}*/
</script>
</head>
<body>
<div class="updiv">
<form id="form1" name="form1" method="post" action="user.do?method=saveCommentDqj">
<input type="hidden" name=cid value="${commentModel.cid}"/>
<input type="hidden" name=uid value="${commentModel.uid}"/>
<input type="hidden" name=replyUid value="${commentModel.replyUid}"/>
<input type="hidden" name=parentId value="${commentModel.parentId}"/>
<input type="hidden" name=rootId value="${commentModel.rootId}"/>
<input type="hidden" name=page value="${page}"/>
  <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="commentText" id="textarea" style="width:350px;height:100px"></textarea></td>
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="submit" type="submit" class="button" id="button" value="添加" /></td>
    </tr>
  </table>
</form>
</body>
</html>