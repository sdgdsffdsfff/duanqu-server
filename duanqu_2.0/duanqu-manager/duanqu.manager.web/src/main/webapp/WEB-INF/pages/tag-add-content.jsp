
<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<script type='text/javascript' src='/duanqu.manager/dwr/interface/DemoController.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/engine.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/util.js'></script>
<body>
<script type="text/javascript">
<c:if test="${message!=null}">
  window.location="content.do?method=list";
</c:if> 

function save(){
  /*  DemoController.saveTag($("#tid").val(),$("#cid").val(),$("#tagText").val(),$("#type").val(),function(data){
	if(data.message=="success"){
	  if(data.type=='1'){
      art.dialog("保存成功",function(){}).time(0.5);
      $("#tag"+data.cid).append("<a href='#'>"+data.tagText+"</a>");
      $("#des"+data.cid).append("#"+data.tagText+"#");
	  var ss="<a href='#' onClick=\"showdiv('修改标签',400,200,'tag.do?method=enterTag&type=0&cid="+data.cid+"&tid="+data.tid+"')\">修改</a>";
	  $("#tagd"+data.cid).html(ss);
	  }else{
	  art.dialog("修改成功",function(){}).time(1);

	  var description=$("#des"+data.cid).html();
      var descNew=description.replace("#"+data.oldText+"#","#"+data.tagText+"#");
	  $("#des"+data.cid).html(descNew);
	  var xx=$("#tag"+data.cid+" a");
	  var xxLength=xx.length;
	  var content="";
	  if(xxLength==1){
      $("#tag"+data.cid).html("<a href='#'>"+data.tagText+"</a>");
	  }else{
      for(var i=0;i<xxLength-1;i++){
       content+="<a href='#'>"+xx[i].text+"</a>";
	  }
	  content=content+"<a href='#'>"+data.tagText+"</a>";
	  $("#tag"+data.cid).html(content);
	  }
	 }
	}else{
    art.dialog("保存失败",function(){}).time(0.5);
	}
    art.dialog.list['N3690'].close();
}
);*/
DemoController.updateContentDescription($("#cid").val(),$("#descprition").val(),function(data){
if(data.message=="succes"){
 art.dialog("保存成功",function(){}).time(0.5);

 $("#tag"+data.cid).html(data.newText);
 $("#des"+data.cid).html(data.desc);
}else{
 art.dialog("保存失败",function(){}).time(0.5);
}
 art.dialog.list['N3690'].close();
});
}






</script>

<div class="updiv">
  <form id="form2" name="form2" method="post" action="tag.do?method=saveTag">
  	<input type="hidden" name="cid" id="cid" value="${mContentSubmit.cid}">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="87" height="44" align="right">描述信息：</td>
        <td width="313"><label for="textarea"></label>
          <textarea name="descprition" id="descprition" cols="35" rows="5" class="textarea-text" required >${contentModel.description}</textarea></td>
      </tr>
      <tr>
        <td align="right" valign="top" >&nbsp;</td>
        <td><input name="button" type="button" class="button" id="button" onclick="save()" value="添加" />
          <input name="button" type="button" class="button" id="button" value="取消" onclick="art.dialog.list['N3690'].close()" /></td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>