
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
	var num=$("#num").val();
	var mjCount=$("#mjCount").val();
	var addNum=$("#addNum").val();
	if(parseInt(addNum)>parseInt(mjCount)){
    art.dialog("你增加的次数大于可用马甲次数",function(){}).time(0.5);
	return false;
	}
	/*DemoController.updateContentSinaQuan(cid,type,id,function(data){
	 if(data=='修改成功'){
	 if(type==1){
      $("#sina"+cid).html(id);
	 }else if(type==3){
	  $("#show"+cid).html(id); 
	 }
	 else{
      $("#quan"+cid).html(id);
	 }
	 }
     art.dialog(data,function(){}).time(0.5);
	 art.dialog.list['N3690'].close();
	});
	*/
	DemoController.updateLikeJfss(num,type,addNum,function(data){
	if(data.flag==true){
    if(type==1){
     $('#'+num).html(parseInt($('#'+num).html())+parseInt(addNum));
	}else{
    $('#fans'+num).html(parseInt($('#fans'+num).html())+parseInt(addNum));

	}
	}

	art.dialog(data.message,function(){}).time(0.5);
	art.dialog.list['N3690'].close();
	});
}

</script>

<div class="updiv">
  <form id="form2" name="form2" method="post" action="">
  	<input type="hidden" name="type" value="${type}" id="type">
	<input type="hidden" name="num" value="${num}" id="num">
	<input type="hidden" name="mjCount" value="${mjCount}" id="mjCount">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
       <tr>
        <td width="100"  align="right">
                            可用马甲数
		</td>
        <td width="300">
        <font color="red">${mjCount}</font>
        </td>
      </tr>
	   <tr>
        <td width="100" height="44" align="right">
                            增加次数
		</td>
        <td width="300"><label for="input-text"></label>
        <input type="text" name="addNum" value="" id="addNum"></input>
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