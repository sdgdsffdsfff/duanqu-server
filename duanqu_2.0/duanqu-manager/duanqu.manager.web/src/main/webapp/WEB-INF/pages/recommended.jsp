
<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css"/>
<script type='text/javascript' src='/duanqu.manager/dwr/interface/DemoController.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/engine.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/util.js'></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="js/common.js"></script>
	
<script type="text/javascript">


function save(){
	if(document.form2.reason.value==null || document.form2.reason.value==""){
		alert("请输入推荐理由!");
		return false;
	}
	var type=$("#type").val();
	var uid=$("#uid").val();
	var reason=$("#reason").val();
	DemoController.insertRecommended(uid,type,reason,function(data){
	 art.dialog(data,function(){}).time(0.5);
	 art.dialog.list['N3690'].close();
	});
	
	
}
	
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="user.do?method=saveMessage">
	<input type="hidden" name="uid" value="${uid}" id="uid"/>
	<input type="hidden" name="type" value="${type}" id="type"/>
	

  <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="reason"   required class="input-text" id="reason" placeholder="" style="width:350px;height:100px"></textarea></td>
    </tr>
    <tr>
    <td width="87" height="44" align="right">&nbsp;</td>
    <td width="313" height="44">
	<input name="button" type="button" class="button" id="button" value="取消" onclick="art.dialog.list['N3690'].close()" />
	<input name="button" type="button" class="button" id="button" onClick="save()" value="确定" />
	</td>
    </tr>
  </table>
</form>
</body>
</html>