<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="common/head.jsp" />

<title>发布需求</title>
<link href="/css/release-requirements.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/header" />
	<h1 class="release-requirements">发布需求</h1>
	<div class="release-requirements-interface">
		<form action="/insertDemandInfo" method="post">
			<div class="release-requirements-interface-content">
				<p class="release-requirements-interface-content-labelling">
					<b>需求内容:</b>
				</p>
				<textarea placeholder="您的发布需求..." class="release-requirements-interface-content-text" name="content"></textarea>
			</div>
			<div class="release-requirements-interface-content">
				<p class="release-requirements-interface-content-labelling">
					<b>您的地址:</b>
				</p>
				<textarea placeholder="您的地址..." class="release-requirements-interface-content-text" name="address"></textarea>
			</div>
			<div class="release-requirements-interface-content">
				<p class="release-requirements-interface-content-labelling">
					<b>酬劳:</b>
				</p>
				<textarea placeholder="酬劳..." class="release-requirements-interface-content-text" name="reward"></textarea>
			</div>
			<!-- 	<a href="###" class="release-requirements-posted">发布</a> -->
			<button type="submit" class="release-requirements-posted">发布</button>
		</form>
	<jsp:include page="common/footer.jsp" />
	</div>
</body>
</html>