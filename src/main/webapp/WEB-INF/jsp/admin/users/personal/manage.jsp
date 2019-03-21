<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="ZH">
<head>
<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
<title>个人用户管理 - 奇虫新管理后台</title>

<link href="/admin/css/personal/manage.css" rel="stylesheet">

</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
		<!-- 导入用户左侧导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-users.jsp" />

		<!-- 内容主体区域 -->
		<div class="layui-body">
			<!-- 搜索框 -->
			<div class="layui-card">
				<div class="layui-card-header">筛选项</div>
				<div id="form" class="layui-card-body layui-form">
					<div class="layui-form-item">
						<div class="layui-input-inline" style="width:300px;">
							<input type="text" class="layui-input" name="search-input" placeholder="请输入搜索内容">
						</div>
						<div class="layui-input-inline" style="width:100px;">
							<button id="personal-search-button" class="layui-btn layui-btn-primary">立即搜索</button>
						</div>
						<!-- 性别 -->
						<div class="layui-input-inline" style="width:auto;">
							<div style="line-height:40px">性别</div>
						</div>
						<div class="layui-input-inline" style="width:80px">
							<select lay-filter="filter-select" data-field="genderId">
								<option value="-1" selected>全部</option>
								<option value="1">保密</option>
								<option value="2">男</option>
								<option value="3">女</option>
							</select>
						</div>
						<!-- 账号状态 -->
						<div class="layui-input-inline" style="width:auto;">
							<div style="line-height:40px">账号状态</div>
						</div>
						<div class="layui-input-inline" style="width:90px">
							<select lay-filter="filter-select" data-field="userStatus">
								<option value="-1" selected>全部</option>
								<option value="10">正常</option>
								<option value="11">未激活</option>
								<option value="12">已冻结</option>
								<option value="13">已禁用</option>
							</select>
						</div>
						<!--  -->
					</div>
				</div>
			</div>
			<!-- layui 数据表格 -->
			<div class="layui-card">
				<div class="layui-card-header">个人信息管理</div>
				<div class="layui-card-body">
					<table id="personal-list" lay-filter="personal-list">
					</table>
				</div>
			</div>
		</div>

		<!-- 数据表格模板 -->
		<script type="text/html" id="templet-personal-avatar">
			<img class="avatar" src="{{d.avatar.url}}?&x-oss-process=image/resize,w_25" title="{{d.nickName}}" alt="头像">
		</script>
		<script type="text/html" id="templet-personal-job">
			{{d.jobInfo.jobName}}
		</script>
		<script type="text/html" id="templet-personal-region">
			{{d.region.regionName}}
		</script>
		<script type="text/html" id="templet-personal-createTime">
			{{d.user.createTime.formatDate()}}
		</script>
		<script type="text/html" id="templet-personal-state">
			{{# if (d.user.state.state.indexOf("账号_") != -1) { }}
				{{ d.user.state.state.substring(3) }}
			{{# } else { }}
				{{ d.user.state.state }}
			{{# } }}
		</script>
		<script type="text/html" id="templet-personal-action">
			<div id="table-btns">
				<a href="/user/{{d.user.id}}" class="layui-btn layui-btn-normal layui-btn-xs" target="_blank">详情</a>
				<a href="javascript:sayNoOpen();" class="layui-btn layui-btn-danger layui-btn-xs">删除</a>
			</div>
		</script>
		<script type="text/html" id="templet-personal-sex">
			<div style="text-align:center;" >{{d.gender.name}}</div>
		</script>
		<script type="text/html" id="templet-personal-age">
			{{# if (d.age >= 900) { }}
				-
			{{# } else { }}
				{{ d.age }}
			{{# } }}
		</script>
		<script type="text/html" id="templet-personal-birthday">
			{{# if (d.age >= 900) { }}
				-
			{{# } else { }}
				{{ d.birthday.formatDate("yyyy年MM月dd日") }}
			{{# } }}
		</script>

		<!-- 导入公共底部 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/footer.jsp" />

	</div>
	<script src="/admin/js/common/common.js"></script>
	<script src="/admin/js/users/personal/manage.js"></script>
</body>
</html>