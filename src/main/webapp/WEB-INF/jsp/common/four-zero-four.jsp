<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>404了</title>
<style type="text/css">
h1 {
	text-align: center;
	margin: 200px auto;
	font-size: 50px;
	font-weight: bold;
}
</style>
</head>
<body>
	<jsp:include page="/header" />
	<h1>404了，悠着点。</h1>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>