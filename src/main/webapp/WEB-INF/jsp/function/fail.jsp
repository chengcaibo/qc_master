<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>置顶失败</title>
<link rel="stylesheet" type="text/css" href="/css/fail.css">
</head>
<body>
	<jsp:include page="/header" />
	<div class="success-includes">
		<h1 class="success-includes-headings">秒杀失败!</h1>
		<p class="success-includes-content">对不起，已经尽力帮你您抢了，但是还是失败了！</p>
		<div class="success-includes-img">
			<p class="success-includes-img-p">×</p>
		</div>
		<a href="#" class="return">返回首页</a>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
