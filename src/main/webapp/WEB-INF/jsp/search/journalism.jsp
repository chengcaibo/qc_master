<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>搜索</title>

<link rel="stylesheet" href="/css/search/real-estate.css">
<link rel="stylesheet" href="/lib/swiper/swiper.min.css">
<link rel="stylesheet" href="/css/search/city-picker.css">


</head>
<body style="background: #f5f5f5;">
	<!--header-->
	<jsp:include page="/header" />

	<!--小广告-->
	<div class="small-ad"></div>

	<!--内容-->
	<div class="industry-content">
		<h1 class="industry-content-headings">
			<a href="#">首页</a> &gt; <a href="#">房产</a>
		</h1>
		<!-- 企业行业广告 -->

		<div class="industry-content-left">

			<img src="/img/industry-two.jpg" alt="" id="content_img">
			<!-- <a data-type="3" data-tmpl="320x250" data-tmplid="187" data-rd="2" data-style="2" data-border="1" href="#">淘宝</a>-->
			<!-- <a data-type="19" data-tmpl="950x90" data-tmplid="2824" biz-url="www.taobao.com" biz-text="淘宝" biz-imgurl="dsffdgaf" data-rd="2" data-style="2" data-border="1" href="#">淘宝</a> -->
		</div>

		<%--  <c:choose>
			<c:when test="${industryTopList.get(0) != null}">
				<div class="industry-content-left">
					<h4 class="h4">
						<a id="show1"
							href="/enterpriseTop?industryCode=${industryTopList.get(0).industryCode}"
							target="_blank">我要展示到这里</a>
					</h4>
					<img src="${industryTopList.get(0).logo}" alt="" id="content_img">
					<div class="content">
						<ul>
							<li><input type="hidden" id="endTime0" name="endTime"
								value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${industryTopList.get(0).endTime}"/>'></li>
							<li class="news_li"><h4>${industryTopList.get(0).unName}</h4></li>
							<li class="news_li"><span class="news_span">介绍</span>${industryTopList.get(0).introduce}</li>
							<li class="news_li"><span class="news_span">联系方式</span>${industryTopList.get(0).telephone}</li>
							<li class="show_li"><a id="show0"
								href="/enterpriseTop?industryCode=${industryTopList.get(0).industryCode}"
								target="_blank">我要展示到这里</a></li>
						</ul>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<p>暂时没有数据</p>
				<li class="show_li"><a
					href="/enterpriseTop?industryCode=${industryTopList.get(0).industryCode}"
					target="_blank">我要展示到这里</a></li>
			</c:otherwise>
		</c:choose> --%>
		<div class="industry-content-middle">
			<div class="industry-content-middle-roasting">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<c:forEach items="${bananerList}" var="item">
							<div class="swiper-slide">
								<img src="/img/${item.picture}" alt="${item.id}">
							</div>
						</c:forEach>
					</div>
					<!-- Add Pagination -->
					<div class="swiper-pagination"></div>
				</div>
			</div>
			<div class="industry-content-middle-bottom">
				<div class="industry-content-middle-bottom-news">
					<h1 class="news-headings">热点新闻</h1>
					<ul class="news-ul">
						<c:forEach items="${JournalismList}" var="item">
							<li><a href="/byId?id=${item.id}">${item.headings}</a></li>
						</c:forEach>
					</ul>

				</div>
				<div class="industry-content-middle-bottom-news no-margin">
					<h1 class="news-headings">新闻动态</h1>
					<ul class="news-ul">
						<c:forEach items="${newsList}" var="item">
							<li><a href="/NewsbyId?id=${item.id}">${item.headings}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<!-- 个人行业广告 -->

		<div class="industry-content-right">

			<img src="/img/industry-one.jpg" alt="" id="content_imge">

		</div>

		<%--  <c:choose>
			<c:when test="${industryTopList.get(1) != null}">
				<div class="industry-content-right">
					<h4 class="h41">
						<a id="showe2"
							href="/enterpriseTop?industryCode=${industryTopList.get(1).industryCode}"
							target="_blank">我要展示到这里</a>
					</h4>
					<img src="${industryTopList.get(1).logo}" alt="" id="content_imge">
					<div class="contente">
						<ul>
							<li><input type="hidden" id="endTime1" name="endTime"
								value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${industryTopList.get(1).endTime}"/>'></li>
							<li class="news_li"><h4>${industryTopList.get(1).unName}</h4></li>
							<li class="news_li"><span class="news_span">介绍</span>${industryTopList.get(1).introduce}</li>
							<li class="news_li"><span class="news_span">联系方式</span>${industryTopList.get(1).telephone}</li>
							<li class="show_li"><a id="showe1"
								href="/enterpriseTop?industryCode=${industryTopList.get(1).industryCode}">我要展示到这里</a></li>
						</ul>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<p>暂时没有数据</p>
				<li class="show_li"><a
					href="/enterpriseTop?industryTypeCode=${industryTopList.get(1).industryTypeCode}">我要展示到这里</a></li>
			</c:otherwise>
		</c:choose> --%>
	</div>

	<!--长条广告-->
	<div class="long-advertising"></div>

	<!--频道-->
	<div class="channel">
		<div class="search-option">
			<ul class="search-option-top">
				<li id="individualButton" style="border-bottom:3px solid #000"><strong>个人</strong></li>
				<li id="groupButton"><strong>团队</strong></li>
				<li id="enterpriselButton"><strong>企业</strong></li>
				<li id="demandButton"><strong>已发的需求</strong></li>
			</ul>
			<!-- 个人信息展示 -->
			<div class="search-option-bottom" style="display: block"
				id="searchIndividual">
				<div class="channel-screening-frame">
					<div class="screening-button-box">
						<p class="screening-button screeningSwitch">
							筛选<i class="icon-screen"></i>
						</p>
					</div>
					<div class="channel-screening filterBox">
						<!-- 地区 -->
						<div class="screening-input-top">
							<div class="docs-methods">
								<!-- <form class="form-inline">
									<div id="distpicker">
										<div class="form-group">
											<div style="position: relative;">
												<span class="region_span">地区:</span> <input
													id="city-picker3" class="form-control" readonly type="text"
													placeholder="请选择&nbsp;/&nbsp;请选择&nbsp;/&nbsp;请选择/"
													data-toggle="city-picker">
											</div>
										</div>
										<div class="form-group">
											<button class="btn btn-warning" id="reset" type="button">重置</button>
											<button class="btn btn-danger" id="destroy" type="button">确定</button>
										</div>
									</div>
								</form> -->
								<p style=" position: relative;left: 39px;">所在地：</p>
								<div class="region-mask">查询中...</div>
								<select class="region-1"></select> <select class="region-2"></select>
								<select class="region-3" name="regionCode" id="regionCode-sel"></select>
							</div>
						</div>

						<!-- 行业 -->
						<div class="screening-input-top">
							<div class="docs-methods">
								<table class="industry_table">
									<tr>
										<td class="left">职位:</td>
										<td class="right" id="tab">
											<div class="region-mask">查询中...</div> <select
											class="job-1"></select> <select
											class="job-2"></select> <select
											id="jobCode-sel" class="job-3"
											name="jobCode"></select>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="screening-input-middle">
							<input type="hidden" name="skillName" value=""> <span
								class="region_span">综合技能：</span>
							<ul class="skill_ul">
								<li class="skill">java开发</li>
								<li class="skill">C#</li>
								<li class="skill">.net开发</li>
								<li class="skill">HTML5</li>
								<li class="skill">销售</li>
								<li class="skill">平面设计</li>
								<li class="skill other"><span>其他</span> <input type="text"
									class="skill_input"></li>

							</ul>
						</div>
						<div class="screening-input-bottom">
							<div class="bottom-centent">
								<span class="region_span" style="position: relative;left: 35px;">年龄：</span>
								<input type="hidden" name="beginAge" value=""> <input
									type="hidden" name="endAge" value="">
								<ul class="age_ul">
									<li class="age_li">16-21</li>
									<li class="age_li">21-26</li>
									<li class="age_li">26-31</li>
									<li class="age_li">31-36</li>
									<li class="age_li">36-41</li>
									<li class="age_li">41-46</li>
									<li class="age_li">46-51</li>
									<li class="age_li">51-56</li>
									<li class="age_li other"><span>其他</span> <input
										type="text" class="s-age"><span>—</span> <input
										type="text" class="e-age">岁</li>
								</ul>
							</div>
						</div>

						<div class="screening-input-middle">
							<div class="bottom-centent">
								<input type="hidden" name="beginHourlyWage" value=""> <input
									type="hidden" name="endHourlyWage" value=""> <span
									class="region_span" style="position: relative;left: 35px;">时薪：</span>
								<ul class="hourly_wage">
									<li class="wage">100-300</li>
									<li class="wage">300-500</li>
									<li class="wage">500-700</li>
									<li class="wage">700-1000</li>
									<li class="wage other"><span>其他</span><input type="text"
										class="s-wage"> <span>—</span> <input type="text"
										class="e-wage">元/时</li>

								</ul>
							</div>
						</div>

						<div class="channel-screening-push-button">
							<button type="button" class="user-filters-btn">确定</button>
							<button class="screeningCancelButton">取消</button>
						</div>
					</div>
				</div>
				<ul class="search-option-bottom-top">
					<c:if test="${empty preferredList}">
						<div class="notData">暂时没有查询到数据哦！</div>
					</c:if>
					<c:forEach items="${preferredList}" var="item">
						<li><i class="integrityLabel"> <img
								src="../../../img/caidai.png">
						</i>
							<div class="li-icon">优选</div>
							<div class="li-img">
								<a href="/user/${item.userId}" class="biaotia" target="_blank"><img
									src="${item.avatar}" alt="图片在此"></a>
							</div>
							<div class="li-content">
								<h1>
									<a href="/user/${item.userId}" class="biaotia" target="_blank">${item.nickName}</a>
								</h1>
								<p>
									职位：${item.jobInfo.jobName}
									<c:if test="${item.jobInfo.jobName == null}">用户暂未选择行业</c:if>
								</p>

								<p>地址：${item.regionName}</p>
								<p>介绍：${item.introduce}</p>
							</div></li>
					</c:forEach>
				</ul>
				<div class="search_user">
					<form id="personal-form" action="/checkUserInfo" method="get">
						<input type="hidden" value="${s}" class="s-info">
						<div class="check_sub">
							<input type="submit" value="查看已选中信息">
						</div>
						<h1 class="personal-no-result"
							style="text-align:center;margin:50px;display:none;">暂时没有任何结果哦！</h1>
						<ul class="search-option-bottom-bottom user-ul personal-info" id="personal-info">
							<c:forEach items="${userList}" var="item">
								<li class="user-li">
									<div class="checkbox-box">
										<input name="userId" class="checkbox-input"
											value="${item.userId}" type="checkbox">
										<div class="checkbox-mask">
											<i class="icon-checked"></i>
										</div>
									</div>
									<div class="content-div">
										<div style="width: 220px;height: 220px;background: #fff;">
											<a href="/user/${item.userId}" target="_blank"> <img alt="暂未上传图片"
												src="${item.avatar}">
											</a>
										</div>
										<h4>
											<a href="/user/${item.userId}" class="biaotia" target="_blank">${item.nickName}</a>
										</h4>
										<p>
											<span>行业：</span>${item.jobInfo.jobName}
											<c:if test="${item.jobInfo.jobName == null}">用户暂未选择行业</c:if>
										</p>
										<p>
											<span>地址：</span>${item.regionName}</p>
										<p class="introduce_p">
											<span>介绍：</span>${item.introduce}</p>
									</div>
								</li>
							</c:forEach>
						</ul>
						<div class="check_sub" style="clear: both;">
							<input type="submit" value="查看已选中信息">
						</div>
					</form>
				</div>
				<div id="paging-personal" class="paging-personal"
					style="text-align: center;margin-top: 30px;clear: both;"></div>
				<!-- <div class="page-change">
					<ul class="pagination pagination-lg">
						<li class="page-last"><a>&laquo;</a></li>
					</ul>
					<ul class="pagination pagination-lg" data-count></ul>
					<ul class="pagination pagination-lg">
						<li class="page-next"><a>&raquo;</a></li>
					</ul>
				</div> -->
			</div>

			<!-- 团队信息展示 -->
			<div class="search-option-bottom" id="searchGroup"
				style="display: none;">
				<div class="channel-screening-frame">
					<div class="screening-button-box">
						<p class="screening-button screeningSwitch">
							筛选<i class="icon-screen"></i>
						</p>
					</div>
					<div class="channel-screening filterBox">
						<!-- 地区 -->
						<div class="screening-input-top">
							<div class="docs-methods">
								<p style=" position: relative;left: 39px;">所在地：</p>
								<div class="region-mask">查询中...</div>
								<select class="region-1"></select> <select class="region-2"></select>
								<select class="region-3" name="regionCode" id="regioncode-group"></select>
							</div>
						</div>

						<!-- 行业 -->
						<div class="screening-input-top">
							<div class="docs-methods">
								<table class="industry_table">
									<tr>
										<td class="left">职位:</td>
										<td class="right" id="tab">
											<div class="region-mask">查询中...</div> <select
											class="industry-1"></select> <select class="industry-2"></select>
											<select id="industryType-code-group" class="industry-3"
											name="industryCode"></select>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="screening-input-middle">
							<input type="hidden" name="beginQuantity" value=""> <input
								type="hidden" name="endQuantity" value=""> <span
								class="region_span">人员数量：</span>
							<ul class="quantity-ul">
								<li class="quantity">2—5</li>
								<li class="quantity">6—10</li>
								<li class="quantity">11—15</li>
								<li class="quantity">16—20</li>
								<li class="quantity">21—25</li>
								<li class="quantity">26—30</li>
								<li class="quantity other"><span>其他</span> <input
									type="text" class="s-quantity"> <span>—</span> <input
									type="text" class="e-quantity">人</li>

							</ul>
						</div>
						<div class="channel-screening-push-button">
							<button type="button" class="group-filters-btn">确定</button>
							<button class="screeningCancelButton">取消</button>
						</div>
					</div>
				</div>

				<ul class="search-option-bottom-top">
					<c:if test="${empty groupPreferredList}">
						<div class="notData">暂时没有查询到数据哦！</div>
					</c:if>
					<c:forEach items="${groupPreferredList}" var="item">
						<li class="group-li"><i class="integrityLabel"> <img
								src="../../../img/caidai.png">
						</i>
							<div class="li-icon">优选</div>
							<div class="li-img">
								<a href="/group/${item.gi.id}" target="_blank"><img src="${item.gi.picture}"
									alt="团体封面" class="hezi-img-img"></a>
							</div>
							<div class="li-content">
								<h1>
									<a href="/group/${item.gi.id}" class="biaotia" target="_blank">${item.gi.groupName}</a>
								</h1>
								<p>行业：${item.gi.industry.industryName}</p>
								<p>地址：${item.gi.region.regionName}</p>
								<p class="group-in">介绍：${item.gi.introduce}</p>
							</div></li>
					</c:forEach>
				</ul>
				<hr style="border-color: #aaa;">
				<h1 class="groupInfo-no-result"
					style="text-align:center;margin:50px;display:none;">暂时没有任何结果哦！</h1>
				<ul class="search-option-bottom-bottom groupInfo-ul group-info" id="group-info">
					<c:forEach items="${groupInfoList}" var="item">
						<li>
							<div class="li-img">
								<a href="/group/${item.gi.id}" target="_blank"><img src="${item.gi.picture}"
									alt="团体封面" class="hezi-img-img"></a>
							</div>
							<div class="li-content">
								<h1>
									<a href="/group/${item.gi.id}" class="biaotia" target="_blank">${item.gi.groupName}</a>
								</h1>
								<p>行业：${item.gi.industry.industryName}</p>
								<p>地址：${item.gi.region.regionName}</p>
								<p>介绍：${item.gi.introduce}</p>
							</div>
						</li>
					</c:forEach>
				</ul>
				<div id="paging-group" style="text-align: center;margin-top: 30px;clear: both;"></div>
				<!-- <div class="page-change">
					<ul class="pagination pagination-lg">
						<li class="page-last"><a>&laquo;</a></li>
					</ul>
					<ul class="pagination pagination-lg" data-count-group></ul>
					<ul class="pagination pagination-lg">
						<li class="page-next-group"><a>&raquo;</a></li>
					</ul>
				</div> -->
			</div>
		</div>


		<!-- 企业信息展示 -->
		<div class="search-option-bottom" id="searchEnterprise"
			style="display: none;">

			<div class="channel-screening-frame">
				<div class="screening-button-box" style="margin-top: 45px;">
					<p class="screening-button screeningSwitch">
						筛选<i class="icon-screen"></i>
					</p>
				</div>
				<div class="channel-screening filterBox">
					<!-- 地区 -->
					<div class="screening-input-top">
						<div class="docs-methods">
							<p style=" position: relative;left: 39px;">所在地：</p>
							<div class="region-mask">查询中...</div>
							<select class="region-1"></select> <select class="region-2"></select>
							<select class="region-3" name="regionCode"
								id="regioncode-enterprise"></select>
						</div>
					</div>

					<!-- 行业 -->
					<div class="screening-input-top">
						<div class="docs-methods">
							<table class="industry_table">
								<tr>
									<td class="left">行业:</td>
									<td class="right" id="tab">
										<div class="region-mask">查询中...</div> <select
										class="industry-1"></select> <select class="industry-2"></select>
										<select id="industryType-code-enterprise" class="industry-3"
										name="industryCode"></select>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="channel-screening-push-button">
						<button type="button" class="enterprise-filters-btn">确定</button>
						<button class="screeningCancelButton">取消</button>
					</div>
				</div>
			</div>

			<ul class="search-option-bottom-top">
				<c:if test="${empty enterprisePreferredList}">
					<div class="notData">暂时没有查询到数据哦！</div>
				</c:if>
				<c:forEach items="${enterprisePreferredList}" var="item">
					<li><i class="integrityLabel"> <img
							src="../../../img/caidai.png">
					</i>
						<div class="li-icon">优选</div>
						<div class="li-img">
							<c:choose>
								<c:when
									test="${item.ei.website != null && item.ei.website != '#' && item.ei.website != '' }">
									<a href="${item.ei.website}" class="biaotia" target="_blank"><img
										src="${item.ei.logo}" alt="暂未上传图片"></a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.ei.id}" class="biaotia" target="_blank"><img
										src="${item.ei.logo}" alt="暂未上传图片"></a>
								</c:otherwise>
							</c:choose>

						</div>
						<div class="li-content">
							<h1>
								<c:choose>
									<c:when
										test="${item.ei.website != null && item.ei.website != '#' && item.ei.website != '' }">
										<a href="${item.ei.website}" class="biaotia">${item.ei.enterpriseName}</a>
									</c:when>
									<c:otherwise>
										<a href="/by/enterprise?id=${item.ei.id}" class="biaotia">${item.ei.enterpriseName}</a>
									</c:otherwise>
								</c:choose>


							</h1>
							<p>行业：${item.industryType.industryName}</p>
							<p>地址：${item.ei.region.regionName}</p>
							<p>介绍：${item.ei.introduce}</p>
						</div></li>
				</c:forEach>
			</ul>
			<hr style="border-color: #aaa;">
			<h1 class="enterprise-no-result"
				style="text-align:center;margin:50px; display: none;display:none;">暂时没有任何结果哦！</h1>
			<ul class="search-option-bottom-bottom enterprise-ul enterprise-info" id="enterprise-info">
				<c:forEach items="${enterpriseList}" var="item">
					<li>
						<div class="li-img">
							<c:choose>
								<c:when test="${item.ei.website != null && item.ei.website != '#' && item.ei.website != '' }">
									<a href="${item.ei.website}" class="biaotia" target="_blank"><img
										src="${item.ei.logo}" alt="暂未上传图片"></a>
								</c:when>
								<c:otherwise>
									<a href="/by/enterprise?id=${item.ei.id}" class="biaotia" target="_blank"><img
										src="${item.ei.logo}" alt="暂未上传图片"></a>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="li-content">
							<h1>
								<c:choose>
									<c:when
										test="${item.ei.website != null && item.ei.website != '#' && item.ei.website != '' }">
										<a href="${item.ei.website}" class="biaotia" target="_blank">${item.ei.enterpriseName}</a>
									</c:when>
									<c:otherwise>
										<a href="/by/enterprise?id=${item.ei.id}" class="biaotia" target="_blank">${item.ei.enterpriseName}</a>
									</c:otherwise>
								</c:choose>
							</h1>
							<p>介绍：${item.ei.introduce}</p>
							<p>行业：${item.industryType.industryName}</p>
							<p>地址：${item.ei.region.regionName}</p>
						</div>
					</li>
				</c:forEach>
			</ul>

			<div id="paging-enterprise"
				style="text-align: center;margin-top: 30px;clear: both;"></div>
			<!-- <div class="page-change">

				<ul class="pagination pagination-lg">
					<li class="page-last-enterprise"><a>&laquo;</a></li>
				</ul>
				<ul class="pagination pagination-lg" data-count-enterprise></ul>
				<ul class="pagination pagination-lg">
					<li class="page-next-enterprise"><a>&raquo;</a></li>
				</ul>
			</div> -->
		</div>

		<!-- 需求信息展示 -->
		<div class="search-option-bottom" id="searchDemand"
			style="display: none;">
			<div class="channel-screening-frame">
				<div class="screening-button-box" style="margin-top: 45px;">
					<p class="screening-button screeningSwitch">
						筛选<i class="icon-screen"></i>
					</p>
				</div>
				<div class="channel-screening filterBox">
					<!-- 地区 -->
					<div class="screening-input-top">
						<div class="docs-methods">
							<p style=" position: relative;left: 39px;">所在地：</p>
							<div class="region-mask">查询中...</div>
							<select class="region-1"></select> <select class="region-2"></select>
							<select class="region-3" name="regionCode" id="regioncode-demand"></select>
						</div>
					</div>



					<div class="screening-input-bottom">
						<div class="bottom-centent">
							<span class="region_span" style="position: relative;left: 35px;">酬劳：</span>
							<input type="hidden" name="beginReward" value=""> <input
								type="hidden" name="endReward" value="">
							<ul class="reward_ul">
								<li class="reward">0-200</li>
								<li class="reward">201-400</li>
								<li class="reward">401-600</li>
								<li class="reward">601-800</li>
								<li class="reward">801-1000</li>
								<li class="reward other"><span>其他</span> <input type="text"
									class="s-reward"> <span>—</span> <input type="text"
									class="e-reward">元</li>
							</ul>
						</div>
					</div>

					<div class="screening-input-middle">
						<div class="bottom-centent">
							<input type="hidden" name="beginReleaseTime" value=""> <input
								type="hidden" name="endReleaseTime" value=""> <span
								class="region_span" style="position: relative;left: 35px;">发布时间：</span>
							<ul class="release-time">
								<li class="releaseTime"><input type="text" id="field_1"
									name="beginReleaseTime" class="s-releaseTime"
									placeholder="开始时间" class="laydate-icon"
									onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									<span>—</span> <input type="text" id="field_1"
									name="endReleaseTime" class="e-releaseTime" placeholder="结束时间"
									class="laydate-icon"
									onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
								</li>
							</ul>
						</div>
					</div>
					<div class="channel-screening-push-button">
						<button type="button" class="demand-filters-btn">确定</button>
						<button class="screeningCancelButton">取消</button>
					</div>
				</div>
			</div>
			<ul class="search-option-bottom-top">
				<c:if test="${empty demandPreferredList}">
					<div class="notData">暂时没有查询到数据哦！</div>
				</c:if>
				<c:forEach items="${demandPreferredList}" var="item">
					<li><i class="integrityLabel"> <img
							src="../../../img/caidai.png">
					</i>
						<div class="li-icon">优选</div>
						<div class="li-img">
							<a href="/one/demand?demandId=${item.di.id}" target="_blank"> <img
								src="${item.di.picture}" alt="暂未发布头像" class="hezi-img-img">
							</a>
						</div>
						<div class="li-content">
							<h1>
								<a href="/one/demand?demandId=${item.di.id}" target="_blank">${item.di.content}</a>
							</h1>
							<p>发布人员：${item.ui.nickName}</p>
							<p>地址:${item.di.address}</p>
							<p>
								发布时间：
								<fmt:formatDate value="${item.di.releaseTime}" type="both" />
							</p>
						</div></li>
				</c:forEach>
			</ul>
			<hr style="border-color: #aaa;">
			<h1 class="demand-no-result"
				style="text-align:center;margin:50px;display:none;">暂时没有任何结果哦！</h1>
			<ul class="search-option-bottom-bottom demand-ul">

				<c:forEach items="${demandInfoList}" var="item">
					<li>
						<div class="li-img">
							<a href="/one/demand?demandId=${item.id}" target="_blank"><img
								src="${item.picture}" alt="暂未发布头像" class="hezi-img-img"></a>
						</div>
						<div class="li-content">
							<h1>
								<a href="/one/demand?demandId=${item.id}" target="_blank">${item.content}</a>
							</h1>
							<p>发布人员：${item.userInfo.nickName}</p>
							<p>地址:${item.address}</p>
							<p>
								发布时间：
								<fmt:formatDate value="${item.releaseTime}" type="both" />
							</p>
						</div>
					</li>
				</c:forEach>
			</ul>
			<div id="paging-demand" style="text-align: center;margin-top: 30px;clear: both;"></div>
			<!-- <div class="page-change">
				<ul class="pagination pagination-lg">
					<li class="page-last-demand"><a>&laquo;</a></li>
				</ul>
				<ul class="pagination pagination-lg" data-count-demand></ul>
				<ul class="pagination pagination-lg">
					<li class="page-next-demand"><a>&raquo;</a></li>
				</ul>
			</div> -->
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script>
	var regionCode = "${userInfo.region.regionCode}";
	var industryCode = "${industry.industryTypeCode}";
	var jobCode = "${userInfo.jobInfo.jobName}";

	$(document).ready(function() {

		var MaxInputs = 8;
		var InputsWrapper = $("#InputsWrapper");
		var AddButton = $("#AddMoreFileBox");

		var x = InputsWrapper.length;
		var FieldCount = 1;

		$(AddButton).click(function(e) {
			if (x <= MaxInputs) {
				FieldCount++;
				/* $(InputsWrapper).append('<div><input type="text" id="field_' + FieldCount + '" /><input type="text" id="field_' + FieldCount + '" /><a href="#" class="removeclass">×</a></div>');  */
				$(InputsWrapper).append('<div>' +
				"<input type='text' id='field_' + " + FieldCount + " + '' name='startTime' class='addTime' placeholder='开始时间' class='laydate-icon' onClick=\"laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})\" /><td>--</td><input type='text' id='field_' + " + FieldCount + " + '' name='endTime' class='addTime' placeholder='结束时间' class='laydate-icon' onClick=\"laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})\" /><a href='#' class='removeclass'>×</a>",
					+'</div>');
				x++;
			}
			return false;
		});

		$("body").on("click", ".removeclass", function(e) {
			if (x > 1) {
				$(this).parent('div').remove();
				x--;
			}
			return false;
		})

	});
</script>
<!-- <script>
	// 查询关键字
	var searchKeyword = "${s}";
</script> -->
<script src="/lib/swiper/swiper.jquery.min.js"></script>
<script src="/js/real-estate.js"></script>
<script src="/js/city-picker.data.js"></script>
<script src="/js/city-picker.js"></script>
<script src="/js/main.js"></script>
<script src="/js/industry.js"></script>
<script src="/js/jobInfo.js"></script>
<script src="/js/regions.js"></script>
<script src="/js/paging-enterprise.js"></script>
<script src="/js/paging-group.js"></script>
<script src="/js/pageing-demand.js"></script>
<script src="/lib/timeJs/laydate.js"></script>
<script src="/js/journalism.js"></script>
</html>