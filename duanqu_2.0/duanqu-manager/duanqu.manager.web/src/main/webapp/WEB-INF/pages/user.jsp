<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.duanqu.common.DuanquConfig" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>趣拍1.0后台管理中心</title>
<link href="css/admin20130619.css" rel="stylesheet" type="text/css"/>
<script type='text/javascript' src='/duanqu.manager/dwr/interface/DemoController.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/engine.js'></script>
<script type='text/javascript' src='/duanqu.manager/dwr/util.js'></script>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script src="js/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<script src="js/jquery.imagePreview.js" ></script>

<script type="text/javascript">
	
	<c:if test="${message!=null}">
	 alert("${message}");
	</c:if>
	
	
	
	function tsfriend(type){
	if(checkBox()==false){
			alert("请至少选中一个用户!");
			return false;
	}
	var str=document.getElementsByName("uidList");
			var uid="";
			for(var i=0;i<str.length;i++){
				if(str[i].checked==true){
					uid+=str[i].value+",";
				}
			}

    DemoController.checkUserIsRecommend(uid,type,function(data){
	if(data==true){
    var actionName="user.do?method=enterRecommended&type="+type+"&uid="+uid
	if(type==1){
		showdiv('编辑推荐好友',300,200,actionName);
	}else{
		showdiv('公共账号推荐',300,200,actionName);
	}
	}else{
    art.dialog("不能重复推荐",function(){}).time(0.5);
	}
	});
	}
	function jcjy(uid){
		if(confirm("你确定解除该用户的禁言吗?")){
		  //form1.action="user.do?method=updateUserNormal&uid="+uid;
		 // form1.submit();
        DemoController.updateUserNormal(uid,function(data){
		 if(data=='解除成功'){
         var value="<a href='#' onClick=\"showdiv('禁言',300,200,'user.do?method=enterUserJy&uid="+uid+"')\">禁言</a>";
         $("#jy"+uid).html(value);
		 }
		art.dialog(data,function(){}).time(0.5);
		});

		}
	}

	function qxrz(uid){

   if(confirm("你确定撤销该认证用户吗?")){

    DemoController.saveAuthentication(uid,0,'',function(data)
	{  
	  if(data.isTrue==true){
        var value="<a href='#' onClick=\"showdiv('认证',300,200,'user.do?method=enterAuthentication&flag=1&uid="+uid+"')\">认证</a>";
        $("#rz"+uid).html(value);
	  }
      art.dialog(data.message,function(){}).time(0.5);
	}
	);
	}
	}
	
	function checkBox(){
		var boxsObj=document.getElementsByName("uidList");
		var flag=false;
		for(var i=0;i<boxsObj.length;i++){
			if(boxsObj[i].checked==true){
				flag=true;
				break;
			}
		}
		return flag;
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
		form1.action="user.do?method=list";
		form1.submit();	
	}
	
	function insertFalseFans(uid){
        DemoController.insertFalseFans(uid,function(data){
			art.dialog(data,function(){}).time(0.5);
			if(data=='增加成功'){
			 $('#fans'+uid).html(parseInt($('#fans'+uid).html())+1)
			}
		});
	}
	
	function tstalent(){
		if(checkBox()==false){
			alert("请至少选中一个用户!");
			return false;
		}
		form1.action="user.do?method=tstalent";
		form1.submit();
	}
	
	function initIndex(){
	    form1.action="user.do?method=initIndex";
	    form1.submit();
	}
	
	function sendMessageQf(){
		if(checkBox()==false){
			alert("请至少选中一个用户!");
			return false;
		}
		var str=document.getElementsByName("uidList");
		var uid="";
		for(var i=0;i<str.length;i++){
			if(str[i].checked==true){
				uid+=str[i].value+",";
			}
		}
		var actionName="user.do?method=enterMessage&type=2&uid="+uid
		showdiv('群发私信',300,200,actionName);
	}
    function mask(uid,status){
     DemoController.updateUserStatus(uid,status,function(data){
	 if(data.flag==true){
       if(status==2){
        $("#limit"+uid).html("<a href='#' onclick=\"mask("+uid+",1)\">取消限制</a>");
	   }else{

        $("#limit"+uid).html("<a href='#' onclick=\"mask("+uid+",2)\">限制排行</a>");
	   }

	 }
	 art.dialog(data.message,function(){}).time(0.5);
	 });
    



	}

	
	
	
