<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<jsp:include page="../common/head.jsp" />
<title>广告详情</title>
<link rel="stylesheet" href="/css/adpublic/advertising-for-details.css">
</head>
<body>
	<jsp:include page="/header" />
	<!--基本介绍-->
	<div class="advertising-for-details">
		
		<div class="advertising-for-details-left">
		
		<div class="adpublic-info-title">
			<h3>广告信息</h3>
		</div>
			<div class="advertising-picture">
				<img src="${adpublic.picture}" alt="">
			</div>
			<div class="advertising-right">
				<h1>详细内容：</h1>
				<p class="advertising-content">${adpublic.content}</p>
				<div class="advertising-phone">
					<p>
						<i class="icon-dianhua"></i>联系方式：
					</p>
					<p>${adpublic.phone}</p>
				</div>
				<div class="advertising-time">
					<p>
						<i class="icon-fabu1"></i>发布时间：
					</p>
					<p>
						<fmt:formatDate value="${adpublic.time}" type="both" />
					</p>
				</div>
				<div class="advertising-address">
					<p>
						<i class="icon-7"></i>发布地址：
					</p>
					<p>${adpublic.address}</p>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>