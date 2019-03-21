<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>抢置顶</title>
<link href="/css/rob-position.css" rel="stylesheet">
<link href="/css/remaining-time.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="/header" />

	<div class="posted"></div>

	<div class="success-includes">

		<c:choose>
			<c:when test="${topList != null}">
				<h1 class="success-includes-headings">倒计时秒杀时间</h1>
				<div class="countdownTag">
					<div class="success-includes-content">
						还有
						<p class="success-includes-content-embedded" id="timeScope">
							<script type="text/javascript">
								leftTimer(new Date('<fmt:formatDate value="${topList.get(0).endTime}" type="both" />'));
							</script>
						</p>
						就可以抢置顶了
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<h1 class="success-includes-headings">倒计时秒杀时间</h1>
				<div class="countdownTag">
					<div class="success-includes-content">
						还有
						<p class="success-includes-content-embedded" id="timeScope">
							<script type="text/javascript">
								leftTimer(new Date('<fmt:formatDate value="${topList.get(1).endTime}" type="both" />'));
							</script>
						</p>
						就可以抢置顶了
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<a href="/" class="return">返回首页</a>

	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script src="/js/position.js"></script>
</html>