
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
	if(document.form2.tagText.value=="" || document.form2.tagText.value==null){
		alert("请输入标签");
		return false;
	}
	form2.submit();
}

</script>

<div class="updiv">
  <form id="form2" name="form2" method="post" action="tag.do?method=saveOtherTag">
  	<input type="hidden" name="tagType" value="${tagType}">
    <table width="400" border="0" cellpadding="0" cellspacing="0">
	<c:choose>
      <c:when test="${tagType==3}">
      <tr>
        <td width="87" height="44" align="right">更新说明：</td>
        <td width="313"><label for="textarea"></label>
          <textarea name="tagText" id="textarea" cols="35" rows="5" class="textarea-text" >${otherTag.tagText}</textarea></td>
      </tr>
	  </c:when>
	  <c:otherwise>
	  <tr>
        <td width="87" height="44" align="right">标签：</td>
        <td width="313"><label for="textarea"></label>
          <textarea name="tagText" id="textarea" cols="35" rows="5" class="textarea-text" required placeholder="请务必按照此种格式输入标签：搞笑|军事|武侠">${otherTag.tagText}</textarea></td>
      </tr>
	  </c:otherwise>
	</c:choose>  
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