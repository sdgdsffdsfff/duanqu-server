
<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script type="text/javascript">
function save(){
	var uid=$("#uid").val();
	var id=$("#id").val();
	if(parseInt(id)<1){
		alert("你输入的位置不能小于1");
		return false;
		
	}
	DemoController.setList_user(uid,id,function(data){
     art.dialog(data,function(){}).time(0.5);
	 art.dialog.list['N3690'].close();
	});
}

</script>

<div class="updiv">
  <form id="form2" name="form2" method="post" action="">
  	<input type="hidden" name="uid" value="${uid}" id="uid"></input>
    <table width="400" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" height="44" align="right">
                            排序位置
		</td>
        <td width="300"><label for="input-text"></label>
        <input type="text" name="num" value="${setUserModel.orderNum}" id="id"></input>
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