<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>你先要发布什么？</title>
<link href="/css/posted.css" rel="stylesheet">
<script src="/js/posted.js"></script>
</head>
<body>
	<jsp:include page="/header" />
	<div class="posted">
		<h1>您想要发布什么？</h1>
		<ul class="select-box">
			<li class="select-box-li"><a href="/requirements" class="select-box-li-p">需求</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>
			<li class="select-box-li"><a class="select-box-li-p" href="/submit/group">团体</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>
			<li class="select-box-li" id="advertising"><a class="select-box-li-p">免费发布广告</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>

			<li class="plug-out" id="bounces"><c:choose>
					<c:when test="${empty sessionScope.currentUser}">
						<div class="prompt-box">
							<p class="prompt-box-p">对不起您还没有登录</p>
							<p class="prompt-box-p">暂时不能发布</p>
						</div>
					</c:when>
					<c:otherwise>
						<form action="/insertAdPublic" method="post" enctype="multipart/form-data">
							<div class="plug-out-li-content">
								<p class="plug-out-li-content-p">内容</p>
								<textarea name="content" class="plug-out-li-content-textarea" placeholder="请填写内容...." maxlength=""></textarea>
							</div>
							<div class="plug-out-li-telephone">
								<p class="telephone">电话：</p>
								<input type="text" name="phone" placeholder="请输入电话号..." class="input-number" id="specialLabel" />
								<div class="wrongCall" id="wrongCall">电话有误</div>
							</div>
							<div class="plug-out-li-address">
								<p class="telephone">地址：</p>
								<textarea name="address" class="plug-out-li-address-textarea" placeholder="请填写内容...."></textarea>
							</div>
							<div class="plug-out-li-picture">
								<p class="plug-out-li-picture-p">上传图片:</p>
								<div id="file-logo-parent">
									<label for="file-logo"> <img src="" title="点击上传LOGO" class="LOGO-img" /></label> <a href=" "
										class="LOGO-photo" style="display: none;"> <input id="file-logo" type="file" class="LOGO-form"
										name="abPublicFile" />
									</a>
								</div>
							</div>
							<button class="plug-out-button" type="submit">发布</button>
						</form>
					</c:otherwise>
				</c:choose></li>
		</ul>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>