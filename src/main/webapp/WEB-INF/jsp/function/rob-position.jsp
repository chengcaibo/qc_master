<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>企业置顶</title>
<link rel="stylesheet" type="text/css" href="/css/rob-position.css">
</head>
<body>
	<jsp:include page="/header" />
	<div class="posted">
		<h1>企业置顶</h1>
		<ul class="select-box">
			<li class="select-box-li"><a class="select-box-li-p" href="locationId?locationId=2">置顶</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>
			<li class="select-box-li"><a class="select-box-li-p" href="locationId?locationId=3">置顶三</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>
			<li class="select-box-li"><a class="select-box-li-p" href="locationId?locationId=4">置顶六</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>
			<li class="select-box-li"><a class="select-box-li-p" href="locationId?locationId=5">置顶十</a>
				<div>
					<div class="effect"></div>
					<div class="effect-two"></div>
				</div></li>
		</ul>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
</html>