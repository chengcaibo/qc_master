<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="common/head.jsp" />

<title>奇虫首页</title>
<link rel="stylesheet" href="/css/index/index.css">
<link rel="stylesheet" href="/lib/swiper/swiper.min.css">
</head>
<body>
	<jsp:include page="/header" />
	<!--banner-->
	<!-- banner-Swiper -->
	<div class="banner">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="images/carousel1.png" alt="">
				</div>
				<div class="swiper-slide">
					<img src="images/carousel2.png" alt="">
				</div>
				<div class="swiper-slide">
					<img src="images/carousel3.png" alt="">
				</div>
			</div>
			<!-- Add Pagination -->
			<div class="swiper-pagination"></div>
			<!-- Add Arrows -->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
		</div>
	</div>


	<!--二级导航-->
	<div class="secondary-navigation">
		<div class="secondary-navigation">
			<div class="secondary-navigation-industry">
				<div class="secondary-navigation-industry-left">
					<i class="icon-hangye"></i>行业：
				</div>
				<div class="secondary-navigation-industry-right"></div>
			</div>
			<div class="Industry-choose">
				<ul class="secondary-navigation-ul vanish">
					<li><a href="journalism?s=生活">生活</a></li>
					<li><a href="journalism?s=物流">物流</a></li>
					<li><a href="journalism?s=互联网">互联网</a></li>
					<li><a href="journalism?s=金融">金融</a></li>
					<li><a href="journalism?s=房产">房产</a></li>
					<li><a href="journalism?s=建筑">建筑</a></li>
					<li><a href="journalism?s=制造">制造</a></li>
					<li><a href="journalism?s=销售">销售</a></li>
					<li id="btnTop">其他<i class="icon-xia"></i></li>
				</ul>
				<ul class="secondary-navigation-ul secondary-navigation-ul-two">
					<li><a href="journalism?s=教育">教育</a></li>
					<li><a href="journalism?s=户外">户外</a></li>
					<li><a href="journalism?s=电子">电子</a></li>
					<li><a href="journalism?s=电商">电商</a></li>
					<li><a href="journalism?s=管理">管理</a></li>
					<li><a href="journalism?s=人力">人力</a></li>
					<li><a href="journalism?s=娱乐">娱乐</a></li>
					<li><a href="journalism?s=农业">农业</a></li>
					<li id="btnBottom">其他<i class="icon-you"></i></li>
				</ul>
			</div>

			<div class="number-people">
				已注册人数<span id="userCount"></span>人
			</div>
		</div>
	</div>

	<!--置顶-->
	<ul class="placed-top">
		<li class="top_li">
			<div class="person-imgs"></div>
			<div class="placed-top-right">
				<h1 class="placed-top-labelling">个人置顶</h1>
				<div class="person_div">
					<ul class="person_content"></ul>
					<p class="show_there"><a href="/userPosition?locationId=1" target="_blank">我也要展示到这里</a></p>
				</div>
			</div>
		</li>
		<li class="top_li">
			<div class="placed-top-img">
				<img style="width: 147px;height: 167px;" src="images/05.png" id="group_img">
			</div>
			<div class="placed-top-right">
				<h1 class="placed-top-labelling">团队介绍</h1>
				<div class="group_div">
					<div>
						<a href="" id="group-name">稍等一哈...我偷个懒儿(*^▽^*)</a>
					</div>
					<div id="group-industryName"></div>
					<div id="group-number"></div>
					<div id="group-tool-cost"></div>
					<div id="group-personnel-cost"></div>
					<div id="group-regionName"></div>
					<div id="group-intrudce" class="intrudce_spot"></div>
				</div>
			</div>
		</li>
		<li class="no-margin">
			<div class="enterprise-img"></div>
			<div class="placed-top-right">
				<h1 class="placed-top-labelling">企业置顶</h1>
				<div class="enterprise_div">
					<ul class="enterprise_content"></ul>
					<p class="show_there_en"><a href="/position" target="_blank">我也要展示到这里</a></p>
				</div>
			</div>
		</li>
	</ul>

	<!--优选-->
	<!-- <div class="navigation">
		<ul>
			<li class="personalShare">个人共享</li>
			<li class="enterpriseShare">企业共享</li>
		</ul>
	</div> -->
	
	<div class="advertisement-content">
		<div class="psersonal-content">
			<span class="psersonal-span">个人共享</span>
			<div class="person-shadown">
				<div class="psersonal-left" id="marquee6"></div>
				<div class="person_five"></div>
			</div>
		</div>
		<div class="enterprise-content">
			<span class="enterprise-span">企业共享</span>
			<div class="enterprise-right">
				<div class="enterprise_three"></div>
				<div class="enterprise_six"></div>
				<div class="enterprise_ten"></div>
			</div>
		</div>
	</div>
	
	
<!-- 	<div class="optimization">
		<div class="optimization-one">
			<div id="marquee6"></div>
		</div>
		<div class="person_five"></div>
		<div class="enterprise_three"></div>
		<div class="enterprise_six"></div>
		<div class="enterprise_ten"></div>
	</div>
 -->
	<!--广告-->
	<div class="advertising">
		<div class="advertising-content">
			<div class="advertising-content-img">
				<!-- Swiper -->
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<div class="ad-img"><img id="adpublic_img" src="images/df7923a0fd2a1ec7f0eb77def5fb871d.jpg" alt=""></div>
							<div class="demand-div">
								<p id="p_text"></p>
								<p id="address_text"></p>
								<p id="content_text"></p>
							</div>
							<a href="/slectAllAdPublic">更多</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="advertising-content">
			<div class="advertising-content-img">
				<!-- Swiper -->
				<div class="swiper-container">
					<div class="swiper-slide">
						<div class="ad-img"><img id="demand-img" src="images/df7923a0fd2a1ec7f0eb77def5fb871d.jpg" alt=""></div>
						<div class="demand-div">
							<p id="demand-nickname"></p>
							<p id="demand-releaseTime"></p>
							<!-- <p id="demand-reward"></p> -->
							<p id="demand-content"></p>
							<p id="demand-address"></p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="advertising-content">
			<div class="advertising-content-img">
				<!-- Swiper -->
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<div class="swiper-slide">
								<div class="ad-img"><img id="enterprise_img" src="images/df7923a0fd2a1ec7f0eb77def5fb871d.jpg" alt=""></div>
								<div class="demand-div">
									<p id="enterpriseName"></p>
									<p id="industryName"></p>
									<p id="address"></p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="advertising-content-roasting">
			<img src="images/07.jpg" alt="">
		</div>
	</div>
	<jsp:include page="common/footer.jsp" />
</body>
<script src="/js/old/index.js"></script>
<script src="/lib/swiper/swiper.jquery.min.js"></script>
<script src="/js/Marquee.js"></script>
</html>