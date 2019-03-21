<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ZH">
<head>
<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
<title>模板页面 - 奇虫新管理后台</title>

</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
		<!-- 导入用户左侧导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-users.jsp" />

		<!-- 内容主体区域 -->
		<div class="layui-body">
			<!-- 区块 -->
			<div class="layui-card">
				<div class="layui-card-header">区块标题</div>
				<div class="layui-card-body">区块内容</div>
			</div>
		</div>

		<!-- 导入公共底部 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/footer.jsp" />

	</div>
	<script src="/admin/js/common/common.js"></script>
</body>
</html>