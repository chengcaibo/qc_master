<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="common/head.jsp" />

<title>${errorTitle}</title>
</head>
<body>
	<jsp:include page="/header" />
	<h1 style="margin-top:50px;text-align: center;height:calc(100vh - 374px);padding-top:15%;">${errorBody}<br>
		<c:if test="noReturnHome == null || noReturnHome != true">
			<a href="/">点击返回到主页</a>
		</c:if>
	</h1>
	<jsp:include page="common/footer.jsp" />
</body>
</html>
