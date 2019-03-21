<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ZH">
<head>
<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
<title>404 - 奇虫新管理后台</title>

<style type="text/css">
.cert-box {
	width: 1000px;
	margin: 0 auto;
	padding: 50px 0;
	padding-left: 20px;
}

h1 {
	font-size: 64px;
	text-align: center;
}
</style>

</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
		<!-- 导入用户左侧导航栏 -->

		<%-- <jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-users.jsp" /> --%>

		<!-- 内容主体区域 -->
		<div class="layui-body">
			<div class="cert-box">
				<h1>404 Not Found</h1>
			</div>
		</div>

		<!-- 导入公共底部 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/footer.jsp" />

	</div>
	<script>
		const id = ${id};
		const httpStatus = 404;
	</script>
	<script src="/admin/js/common/common.js"></script>
</body>
</html>