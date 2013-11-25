<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>短趣APP编辑管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">

function checkRedio(){
	var flag=false;
    var uid=document.getElementsByName("uid");
	for(var i=0;i<uid.length;i++){
    if(uid[i].checked==true){
     flag=true;
	 break;
	}
	}
	return flag;
}

function save(){
	
    if(checkRedio()){
	var uid=$('input:radio[name="uid"]:checked').val(); 
	var cid=$("#cid").val();
	DemoController.saveForward(uid,cid,function(data){
     art.dialog(data,function(){}).time(0.5);
	 art.dialog.list['N3690'].close();
	});
	}else{
     alert("请选择一个公共账号");
	 return false;
	}
}
</script>




</head>	


	
	
<div class="updiv">
<form id="form2" name="form2" method="post" action="">
    <input type="hidden" name="cid" id="cid" value="${mContentSubmit.cid}">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
     <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea">请选择公共账号：</label>
      </br>
      	<c:forEach items="${listPublishUser}" var="item">
         <input type="radio" name="uid" value="${item.uid}">${item.nick_name}</input> </br>
        </c:forEach>
      </td>     
      </tr>
    <tr>
    	<td width="87" height="44" align="right">&nbsp;</td>
      <td align="left"><input name="button" type="button" onclick="art.dialog.list['N3690'].close()" class="button" id="button" value="取消" />
      <input name="button" type="button" class="button" id="button" value="确定" onclick="save()"/></td>
    </tr>
  </table>
</form>
</div>
</html>
