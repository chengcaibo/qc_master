<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>新闻</title>
<link rel="stylesheet" href="/css/search/news.css">

</head>
<body>
	<!--header-->
	<jsp:include page="/header" />

	<!--新闻-->
	<div class="news">
		<div class="news-top">
			<a href="/">首页</a> &gt; <a href="/journalism">新闻</a>
		</div>

		<div class="news-content">
		
			<c:choose>
				<c:when test="${journalismInfo != null}">
					<div class="news-content-center">
						<h1>${journalismInfo.headings}</h1>
						<p id="content">${journalismInfo.content}</p>
					</div>
					<div class="news-content-bottom">
						<a href="#" class="share"> 分享 <i class="icon-fenxiang"></i>
						</a>
						<div class="source">
							<p>来源：</p>
							<p>${journalismInfo.source}</p>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="news-content-center">
						<h1>${newsInfo.headings}</h1>
						<p id="content">${newsInfo.content}</p>
					</div>
					<div class="news-content-bottom">
						<a href="#" class="share"> 分享 <i class="icon-fenxiang"></i>
						</a>
						<div class="source">
							<p>来源：</p>
							<p>${newsInfo.source}</p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<a href="/" class="home-page">返回首页</a>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>