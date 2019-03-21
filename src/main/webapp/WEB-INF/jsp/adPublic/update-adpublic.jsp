<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />
<title>修改广告</title>
<link rel="stylesheet" href="/css/advertising.css">
<script src="/js/advertising.js"></script>
</head>
<body>
	<jsp:include page="/header" />
	<form id="form" method="post" enctype="multipart/form-data">
		<div class="return-icon" id="icon-div" style="display: none;">
			<i class="return-close icon-close" id="close-div"></i> <i
				class="return-success icon-success"></i>
			<h3>恭喜您,保存成功！</h3>
			<span><a href="/">返回首页>></a></span>
		</div>
		<div class="advertising">
			<h1>发布广告</h1>
			<div class="advertising-top">
				<p class="prompt content-sensitive">内容有敏感词</p>
				<p class="prompt content-empty">内容不能为空</p>
				<p>内容：</p>
				<textarea name="content" placeholder="请输入..." maxlength="200"
					id="content">${adpublic.content}</textarea>
			</div>
			<div class="advertising-picture">
				<p>图片：</p>
				<div id="file-headPortrait-parent">
					<label for="file-headPortrait"> <i></i> <img src="${adpublic.picture}"
						title="点击上传图片" class="headPortrait-img" />
					</label> <a href="#" class="headPortrait-photo" style="display: none;">
						<input id="file-headPortrait" name="abPublicFile" type="file"
						class="headPortrait-form" value="${adpublic.picture}"/>
					</a>
				</div>
			</div>
			<div class="address">
				<p class="prompt address-sensitive">地址有敏感词</p>
				<p class="prompt address-empty">地址不能为空</p>
				<p>地址：</p>
				<textarea placeholder="请输入..." name="address" maxlength="100" id="address">${adpublic.address}</textarea>
			</div>
			<div class="telephone">
				<p>手机号：</p>
				<input type="text" name="phone" placeholder="请输入手机号..."
					id="changeTelephone" value="${adpublic.phone}">
			</div>
			<div class="push-button">
				<button id="release-sueescc" type="button">立即发布</button>
			</div>

		</div>
		<jsp:include page="../common/footer.jsp" />
	</form>
</body>
</html>