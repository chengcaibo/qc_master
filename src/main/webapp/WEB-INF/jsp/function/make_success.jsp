<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>置顶成功页面</title>
<link rel="stylesheet" type="text/css" href="/css/success-includes.css">
</head>
<body>
	<jsp:include page="/header" />
	<div class="success-includes">
		<h1 class="success-includes-headings">秒杀成功!</h1>
		<p class="success-includes-content">您的努力没有白费，成功的抢到了奇虫的置顶，您更加容易接更多的活了！</p>
		<div class="success-includes-img">
			<p class="success-includes-img-p">√</p>
		</div>
		<a href="/" class="return">返回首页</a>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
