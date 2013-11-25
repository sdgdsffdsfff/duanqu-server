

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
<c:if test="${message!=null}">
 alert("${message}");
 <c:choose>
 <c:when test="${dqj==1}">
 window.location="content.do?method=dqjList";
 </c:when>
 <c:otherwise>
 window.location="content.do?method=list";
 </c:otherwise>
 </c:choose>
</c:if>

function choose(lx){
	if(lx==0){
		document.getElementById("fbsj").style.display="none";
	}
	if(lx==1){
		document.getElementById("fbsj").style.display="";
	}
}	
</script>

</head>
<body>
<div class="updiv">
  <form id="form1" name="form1" enctype="multipart/form-data" method="post" action="content.do?method=saveUpload">
    <input type="hidden" name="dqj" value="${dqj}"/>
    <table width="400" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" align="right" valign="top">选择视频：</td>
        <td width="300"><label for="fileField"></label>
          <input type="file" name="video" id="fileField" required /></td>
      </tr>
      <tr>
        <td width="100" align="right" valign="top">选择首帧图片：</td>
        <td width="300"><label for="fileField"></label>
          <input type="file" name="thumbnails" id="fileField" required /></td>
      </tr>
      <tr>
        <td align="right" valign="top">描述：</td>
        <td><label for="textarea"></label>
          <textarea name="description" id="textarea" cols="35" rows="5"  ></textarea></td>
      </tr>
      <tr>
        <td align="right" valign="top">发送类型：</td>
        <td><label for="textarea"></label>
        <input type="radio" name="isShow" value="0" checked onclick="choose(0)" />立即发送
        <input type="radio" name="isShow" value="1" onclick="choose(1)" />定时发送
      </tr>
      <tr id="fbsj" style="display:none">
        <td align="right" valign="top">发布时间：</td>
        <td><input class="Wdate" type="text"  name="uploadTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" ></td>
      </tr>
      <tr>
        <td align="right" valign="top">&nbsp;</td>
        <td><input type="checkbox" value="1" <c:if test="${dqj==1}"> checked </c:if> name="isDqj"/>
          以&ldquo;短趣君&rdquo;发布 </td>
      </tr>
      <c:if test="${dqj==1}">
        <tr>
        <td align="right" valign="top">&nbsp;</td>
        <td><input type="checkbox" value="1" name="top"/>
            置顶 </td>
      </tr>
      </c:if>
      
      <tr>
      	<td align="right" valign="top">&nbsp;</td>
       <td>
        <font color="red">视频文件最好用mp4格式，图片文件最好用jpg格式</font>
       </td>
      </tr>
      <tr>
        <td align="right" valign="top" >&nbsp;</td>
        <td><input name="button" type="submit" class="button" id="button" value="提交保存" />
          <input name="button" type="button" class="button" id="button" value="取消" /></td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
