<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
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



</head>
<body>
<script type="text/javascript">	
	
	<c:if test="${message=='success'}">
	alert("推送成功");
	</c:if>
	<c:if test="${message=='fail'}">
	alert("推送失败");
	</c:if>
	<c:if test="${ts=='2Success'}">
	alert("删除成功");
	</c:if>
	<c:if test="${ts=='2Fail'}">
	alert("删除失败");
	</c:if>
		<c:if test="${ts=='3Success'}">
	alert("屏蔽成功");
	</c:if>
	<c:if test="${ts=='3Fail'}">
	alert("屏蔽失败");
	</c:if>
	
	
function checkBox(){
		var boxsObj=document.getElementsByName("cidList");
		var flag=false;
		for(var i=0;i<boxsObj.length;i++){
			if(boxsObj[i].checked==true){
				flag=true;
				break;
			}
		}
		return flag;
	}
	function tsrecommend(){
		 if(checkBox()==false){
				art.dialog({content: '请至少选中一个用户'}).time(1);
				return false;
			}
			var str=document.getElementsByName("cidList");
			var cid="";
			for(var i=0;i<str.length;i++){
				if(str[i].checked==true){
					cid+=str[i].value+",";
				}
			}


            DemoController.checkIsPrivate(cid,0,function(data){
			if(data.flag==true){
			var actionName="content.do?method=enterTs&type=0&cid="+cid;
			showdiv('推送首页',300,200,actionName);
			}else{
			 art.dialog({content: data.message});
			 return false;
			}
			});

	}
	
   function tsfind(){
	   if(checkBox()==false){
			art.dialog({content: '请至少选中一条内容'}).time(1);
			return false;
		}
		var str=document.getElementsByName("cidList");
		var cid="";
		for(var i=0;i<str.length;i++){
			if(str[i].checked==true){
				cid+=str[i].value+",";
			}
		}
		DemoController.checkIsPrivate(cid,1,function(data){
			if(data.flag==true){
              var actionName="content.do?method=enterTs&type=1&cid="+cid;
		      showdiv('推送发现',300,200,actionName);
		    }else{
			 art.dialog({content: data.message});
			 return false;
			}
		});
	}
	
	function forward(){
	
	 if(checkBox()==false){
			art.dialog({content: '请至少选中一条内容'}).time(1);
			return false;
		}
		var str=document.getElementsByName("cidList");
		var cid="";
		for(var i=0;i<str.length;i++){
			if(str[i].checked==true){
				cid+=str[i].value+",";
			}
		}
		
		var actionName="content.do?method=enterForward&cid="+cid;
		showdiv('公共账号转发',300,200,actionName);
	}
	
	
	
	
	
	
    function getByUid(nickName,uid){
    form1.uid.value=uid;
	form1.nickName.value=nickName;
	query();
	}


	function deleteContent(cid,sStatus){
		var str;
		if(sStatus=="3"){
			str="你确定屏蔽该条内容吗?";
		}else if(sStatus=="2"){
			str="你确定删除该条内容吗?";
		}else if(sStatus=="0"){
			str="你确定取消屏蔽该条内容吗?";
		}
		if(confirm(str)){
			form1.action="content.do?method=deleteContent&dqj=0&sStatus="+sStatus+"&cid="+cid;
			form1.submit();
		}
	}
	function mask(cid,sStatus,type){
        DemoController.mask(cid,sStatus,function(data){
		if(data.flag==true){
        if(sStatus==3){
        $("#mask"+cid).html("<a href='#' onclick=\"mask("+cid+",0,1)\">取消屏蔽</a>");
		}else if(sStatus==4){
        $("#limit"+cid).html("<a href='#' onclick=\"mask("+cid+",0,2)\">取消限制</a>");
		}else{
			if(type==1){
                $("#mask"+cid).html("<a href='#' onclick=\"mask("+cid+",3,1)\">屏蔽</a>");
			}else{
              $("#limit"+cid).html("<a href='#' onclick=\"mask("+cid+",4,2)\">限制排行</a>");
			}

		}
		}
		art.dialog(data.message,function(){}).time(0.5);
		});
        
        

	
	}









	function insertContent(cid){
        DemoController.inserContentLike(cid,function(data){
			art.dialog(data,function(){}).time(0.5);
			if(data=='喜欢成功'){
			 $('#'+cid).html(parseInt($('#'+cid).html())+1)
			}
		});
	}
	function empty(){
    form1.uid.value="";
	form1.nickName.value="";
	form1.key.value="";
	form1.zt.value="";
	form1.uploadTimeQ.value="";
	form1.uploadTimeZ.value="";
	form1.tid.value=0;
	form1.sid.value=0;
	form1.pxtj.value="";
	form1.pxlx.value="";
	}
	function search_order_by(pxtj,pxlx){
     form1.pxtj.value=pxtj;
	 form1.pxlx.value=pxlx;
	 query();
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
		if(form1.cxrk.value==3||form1.cxrk.value==4){
		form1.cxrk.value=1;
		}
		form1.action="content.do?method=list";
		form1.submit();	
	}
