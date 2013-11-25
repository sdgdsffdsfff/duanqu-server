<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css">

<script type='text/javascript' src='/duanqu.manager/dwr/interface/DemoController.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/engine.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/util.js'></script>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">	
function query(index){
	if(index!=undefined){
		var patrn=/^[1-9][0-9]*$/;
		if(!patrn.exec(index)){
			form1.page.value=1;
		}else{
			form1.page.value=index;
		}
	}
	form1.action="tag.do?method=editerTagList";
	form1.submit();	
}

function checkBox(){
	var boxsObj=document.getElementsByName("tidList");
	var flag=false;
	for(var i=0;i<boxsObj.length;i++){
		if(boxsObj[i].checked==true){
			flag=true;
			break;
		}
	}
	return flag;
}

function tsHot(){
	 if(checkBox()==false){
			alert("请至少选中一个标签!");
			return false;
		}
		var str=document.getElementsByName("tidList");
		var tid="";
		for(var i=0;i<str.length;i++){
			if(str[i].checked==true){
				tid+=str[i].value+",";
			}
		}
		DemoController.tsHost(tid,function(data){
			art.dialog(data,function(){}).time(0.5);
		});	
 
}



</script>
</head>

<body>
<script src="js/jquery.imagePreview.js" ></script>
<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a> </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
     <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 标签管理</div>
      <div class="content">
        <div class="tab"> <a href="tag.do?method=editerTagList"  class="on">标签列表</a> <a href="tag.do?method=hotList">推荐列表</a> <a href="tag.do?method=hotImageList">推荐图片标签列表</a></div>
        <div class="searcharea">
         <form action="" method="post" name="form1">
		 
            <label for="tag">按标签名：</label>
            <input type="text" class="input-text" name="tagText" id="tag" value="${vo.tagText}">
            <input type="button" value="搜索" class="button" name="search" onclick="query()">
        </div>
          <div class="table-list">
            <div class="pd8">
              <a href="###" class="allselect">全选</a> <a href="###" class="cancel">全不选</a> &nbsp;&nbsp;&nbsp;&nbsp;推送到：
              <input name="" type="button" value="热门推荐"  class="button" onClick="tsHot()">
              <input name="input" type="button"  value="添加发布标签" class="button" onClick="showdiv('添加发布标签',300,200,'tag.do?method=enterOtherTag&tagType=1')">
              <input name="input2" type="button"  value="添加搜索标签" class="button"onClick="showdiv('添加搜索标签',300,200,'tag.do?method=enterOtherTag&tagType=2')" >
			  <input name="input2" type="button"  value="添加更新说明" class="button"onClick="showdiv('添加更新说明',300,200,'tag.do?method=enterOtherTag&tagType=3')" >
            </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th width="108">标签名</th>               
                  <th width="385">标签内容数</th>
                  <th width="385">操作</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.tid}"/>" id="newslist-<c:out value="${item.tid}"/>" name="tidList"></td>
                  <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">${item.tag_text}</a></td>
                  <td align="center" class="time"><a href="content.do?method=list&cxrk=2&tid=${item.tid}" >${item.zs}</a></td>
                  <td align="center" class="time"><a href="###" onClick="showdiv('添加推存标签',300,200,'tag.do?method=enterHotTag&tid=${item.tid}')" >推荐图片标签</a></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="pd8">
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
