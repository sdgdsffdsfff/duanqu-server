
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


function saveMessage(){
	if(document.form2.messageText.value==null || document.form2.messageText.value==""){
		alert("请输入发送的消息内容!");
		return false;
	}
	var qflx=$('input[name="qflx"]:checked').val();
	var fslx=$("#fslx").val();
	var id=$("#id").val();
	var messageId=$("#messageId").val();
	if(qflx==undefined){
     qflx="";
	}
    DemoController.saveMessage($("#uid").val(),fslx,qflx,messageId,id,$("#textarea").val(),function(data)
	{  
	  if(data=='发送成功'){
      if(fslx==1){
       $("#"+messageId).text('已回复')
	  }
	   if(fslx==2){
       $("#"+id).text('已处理')
	  }
      
	  }
      art.dialog(data,function(){}).time(0.5);
	  art.dialog.list['N3690'].close();
	}
	);
	


}
	
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="user.do?method=saveMessage">
	<input type="hidden" name="uid" value="${managerMessageSubmit.uid}" id="uid"/>
	<input type="hidden" name="fslx" value="${managerMessageSubmit.fslx}" id="fslx"/>
	<input type="hidden" name="messageId" value="${managerMessageSubmit.messageId}" id="messageId"/>
	<input type="hidden" name="id" value="${managerMessageSubmit.id}" id="id"/>

  <table width="400" border="0" cellpadding="0" cellspacing="0">
   <c:if test="${managerMessageSubmit.type==2}">
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="radio"></label>
	  <input type="radio" name="qflx" value="1" id="qflx1">给所有人群发</input>
	  <input type="radio" name="qflx" value="2" id="qflx2" checked>给选中人群发</input>
      </td>
    </tr>
	</c:if>
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="messageText"   required class="input-text" id="textarea" placeholder="" style="width:350px;height:100px"></textarea></td>
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="button" type="button" class="button" id="button" onClick="saveMessage()" value="发送" /></td>
    </tr>
  </table>
</form>
</body>
</html>