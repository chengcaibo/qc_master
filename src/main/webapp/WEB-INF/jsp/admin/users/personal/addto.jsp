<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="ZH">
<head>
<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
<title>个人用户管理 - 奇虫新管理后台</title>

<link rel="stylesheet" href="/admin/css/personal/addto.css">

</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-users.jsp" />

		<!-- 内容主体区域 -->
		<div class="layui-body">
			<div class="layui-card">
				<div class="layui-card-header">添加个人信息</div>
				<div class="layui-card-body">
					<form id="form" class="layui-form" method="POST" enctype="multipart/form-data">
						<div class="layui-form-item">
							<label class="layui-form-label"><span class="red">*</span>昵称</label>
							<div class="layui-input-block">
								<input type="text" name="nickName" placeholder="请输入昵称" autocomplete="off" class="layui-input" lay-verify="required">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label"><span class="red">*</span>手机号码</label>
							<div class="layui-input-block">
								<input type="text" name="telephone" placeholder="请输入手机号码" autocomplete="off" class="layui-input"
									lay-verify="required|phone">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label"><span class="red">*</span>性别</label>
							<div class="layui-input-block">
								<input type="radio" name="gender.id" value="1" title="保密" checked>
								<!--  -->
								<input type="radio" name="gender.id" value="2" title="男">
								<!--  -->
								<input type="radio" name="gender.id" value="3" title="女">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">头像</label>
							<div class="layui-input-inline">
								<label><img class="avatar" src="/images/picture-bg.jpg"><input id="avatarFile" type="file" name="avatarFile"
									accept="image/*" style="display: none;"></label>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">生日</label>
							<div class="layui-input-block">
								<input type="text" id="birthday" name="birthday" placeholder="请输入生日" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">电子邮箱</label>
							<div class="layui-input-block">
								<input type="text" name="email" placeholder="请输入电子邮箱" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">时薪</label>
							<div class="layui-input-block">
								<input type="text" name="hourlyWage" placeholder="请输入时薪" autocomplete="off" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">简介</label>
							<div class="layui-input-block">
								<textarea name="introduce" placeholder="请输入简介" class="layui-textarea"></textarea>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">优势</label>
							<div class="layui-input-block">
								<textarea name="ascendancy" placeholder="请输入优势" class="layui-textarea"></textarea>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">职业</label>
							<div class="layui-input-inline" style="width:180px">
								<select class="job-1" lay-filter="job"></select>
							</div>
							<div class="layui-input-inline" style="width:180px;">
								<select class="job-2" lay-filter="job"></select>
							</div>
							<div class="layui-input-inline" style="width:180px">
								<select class="job-3" lay-filter="job" name="jobInfo.jobCode"></select>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">地区</label>
							<div class="layui-input-inline" style="width:180px">
								<select class="region-1" lay-filter="region"></select>
							</div>
							<div class="layui-input-inline" style="width:180px;">
								<select class="region-2" lay-filter="region"></select>
							</div>
							<div class="layui-input-inline" style="width:180px">
								<select class="region-3" lay-filter="region" name="region.regionCode"></select>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<button class="layui-btn" lay-submit lay-filter="submit">立即添加</button>
								<button id="btn-reset" type="reset" class="layui-btn layui-btn-primary">重置</button>
							</div>
						</div>
						<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
					</form>
				</div>
			</div>
		</div>


		<!-- 导入公共底部 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/footer.jsp" />

	</div>
	<script src="/admin/js/common/common.js"></script>
	<script src="/js/job-layui.form.js"></script>
	<script src="/js/regions-layui.form.js"></script>
	<script src="/admin/js/users/personal/addto.js"></script>
</body>
</html>