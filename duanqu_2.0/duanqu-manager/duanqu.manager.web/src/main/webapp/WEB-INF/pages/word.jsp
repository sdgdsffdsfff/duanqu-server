<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>

<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/jquery.imagePreview.js" ></script>

<script type="text/javascript">	

<c:if test="${message!=null}">
	alert("${message}");
</c:if>


function del(id){
	if(confirm("确认删除该敏感吗？")){
		form1.action="filter.do?method=deleteWord&id="+id;
		form1.submit();	
	}
	
}

function query(index){
	if(index!=undefined){
		var patrn=/^[1-9][0-9]*$/;
		if(!patrn.exec(index)){
			form1.page.value=1;
		}else{
			form1.page.value=index;
		}
	}
	form1.action="filter.do?method=list";
	form1.submit();	
}
</script>


</head>
<body>



<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a>
  	 <p><b class="s1">皮肤1</b><b class="s2">皮肤1</b><b class="s3">皮肤1</b><b class="s4">皮肤1</b></p>
  	</span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
    <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 敏感词</div>
      <div class="content">
        <form action="" method="post" name="form1">
		

          <div class="table-list">
            <div class="pd8">
              <input type="button" value="添加敏感字"  class="button" onClick="showdiv('添加敏感字',300,200,'filter.do?method=enter')">
            </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th ><div class="w80">敏感字</div></th>
                  <th align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.id}"/>" id="newslist-<c:out value="${item.id}"/>" name="idList"></td>
                  <td align="center" >${item.filterText}</td>
                  <td align="center"><a href="###"  class="del" onclick="del('${item.id}')" >删除</a></td>
                </tr>
              </c:forEach>  
               
              </tbody>
            </table>
            <div class="pd8  clearfix">
            <%@ include file="page.jsp"%> 
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
