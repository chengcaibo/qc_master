<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<jsp:include page="../common/head.jsp" />
<meta charset="UTF-8">
<title>详情</title>
<link href="/css/demand/detail.css" type="text/css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/header" />
	<div class="release-div-all">
		<div class="release-header">
			<h3>需求详情</h3>
		</div>
		<div class="release-content">
			<span><img src="${demand.picture}"></span>
			<ul>
				<li><span class="span-title">需求内容:</span>
					<p class="span-content-p">${demand.content}</p></li>
				<%-- <li><span class="span-title">所给酬劳:</span> <span
					class="span-content">${demand.reward} — ${demand.endReward}元</span></li> --%>
				<li><c:choose>
						<c:when test="${demand.reward>0}">
							<span class="span-title">所给酬劳:</span>
							<span class="span-content">${demand.reward} —
								${demand.endReward}元</span>
						</c:when>
						<c:otherwise>
							<span class="span-title">所给酬劳:</span>
							<span class="span-content">面议</span>
						</c:otherwise>
					</c:choose></li>
				<li><span class="span-title">备注信息:</span> <span
					class="span-content">${demand.note}</span></li>
				<li><span class="span-title">联系电话:</span> <span
					class="span-content">${demand.phone}</span></li>
				<li><span class="span-title">联系人员:</span> <span
					class="span-content">${demand.contact}</span></li>
				<li>
					<span class="span-title">具体地址:</span>
					<span class="span-content">${demand.address}&nbsp;${demand.addressName}</span>
				</li>
				<li>
					<input type="hidden" value="${demand.user.id}" name="demandUser"/>
					<input type="hidden" value="${demand.id}" name="demandId">
					<a class="response">我可以</a>
				</li>
			</ul>
		</div>
		<div></div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script src="/js/demand/detail.js"></script>
</html>