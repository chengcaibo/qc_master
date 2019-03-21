<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<meta charset="UTF-8">
<title>帮助中心</title>
<jsp:include page="../common/head.jsp" />
<link rel="stylesheet" href="/css/footer-info/help-center.css">
</head>
  <body>
    <jsp:include page="/header" />
    <div class="help-center-box">
    	<div class="use-guide" id="use-guide">
    		<h3>使用指南</h3>
    		<ul class="choose-ul">
				<li class="small-program-use">小程序使用指南</li>
				<li class="computer-use">PC电脑端使用指南</li>
			</ul>
			
			<div class="small-program-box" id="small-program-box">
				<p class="small-p">
					打开微信<span>→</span>发现<span>→</span>小程序<span>→</span>搜 "奇虫"<span>→</span>打开预览
				</p>
				<img alt="小程序" src="/img/how-register/small.jpg">
				<ul>
					<li>安卓机点击右上角 ,另存为桌面</li>
					<li>苹果机点右上角,显示在聊天顶部</li>
					<li class="info-title">属于自己的CEO</li>
					<li class="process-info">
						<p class="small-p">
							注册<span>→</span>发布<span>→</span>预约<span>→</span>解决
						</p>
					</li>
				</ul>
			</div>
			<div class="computer-box" id="computer-box">
			<p class="computer-p">
				打开PC端首页(<a href="http://www.qc1318.com" target="_blank">www.qc1318.com</a>)<span>→</span>注册<span>→</span>登录<span>→</span>浏览
			</p>
			<img alt="奇虫首页" src="/img/how-register/personal-1.png">
			<img alt="奇虫注册" src="/img/how-register/personal-2.png" class="register-img">
			<p class="computer-p">
				预约<span>→</span>解决
			</p>
			<img alt="时间" src="/img/how-register/time.png">
			<img alt="成功" src="/img/how-register/success.png">
			</div>
    	</div>
    	
    	<div class="qc-service">
    		<h3>奇虫服务</h3>
    		<div class="layui-tab layui-tab-card">
			  <ul class="layui-tab-title" style="background: #000;color:#fff;">
			    <li class="layui-this">用户审核</li>
			    <li>用户违规处理</li>
			    <li>奇虫平台对接流程</li>
			  </ul>
			  <div class="layui-tab-content" style="height: auto;line-height: 30px;">
			    <div class="layui-tab-item layui-show">
			    <b>人工审核</b><br>
			            为确保用户在奇虫平台提交的信息安全可靠，奇虫平台成立了人工审核部门，制定严格的审核规范，第一时间对平台提交信息进行审核认证。针对个人、团体、企业内容、需求发布、响应、线上时间预约等，每一个信息细节都实施监控并严格把关;<br>
			    <b>审核内容</b><br>
			            包含了头像、昵称、个人简介、职业技能、荣誉资质、服务案例、需求详情、相册图片、身份/技能认证资料等信息;<br>
			    </div>
			    <div class="layui-tab-item">
			  	 奇虫平台提供了“个人中心”模块，提供了详细用户使用《奇虫规则》，其中包括：《使用协议》、《常见问题》《审核规范》等;<br>
				对于违规预约对接操作不正当及不诚信对接等行为，《奇虫》为用户提供举报、不诚信黑名单及信息反馈等板块，提倡用户及时举报不良行为，维护自身合法权益及良好的信息共享空间;<br>
				<b>违规账号处理</b><br>
				<b>同时《奇虫》将根据违规情节大小给予如下处理方式</b><br>
				<b>（1）</b>禁用：平台有违反《奇虫平台审核规范》的行为，但情节不严重用户，根据《奇虫》审核确认后，禁用时间为10天，连续禁用两次会自动封号;<br>
				<b>（2）</b>封号：平台有违反《奇虫平台审核规范》的操作行为且情节严重用户，平台或将给予永久封号及黑名单处理，不退还任何费用;<br>
				<b>违法行为处理</b><br>
				 《奇虫》一旦发现用户有违法行为，将全力配合执法部门，按照相关要求进行处理;<br>
			    </div>
			    <div class="layui-tab-item">
			   	<b>（1）</b>用户相互直接电话沟通对接功能：为保障用户交易安全和信息真实可靠性，平台提供电话预约对接模块，对接双方用户面对面。鼓励双方合作前进行事项沟通，确认信息、安全无误后确认合作对接服务内容;<br>
				<b>（2）</b>《奇虫》平台提供在线聊一聊功能：为保障买卖双方合法权益，平台提供在线聊天预约对接模块，对接双方用户面对面沟通。鼓励双方合作前进行事项沟通，确认信息、安全无误后确认完成合作对接;<br>
				<b>（3）</b>《奇虫》平台提供在线响应功能：为保障对接双方可选择权益，平台提供在线响应预约对接模块，对接双方用户面对面沟通。鼓励双方合作前进行事项沟通，确认信息、安全无误后完成合作对接;<br>
				<b>（4）</b>《奇虫》平台为在平台上预约进行合作对接的注册用户提供服务，因《奇虫》平台不涉及所有注册用户方相互之间的相关费用来往。若因用户之间某方违约给用户带来损失，服务双方出现纠纷，《奇虫》平台不承担任何责任。由用户自行负责。用户可以向平台进行不诚信举报，亦可向所在地依法提请仲裁;<br>
				<b>（5）</b>平台的管控 ： 《奇虫》用户信息对接后合作支付流程全部在线下双方或多方自行协商合作完成，是在奇虫平台之外的交易行为，产生任何问题，奇虫平台无法管控，责任由用户自行承担;<br>
				<b>（6）</b>风险提示：奇虫提示线下任何涉及金钱的交易动作，用户应意识到服务风险和预见性风险警示;<br>
				<b>（7）</b>投诉纠纷处理：奇虫平台用户对线下合作对接服务有争议的，平台收到举报投诉后根据双方举证材料（沟通记录、图像材料、及可证明类材料）及官方出具违规通知类的，将给予不诚信黑名单用户或永久性关闭用户处理; <br>
			    
			    </div>
			  </div>
			</div>
    	</div>
    	
    	<div class="common-problem" id="common-problem">
    		<h3>常见问题</h3>
    		<div class="layui-tab layui-tab-card">
			  <ul class="layui-tab-title" style="background: #000;color:#fff;">
			    <li class="layui-this">审核</li>
			    <li>认证</li>
			  </ul>
			  <div class="layui-tab-content" style="height: auto;line-height: 30px;">
			    <div class="layui-tab-item layui-show" >
			  		<b>上传的信息多久审核通过？</b><br>
					用户自上传信息之后起24小时内审核通过。如果时间已到还没有审核通过kefu@奇虫318.com联系我们。<br>
					<b>什么样的头像更容易审核通过？</b><br>
					必须是本人正面的，露出清晰五官的图片。 <br>
			    </div>
			    <div class="layui-tab-item">
			    	<b>注册时如何进行身份认证？</b><br>
					1.登录奇虫平台后，点击注册进入填写身份认证。<br>
					2.在身份证号码提示框内输入真实有效的本人身份证号码。<br>
					3.分别上传清晰的手持证件头部照、身份证正面照、点击提交认证。<br>
					注：(1)用户认证后，身份证号等相关个人隐私信息不会显示在平台上。<br>
					(2)无大陆身份证的用户可以通过护照或者港澳台身份证进行认证哦。<br>
					<b>为什么需要身份认证？</b> <br>
					技能发布 与 需求发布 <br>
					1.身份认证对平台注册用户的诚信度，增强需求者信息对接选择合作的参考依据，提升合作对接成功率。<br>
					2. 必须要进行身份认证才可以使用奇虫平台发布功能，秒抢平台上优越展示位置。<br>
					3.身份认证后用户的诚信度高，信息排名可能会靠前，增强曝光度。<br>
					4.为确保平台枢纽对接安全，身份认证后才可以对接业务合作。<br>
					<b>技能认证、上传资质与案例</b><br>
					1.登录奇虫平台后，点击个人中心。<br>
					2.在证书名称提示框内输入要上传的资质名称与案例图示。<br>
					3.上传证书图片，提交完成技能认证。<br>
					4.最多上传4个资质证书，4个案例图示 。<br>
					<b>如何修改自己的认证信息？</b><br>
					认证信息不能自行修改，为了保证信息的真实有效性，平台仅提供一次审核提交机会，经过平台审核后显示在个人主页。<br>
					<b>身份认证被驳回怎么办？</b><br>
					身份认证被驳回，按照要求重新申请。在提交认证信息时，提交前请核对身份号是否输入正确，个人头像和身份证头像是否一致，上传的手持身份证照片是否清晰。具体可点击“查看示例”进行相应修改。<br>
			    </div>
			  </div>
			</div>
    	</div>
    	<div class="contact" id="contact">
    		<h3>联系客服</h3>
    		<div class="contact-content">
    			<img alt="客服" src="/img/how-register/customer-service.png">
    			<div class="contact-mode">
	    			<ul>
	    				<li>客服热线：010 - 86005888</li>
	    				<li>客服邮箱：qc@qc1318.com</li>
	    				<li>客服Q Q：1585158453</li>
	    			</ul>
					<ul>
						<li><h4>服务时间</h4></li>
						<li>工作日：09:00 — 17:00</li>
						<li>节假日：09:00 — 22:00</li>
					</ul>
    			</div>
    		</div>
    	</div>
    </div>
    <jsp:include page="../common/footer.jsp" />
  </body>
  <script src="/js/footer-info/help-center.js"></script>
</html>
