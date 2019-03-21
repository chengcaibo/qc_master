<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="common/head.jsp" />
<meta charset="UTF-8">
<title>我预约的人来信展示页</title>
<link href="/css/appintment/mailbox-information-page.css"
	rel="stylesheet">
<link href="/css/star.css" rel="stylesheet" />
</head>
<body>

	<jsp:include page="/header" />
	
	<div class="return-icon" id="icon-div" style="display: none;">
		<i class="return-close icon-close" id="close-div"></i> <i
			class="return-success icon-success"></i>
		<h3 class="suc">恭喜您,保存成功</h3>
		<span><a href="/">返回首页>></a></span>
	</div>
	<!--侧边栏-->
	<div class="select-button">
		<a style="background: #fff;color: #333;" id="my-about">我的预约</a> 
		<a id="others-about">预约我的人</a> <a id="re">我响应的人</a> <a id="re-my">响应我的人</a>
	</div>
	
	<!--我的预约-版心内容-->
	<div class="mailbox-information-page-my my">

		<ul class="confirmed-button">
			<li id="my-stay-confirmed">待确认</li>
			<li id="my-stay-cancel">待取消</li>
			<li id="my-already-confirmed">已确认</li>
		</ul>
		<!--待确认-->
		<div class="same-style stay-confirmed-content">
			<ul class="same-style-label stay-confirmed-label">
				<li>被预约方</li>
				<li>开始时间</li>
				<li>结束时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content stay-confirmed-label-content">
				<c:forEach items="${apintmentListAList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="myStayConfirmButton-Cancel">取消</p></li>
				</c:forEach>
				<c:forEach items="${apintmentEAlList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="myStayConfirmButton-Cancel">取消</p></li>
				</c:forEach>
			</ul>
		</div>
		<!--待取消-->
		<div class="same-style stay-cancel-content">
			<ul class="same-style-label stay-cancel-label">
				<li>被预约方</li>
				<li>开始时间</li>
				<li>结束时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content stay-cancel-label-content">
				<c:forEach items="${apintmentAcancellList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<a class="confirm-to-cancel">确认取消</a>
						</p></li>
				</c:forEach>

				<c:forEach items="${apintmentEAcancellList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<a class="confirm-to-cancel">确认取消</a>
						</p></li>
				</c:forEach>
				<c:forEach items="${apintmentBcancellList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiA.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p></p></li>
				</c:forEach>
			</ul>
		</div>
		<!--已确认-->
		<div class="same-style already-confirmed-content">
			<ul class="same-style-label already-confirmed-label">
				<li>被预约方</li>
				<li>开始时间</li>
				<li>结束时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content already-confirmed-label-content">
				<c:forEach items="${apintmentAProceedList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" /> <input
						type="hidden" value="${item.uiB.user.id}" name="reviewersId" /> <input
						type="hidden"
						value="<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />"
						name="startTime" /> <input type="hidden"
						value="<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />"
						name="endTime" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p id="myHasConfirmButtonComplete">
							<a class="complete">完成</a>/<a class="aStayConfirmButton-Cancel">取消</a>
						</p></li>
				</c:forEach>
				<c:forEach items="${apintmentEAProceedlList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" /> <input
						type="hidden" value="${item.uiB.user.id}" name="reviewersId" /> <input
						type="hidden"
						value="<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />"
						name="startTime" /> <input type="hidden"
						value="<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />"
						name="endTime" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p id="myHasConfirmButtonComplete">
							<a class="complete">完成</a>/<a class="aStayConfirmButton-Cancel">取消</a>
						</p></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<!--预约我的人-版心内容-->
	<div class="mailbox-information-page-my others" style="display: none">
		<ul class="confirmed-button">
			<li id="others-stay-confirmed">待确认</li>
			<li id="others-stay-cancel">待取消</li>
			<li id="others-already-confirmed">已确认</li>
		</ul>
		<!--待确认-->
		<div class="same-style stay-confirmed-content">
			<ul class="same-style-label stay-confirmed-label">
				<li>预约方</li>
				<li>开始时间</li>
				<li>结束时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content stay-confirmed-label-content">
				<c:forEach items="${apintmentListBList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiA.nickName}</p>
						<p class="p-start-time">
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="p-end-time">
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<a class="bookingStayConfirmButtonConfirm">确认</a>/
							<a class="bookingStayConfirmButtonRefused">拒绝</a>
						</p></li>
				</c:forEach>
				<c:forEach items="${apintmentEAUlList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.enterpriseInfo.enterpriseName}</p>
						<p class="p-start-time">
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p class="p-end-time">
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<a class="bookingStayConfirmButtonConfirm">确认</a>/<a
								class="bookingStayConfirmButtonRefused">拒绝</a>
						</p></li>
				</c:forEach>
			</ul>
		</div>

		<!--待取消-->
		<div class="same-style stay-cancel-content">
			<ul class="same-style-label stay-cancel-label">
				<li>预约方</li>
				<li>开始时间</li>
				<li>结束时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content stay-cancel-label-content">
				<c:forEach items="${apintmentBcancellList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiA.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<a class="confirm-to-cancel">确认取消</a>
						</p></li>
				</c:forEach>
				<c:forEach items="${apintmentEAUcancellList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.enterpriseInfo.enterpriseName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<a class="confirm-to-cancel">确认取消</a>
						</p></li>
				</c:forEach>

				<c:forEach items="${apintmentAcancellList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p></p></li>
				</c:forEach>
			</ul>
		</div>
		<!--已确认-->
		<div class="same-style already-confirmed-content">
			<ul class="same-style-label already-confirmed-label">
				<li>预约方</li>
				<li>开始时间</li>
				<li>结束时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content already-confirmed-label-content">
				<c:forEach items="${apintmentBProceedList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.uiA.nickName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p id="myHasConfirmButtonComplete"></p></li>
				</c:forEach>
				<c:forEach items="${apintmentEAUProceedlList}" var="item">
					<li><input type="hidden" value="${item.id}" name="uAUId" />
						<p>${item.enterpriseInfo.enterpriseName}</p>
						<p>
							<fmt:formatDate value="${item.startTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>
							<fmt:formatDate value="${item.endTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p id="myHasConfirmButtonComplete"></p></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!--我的响应-->
	<div class="my-response mailbox-information-page-my" style="display: none;">
		<ul class="confirmed-button">
			<li id="response-stay-confirmed">待确认</li>
			<li id="response-already-confirmed">已确认</li>
		</ul>
		<!--待确认-->
		<div class="same-style" id="response-stay-confirmed-content">
			<ul class="same-style-label stay-confirmed-label">
				<li>我响应的</li>
				<li>响应内容</li>
				<li>响应人数</li>
				<li>时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<!-- 个人响应个人 -->
			<ul class="same-style-label-content stay-confirmed-label-content">
				<c:forEach items="${theResponseAlist}" var="item">
					<li>
						<p>
							<a href="/user/${item.userInfoB.user.id}">${item.userInfoB.realName}</a>
							<input type="hidden" value="${item.id}" />
						</p>
						<p>${item.demand.content}</p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					
					</li>

				</c:forEach>
				<!-- 企业响应企业 -->
				<c:forEach items="${theResponseAAlist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}"
										class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>${item.demand.content}</p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>
				</c:forEach>
				<!-- 个人响应企业 -->
				<c:forEach items="${theResponseABlist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}"
										class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>${item.demand.content}</p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>

				</c:forEach>
				<!-- 企业响应个人 -->
				<c:forEach items="${theEnterpriseAndUserBResponselist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" /><a
								href="/user/${item.userInfoB.user.id}">${item.userInfoB.realName}</a>
						</p>
						<p>${item.demand.content}</p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>
				</c:forEach>
				<!-- 个人响应个人发布的团队 -->
				<c:forEach items="${theResponseteamUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}"/><a
								href="/user/${item.userB.id}">${item.userInfoB.realName}</a>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>
				</c:forEach>
				<!-- 企业响应个人发布的团队 -->
				<c:forEach items="${theEnderResponseteamUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" /><a
								href="/user/${item.userB.id}">${item.userInfoB.realName}</a>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>
				</c:forEach>
				<!-- 企业响应企业 发布的团队-->
				<c:forEach items="${theEnterpriseteamAndUserResponselist}"
					var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" /><input type="hidden"
								value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}"
										class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>
				</c:forEach>
				<!-- 个人响应企业发布的团队 响应者是登录者 -->
				<c:forEach items="${theByUserAAndEnterpriseBResponseTeamlist}"
					var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}"/><input type="hidden"
								value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}"
										class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!--已确认-->
		 <div class="same-style" id="already-confirmed-content" style="display:none">
			<ul class="same-style-label already-confirmed-label">
				<li>被响应者</li>
				<li>响应内容</li>
				<li>响应人数</li>
				<li>时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content already-confirmed-label-content">
				<!--个人响应个人的已完成记录  -->
					<c:forEach items="${theResponseAilist}" var="item">
					<li>
					<p>
					
					<input type="hidden" value="${item.id}" name="threResponse"/><a href="/user/${item.userInfoB.user.id}">${item.userInfoB.realName}</a></p>
					<p>${item.demand.content}</p>
					<P>无</P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
					<p><a class="complete-demand">完成</a></p>
				</li>
				</c:forEach>
				<!-- 个人响应企业 -->
				<c:forEach items="${theResponseABilist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}" name="threResponse"/><c:choose>
								<c:when test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}" class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
						</c:choose>
						</p>
					<p>${item.demand.content}</p>
					<P>无</P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
					<p><a class="complete-demand">完成</a></p>
				</li>
				</c:forEach>
				<!-- 企业响应企业 -->
				<c:forEach items="${theResponseAAilist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}" name="threResponse"/><c:choose>
								<c:when test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}" class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
						</c:choose></p>
					<p>${item.demand.content}</p>
					<P> </P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
					<p><a class="complete-demand">完成</a></p>
					</li>
				</c:forEach>
				<!-- 企业响应个人的 -->
				<c:forEach items="${theResponsesBAilist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}" name="threResponse"/><a href="/user/${item.userInfoB.user.id}">${item.userInfoB.realName}</a></p>
					<p>${item.demand.content}</p>
					<P>无</P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
					<p><a class="complete-demand">完成</a></p>
				</li>
				</c:forEach>
				<!-- 个人响应个人的团队确认 -->
				<c:forEach items="${confrimTheResponseteamUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" name="threResponse"/><a
								href="/user/${item.userB.id}">${item.userInfoB.realName}</a>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p><a class="complete-team">完成</a></p>
					</li>
				</c:forEach>
				<!-- 企业响应个人发布的团队 -->
				<c:forEach items="${confrimTheEnderResponseteamUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" name="threResponse" /><a
								href="/user/${item.userB.id}">${item.userInfoB.realName}</a>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p><a class="complete-team">完成</a></p>
					</li>
				</c:forEach>
				<!-- 企业响应企业 发布的团队-->
				<c:forEach items="${confrimTheEnterpriseteamAndUserResponselist}"
					var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" name="threResponse"/><input type="hidden"
								value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}"
										class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p><a class="complete-team">完成</a></p>
					</li>
				</c:forEach>
				<!-- 个人响应企业发布的团队 响应者是登录者 -->
				<c:forEach items="${confrimTheByUserAAndEnterpriseBResponseTeamlist}"
					var="item">
					<li>
						<p>
							<input type="hidden" value="${item.id}" name="threResponse" /><input type="hidden"
								value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfoB.website != null && item.enterpriseInfoB.website != '#' && item.enterpriseInfoB.website != '' }">
									<a href="${item.enterpriseInfoB.website}" class="biaotia">${item.enterpriseInfoB.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfoB.id}"
										class="biaotia">${item.enterpriseInfoB.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p><a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p><a class="complete-team">完成</a></p>
					</li>
				</c:forEach>
			</ul>
		</div> 
		<!-- 		<ul> -->
		<%-- 			<c:forEach items="${theResponseAlist }" var="item"> --%>
		<!-- 				<li> -->
		<%-- 					<p>${item.userInfoB.realName}</p> --%>
		<!-- 						<p> -->
		<%-- 							<fmt:formatDate value="${item.demand.content}" --%>
		<%-- 								pattern="yyyy-MM-dd HH:mm" /> --%>
		<!-- 						</p> -->
		<!-- 						<p> -->
		<%-- 							<fmt:formatDate value="${item.}" --%>
		<%-- 								pattern="yyyy-MM-dd HH:mm" /> --%>
		<!-- 						</p> -->
		<%-- 						<p>${item.state.state}</p> --%>
		<!-- 						<p> -->
		<!-- 							<a class="beStayConfirmButton-Cancel">取消</a> -->
		<!-- 						</p> -->
		<!-- 				</li> -->

		<%-- 			</c:forEach> --%>
		<!-- 		</ul> -->
	</div>

	<!--响应我的人-->
	<div class="responseMy mailbox-information-page-my"
		style="display:none">
		<ul class="confirmed-button">
			<li id="responseMy-stay-confirmed">待确认</li>
			<li id="responseMy-already-confirmed">已确认</li>
		</ul>
		<!--待确认-->
		<div class="same-style" id="response-My-stay-confirmed">
			<ul class="same-style-label stay-confirmed-label">
				<li>响应者</li>
				<li>响应内容</li>
				<li>响应人数</li>
				<li>时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content stay-confirmed-label-content">
				<!-- 个人响应个人 session是被响应的一方 -->
				<c:forEach items="${theResponseBlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" /><a
								href="/user/${item.userInfo.user.id}">${item.userInfo.realName}</a>
						</p>
						<p>
						<input type="hidden" name="demandId" value="${item.demand.id}">
						<a href="/one/demand?demandId=${item.demand.id}">${item.demand.content}</a></p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
							<a class="confirm-demand">确认</a>
							<a class="refused-to">拒绝</a>
						</p>
					</li>
				</c:forEach>
				<!-- 个人响应企业 session是被响应的一方 -->
				<c:forEach items="${theUserAndEnterpriseResponselist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" /><a
								href="/user/${item.userInfo.user.id}">${item.userInfo.realName}</a>
						</p>
						<p>
						<input type="hidden" name="demandId" value="${item.demand.id}">
						<a href="/one/demand?demandId=${item.demand.id}">${item.demand.content}</a></p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
						<a class="confirm-demand">确认</a>
							<a class="refused-to">拒绝</a>
						</p>
					</li>
				</c:forEach>
				<!-- 企业响应企业 session是被响应的一方 -->
				<c:forEach items="${theResponseBBlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}"
										class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
						<input type="hidden" name="demandId" value="${item.demand.id}">
						<a href="/one/demand?demandId=${item.demand.id}">${item.demand.content}</a></p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
						<a class="confirm-demand">确认</a>
							<a class="refused-to">拒绝</a>
						</p>
					</li>
				</c:forEach>
				<!-- 企业响应个人的 -->
				<c:forEach items="${theResponsesBAlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}"
										class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
						<input type="hidden" name="demandId" value="${item.demand.id}">
						<a href="/one/demand?demandId=${item.demand.id}">${item.demand.content}</a></p>
						<P>无</P>
						<p>
							<fmt:formatDate value="${item.responseTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
						<a class="confirm-demand">确认</a>
							<a class="refused-to">拒绝</a>
						</p>
					</li>
				</c:forEach>
				<!-- 个人响应个人的团队 -->
				<c:forEach items="${theResponseteamBUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" /><a
								href="/user/${item.userA.id}">${item.userInfoA.realName}</a>
						</p>
						<p>
							<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
							<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a>
						</p>
					</li>
				</c:forEach>
				<!-- 个人响应企业发布的团队 -->
				<c:forEach items="${theEnderResponseteamBUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" /><a
								href="/user/${item.userA.id}">${item.userInfo.realName}</a>
						</p>
						<p>
						<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
							<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a>
						</p>
					</li>
				</c:forEach>
				<!-- 企业响应企业发布的团队 -->
				<c:forEach items="${theEnterpriseteamAndUserBResponselist}"
					var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}"
										class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
						<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
							<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a>
						</p>
					</li>
				</c:forEach>
				
				<c:forEach items="${theEnterpriseAderResponseteamUserlist}"
					var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}"
										class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
						<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
							<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a>
						</p>
					</li>
				</c:forEach>
			</ul>
		</div>

		<!--已确认-->
		 <div class="same-style" id="response-My-confirmed-content" style="display:none">
			<ul class="same-style-label already-confirmed-label">
				<li>我响应的</li>
				<li>响应内容</li>
				<li>响应人数</li>
				<li>时间</li>
				<li>进度</li>
				<li>选择</li>
			</ul>
			<ul class="same-style-label-content already-confirmed-label-content">
			<!-- 个人响应个人的 -->
				<c:forEach items="${theResponseBilist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}"/><a href="/user/${item.userInfo.user.id}">${item.userInfo.realName}</a></p>
					<p>${item.demand.content}</p>
					<P>无</P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
				</li>
				</c:forEach>
				<!-- 个人响应企业的 -->
				<c:forEach items="${theuserBResponseABlist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}"/><a href="/user/${item.userInfo.user.id}">${item.userInfo.realName}</a></p>
					<p>${item.demand.content}</p>
					<P> </P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
				</li>
				</c:forEach>
				<!-- 企业响应企业的 -->
				<c:forEach items="${theResponseBBilist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}"/><c:choose>
								<c:when test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}" class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
						</c:choose></p>
					<p>${item.demand.content}</p>
					<P>无</P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
				</li>
				</c:forEach>
				<!-- 企业响应个人的 -->
				<c:forEach items="${theEnterpriseBAndUserBResponselist}" var="item">
					<li>
					<p><input type="hidden" value="${item.id}"/>
					<c:choose>
								<c:when test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}" class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
						</c:choose></p>
					<p>${item.demand.content}</p>
					<P>无</P>
					<p>
						<fmt:formatDate value="${item.responseTime}"
							pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p>${item.state.state}</p>
				</li>
				</c:forEach>
				<!-- 个人响应个人的团队 -->
				<c:forEach items="${confrimTheResponseteamBUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" /><a
								href="/user/${item.userA.id}">${item.userInfoA.realName}</a>
						</p>
						<p>
							<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p class="choose-info">
							<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a> 
						</p>
					</li>
				</c:forEach>
				<!-- 个人响应企业发布的团队 -->
				<c:forEach items="${confrimTheEnderResponseteamBUserlist}" var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" /><a
								href="/user/${item.userA.id}">${item.userInfo.realName}</a>
						</p>
						<p>
						<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
					<!-- 		<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a> -->
						</p>
					</li>
				</c:forEach>
				<!-- 企业响应企业发布的团队 -->
				<c:forEach items="${confrimTheEnterpriseteamAndUserBResponselist}"
					var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}"
										class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
						<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
					<!-- 		<a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a> -->
						</p>
					</li>
				</c:forEach>
				
				<c:forEach items="${confrimTheEnterpriseAderResponseteamUserlist}"
					var="item">
					<li>
						<p>
							<input type="hidden" name="threResponse" value="${item.id}" />
							<c:choose>
								<c:when
									test="${item.enterpriseInfo.website != null && item.enterpriseInfo.website != '#' && item.enterpriseInfo.website != '' }">
									<a href="${item.enterpriseInfo.website}" class="biaotia">${item.enterpriseInfo.enterpriseName}</a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.enterpriseInfo.id}"
										class="biaotia">${item.enterpriseInfo.enterpriseName}></a>
								</c:otherwise>
							</c:choose>
						</p>
						<p>
						<input type="hidden" value="${item.groupInfo.id}" name="groupId">
						<a href="/group/${item.groupInfo.id}" class="aaaa">${item.groupInfo.groupName}</a></p>
						<p>${item.requiredNumber}人</p>
						<p>
							<fmt:formatDate value="${item.responseTeamTime}"
								pattern="yyyy-MM-dd HH:mm" />
						</p>
						<p>${item.state.state}</p>
						<p>
							<!-- <a class="confrim-team">确认</a>
							<a class="cananl-team">拒绝</a> -->
						</p>
					</li>
				</c:forEach>
			</ul>
		</div> 
		<!-- 		<ul> -->
		<%-- 			<c:forEach items="${theResponseAlist }" var="item"> --%>
		<!-- 				<li> -->
		<%-- 					<p>${item.userInfoB.realName}</p> --%>
		<!-- 						<p> -->
		<%-- 							<fmt:formatDate value="${item.demand.content}" --%>
		<%-- 								pattern="yyyy-MM-dd HH:mm" /> --%>
		<!-- 						</p> -->
		<!-- 						<p> -->
		<%-- 							<fmt:formatDate value="${item.}" --%>
		<%-- 								pattern="yyyy-MM-dd HH:mm" /> --%>
		<!-- 						</p> -->
		<%-- 						<p>${item.state.state}</p> --%>
		<!-- 						<p> -->
		<!-- 							<a class="beStayConfirmButton-Cancel">取消</a> -->
		<!-- 						</p> -->
		<!-- 				</li> -->

		<%-- 			</c:forEach> --%>
		<!-- 		</ul> -->
	</div>

	<div class="box" style="display:'none'">
		<div class="evaluation-box">
			<p>我的评价：</p>
			<textarea name="evaluation" placeholder="请输入您的评价" maxlength="120"></textarea>
		</div>
		<div class="submit-button">
			<a class="submit-button-cao">提交评论</a>
		</div>
		<div id="startone" class="block clearfix">
			<div class="star_score"></div>
			<p style="float:left;">
				评分：<span class="fenshu"></span> 分
			</p>
			<div class="attitude"></div>
		</div>
		<div class="star-tip-prompt-father">
			<div class="star-tip-prompt">
				<i class="icon-like-xing-2"></i> <i class="icon-like-xing-2"></i> <i
					class="icon-like-xing-2"></i> <i class="icon-like-xing-2"></i> <i
					class="icon-like-xing-2"></i>
			</div>
			<p class="text">老铁满意得没得说</p>
		</div>

		<div class="star-tip-prompt-father">
			<div class="star-tip-prompt">
				<i class="icon-like-xing-2"></i> <i class="icon-like-xing-2"></i> <i
					class="icon-like-xing-2"></i> <i class="icon-like-xing-2"></i> <i
					class="icon-like-xing-1"></i>
			</div>
			<p class="text">满意</p>
		</div>

		<div class="star-tip-prompt-father">
			<div class="star-tip-prompt">
				<i class="icon-like-xing-2"></i> <i class="icon-like-xing-2"></i> <i
					class="icon-like-xing-2"></i> <i class="icon-like-xing-1"></i> <i
					class="icon-like-xing-1"></i>
			</div>
			<p class="text">一般</p>
		</div>

		<div class="star-tip-prompt-father">
			<div class="star-tip-prompt">
				<i class="icon-like-xing-2"></i> <i class="icon-like-xing-2"></i> <i
					class="icon-like-xing-1"></i> <i class="icon-like-xing-1"></i> <i
					class="icon-like-xing-1"></i>
			</div>
			<p class="text">略差</p>
		</div>

		<div class="star-tip-prompt-father">
			<div class="star-tip-prompt">
				<i class="icon-like-xing-2"></i> <i class="icon-like-xing-1"></i> <i
					class="icon-like-xing-1"></i> <i class="icon-like-xing-1"></i> <i
					class="icon-like-xing-1"></i>
			</div>
			<p class="text">较差</p>
		</div>
	</div>
	<jsp:include page="common/footer.jsp" />
</body>
<script src="/js/appintment/operation-appintment.js"></script>
<script src="/js/startScore.js"></script>
<script>
	scoreFun($("#startone"))
	scoreFun($("#starttwo"), {
		fen_d : 22, //每一个a的宽度
		ScoreGrade : 5 //a的个数 10或者
	})
</script>
</html>