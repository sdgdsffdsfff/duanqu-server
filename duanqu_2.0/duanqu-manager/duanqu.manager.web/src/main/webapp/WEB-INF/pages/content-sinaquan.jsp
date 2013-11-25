
<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script type="text/javascript">
<c:if test="${message!=null}">
  alert("${message}");
  window.location="tag.do?method=editerTagList";
</c:if> 

function save(){
	var type=$("#type").val();
	var cid=$("#cid").val();
	var id=$("#id").val();
	DemoController.updateContentSinaQuan(cid,type,id,function(data){
	 if(data=='修改成功'){
	 if(type==1){
      $("#sina"+cid).html(parseInt($("#sina"+cid).html())+parseInt(id));
	 }else if(type==3){
	  $("#show"+cid).html(parseInt($("#show"+cid).html())+parseInt(id));
	 }
	 else{
      $("#quan"+cid).html(parseInt($("#quan"+cid).html())+parseInt(id));
	 }
	 }
     art.dialog(data,function(){}).time(0.5);
	 art.dialog.list['N3690'].close();
	});
}

</script>

<div class="updiv">
  <form id="form2" name="form2" method="post" action="tag.do?method=saveOtherTag">
  	<input type="hidden" name="type" value="${type}" id="type">
  	<input type="hidden" name="cid" value="${cid}" id="cid"></input>
    <table width="400" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" height="44" align="right">
		<c:choose>
		<c:when test="${type==1}">
		增加新浪假分享数:
		</c:when>
		<c:when test="${type==3}">
		增加假播放数:
		</c:when>
		<c:otherwise>
        增加朋友圈假分享数:
		</c:otherwise>
		</c:choose></td>
        <td width="300"><label for="input-text"></label>
        <input type="text" name="num" value="" id="id"></input>
        </td>
      </tr>
      <tr>
        <td align="right" valign="top" >&nbsp;</td>
        <td><input name="button" type="button" class="button" id="button" onclick="save()" value="添加" />
          <input name="button" type="button" class="button" id="button" value="取消" /></td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>