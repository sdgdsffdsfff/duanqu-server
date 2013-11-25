

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
function choose(value){
	if(value=='tag' || value=='user' || value=='wap'){
		document.getElementById("cs").style.display="";
	}else{
        document.getElementById("cs").style.display="none";
	}
}	
function save(){
var bannerType=document.form2.bannerType.value;
if(bannerType=='tag' || bannerType=='user' || bannerType=='wap'){
if(document.form2.innerParam.value==""){
alert("类别是频道,达人,WAP页面的必须输入参数");
return false;
}
}
form2.submit();
}
</script>

</head>
<body>
<div class="updiv">
  <form id="form2" name="form2" enctype="multipart/form-data" method="post" action="banner.do?method=saveUpload">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="32" align="right">名称：</td>
      <td><label for="textarea2"></label>
        <input name="title" type="text" id="textarea2" value="" size="35"  class="input-text"   /></td>
    </tr>
    <tr>
      <td height="31" align="right" >图片：</td>
      <td><label for="fileField"></label>
        <input type="file" name="imgUrl" id="fileField" class="input-text" /></td>
    </tr>
      <tr>
        <td align="right" valign="top">类别：</td>
        <td><label for="textarea"></label>
        <select name="bannerType" onClick="choose(this.value)">
        <option value="tag">频道</option>
        <option value="user">达人</option>
        <option value="hot_talent">达人榜</option>
        <option value="invite">邀请好友</option>
        <option value="hot_content">热门内容</option>
        <option value="search_key">搜索</option>
        <option value="channel">热门频道</option>
        <option value="wap">WAP页面</option>
        </select>
        </td>
      </tr>
      <tr id="cs">
        <td align="right" valign="top">参数：</td>
        <td><label for="textarea"></label>
         <input name="innerParam" type="text" id="innerParam" value="" size="35"  class="input-text"   />
        </td>
      </tr>
      <tr >
        <td align="right" valign="top" >&nbsp;</td>
        <td><input name="button" type="button" class="button" id="button" value="保存"  onClick="save()"/>
          <input name="button" type="button" class="button" id="button" value="取消" /></td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
