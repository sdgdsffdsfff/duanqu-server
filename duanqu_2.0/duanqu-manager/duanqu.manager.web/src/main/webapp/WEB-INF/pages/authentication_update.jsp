
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
var secs = 20;    
var wait = secs * 1000; 
function disButton(){
document.form2.button.value = "保 存(" + secs + ")";  
document.form2.button.disabled = true;    
 for(i = 1; i<= secs; i++) {   
		window.setTimeout("update(" + i + ")",i * 1000);   
		}   
		window.setTimeout("timer()", wait); 
}
function update(num) {             
	if(num == (wait/1000)) {       
		document.form2.button.value = "保 存";}       
		else {      
			var printnr = (wait / 1000) - num;      
			document.form2.button.value = "保 存(" + printnr + ")";}     
			} 
function timer() {       
	document.form2.button.disabled = false;      
	document.form2.button.value = "保 存";
	}       
	  
function saveMessage(){
	if(document.form2.messageText.value==null || document.form2.messageText.value==""){
		alert("请输入认证理由!");
		return false;
	}
   // disButton();
	var uid=$("#uid").val();
    DemoController.updateAuthentication(uid,$("#textarea").val(),function(data)
	{  
      art.dialog(data,function(){}).time(1);
	  art.dialog.list['N3690'].close();
	}
	);
	


}
	
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="user.do?method=saveMessage">
	<input type="hidden" name="uid" value="${uid}" id="uid"/>
  <table width="400" border="0" cellpadding="0" cellspacing="0">
  
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="messageText"   required class="input-text" id="textarea" placeholder="" style="width:350px;height:100px">${messageText}</textarea></td>
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="button" type="button" class="button" id="button" onClick="saveMessage()" value="保 存" /></td>
    </tr>
  </table>
</form>
</body>
</html>