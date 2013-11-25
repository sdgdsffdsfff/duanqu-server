
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
	
function saveJy(){
    var uid=$("#uid").val();
	DemoController.saveUserJy(uid,$("#textarea").val(),$("#banEndtimeStr").val(),function(data){
	if(data=='禁言成功'){
     var value="<a href='#' onclick='jcjy("+uid+")'>解除禁言</a>";
     $("#jy"+uid).html(value);
	}
	art.dialog(data,function(){}).time(0.5);
	art.dialog.list['N3690'].close();
	});

}
	
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="user.do?method=saveUserJy">
	<input type="hidden" name="uid" value="${uid}" id="uid">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea">禁言原因:</label>
        <textarea name="banText"   required class="input-text" id="textarea" placeholder="" style="width:350px;height:100px"></textarea></td>
    </tr>
    <tr>
     <td width="87" height="44" align="right">&nbsp;</td>
     <td width="313"><label for="input_text">禁言结束时间:</label>
     <input class="Wdate" type="text"  name="banEndtimeStr" id="banEndtimeStr" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
    </td>       
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="button" type="button" class="button" id="button" value="保存" onClick="saveJy()" /></td>
    </tr>
  </table>
</form>
</body>
</html>