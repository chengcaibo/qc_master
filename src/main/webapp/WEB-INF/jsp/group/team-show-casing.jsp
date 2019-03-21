<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>团队展示</title>
<link rel="stylesheet" type="text/css" href="/css/team-show-casing.css" />
</head>
<body>
	<jsp:include page="/header" />
	<div class="team-introduce">
		<!--联系方式-->
		<div class="team-introduce-contact">
			<div class="team-introduce-contact-img">
				<img src="${groupInfo.picture}" alt="" />
			</div>
			<div class="team-introduce-contact-content">
				<div class="team-introduce-contact-content-top">
					<div class="contact-content-top-left">
						<h6>名称：${groupInfo.groupName}</h6>
						<input type="hidden" name="team-id" value="${groupInfo.id}">
						<input type="hidden" value="${groupInfo.user.id}" name="user-b-id">
						<div class="contact-phone">
							<p><b>联系方式：</b>${g.userInfo.telephone}</p>
						</div>
						<div class="basic-info">
							<p>${groupInfo.industry.industryName}</p>
							<p><b>人数：</b>${groupInfo.quantity}人</p>
							<p><b>所剩人数：</b>${remainingNumber}人</p>
							<input type="hidden" name="quantity" value="${remainingNumber}">
							<p><b>人员费用：</b>${groupInfo.personnelCost}元</p>
							<p><b>人员说明：</b><br>
								${groupInfo.personnelExplain}
							</p>
							<p><b>工具费用：</b>${groupInfo.toolCost}元</p>
							<p><b>工具说明：</b><br>
								${groupInfo.toolExplain}
							</p>
						</div>
						<div class="response-btn">
							<a class="submit1"><img src="/img/wyl.png"></a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--介绍内容-->
		<div class="team-introduce-introduceContent">
			<div class="content">
				<h5>团队介绍</h5>
				<p>${groupInfo.introduce}</p>
			</div>
		</div>


		<!--相似团队推荐-->
		<div class="similar-team-recommendation">
			<h3>相似团队推荐</h3>
			<ul>
				<c:if test="${empty likeGroupInfo}">
					<li>暂时没有查询到数据哦！</li>
				</c:if>
				<c:forEach items="${likeGroupInfo}" var="item">
					<li><a href="/group/${item.gi.id}"><img src="${item.gi.picture}" alt="" /></a></li>
				</c:forEach>
				<!-- <li>
						<img src="img/e7982447eaaa55c729f192225668ec70.jpeg" alt="" />
					</li>
					<li>
						<img src="img/2cb0a260b86492c8b0803313c173fc29.jpg" alt="" />
					</li>
					<li>
						<img src="img/2cb0a260b86492c8b0803313c173fc29.jpg" alt="" />
					</li> -->

			</ul>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script type="text/javascript" src="/js/group/reponseTeam.js"></script>
</html>
