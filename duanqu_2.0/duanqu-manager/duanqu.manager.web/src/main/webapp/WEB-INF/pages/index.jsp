<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<h2>Hello World!</h2>

<h2>${message}</h2>
<form action="login.do?method=login" id="form1" name="form1">
<input type="text" name="username"></input>
<input type="text" name="password"></input>
<input type="text" name="fullname"></input>

<input type="submit" value="登录"></input>
</form>

<h2>${userModel.username}</h2>
<h2>${userModel.password}</h2>
<h2>${userModel.fullname}</h2>
</body>
</html>
