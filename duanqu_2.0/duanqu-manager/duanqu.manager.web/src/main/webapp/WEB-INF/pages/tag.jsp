<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>

<body>
<script src="js/jquery.imagePreview.js" ></script>
<div id="Main">
  <div id="Top"> <span>王海燕，你好！<a href="#">退出登录</a> </span>
    <h2>趣拍1.0后台管理中心</h2>
  </div>
  <div id="Content">
   <%@ include file="left.jsp"%>
    <div id="Right" class="clearfix">
      <div class="position">当前位置： 内容管理 > 标签管理</div>
      <div class="content">
        <div class="tab"> <a href="tag.html" class="on">用户标签</a> <a href="tag-edit.html">编辑创建标签</a> </div>
        <div class="searcharea">
          <form action="">
            <label for="tag">按标签名：</label>
            <input type="text" class="input-text" name="tag" id="tag">
            <label for="keyword">按关键字：</label>
            <input type="text" class="input-text" name="keyword" id="keyword">
            <input type="submit" value="搜索" class="button" name="search">
          </form>
        </div>
        <form  method="get">
          <div class="table-list">
            <div class="pd8"> <span class="fr"> </span> <a href="###" class="allselect">全选</a> <a href="###" class="cancel">全不选</a> &nbsp;&nbsp;&nbsp;&nbsp;
              <input name="" type="button" value="推送到热门推荐"  class="button">
            </div>
            <table width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th width="28">&nbsp;</th>
                  <th width="108">标签名</th>
                  <th width="244">图片</th>
                  <th width="385">标签内容数</th>
                  <th width="685" align="center">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td align="center"><input type="checkbox" value="163" id="newslist-1" name="newslist"></td>
                  <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">资讯快车</a></td>
                  <td  align="center"><span class="imgpreview"><img src="images/i1371451381_1.jpg" alt="" width="35" class="preview" bimg="http://img03.taobaocdn.com/bao/uploaded/i3/T1NmDBXgpXXXaWCr3Y_025558.jpg_210x1000.jpg"></span></td>
                  <td align="center" class="time"><a href="###" class="showtagcontent" data-role="tag-content.html">200</a></td>
                  <td align="center"><a href="#" class="taghide">隐藏</a></td>
                </tr>
                <tr>
                  <td align="center"><input type="checkbox" value="164" id="newslist-2" name="newslist"></td>
                  <td align="center" ><a href="#" charset="hz-4006606-1000631" target="_blank">资讯快车</a></td>
                  <td  align="center"><span class="imgpreview"><img src="images/i1371451381_1.jpg" alt="" width="35" class="preview" bimg="http://img03.taobaocdn.com/bao/uploaded/i3/T1NmDBXgpXXXaWCr3Y_025558.jpg_210x1000.jpg"></span></td>
                  <td align="center" class="time"><a href="#">200</a></td>
                  <td align="center"><a href="#">取消隐藏</a></td>
                </tr>
              </tbody>
            </table>
            <div class="pd8">
              <div id="pages"> 163 条记录 1/9 页 <a href="/admin.php?a=index&amp;m=items&amp;p=2">下一页</a> &nbsp;<span class="current">1</span>&nbsp;<a href="/admin.php?a=index&amp;m=items&amp;p=2">&nbsp;2&nbsp;</a>&nbsp;<a href="/admin.php?a=index&amp;m=items&amp;p=3">&nbsp;3&nbsp;</a>&nbsp;<a href="/admin.php?a=index&amp;m=items&amp;p=4">&nbsp;4&nbsp;</a>&nbsp;<a href="/admin.php?a=index&amp;m=items&amp;p=5">&nbsp;5&nbsp;</a> <a href="/admin.php?a=index&amp;m=items&amp;p=6">下5页</a> <a href="/admin.php?a=index&amp;m=items&amp;p=9">最后一页</a></div>
              <a href="#" class="allselect">全选</a> <a href="#" class="cancel">全不选</a> &nbsp;&nbsp;
              <input name="input" type="button" value="推送到热门推荐"  class="button">
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
