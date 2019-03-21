<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ZH">
<head>
<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
<title>企业认证审核 - 奇虫新管理后台</title>
<link href="/admin/css/audits/index.css" rel="stylesheet">

</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
		<!-- 导入审核左侧导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-audits.jsp" />

		<!-- 内容主体区域 -->
		<div class="layui-body">
			<!-- 等待审核 -->
			<div class="layui-card">
				<div class="layui-card-header">
					等待中的审核 - 剩余<span id="cert-enterprise-wait-count">-</span>条
				</div>
				<div class="layui-card-body">
					<table class="layui-table">
						<colgroup>
							<col>
							<col>
							<col>
							<col>
							<col>
							<col style="width:115px;">
						</colgroup>
						<thead>
							<tr>
								<th>审核ID</th>
								<th>公司名称</th>
								<th>法人姓名</th>
								<th>营业执照</th>
								<th>申请时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="cert-enterprise-wait-tbody">
							<tr>
								<td colspan="6" class="common-nodata">查询中...</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align: right;" id="cert-enterprise-wait-page"></div>
				</div>
			</div>
			<!-- 通过的审核 -->
			<div class="layui-card">
				<div class="layui-card-header">
					通过的审核 - 共<span id="cert-enterprise-passed-count">-</span>条
				</div>
				<div class="layui-card-body">
					<table class="layui-table">
						<thead>
							<tr>
								<th>审核ID</th>
								<th>公司名称</th>
								<th>法人姓名</th>
								<th>营业执照</th>
								<th>申请时间</th>
								<th>通过时间</th>
							</tr>
						</thead>
						<tbody id="cert-enterprise-passed-tbody">
							<tr>
								<td colspan="6" class="common-nodata">查询中...</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align: right;" id="cert-enterprise-passed-page"></div>
				</div>
			</div>
			<!-- 驳回的审核 -->
			<div class="layui-card">
				<div class="layui-card-header">
					驳回的审核 - 共<span id="cert-enterprise-not-passed-count">-</span>条
				</div>
				<div class="layui-card-body">
					<table class="layui-table">
						<thead>
							<tr>
								<th>审核ID</th>
								<th>公司名称</th>
								<th>法人姓名</th>
								<th>营业执照</th>
								<th>驳回时间</th>
								<th>驳回原因</th>
							</tr>
						</thead>
						<tbody id="cert-enterprise-not-passed-tbody">
							<tr>
								<td colspan="6" class="common-nodata">查询中...</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align: right;" id="cert-enterprise-not-passed-page"></div>
				</div>
			</div>
		</div>

		<!-- 导入公共底部 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/footer.jsp" />

	</div>
	<script src="/admin/js/common/common.js"></script>
	<script src="/admin/js/audits/enterprise-index.js"></script>

</body>
</html>