</script>
	
<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a>
    <p><b class="s0 cs" id="theme0">皮肤1</b><b class="s1  cs" id="theme1">皮肤1</b><b class="s2  cs"  id="theme2">皮肤1</b></p>
    </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
     <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 内容列表</div>
      <div class="content">
        <div class="tab"> <a href="content.do?method=list&cxrk=1"  class="on">所有内容</a> <a href="content.do?method=tsList" >推送内容</a> <a href="content.do?method=tsHotList" >热门推送</a></div>
        <div class="searcharea">
          <form action="" name="form1" method="post">
            <input type="hidden" name="uid" value="${vo.uid}">
            <input type="hidden" name="cxrk" value="${vo.cxrk}"></input>
            <input type="hidden" name="tid" value="${vo.tid}"></input>
			<input type="hidden" name="sid" value="${vo.sid}"></input>
			<input type="hidden" name="pxtj" id="pxtj" value="${vo.pxtj}">
			<input type="hidden" name="pxlx" id="pxlx" value="${vo.pxlx}">

            <label for="keyword">按关键字:</label>
            <input type="text" class="input-text" name="key" id="keyword" value="${vo.key}">
            <label for="author">按发布者:</label>
            <input type="text" class="input-text" name="nickName" value="${vo.nickName}" id="author">
            <label for="author">按内容:</label>
            <select name="zt">
            	<option value="">所有</option>
            	<option value="0" <c:if test="${vo.zt=='0'}"> selected </c:if> >正常</option>
            	<option value="3" <c:if test="${vo.zt=='3'}"> selected </c:if>>已屏蔽</option>
            	<option value="1" <c:if test="${vo.zt=='1'}"> selected </c:if>>作者删除</option>
            	<option value="2" <c:if test="${vo.zt=='2'}"> selected </c:if>>编辑删除</option>
            </select>
             <label for="author">按内容公开性:</label>
            <select name="isPrivate">
            	<option value="">所有</option>
            	<option value="0" <c:if test="${vo.isPrivate=='0'}"> selected </c:if> >公开</option>
            	<option value="1" <c:if test="${vo.isPrivate=='1'}"> selected </c:if>>仅自己</option>
            	<option value="2" <c:if test="${vo.isPrivate=='2'}"> selected </c:if>>分组</option>
            </select>
            <label for="timebegin">按时间：</label>
            <input class="Wdate" type="text"  name="uploadTimeQ" value="${vo.uploadTimeQ}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" >
            -
            <input class="Wdate" type="text" name="uploadTimeZ" value ="${vo.uploadTimeZ}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" >
            <input type="button" value="搜索" class="button" name="search" onclick="query()">
			<input type="button" value="清空" class="button" name="search" onclick="empty()">
        </div>
          <div class="table-list">
            <div class="pd8"> <span class="fr">
              <input name="" type="button" value="上传视频"  class="button" onClick="showdiv('上传视频',500,250,'content.do?method=enterUpload&dqj=0')">
              </span> <a href="###" class="allselect">全选</a> <a href="###" class="cancel">全不选</a>
              <input name="" type="button" value="首页编辑推荐" onclick="tsrecommend()" class="button">
              <input name="" type="button"  value="发现编辑推荐" onclick="tsfind()" class="button">
              <input name="" type="button"  value="公共账号转发" onclick="forward()" class="button"/>
            </div>
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="50">&nbsp;</th>
                  <th width="50"><div class="w80">内容ID</div></th>
                  <th width="98"><div class="w80">视频图片</div></th>
                  <th width="98"><div class="w80">发布者</div></th>
                  <th width="60"><div class="w80">内容公开性</div></th>
                  <th width="138"><div class="w120">发布时间</div></th>
                  <th width="178"><div class="w160">描述</div></th>
                  <th width="178"><div class="w160">标签</div></th>
                  <th width="98" align="center"><div class="w80">播放次数<a href="#" onClick="search_order_by('show_times','ASC')">▲</a><a href="#" onClick="search_order_by('show_times','DESC')">▼</a></div></th>
                  <th width="78"><div class="w80">喜欢数<a href="#" onClick="search_order_by('like_num','ASC')">▲</a><a href="#" onClick="search_order_by('like_num','DESC')">▼</a></div></th>
                  <th width="78"><div class="w80">评论数<a href="#" onClick="search_order_by('comment_num','ASC')">▲</a><a href="#" onClick="search_order_by('comment_num','DESC')">▼</a></div></th>
                  <th width="90"><div class="w50">新浪分享数<a href="#" onClick="search_order_by('sinashare_num','ASC')">▲</a><a href="#" onClick="search_order_by('sinashare_num','DESC')">▼</a></div></th>
                  <th width="90"><div class="w50">朋友圈分享数<a href="#" onClick="search_order_by('quanshare_num','ASC')">▲</a><a href="#" onClick="search_order_by('quanshare_num','DESC')">▼</a></div></th>
                  <th width="78" align="center"><div class="w50">内容状态</div></th>
                  <th width="366" align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${vo.objList}" var="item">
                <tr>
                  <td align="center"><input type="checkbox" value="<c:out value="${item.cid}"/>" id="newslist-<c:out value="${item.cid}"/>" name="cidList"></td>
                  <td align="center">${item.cid}</td>
                  <td  align="center"><span class="imgpreview" ><img src="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" width="35" class="preview videopic" bimg="<%=DuanquConfig.getAliyunThumbnailDomain()%>${item.thumbnailsUrl}" data-role="<%=DuanquConfig.getAliyunHDVideoDomain()%>${item.videoUrlHd}"></span></td>
                  <td ><a href="#" onClick="getByUid('${item.nickName}','${item.uid}')">${item.nickName}</a></td>
                  <td align="center">
                  <c:choose>
                  	<c:when test="${item.isPrivate==0}">公开</c:when>
                  	<c:when test="${item.isPrivate==1}">仅自己</c:when>
                  	<c:when test="${item.isPrivate==2}">分组</c:when>     	
                  </c:choose>
                  </td>
                  <td align="left" class="time">${item.uploadTimeStr}</td>
                  <td id="des${item.cid}">${item.description}</td>
                  <td align="center"><span  class="taglist" id="tag${item.cid}">
                  <c:forEach items="${item.list}" var="item2">
                  <a href="#">${item2.tagText}</a> 
                  </c:forEach>
                  </span>&nbsp;&nbsp;
                  
                  <span class="addtag taglist" id="tagd${item.cid}">
				  <c:if test="${item.isPrivate==0}">
                  <a href="#" onClick="showdiv('修改描述信息',400,200,'tag.do?method=enterTag&cid=${item.cid}')" style="cursor:pointer">修改</a>
                  </c:if>
				  </span>

                  </td>
                  <td align="center">${item.showTimes} |<div id="show${item.cid}">${item.falseShowTimes}</div>
				   <c:if test="${item.isPrivate==0}">
				  <a href="###" onClick="showdiv('修改假播放次数',400,200,'content.do?method=enterSinaQuan&type=3&cid=${item.cid}')" style="cursor:pointer">修改</a>
				  </c:if>
				  </td>
                  <td align="center">${item.likeNum} | <div id="${item.cid}">${item.falseLikeNum}</div>
                  <c:if test="${item.isPrivate==0}">
				  <a href="###" onClick="showdiv('增加喜欢数',400,200,'user.do?method=enterMj&type=1&num=${item.cid}')" style="cursor:pointer">修改</a>
				  </c:if>
				  </td>
                  <td align="center">${item.commentNum} | ${item.falseCommentNum}</td>
                  <td align="center">${item.sinashareNum} |<div id="sina${item.cid}"> ${item.sinashareFalseNum}</div> 
				  <c:if test="${item.isPrivate==0}">
				  <a href="###" onClick="showdiv('修改新浪假分享数',400,200,'content.do?method=enterSinaQuan&type=1&cid=${item.cid}')" style="cursor:pointer">修改</a>
				  </c:if>
				  </td>
                  <td align="center">${item.quanshareNum} |<div id="quan${item.cid}"> ${item.quanshareFalseNum}</div>
				  <c:if test="${item.isPrivate==0}">
				  <a href="###" onClick="showdiv('修改朋友圈假分享数',400,200,'content.do?method=enterSinaQuan&type=2&cid=${item.cid}')" style="cursor:pointer">修改</a>
				  </c:if>
				  </td>
                  <td align="center">
                  <c:choose>
                  	<c:when test="${item.cStatus==0}">正常</c:when>
                  	<c:when test="${item.cStatus==1}">作者删除</c:when>
                  	<c:when test="${item.cStatus==2}">编辑删除</c:when>
                  	<c:when test="${item.cStatus==3}">屏蔽</c:when>
                  </c:choose>
                  </td>
                  <td align="center"> <c:if test="${item.isPrivate==0}"><a href="###" onClick="showdiv('评论',40,200,'content.do?method=comment&cid=${item.cid}')">评论</a></c:if>
				  
				  <div id="mask${item.cid}">
				  <c:choose>
				  <c:when test="${item.cStatus==3}"><a href="#" onclick="mask(${item.cid},0,1)">取消屏蔽</a></c:when>
				  <c:otherwise><a href="#" onclick="mask(${item.cid},3,1)">屏蔽</a></c:otherwise>
				  </c:choose>
                  </div>
				  <div id="limit${item.cid}">
				  <c:choose>
				  <c:when test="${item.cStatus==4}"><a href="#" onclick="mask(${item.cid},0,2)">取消限制</a></c:when>
				  <c:otherwise><a href="#" onclick="mask(${item.cid},4,2)">限制排行</a></c:otherwise>
				  </c:choose>
				  </div>
                    <a href="###" onClick="showdiv('设置排序位置',400,200,'content.do?method=setList&cid=${item.cid}&type=1')" style="cursor:pointer">老版设置排序位置</a></br>
					 <a href="###" onClick="showdiv('设置排序位置',400,200,'content.do?method=setList&cid=${item.cid}&type=2')" style="cursor:pointer">新版设置排序位置</a></br>
				  
				  <c:if test="${item.cStatus!=2}"><a href="#" class="" onclick="deleteContent(${item.cid},2)">删除</a></c:if></td>
                </tr>
               </c:forEach>
              </tbody>
            </table>
           <div class="pd8">
              <%@ include file="page.jsp"%>
           </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
