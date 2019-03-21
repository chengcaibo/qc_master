<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>下载页面</title>
<jsp:include page="../common/head.jsp" />

<style>
body {
	padding: 10px !important;
}

.btn {
	height: 50px;
	line-height: 37px;
	width: 100%;
	font-size: 22px;
}
</style>

</head>

<body>

	<h3>已开始下载，如若未开始下载，请右上角并选择从浏览器中打开。</h3>

	<hr>

	<h4>如若还未开始下载，请点击下方按钮开始下载。</h4>

	<br>

	<a class="btn btn-info" href="https://img.qc1318.com/app/jiao-lian-ba.apk" download>点击下载</a>

	<script src="/js/share/share-download.js"></script>

</body>

</html>