<%@ page language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>趣拍1.0后台管理中心</title>
		<link href="css/admin20130619.css" rel="stylesheet" type="text/css"/>
		<script
			src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script language="javascript" type="text/javascript"
			src="js/My97DatePicker/WdatePicker.js"></script>
		
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
function choose(value){
	if(value==1){
		document.getElementById("cs").style.display="";
	}else{
        document.getElementById("cs").style.display="none";
	}
}	
function save(){
form2.submit();
}
</script>
</head>
	<body onload="choose(1)">
		<div class="updiv">
			<form id="form2" name="form2" enctype="multipart/form-data"
				method="post" action="resource.do?method=saveUpload">
				<table width="400" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="32" align="right">
							描述：
						</td>
						<td>
							<label for="textarea2"></label>
							<input name="description" type="text" id="textarea2" value=""
								size="35" class="input-text" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="top">
							类别：
						</td>
						<td>
							<label for="textarea"></label>
							<select name="type" onClick="choose(this.value)">
								<option value="1">
									背景音乐
								</option>
								<option value="2">
									表情
								</option>
								<option value="3">
									贴纸
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="31" align="right">
							展示图标：
						</td>
						<td>
							<label for="fileField"></label>
							<input type="file" name="resourceIconFile" id="resourceIconFile"
								class="input-text" />
						</td>
					</tr>
					<tr id="cs">
						<td height="31" align="right">
							音乐试听文件：
						</td>
						<td>
							<label for="fileField"></label>
							<input type="file" name="resourceMusicFile"
								id="resourceMusicFile" class="input-text" />
						</td>
					</tr>
					<tr>
						<td height="31" align="right">
							资源包：
						</td>
						<td>
							<label for="fileField"></label>
							<input type="file" name="resourceFile" id="resourceFile"
								class="input-text" />
						</td>
					</tr>


					<tr>
						<td align="right" valign="top">
							&nbsp;
						</td>
						<td>
							<input name="button" type="button" class="button" id="button"
								value="保存" onClick="save()" />
							 <input name="button" type="button" class="button" id="button" value="取消" onclick="art.dialog.list['N3690'].close()" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
