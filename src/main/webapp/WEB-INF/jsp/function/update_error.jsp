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
		<h1 class="success-includes-headings">保存失败!</h1>
		<p class="success-includes-content">请您重新填写,保存。带来不便，敬请谅解！</p>
		<!-- <div class="success-includes-img">
			<p class="success-includes-img-p">X</p>
		</div> -->
		<a href="/" class="return">返回首页</a>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
