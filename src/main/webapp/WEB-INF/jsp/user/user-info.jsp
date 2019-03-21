<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>预约</title>

<link href="/home-css/icon/iconfont.css" rel="stylesheet">
<link rel="stylesheet" href="/css/prsonal-profile.css">
<link rel="stylesheet" href="/css/lq.datetimepick.css">

</head>
<body>
	<div class="focus-on-success focus-on-close" style="display: none;">
		<i class="return-close icon-close" id="close-div"></i> <i
			class="return-success icon-success"></i>
		<h3 class="suc">恭喜您,关注成功</h3>
		<p class="p1-info"></p>
		<span><a href="/">返回>></a></span>
	</div>
	<jsp:include page="/header" />
	<!--第一块-->
	<div class="my-home-page">
		<p class="back_p"></p>
		<h1>我的主页</h1>
		<div>
			<p>WODEZHUYE</p>
		</div>
	</div>
	<div class="prsonal-profile-top">
		<input type="hidden" id="user-id-input" class="follow-user" name="id" value="${id}">
		<div class="prsonal-profile-top-img">
			<img src="${ui.avatar}" alt="">
		</div>
		<div class="prsonal-profile-top-information">
			<div class="information-top">
				<p class="information-top-name">${ui.realName}</p>
				<p class="information-top-certification">
					<i class="icon-gerenrenzheng"></i>诚信用户
				</p>
				<a class="information-top-addition" id="addition">${messg}</a>
				<div class="information-meddle-zan">
					<%-- <input name="userId" class="userId-follow" type="hidden" value="${userInfo.user.id}"> --%>
					<input type="hidden" name="userId" value="${ui.user.id}" class="follow-userId" id="userId">
					
					<a id="giveLike"><i class="icon-zan" id="giveLikeI"></i>${likesInfoCount}</a>
				</div>
			</div>
			<div class="information-meddle">
				<div class="industry-div industry-span">

					<p>
						职业：<span>${ui.jobInfo.jobName}</span>
						<c:if test="${ui.jobInfo.jobName == null}">
							<span>用户暂无选择职业</span>
						</c:if>
					</p>
				</div>
				<div class="information-meddle-credit">
					<p>
						信用评级：
						<c:if test="${ui.score < 0}">
							暂无评分
						</c:if>
						<c:if test="${ui.score > 0}">
							<c:forEach items="${ui.scoreViews}" var="item">
								<i class="${item}"></i>
							</c:forEach>
						</c:if>
					</p>
				</div>
				<div class="information-meddle-ability">
					<p>
						工作年限：<span>${ui.jobYears}</span>
					</p>
					<p>
						年龄：<span>${ui.age}</span>
					</p>
				</div>
				<div class="information-meddle-ability">
					<p>
						现住址：<span>${ui.address}</span>
					</p>
				</div>
			</div>
		</div>
		<div class="prsonal-profile-top-salary">
			<div class="salary-frame">
				<div class="salary-frame-top">
					<p>时薪：</p>
					<p class="hourlyWage-info">${ui.hourlyWage}</p>
					<span class="hourl">/小时</span>
				</div>
				<div class="salary-frame-bottom">
					<p>累计预约次数：</p>
					<p>${userCount}<span>次</span>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="sharing">
		<p>
			分享给 <a href="#"><i class="icon-wechat"></i></a> <a href="#"><i
				class="icon-qq"></i></a> <a href="#"><i class="icon-weibo"></i></a>
		</p>
	</div> -->


	<!--可预约时间-->
	<div class="my-home-page">

		<p class="back_p"></p>
		<h1>预约时间</h1>
		<div>
			<p>YUYUESHIJIAN</p>
		</div>
	</div>
	<!-- <form action="/insertReservationTwo" method="post"> -->
	<div class="appointment-info">
		<div class="appointment-title">
			<span style="font-size: 20px;color: #fff;">日&nbsp;期：</span> <span
				style="font-size: 20px;color: #fff;" class="time-info"></span>

			<div class="form-addtime float-left w140" style="display: inline-block">
				<input type="text" style="background: #fff" readonly="readonly" name="timeList" id="datetimepicker3" class="hidden-close-button form-control" value="">
				<!-- <a type="button" class="close-calendar">关闭日历</a> -->
			</div>
		</div>
		<div class="fixed-time">
			<div class="make-info" style="display: none;">
				<i class="return-close icon-close" id="close-div"></i> <i
					class="return-success icon-success"></i>
				<h3 class="suc">恭喜您,预约成功</h3>
				<p class="p1"></p>
				<p class="p1-s-time"></p>
				<p class="p1-e-time"></p>
				<p class="p2"></p>
				<p>请按照约定时间、约定地点到达</p>
				<span><a href="/">返回首页>></a></span>
			</div>
			<ul class="appointment-ul">
				<li class="time-in"><label class="checkbox-box"></label><span>00:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>01:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>02:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>03:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>04:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>05:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>06:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>07:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>08:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>09:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>10:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>11:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>12:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>13:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>14:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>15:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>16:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>17:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>18:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>19:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>20:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>21:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>22:00</span></li>
				<li class="time-in"><label class="checkbox-box"></label><span>23:00</span></li>
			</ul>
			<div class="sub-time">
				<input type="hidden" value="${ui.user.id}" id="userBId" name="userB" />
				<input type="hidden" value="${appintmentTime.id}" class="appintmentTime-id" name="id">
				<input type="hidden" value="${ui.telephone}" id="user-telephone" name="telephone" /> 
				<input type="hidden" value="${ui.realName}" id="user-realName" name="realName" /> 
				<input type="hidden" name="sT" value="" class="t-on" placeholder="所选开始时间"> 
				<input type="hidden" name="eT" value="" class="t-on" placeholder="所选结束时间">

				<input type="text" readonly="readonly" name="startTime" value="" class="times-on" placeholder="所选开始时间"> 
				<input type="text" readonly="readonly" name="endTime" value="" class="times-on" placeholder="所选结束时间">
				<button type="reset">重置</button>
				<button type="submit" id="appointment-btn">预约</button>
			</div>
		</div>
	</div>
	<!-- </form> -->

	<!-- <div class="available-time">
		<div class="available-time-content">
			<ul class="available-time-content-top" id="appointmentTime"></ul>
			<div class="available-time-content-bottom">
				<ul></ul>
			</div>
		</div>
	</div> -->
	<!--我的技能-->
	<div class="my-home-page">
		<p class="back_p"></p>
		<h1>我的技能</h1>
		<div>
			<p>WODEJINENG</p>
		</div>
	</div>
	<div class="my-skills">
		<c:if test="${empty skillInfoList}">
			<li style="text-align: center;margin: 40px;font-size: 30px;">暂时没有技能可展示！</li>
		</c:if>
		<c:forEach items="${skillInfoList}" var="item">
			<div class="my-skills-content">
				<p>${item.skillName}</p>
				<p></p>
			</div>
		</c:forEach>
	</div>
	<!--自我介绍-->
	<div class="my-home-page">
		<p class="back_p"></p>
		<h1>我的介绍</h1>
		<div>
			<p>WODEJIESHAO</p>
		</div>
	</div>
	<div class="self-introduction">
		<h1>
			自我介绍<span>●</span>
		</h1>
		<div class="self-introduction-content">
			<p>${ui.introduce}</p>
		</div>

		<div class="self-introduction">
			<h1>
				我的优势<span>●</span>
			</h1>
			<div class="my-ascendancy-centent">
				<p>${ui.ascendancy}</p>
			</div>
		</div>
	</div>
	<!--案例&证书-->
	<div class="my-home-page">
		<p class="back_p"></p>
		<h1>资质&amp;案例</h1>
		<div>
			<p>ZIZHI&amp;ANLI</p>
		</div>
	</div>
	<div class="case-and-certificate">
		<div class="case">
			<h1>工作案例</h1>
			<ul>

				<c:if test="${empty caseInfoList}">
					<li style="text-align: center; font-size: 30px; background: transparent; width: 100%;box-shadow: none;">用户暂未上传工作案例</li>
				</c:if>
				<c:forEach items="${caseInfoList}" var="item">
					<li>
						<img src="${item.picture}" alt="${item.caseName}">
						<div class="caseinfo-content">
							<p>案例名称：${item.caseName}</p>
							<p>案例时间：<fmt:formatDate value="${item.startTime}" type="both" />——<fmt:formatDate value="${item.endTime}" type="both" /></p>
							<p>
								案例介绍：
								<span>
									${item.caseContent}
									
								</span>
							</p>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="certificate">
			<h1>资质证书</h1>
			<ul>

				<c:if test="${empty industryAptitudeList}">
					<li style="text-align: center; font-size: 30px; background: transparent; width: 100%;box-shadow: none;">用户暂未上传资质证书</li>
				</c:if>
				<c:forEach items="${industryAptitudeList}" var="item">
					<li><img src="${item.picture}" alt="${item.text}"></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<!--预约-->
	<div class="my-home-page">
		<p class="back_p"></p>
		<h1>预约记录</h1>
		<div>
			<p>YUYUEJILU</p>
		</div>
	</div>
	<div class="covenant">
		<ul>
			<c:if test="${empty appintmentTimelist1}">
				<li style="text-align: center;padding: 18px 0;font-size: 18px;">暂时没有被预约过</li>
			</c:if>
			<c:forEach items="${appintmentTimelist1}" var="item">
				<li>
					<div class="covenant-content">
						<p
							style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 50px;">${item.uiA.nickName}</p>
						<p
							style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 65px;">${item.uiB.nickName}</p>
						<p>
							<fmt:formatDate type="both" value="${item.startTime}" />
						</p>
						<p>
							<fmt:formatDate type="both" value="${item.endTime}" />
						</p>
						<p>满意程度：100%</p>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>



	<!--对他的评价-->
	<div class="my-home-page">
		<p class="back_p"></p>
		<h1>评价</h1>
		<div>
			<p>PINJIA</p>
		</div>
	</div>
	<div class="appraise">
		<ul>
			<c:if test="${empty commentariesList}">
				<li style="text-align: center;padding: 12px 0;font-size: 18px;">暂时没有评论！！！</li>
			</c:if>
			<c:forEach items="${commentariesList}" var="item">
				<li>
					<p>
						<span>热心用户：${item.reviewers.nickName}</span>${item.evluationContent}
					</p>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="sharing sharing-new">
		<a href="#">更多</a>
	</div>
	<!--分享-->
	<div class="sharing sharing-bottom">
		<p>
			分享给 <a href="#"><i class="icon-wechat"></i></a> <a href="#"><i
				class="icon-qq"></i></a> <a href="#"><i class="icon-weibo"></i></a>
		</p>
	</div>

	<!--您可能还喜欢-->
	<div class="you-like">
		<h1>您可能还喜欢</h1>
		<div class="bg-img"></div>
		<ul>
			<c:if test="${empty pushuserList}">
				<li style="text-align: center; font-size: 30px; background: transparent; width: 100%;box-shadow: none;">暂时没有可推荐给您的！！！</li>
			</c:if>
			<c:forEach items="${pushuserList}">
				<li><a href="/user/${ui.id}"><img src="${ui.avatar}" alt=""></a></li>
			</c:forEach>
			<!-- 
			<li><img src="/images/001.jpg" alt=""></li>
			<li><img src="/images/002.jpg" alt=""></li>
			<li><img src="/images/zjl.jpg" alt=""></li>
			<li><img src="/images/zjl.jpg" alt=""></li> -->
		</ul>
	</div>
	<div id="bodyMask"></div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script src="/js/selectUi.js"></script>
<script src="/js/lq.datetimepick.js"></script>
<script src="/js/user-info.js"></script>
<script src="/js/prsonal-profile.js"></script>
</html>
