<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>新闻</title>
<link rel="stylesheet" href="/css/check-search.css">
</head>
<body>
	<!--header-->
	<jsp:include page="/header" />
	<div class="person-big-div">
		<div class="check-h4"><h4>所选信息</h4></div>
		<div class="check-return"><h4><a href="/journalism?s=">返回>></a></h4></div>
		<div class="seach-in-ul">
			<ul>
			<c:forEach items="${uiList}" var="item">
				<li class="person-li">
				<div class="pseron-content">
					<img src="${item.avatar}">

					<div class="person-info">
						<p><span>姓名：</span>${item.realName}</p>
						<p><span>年龄：</span>${item.age}</p>
						<p><span>行业：</span>你猜</p>
					</div>
					<div class="person-info">
						<p class="person-on"><span>电话：</span>${item.telephone}</p>
						<p class="person-on"><span>邮箱：</span>${item.email}</p>
						<p class="person-on"><span>评分：</span>
						<c:if test="${item.score < 0 }">
							暂无评分
						</c:if>
						<c:if test="${item.score > 0 }">
							<c:forEach items="${item.scoreViews}" var="item">
								<i class="${item}"></i>
							</c:forEach>
						</c:if></p>
						
						
					</div>
					<div class="person-info-introduce">
						<p>
							<span>介绍：</span>
							${item.introduce}
						</p>
					</div>
					<div class="person-border"></div>
					<div class="person-info-num">
						<p class=""><span>时薪：</span>${item.hourlyWage}</p>
						<p><span>累计预约次数：</span>${item.appintmentCount}</p>
					</div>
				</div>
			</li>
			</c:forEach>
			
		</ul>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>