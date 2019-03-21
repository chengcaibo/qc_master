<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="ZH">

<head>
	<jsp:include page="./common/head.jsp" />

	<title>奇虫新管理后台</title>

	<link href="/admin/css/themes/default.css" rel="stylesheet">
	<link href="/admin/css/component.css" rel="stylesheet">
	<link href="/admin/css/home/index.css" rel="stylesheet">

	<script src="https://cdn.jsdelivr.net/npm/vue@2.5.17/dist/vue.js"></script>

	<!-- <script src="https://cdn.bootcss.com/vue/2.5.17-beta.0/vue.min.js"></script> -->
</head>

<body class="layui-layout-body">
	<div id="app" class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="./layout/nav-top.jsp" />
		<!-- 导入用户左侧导航栏 -->
		<jsp:include page="./layout/nav-left-home.jsp" />

		<div class="layui-body" :hideLoading="hideLoading">
			<div class="body-loading">
				<img src="/svg/equalizer-bars-loader.svg" alt="svg loading" width="120">
			</div>
			<!-- 个人用户注册量统计 -->
			<div class="layui-card">
				<div class="layui-card-header">个人用户注册量统计</div>
				<div class="layui-card-body">
					<div class="count-box">
						<template v-for="(item, index) in personalSignArray">
							<icon-number v-bind="item" :idx="index" type="personal" @click-detail="onClickDetail" />
						</template>
					</div>
				</div>
			</div>
			<!-- 企业用户注册量统计 -->
			<div class="layui-card">
				<div class="layui-card-header">企业用户注册量统计</div>
				<div class="layui-card-body">
					<div class="count-box">
						<template v-for="(item, index) in enterpriseSignArray">
							<icon-number v-bind="item" :idx="index" type="enterprise" @click-detail="onClickDetail" />
						</template>
					</div>
				</div>
			</div>
			<!-- 需求发布量统计 -->
			<div class="layui-col-xs12 layui-col-md6" style="padding-right:5px;">
				<div class="layui-card">
					<div class="layui-card-header">需求发布量统计</div>
					<div class="layui-card-body" style="font-size:0;">
						<div class="count-box">
							<template v-for="(item, index) in demandThreeDaysArray">
								<icon-number v-bind="item" :idx="index" type="demand" @click-detail="onClickDetail" />
							</template>
						</div>
					</div>
				</div>
			</div>
			<!-- 最新的十条需求 -->
			<div class="layui-col-xs12 layui-col-md6" style="padding-left:5px;">
				<div class="layui-card">
					<div class="layui-card-header">最新的十条需求</div>
					<div class="layui-card-body demand-newest-box">
						<table id="demands" lay-filter="demands"></table>
					</div>
				</div>
			</div>


		</div>
	</div>

	<!-- 导入公共底部 -->
	<jsp:include page="./layout/footer.jsp" />

	<script src="/admin/js/common/common.js"></script>
	<script src="/admin/js/home/index.js"></script>

</body>

</html>