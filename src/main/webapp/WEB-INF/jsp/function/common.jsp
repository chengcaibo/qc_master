<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>${title}</title>
<link rel="stylesheet" type="text/css" href="/css/success-includes.css">
</head>
<body class="update_div">
	<jsp:include page="/header" />
	<div class="success-all">
		<i class="${iconClass}"></i> <span class="success-span">${title}</span><br><br>
		<span class="success-span">${content}</span><br>
		<br> <span class="success-span">您可以返回<a href="/">首页</a>或<a
			href="#" onclick="history.back()">上一级</a></span>
		<!-- <input type ="button" value ="返回上一页"  onclick="javascript:goback();"> -->
	</div>

	<jsp:include page="../common/footer.jsp" />
</body>
</html>
