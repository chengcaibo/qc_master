<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<title>分享页面</title>

	<style type="text/css">


		body{
			background-size:cover;
		}

		.download {
			background-color: #333333;
			border-radius: 25px;
			border: none;
			width: 150px;
			height: 30px;
			color: white;
			margin-top: 10px;
			outline: none;
		}

		.background {
			background: url('https://img.qc1318.com/img/share-background-2.5-mini.png');
			width: 100%;
			height: 100%;
			background-size: cover;
			left:0;
			top:0;
		}

		h1 {
			text-align: center;
		}

		.box {
			position: absolute;
			bottom: 10px;
			width: 100%;
			display: flex;
			flex-direction: column;
			text-align: center;
		}

		.textboxset{
			background-color: #95abbd;
			height: 30px;
			width: 180px;
			border-radius: 5px;
			border:none;
			margin-bottom: 5px;
			outline: none;
		}
		.buttonset{
			background-color:#00FF00;
			height: 32px;
			width: 78px;
			border-radius: 15px;
			border: none;
			outline: none;
		}
		.validation{
			display: flex;
			flex-direction: row;
			text-align: center;
		}
		.container{
			display: flex;
			flex-direction: column;
			text-align: center;
		}
		.validationbutton{
			vertical-align: center;
			height: 20px;
			border-radius:15px ;
		}
	</style>

</head>

<body>
<div style="position:absolute; width:100%; height:100%; z-index:-1; left:0; top:0;">
	<img src="https://img.qc1318.com/img/share-background-2.5-mini.png" height="100%" width="100%">
</div>
<div style="position: absolute;bottom: 10%;left: 50%;margin-left: -129px;">
	<form action="download" method="post">
		<div style="text-align: center">
			<%--placeholder属性用来提示用户输入的内容--%>
			<input class="textboxset" type="text" placeholder="请输入手机号">
		</div>
		<div>
			<div class="validation">
				<div style="width: 100%;margin-right:-49.5%;text-align: center;vertical-align: center; ">
					<input class="textboxset" type="text" placeholder="请输入验证码"/>
				</div>
				<div class="validationbutton">
					<input type="button" class="buttonset" value="获取验证码">
				</div>
			</div>
		</div>
		<div style="text-align: center;">
			<input class="download" id="btn_download"  type="submit" value="立即领红包" />
		</div>
		<input type="hidden" id="userid" name="userid" value="${id}" />
	</form>
</div>
</body>

</html>