<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>广告</title>
<link rel="stylesheet" type="text/css" href="/css/adpublic.css">
</head>
<body>

	<jsp:include page="/header" />

	<div class="advertising">
		<div class="advertising-content">
			<c:choose>
				<c:when test="${userInfoAdpublicList != null}">
					<c:forEach items="${userInfoAdpublicList}" var="item">
						<a href="/query/one/adpublic?adPublicId=${item.id}">
							<div class="advertising-content-first">
								<div class="advertising-content-first-img">
									<img src="${item.picture}" alt="广告图片" class="img">
								</div>
								<ul class="advertising-content-particulars">
									<li class="advertising-content-particulars-li">
										<p class="advertising-content-particulars-li-p">
											<b>广告内容：</b>${item.content}</p>
										<p class="contact">
											<b>发布时间:</b>
											<fmt:formatDate type="both" value="${item.time}" />
										</p>
										<p class="address">
											<b>所属地址:</b>${item.address}</p>
									</li>
								</ul>
							</div>
						</a>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach items="${enterpriseAdpublicList}" var="item">
						<div class="advertising-content-first">
							<div class="advertising-content-first-img">
								<img src="${item.picture}" alt="广告图片" class="img">
							</div>
							<ul class="advertising-content-particulars">
								<li class="advertising-content-particulars-li">
									<p class="advertising-content-particulars-li-p">
										<b>广告内容：</b>${item.content}</p>
									<p class="contact">
										<b>发布时间：</b>
										<fmt:formatDate type="both" value="${item.time}" />
									</p>
									<p class="address">
										<b>所属地址：</b>${item.address}</p>
								</li>
							</ul>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script src="/js/adpublic.js"></script>
</html>