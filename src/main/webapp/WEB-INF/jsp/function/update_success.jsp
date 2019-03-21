<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>保存成功页面</title>
<link rel="stylesheet" type="text/css" href="/css/success-includes.css">
</head>
<body class="update_div">
<jsp:include page="/header" />
	<div class="success-all">
		<i class="icon-success"></i>
		<span class="success-span">恭喜，保存成功</span>
		<span class="success-span">您可以返回<a href="/">首页</a>或<a href="#" onclick="history.back()">上一级</a></span>
		<!-- <input type ="button" value ="返回上一页"  onclick="javascript:goback();"> -->
	</div>
	
	<!-- <div class="success-includes">
		<h1 class="success-includes-headings">更新成功!</h1>
		<p class="success-includes-content">恭喜您修改成功，您可以在奇虫找您想做的事！</p>
		<div class="success-includes-img">
			<p class="success-includes-img-p">√</p>
		</div>
		<a href="/" class="return">返回首页</a>
	</div> -->
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
