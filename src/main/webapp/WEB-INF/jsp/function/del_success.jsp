<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>删除成功页面</title>
<link rel="stylesheet" type="text/css" href="/css/success-includes.css">
</head>
<body>
	<jsp:include page="/header" />
	<div class="success-includes">
		<h1 class="success-includes-headings">删除成功!</h1>
		<p class="success-includes-content">谢谢您的使用，欢迎您继续通过我们的平台发布信息！更多惊喜等你来哦！！！</p>
		<div class="success-includes-img">
			<p class="success-includes-img-p">√</p>
		</div>
		<a href="/" class="return">返回首页</a>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
