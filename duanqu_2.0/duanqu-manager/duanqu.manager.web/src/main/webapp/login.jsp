<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>短趣2.0管理系统</title>
<style>
*:focus {
	outline: none;
}
body {
	font: 14px/21px "Lucida Sans", "Lucida Grande", "Lucida Sans Unicode", sans-serif;
	background:#fff;
}
.login_form label {
	font-family:Georgia, Times, "Times New Roman", serif;
}
.login_form ul {
	width:750px;
	list-style-type:none;
	list-style-position:outside;
	margin:0px;
	padding:0px;
}
.login_form li {
	padding:12px;
	position:relative;
}
#main {
	width:700px;
	margin:0 auto;
	padding-top:20%
}
.login_form label {
	width:100px;
	margin-top: 3px;
	display:inline-block;
	float:left;
	text-align:right;
	padding:3px 15px 3px;
}
.login_form input {
	height:24px;
	width:220px;
	padding:5px 8px;
	line-height:24px
}
.login_form button {
	margin-left:130px;
}
.login_form input {
	border:1px solid #aaa;
	box-shadow: 0px 0px 3px #ccc, 0 10px 15px #eee inset;
	border-radius:2px;
	padding-right:30px;
	-moz-transition: padding .25s;
	-webkit-transition: padding .25s;
	-o-transition: padding .25s;
	transition: padding .25s;
}
.login_form input:focus {
	background: #fff;
	border:1px solid #555;
	box-shadow: 0 0 3px #aaa;
	padding-right:70px;
}
 .login_form input:required:valid {
 box-shadow: 0 0 5px #5cd053;
 border-color: #28921f;
}
 .login_form input:required {
 background: url(images/red_asterisk.png) no-repeat scroll 98% center #FFFFFF;
}
.login_form input:required:valid {
 background: url(images/valid.png) no-repeat scroll 98% center #FFFFFF;
 border-color: #28921F;
 box-shadow: 0 0 5px #5CD053;
}
 .login_form input:focus:invalid {
 background: #fff url(images/invalid.png) no-repeat 98% center;
 box-shadow: 0 0 5px #d45252;
 border-color: #b03535
}
 .login_form input:focus:invalid {
 box-shadow: 0 0 5px #d45252;
 border-color: #b03535
}
button.submit {
	background-color: #68b12f;
	background: -webkit-gradient(linear, left top, left bottom, from(#68b12f), to(#50911e));
	background: -webkit-linear-gradient(top, #68b12f, #50911e);
	background: -moz-linear-gradient(top, #68b12f, #50911e);
	background: -ms-linear-gradient(top, #68b12f, #50911e);
	background: -o-linear-gradient(top, #68b12f, #50911e);
	background: linear-gradient(top, #68b12f, #50911e);
	border: 1px solid #509111;
	border-bottom: 1px solid #5b992b;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	-ms-border-radius: 3px;
	-o-border-radius: 3px;
	box-shadow: inset 0 1px 0 0 #9fd574;
	-webkit-box-shadow: 0 1px 0 0 #9fd574 inset;
	-moz-box-shadow: 0 1px 0 0 #9fd574 inset;
	-ms-box-shadow: 0 1px 0 0 #9fd574 inset;
	-o-box-shadow: 0 1px 0 0 #9fd574 inset;
	color: white;
	font-size:14px;
	font-weight: bold;
	padding: 8px 20px;
	text-align: center;
	text-shadow: 0 -1px 0 #396715;
}
button.submit:hover {
	opacity:.85;
	cursor: pointer;
}
button.submit:active {
	border: 1px solid #20911e;
	box-shadow: 0 0 10px 5px #356b0b inset;
	-webkit-box-shadow:0 0 10px 5px #356b0b inset;
	-moz-box-shadow: 0 0 10px 5px #356b0b inset;
	-ms-box-shadow: 0 0 10px 5px #356b0b inset;
	-o-box-shadow: 0 0 10px 5px #356b0b inset;
}

</style>
</head>
<script type="text/javascript">
<c:if test="${message=='fail'}">
alert("你输入的用户名或者密码不正确！");
</c:if>

function login(){
if(document.login_form.username.value==""){
  alert("请输入用户名！");
  return false;
}

if(document.login_form.password.value==""){
  alert("请输入用户密码！");
  return false;
}
login_form.submit();
}


</script>


<body>
<div id="main"> 　
  <form class="login_form" action="login.do?method=login" method="post" name="login_form">
    <ul>
      <li>
        <label for="name">用户名:</label>
        <input type="text"  placeholder="用户名" name="username" required    />
      </li>
      <li>
        <label for="email">密码:</label>
        <input type="password" name="password" placeholder="密码" required />
      </li>
      <li>
        <button class="submit" type="button" onclick="login()">登陆管理</button>
      </li>
    </ul>
  </form>
</div>
</body>
</html>