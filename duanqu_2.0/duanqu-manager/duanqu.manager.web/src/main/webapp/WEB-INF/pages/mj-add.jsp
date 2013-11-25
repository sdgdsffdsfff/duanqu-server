<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<script type="text/javascript">
<c:if test="${message!=null}">
 alert("${message}");
 window.location="user.do?method=mjlist";
</c:if>

</script>
<div class="updiv">
<form id="form1" name="form1" enctype="multipart/form-data" method="post" action="user.do?method=saveMj">
  <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="32" align="right">昵称：</td>
      <td><label for="textarea2"></label>
        <input name="nickName" type="text" id="textarea2" value="" size="35"  class="input-text" required="required" placeholder="不超过15字" /></td>
    </tr>
    <tr>
      <td height="31" align="right" >头像：</td>
      <td><label for="fileField"></label>
        <input type="file" name="avatarUrl" id="fileField" class="input-text" /></td>
    </tr>
    <tr>
      <td height="31" align="right" >背景：</td>
      <td><label for="fileField2"></label>
        <input type="file" name="backgroundUrl" id="fileField2" class="input-text" /></td>
    </tr>
    <tr>
      <td width="87" height="44" align="right">个性签名：</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="signature"   required class="input-text" id="textarea" placeholder="" style="width:300px;height:100px"></textarea></td>
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td height="35"><input name="button" type="submit" class="button" id="button" value="添加" /></td>
    </tr>
  </table>
</form>
