
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
 window.location="user.do?method=list";
</c:if>



function choose(lx){
	if(lx==0){
		document.getElementById("fbsj").style.display="none";
	}
	if(lx==1){
		document.getElementById("fbsj").style.display="";
	}
}	


	
function save(){

  var rec=$('input[name="recommend"]:checked').val();
  if(rec==undefined){
	  rec=0;
  }
  DemoController.saveTsrecommend($('input[name="tslx"]:checked').val(),$("#type").val(),rec,$("#cid").val(),$("#tssjStr").val(),function(data){
	  art.dialog(data,function(){}).time(0.5);
	  art.dialog.list['N3690'].close();
  });



	
}
	
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="content.do?method=tsrecommend">
	<input type="hidden" name="cid" id="cid" value="${mContentSubmit.cid}">
	<input type="hidden" name="type" id="type" value="${mContentSubmit.type}">
  <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="right" valign="top">推送类型：</td>
        <td><label for="textarea"></label>
        <input type="radio" name="tslx" value="0" checked onclick="choose(0)" />立即推送
        <input type="radio" name="tslx" value="1" onclick="choose(1)" />定时推送
    </tr>
    <tr id="fbsj" style="display:none">
        <td align="right" valign="top">发布时间：</td>
        <td><input class="Wdate" type="text"  name="tssjStr" id="tssjStr" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" ></td>
    </tr>
     <tr>
        <td align="right" valign="top">&nbsp;</td>
        <td><input type="checkbox" value="1" name="recommend" id="recommend"/>
                             加&ldquo;荐&rdquo;标识</td>
      </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="button" type="button" class="button" id="button" value="推送" onclick="save()" /></td>
    </tr>
  </table>
</form>
</body>
</html>