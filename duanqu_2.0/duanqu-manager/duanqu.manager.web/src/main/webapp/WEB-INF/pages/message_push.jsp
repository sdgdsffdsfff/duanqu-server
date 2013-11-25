
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
	// art.dialog(data,function(){}).time(0.5);
	//  art.dialog.list['N3690'].close();
	if(document.form2.messageText.value==null || document.form2.messageText.value==""){
		alert("请输入发送的消息内容!");
		return false;
	}
	if(document.form2.messageText.value.length>60){
		alert("消息内容不能超过60个字!");
		return false;
	}
	
	var type=$("#type").val();
	var tslx=$('input[name="tslx"]:checked').val();
	var messageText=$("#textarea").val();
	var innerParam=$("#innerParam").val();
	var createTime=$("#createTime").val();
	if(tslx==undefined){
     tslx="";
	}
	 DemoController.saveMessagePush(type,tslx,messageText,innerParam,createTime,function(data)
	{  
      art.dialog(data,function(){}).time(0.5);
	  art.dialog.list['N3690'].close();
	}
	);
}
function choose(tslx){
 if(tslx==1){
 document.getElementById("tssj").style.display="none";
 document.form2.createTime.value="";
 }else{
 document.getElementById("tssj").style.display="";
 }

}

function select(type){
   if(type=="USER_INDEX" || type=="CONTENT_DETAIL" || type=="SEARCH_KEY" || type=="TAG" || type=="TOPIC_LIST" || type=="WAP_PAGE"){
    document.getElementById("cs").style.display="";
	if(type=="USER_INDEX"){
    $("#ts").html("<font color='red'>请输入用户ID</font>");
	}else if(type=="CONTENT_DETAIL"){
     $("#ts").html("<font color='red'>请输入内容ID</font>");
	}else if(type=="SEARCH_KEY"){
     $("#ts").html("<font color='red'>请输入关键词</font>");
	}else if(type=="TAG"){
      $("#ts").html("<font color='red'>请输入标签</font>");
	}else if(type=="TOPIC_LIST"){
      $("#ts").html("<font color='red'>请输入话题ID</font>");
	}else if(type=="WAP_PAGE"){
      $("#ts").html("<font color='red'>请输入URL</font>");
	}
   }else{
    document.getElementById("cs").style.display="none";
	document.form2.innerParam.value="";
   }
 
}


	
</script>
</head>
<body>
<div class="updiv">
<form id="form2" name="form2" method="post" action="">
  <table width="400" border="0" cellpadding="0" cellspacing="0">
  
    <tr>
        <td align="right" valign="top">类别：</td>
        <td><label for="textarea"></label>
        <select name="type" id="type" onClick="select(this.value)">
        <option value="MESSAGE_DIALOG">私信对话列表</option>
        <option value="MESSAGE_AT">私信AT消息</option>
        <option value="MESSAGE_COMMENT">消息评论列表</option>
        <option value="INDEX">首页</option>
        <option value="HOT_CONTENT">热门内容榜</option>
        <option value="HOT_TALENT">热门达人榜</option>
        <option value="HOT_CHANNEL">热门频道</option>
        <option value="USER_INDEX">用户个人中心</option>
		<option value="CONTENT_DETAIL">单条内容</option>
        <option value="SEARCH_KEY">搜索结果页面</option>
		<option value="WAP_PAGE">WAP页面</option>
        <option value="TAG">标签内容列表页面</option>
		<option value="TOPIC_INDEX">话题列表页面</option>
        <option value="TOPIC_LIST">话题内容列表页</option>
        </select>
        </td>
      </tr>
      <tr id="cs" style="display:none">
        <td align="right" valign="top">参数：</td>
        <td><label for="textarea"></label>
         <input name="innerParam" type="text" id="innerParam" value="" size="35"  class="input-text"   />
		 <div id="ts">
		 
		 </div>
        </td>
      </tr>
	  <tr>
        <td align="right" valign="top">推送类型：</td>
        <td><label for="textarea"></label>
          <input type="radio" name="tslx" value="1" id="qflx1" checked onclick="choose(1)"></input>立即推送
	     <input type="radio" name="tslx" value="2" id="qflx2" onclick="choose(2)"></input>定时推送
        </td>
      </tr>

	  <tr id="tssj" style="display:none">
        <td align="right" valign="top">推送时间：</td>
        <td><label for="textarea"></label>
          <input class="Wdate" type="text" name="createTime" id="createTime" value ="${vo.tssj}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
        </td>
      </tr>

	
    <tr>
      <td width="87" height="44" align="right">&nbsp;</td>
      <td width="313"><label for="textarea"></label>
        <textarea name="messageText"   required class="input-text" id="textarea" placeholder="" style="width:300px;height:100px"></textarea></td>
    </tr>
    <tr>
      <td align="right" valign="top" >&nbsp;</td>
      <td><input name="button" type="button" class="button" id="button" onClick="saveMessage()" value="发送" /></td>
    </tr>
  </table>
</form>
</body>
</html>