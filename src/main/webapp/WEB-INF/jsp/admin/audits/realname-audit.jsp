<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html lang="ZH">
<head>
<jsp:include page="/WEB-INF/jsp/admin/common/head.jsp" />
<title>正在审核：${info.realName} - 实名认证审核 - 奇虫管理后台</title>
<link href="/admin/css/audits/common.css" rel="stylesheet">

</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<!-- 导入公共头部导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-top.jsp" />
		<!-- 导入审核左侧导航栏 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/nav-left-audits.jsp" />

		<!-- 内容主体区域 -->
		<div id="viewer" class="layui-body">
			<!-- 区块 -->
			<div class="layui-card">
				<div class="layui-card-header">正在审核：${info.realName}</div>
				<div class="layui-card-body">
					<div class="cert-info-item cert-info-item-float">
						<span>用户Id：</span><span>${info.user.id}</span>
					</div>
					<div class="cert-info-item cert-info-item-float">
						<span>用户奇虫号：</span><span>${info.user.username}</span>
					</div>
					<div class="cert-info-item cert-info-item-float">
						<span>用户类型：</span><span><c:if test="${info.user.typeId == 1}">个人</c:if> <c:if test="${info.user.typeId == 2}">企业</c:if></span>
					</div>
					<div class="cert-info-item cert-info-item-float">
						<span>注册时间：</span><span><fmt:formatDate value="${info.user.createTime}" pattern="yyyy年MM月dd日 hh:mm:ss" /></span>
					</div>
					<div class="clear"></div>
					<div class="cert-info-item cert-info-item-float">
						<span>真实姓名：</span><span>${info.realName}</span>
					</div>
					<div class="cert-info-item cert-info-item-float">
						<span>身份证件号码：</span><span>${info.identity}</span>
					</div>
					<div class="cert-info-item cert-info-item-float">
						<span>审核申请提交时间：</span><span><fmt:formatDate value="${info.createTime}" pattern="yyyy年MM月dd日 hh:mm:ss" /></span>
					</div>
					<div class="clear"></div>
					<div class="cert-info-item cert-info-item-float id-a">
						<span>身份证正面照片：</span><span><img alt="身份证正面照片" title="身份证正面照片（点击查看大图）" src="${info.identityPictureA}" data-original="${info.identityPictureA}"></span>
					</div>
					<div class="cert-info-item cert-info-item-float id-b">
						<span>身份证反面照片：</span><span><img alt="身份证反面照片" title="身份证反面照片（点击查看大图）" src="${info.identityPictureB}" data-original="${info.identityPictureB}"></span>
					</div>
					<div class="clear"></div>
					<div class="cert-info-item id-c">
						<span>身份证手持照片：</span><span><img alt="身份证手持照片" title="身份证手持照片（点击查看大图）" src="${info.identityPictureC}" data-original="${info.identityPictureC}"></span>
					</div>
					<div class="cert-info-button">
						<button class="layui-btn layui-btn-danger" name="rejected">驳回</button>
						<button class="layui-btn layui-btn-warm" name="notSure">待定并审核下一条</button>
						<button class="layui-btn btn-passed" name="passed">通过</button>
					</div>

				</div>
			</div>
		</div>

		<!-- 导入公共底部 -->
		<jsp:include page="/WEB-INF/jsp/admin/layout/footer.jsp" />

	</div>
	<script>const id = ${info.id};</script>
	<script src="/admin/js/common/common.js"></script>
	<script src="/admin/js/audits/realname-audit.js"></script>

</body>
</html>