</script>
<div id="Main">
  <div id="Top"> <span>${userSession.fullname}，你好！<a href="#">退出登录</a> </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
    <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 用户管理 > 用户列表</div>
      <div class="content">
      <div class="tab"> <a href="user.do?method=list"  class="on">用户列表</a> <a href="user.do?method=tsList" >推荐用户列表</a> <a href="user.do?method=tsHotList" >推荐热门列表</a><a href="user.do?method=pushMessageHistory" >推送历史记录</a></div>
        <div class="searcharea">
         <form  method="post" name="form1">
            <input type="hidden" name="pxtj" id="pxtj" value="${vo.pxtj}"/>
			<input type="hidden" name="pxlx" id="pxlx" value="${vo.pxlx}"/>
            <label for="tag">按用户名：</label>
            <input type="text" class="input-text" name="nickname"  value="${vo.nickname}" id="tag"/>
            <label for="select">按用户类型：</label>
            <select name="usertype" id="select">
              <option value="">选择用户类型</option>
              <option value="">普通用户</option>
              <option value="">达人用户</option>
              <option value="">马甲用户</option>
            </select>
            <label for="select">按绑定帐号：</label>
            <select name="userbind" id="select">
              <option value="">请选择绑定方式</option>
              <option value="1">新浪微博</option>
              <option value="2">QQ</option>
              <option value="3">手机号</option>
            </select>
            <label for="zt">按用户状态：</label>
            <input type="radio" name="zt" value="0"></input>禁言
            <input type="radio" name="zt" value="1"></input>正常
            <input type="button" value="搜索" class="button"  name="search" onclick="query()"/>
          
        </div>
        
          <div class="table-list">
            <div class="pd8"><a href="###" class="allselect">全选</a> <a href="###" class="cancel">全不选</a>
              <input name="" type="button" value="推荐到好友推荐" onclick="tsfriend(1)"  class="button"/>
              <input name="" type="button"  value="推荐到热门达人榜" class="button" onClick="tstalent()"/>
			  <input name="" type="button" value="公共账户推荐" onclick="tsfriend(2)"  class="button"/>
              <input name="input" type="button"  value="群发私信" class="button" onClick="sendMessageQf()"/>
			  <input name="input" type="button"  value="群发推送" class="button" onClick="showdiv('群发推送',300,200,'user.do?method=enterMessagePush')"/>
			  <input name="input" type="button"  value="索引初始化" class="button" onClick="initIndex()"/>
			  
			  
            </div>
            <table width="100%" cellspacing="0" style="table-layout:word-wrap:break-word;word-break:break-all">
              <thead>
                <tr>
                  <th width="48">&nbsp;</th>
                  <th width="100"><div class="w80">头像</div></th>
                  <th width="100"><div class="w80">用户ID</div></th>
                  <th width="100"><div class="w80">昵称</div></th>
                  <th width="154"><div class="w120">注册时间</div></th>
                <!-- 
                  <th width="90"><div class="w80">绑定账号</div></th>
                  <th width="90" align="center"><div class="w80">用户类型</div></th>
                  <th width="146" align="center"><div class="w120">上次登陆</div></th>
                  
                  -->
                  <th width="112"><div class="w60">关注数<a href="#" onClick="search_order_by('follow_num','ASC')">▲</a><a href="#" onClick="search_order_by('follow_num','DESC')">▼</a></div></th>
                  <th width="80"><div class="w60">粉丝数<a href="#" onClick="search_order_by('fans_num','ASC')">▲</a><a href="#" onClick="search_order_by('fans_num','DESC')">▼</a></div></th>
                  <th width="80" align="center"><div class="w60">好友数<a href="#" onClick="search_order_by('friend_num','ASC')">▲</a><a href="#" onClick="search_order_by('friend_num','DESC')">▼</a></div></th>
                  <th width="80"><div class="w60">内容数<a href="#" onClick="search_order_by('nrs','ASC')">▲</a><a href="#" onClick="search_order_by('nrs','DESC')">▼</a></div></th>
                  <th width="80"><div class="w60">分享数<a href="#" onClick="search_order_by('fxs','ASC')">▲</a><a href="#" onClick="search_order_by('fxs','DESC')">▼</a></div></th>
                  
                  <th width="296" align="center"><div class="w120">操作</div></th>
                </tr>
              </thead>
              <tbody>
              	
              <c:forEach items="${vo.objList}" var="item">
              	
                <tr>
                  <td align="center"><input type="checkbox" value='<c:out value="${item.uid}"/>' id="newslist-<c:out value="${item.uid}"/>" name="uidList"></td>
                  <td ><span class="imgpreview"><img src="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatarUrl}" width="35" class="preview" bimg="<%= DuanquConfig.getAliyunAvatarDomain()%>${item.avatarUrl}"></span></td>
                  <td align="center"><span class="time">${item.uid}</span></td>
                  <td align="center"><div id="au${item.uid}">
                  
                  <c:if test="${item.isAuthentication==1}">
				  <font color="red">V</font>
				  </c:if>
                  <c:out value="${item.nickName}"/>
				  </a>
				  </div>
				  </td>     
                  <td align="center"><span class="time">${item.createTime}</span></td>
                  <!--  
                  <td align="center">QQ</td>
                  <td align="center">普通用户</td>
                  <td align="center"><span class="time">${item.lastLoginTime}</span></td>         
                  -->
                  <td align="center"><c:out value="${item.followNum}"/></td>
                  <td align="center"><c:out value="${item.fansNum}"/>| <div id="fans${item.uid}"><c:out value="${item.fansFalseNum}"/></div><a href="###" onClick="showdiv('增加粉丝数',400,200,'user.do?method=enterMj&type=2&num=${item.uid}')" style="cursor:pointer">修改</a></td>
                  <td align="center"><c:out value="${item.friendNum}"/></td>
                  <td align="center"><a href="content.do?method=list&cxrk=3&uid=${item.uid}&nickName=${item.nickName}"><c:out value="${item.nrs}"/></a></td>
                  <td align="center"><c:out value="${item.fxs}"/></td>
                  <td align="center"><a href="###" onClick="showdiv('发私信',300,200,'user.do?method=enterMessage&fslx=0&type=1&uid=${item.uid}')">私信</a>
				  
				  <div id="rz${item.uid}" >
				  <c:choose>
				  <c:when test="${item.isAuthentication==1}">
				  <a href="###" onClick="qxrz(${item.uid})">取消认证</a>
				  </c:when>
				  <c:otherwise>				  
				  <a href="###" onClick="showdiv('认证',300,200,'user.do?method=enterAuthentication&flag=1&uid=${item.uid}')">认证</a>
				  </c:otherwise>
				  </c:choose>
				  </div>

				  <c:if test="${item.isAuthentication==1}">
                  <a href="###" onClick="showdiv('修改认证理由',300,200,'user.do?method=updateAuthentication&uid=${item.uid}')">修改认证理由</a>
				  </c:if>

				 
				  <div id="jy${item.uid}" ><c:choose><c:when test="${item.status==1}"><a href="###" onClick=showdiv('禁言',300,200,'user.do?method=enterUserJy&uid=${item.uid}')>禁言</a></c:when><c:otherwise><a href="###" onclick="jcjy('${item.uid}')">解除禁言</a></c:otherwise></c:choose><div>
				  
				  <div id="limit${item.uid}" >
				  <c:choose><c:when test="${item.status==2}"><a href="###" onClick="mask('${item.uid}',1)">取消限制</a></c:when><c:otherwise><a href="###" onclick="mask('${item.uid}',2)">限制排行</a></c:otherwise></c:choose>
				  <div>

                  <a href="###" onClick="showdiv('设置排序位置',400,200,'user.do?method=setList_user&uid=${item.uid}')" style="cursor:pointer">设置排序位置</a>

				  </td>
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
