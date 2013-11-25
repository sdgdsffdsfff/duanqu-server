<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/jquery.imagePreview.js" ></script>
</head>
<script type="text/javascript">
    <c:if test="${message!=null}">
     alert("${message}");
    </c:if>
    
	function reply(cid,replyUid,prientId,rootId){
		var page=document.form1.page.value;
		var str="user.do?method=enterCommentDqj&uid=1&cid="+cid+"&replyUid="+replyUid+"&parentId="+prientId+"&rootId="+rootId;
		showdiv('回复评论',300,200,str);
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
		form1.action="user.do?method=dqjCommentList";
		form1.submit();	
	}
   function deleteComment(id,rootId){
	   form1.action="user.do?method=deleteCommentDqj&id="+id+"&rootId="+rootId;
	   form1.submit();	
  }
   function updateComment()
   {
	    DemoController.updateCommentDqj(${vo.cid},function(data){
	    	 art.dialog(data,function(){}).time(0.5);
		 parent.art.dialog.list['tagcontentlist'].close();
		});
   }




	
</script>


<body style="background:#fff">
<input name="" type="button"  value="已查看该内容下所有评论" class="button" onClick="updateComment()">
<div id="Main">
<div class="table-list">
 <form  method="post" name="form1">

 <input type="hidden" name="cid" value="${vo.cid}" ></input>
  <table width="588" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
    <thead>
    	          
    
                <tr>
                  <th width="78" align="left">视频图</th>
                  <th width="98">头像</th>
                  <th width="78">用户昵称</th>
                  <th width="178" align="left"><DIV id="u1296">
                    <DIV id="u1296_rtf">
                      <p>评论内容</p>
                    </DIV>
                  </DIV></th>
                  <th width="78">操作</th>
                </tr>
              </thead>
              <tbody>
              	
              	
              	<c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td  align="center"><span class="imgpreview" ><img src="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" width="35" class="preview videopic" bimg="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" data-role="<%=DuanquConfig.getAliyunHDVideoDomain()%>${item.videoUrlHd}"></span></td>
                  <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatarUrl}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatarUrl}"></span></td>
                  <td align="center">${item.nickName}</td>
                  
                  <c:choose>
                  <c:when test="${empty item.managerCommentSubmit}">
                  <td>${item.commentText}</td>
                  </c:when>
                  <c:otherwise>
                  	<td><font size="1"><font color="#006400">${item.managerCommentSubmit.nickName}:</font>${item.managerCommentSubmit.commentText}</br></font>
                  	
                  	<font color="#006400">${item.nickName}</font>回复
					<font color="#006400">${item.replyUser.nickName}</font>:<font color="red">${item.commentText}</font></td>
                  </c:otherwise>	
                  </c:choose> 
                  <td align="center">
				  <a href="###" onClick="reply('${item.cid}','${item.uid}','${item.id}','${item.rootId}')">回复</a>
				  <a href="###" onClick="deleteComment('${item.id}','${item.rootId}')">删除</a>
				  </td>
                </tr>
              </c:forEach>
               
              </tbody>
            </table>
            <div class="pd8 clearfix">
           <%@ include file="page.jsp"%>   
		 </form>
  </div>
</div>
</div>
</body>
</html>
