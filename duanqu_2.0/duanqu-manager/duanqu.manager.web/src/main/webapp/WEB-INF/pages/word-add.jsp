
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
<script src="js/jquery.imagePreview.js" ></script>

<script type="text/javascript">
    <c:if test="${message!=null}">
	alert("${message}");
	window.location="filter.do?method=list";
	</c:if>
	function save(){

    if(document.form2.filterText.value=="" || document.form2.filterText.value==null){
		alert("请输入敏感词");
		return false;
	}
	form2.submit();
	}
</script>

<div class="updiv">
  <form id="form2" name="form2" method="post" action="filter.do?method=saveWord">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="87" height="44" align="right">敏感字：</td>
        <td width="313"><label for="textarea"></label>
          <input name="filterText" type="text" id="textarea" value="" size="35"  class="input-text" required placeholder="必填，最多15字"></td>
      </tr>
      <tr>
        <td align="right" valign="top" >&nbsp;</td>
        <td><input name="button" type="submit" class="button" id="button" value="添加" onclick="save()"/>
          <input name="button" type="button" class="button" id="button" value="取消" onclick="art.dialog.list['N3690'].close()"/></td>
      </tr>
    </table>
  </form>
</div>
