<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<meta charset="UTF-8">
<title>奇虫首页</title>
<jsp:include page="common/head.jsp" />
<link rel="stylesheet" href="/css/index/index-new.css">
<link rel="stylesheet" href="/lib/swiper/swiper.min.css">
<link rel="stylesheet" href="/css/index/index-load-more-adpublic.css">
</head>
<body>
	<div>
		<jsp:include page="/header" />
		<div class="banner">
			<!-- <img src="/img/home/back-01.jpg" alt="" class="banner-img">
			<img src="/img/home/back-02.jpg" alt="" class="banner-img">
			<img src="/img/home/back-03.jpg" alt="" class="banner-img">
			<img src="/img/home/back-04.jpg" alt="" class="banner-img"> -->
			<img src="/img/home/back-qc-1.jpg" alt="" class="banner-img">
		</div>
		<!--我要发布-->
		<div class="nav nav-change">
			<ul class="nav-item nav-list nav-change-ul">
				<li class="nav-change-ul-li nav-change-ul-li-one">告别朝九晚五，做自己的CEO</li>
				<li class="line"></li>
				<li class="nav-change-ul-li nav-change-ul-li-two"><a
					href="/requirements" title="发布信息">我要发布</a>
					<ul class="nav-item-child nav-item-child-release">
						<li><a href="/requirements" title="">需求发布</a></li>
						<!-- <li><a href="/center" title="">发布个人</a></li>
						<li><a href="/center" title="">发布企业</a></li> -->
						<li><a href="/submit/group" title="">团体发布</a></li>
						<li><a href="/jumpAdpublic" title="">广告信息发布</a></li>
					</ul>
				<li class="line"></li>
				<li class="nav-change-ul-li nav-change-ul-li-three"><a
					href="/journalism?s=">我要预约</a></li>
				<li class="line"></li>
				<li class="nav-change-ul-li nav-change-ul-li-four">共享你的品牌</li>
			</ul>
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
						<li><a href="/journalism?s=生活"><i
								class="icon-shenghuobaihuo"></i>生活</a></li>
						<li><a href="/journalism?s=物流"><i class="icon-wuliu"></i>物流</a></li>
						<li><a href="/journalism?s=互联网"><i
								class="icon-hulianwang"></i>互联网</a></li>
						<li><a href="/journalism?s=金融"><i class="icon-3"></i>金融</a></li>
						<li><a href="/journalism?s=房产"><i class="icon-fangchan"></i>房产</a></li>
						<li><a href="/journalism?s=建筑"><i
								class="icon-jianzhucailiao"></i>建筑</a></li>
						<li><a href="/journalism?s=制造"><i
								class="icon-mittensclothes"></i>制造</a></li>
						<li><a href="/journalism?s=销售"><i class="icon-xiaoshou"></i>销售</a></li>
						<li id="btnTop">其他<i class="icon-xia"></i></li>
					</ul>
					<ul class="secondary-navigation-ul secondary-navigation-ul-two">
						<li><a href="/journalism?s=教育"><i class="icon-jiaoyu1"></i>教育</a></li>
						<li><a href="/journalism?s=户外"><i class="icon-huwai"></i>户外</a></li>
						<li><a href="/journalism?s=电子"><i
								class="icon-dianziweilan"></i>电子</a></li>
						<li><a href="/journalism?s=电商"><i class="icon-dianshang1"></i>电商</a></li>
						<li><a href="/journalism?s=管理"><i class="icon-guanli"></i>管理</a></li>
						<li><a href="/journalism?s=人力"><i
								class="icon-renliziyuanzhutifenxi"></i>人力</a></li>
						<li><a href="/journalism?s=娱乐"><i class="icon-yulebao"></i>娱乐</a></li>
						<li><a href="/journalism?s=农业"><i class="icon-nongye"></i>农业</a></li>
						<li id="btnBottom">其他<i class="icon-you"></i></li>
					</ul>
				</div>

				<div class="number-people">
					已注册用户数<span id="userCount"></span>
				</div>
			</div>
		</div>


		<!--优选-->
		<div class="optimization">
			<div class="optimization-top">
				<div class="optimization-title">— &nbsp;&nbsp;个人共享&nbsp;&nbsp;—<a href="/journalism?s=#personal-info" class="more-info user-more">更多</a></div>
				<div class="optimization-title">— &nbsp;&nbsp;团队共享&nbsp;&nbsp;—<a href="/journalism?s=#group-info" class="more-info group-more">更多</a></div>
				<div class="optimization-title">— &nbsp;&nbsp;企业共享&nbsp;&nbsp;—<a href="/journalism?s=#enterprise-info" class="more-info enterprise-more">更多</a></div>
			</div>
			<div class="optimization-bottom">
				<ul class="optimization-content" id="user-thirteen">
				</ul>
				<ul class="optimization-content" id="group-thirteen">
				</ul>
				<ul class="optimization-content" id="enterprise-thirteen">
				</ul>
			</div>
		</div>


		<!--置顶-->
		<ul class="placed-top">
			<li id="personal-top">
				<div class="placed-top-img">
					<a id="img-a" target="_blank"><img src="" alt="个人图片" id="user-img"></a>
				</div>
				<div class="placed-top-right">
					<h1 class="placed-top-labelling">个人置顶</h1>
					<p class="placed-top-content personal-content" id="user-content">
						<a id="user-a"></a>
					</p>
					<a href="/userPosition?locationId=1" class="seconds-kill-prompt" target="_blank">我也要展示</a>
					<p class="down-time" id="person-top-down-time">还有29天 00时 37分
						00.15秒</p>
					<!-- <p class="success-includes-content-embedded" id="timeScope">
						<script type="text/javascript">
							leftTimer(new Date('<fmt:formatDate value="${topList.get(0).endTime}" type="both" />'));
						</script>
					</p> -->
				</div>
			</li>
			<li>
				<div class="placed-top-img">
					<a id="group-img-a" target="_blank"><img src="" alt="个人图片" id="group-img"></a>
				</div>
				<div class="placed-top-right">
					<h1 class="placed-top-labelling">团队介绍</h1>
					<p class="placed-top-content" id="group-content">
						<a id="group-content"></a>
					</p>
				</div>
			</li>
			<li class="no-margin" id="enterprise-top">
				<div class="placed-top-img">
					<a id="enterprise-img-a" target="_blank"> <img src="" alt="企业logo"
						id="enterprise-img"></a>
				</div>
				<div class="placed-top-right">
					<h1 class="placed-top-labelling">企业置顶</h1>
					<p class="placed-top-content enterprise-content">
						<a id="enterprise-content" class="enterprise-info-content" target="_blank"></a>
					</p>
					<a href="locationId?locationId=2" target="_blank"
						class="seconds-kill-prompt">我也要展示</a> <a class="reservation">预定</a>
					<p class="down-time" id="ent-top-down-time">还有29天 00时 37分
						00.15秒</p>
				</div>
			</li>
		</ul>
		<!--广告-->
		<div class="guanggao">
			<img src="/images/henglan.gif" alt="">
		</div>

		<!--小广告-->
		<div class="advertising">
			<div class="optimization-top advertising-title">
				<div class="optimization-title">
					— &nbsp;&nbsp;个人广告信息&nbsp;&nbsp; — <a href="/moreAdpublic">更多</a>
				</div>
				<div class="optimization-title">
					— &nbsp;&nbsp;需求广告信息&nbsp;&nbsp; — <a href="/journalism?s=">更多</a>
				</div>
				<div class="optimization-title">
					— &nbsp;&nbsp;企业广告信息&nbsp;&nbsp; — <a
						href="/moreAdpublic—enterprise">更多</a>
				</div>
			</div>
			<div class="advertising-personal">
				<div
					class="advertising-personal-layer advertising-personal-layer-one">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
				<div
					class="advertising-personal-layer advertising-personal-layer-two">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
				<div
					class="advertising-personal-layer advertising-personal-layer-three"
					style="display: none;">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
			</div>
			<div class="advertising-personal">
				<div
					class="advertising-personal-layer advertising-personal-layer-demand-one">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
				<div
					class="advertising-personal-layer advertising-personal-layer-demand-two">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
				<div
					class="advertising-personal-layer advertising-personal-layer-demand-three"
					style="display: none;">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
			</div>

			<div class="advertising-personal">

				<div
					class="advertising-personal-layer advertising-personal-layer-enterprise-one">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
				<div
					class="advertising-personal-layer advertising-personal-layer-enterprise-two">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
				</div>
				<div
					class="advertising-personal-layer advertising-personal-layer-enterprise-three"
					style="display: none;">
					<div class="advertising-box">
						<div class="advertising-img common-img-hover-bigger-2">
							<img src="" alt="">
						</div>
						<div class="advertising-content">
							<p>个人需求内容</p>
							<p>个人详细信息</p>
							<p>电话：888888888</p>
							<p>发布时间：2018-1-1</p>
							<a href="#">详情...</a>
						</div>
					</div>
					<div class="advertising-box">
						<img src="" alt="">
					</div>
					<div class="advertising-content">
						<p>个人需求内容</p>
						<p>个人详细信息</p>
						<p>电话：888888888</p>
						<p>发布时间：2018-1-1</p>
						<a href="#">详情...</a>
					</div>
				</div>
			</div>
		</div>

		<!-- 加载更多广告 -->
		<div class="load-more-adpublic">
			<div class="load-info">
				<!-- 个人广告信息 -->
				<div class="personal-adpublic personal-adpublic-info"></div>
				<!-- 需求广告 -->
				<div class="demand-adpublic demand-adpublic-info"></div>
				<!-- 企业广告信息 -->
				<div class="enterprise-adpublic enterprise-adpublic-info"></div>
			</div>
			<!-- 点击加载 -->
			<div class="load-more">
				<h3>加载更多</h3>
			</div>
			<div class="load-info-again" style="display: none;">
				<!-- 个人广告信息 -->
				<div class="personal-adpublic personal-adpublic-again"></div>
				<!-- 需求广告 -->
				<div class="demand-adpublic demandMore"></div>
				<!-- 企业广告信息 -->
				<div class="enterprise-adpublic enterpriseMore"></div>
			</div>
		</div>
		<div class="qc-adpublic">
			<i class="qc-icon icon-close close-adpublic"></i> <img
				src="/images/img1.jpg">
		</div>
	</div>
	<jsp:include page="common/footer.jsp" />
</body>
<script src="/js/position.js"></script>
<script src="/js/index/Marquee.js"></script>
<script src="/js/index/index.js"></script>
<script src="/js/index/index-load-more-adpublic.js"></script>
</html>