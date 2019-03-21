<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />
<meta charset="UTF-8">
<title>发布需求</title>
<link rel="stylesheet" href="/css/demand/release.css">
<link rel="stylesheet" href="/css/search/city-picker.css">

</head>
<body>
	<jsp:include page="/header" />
	<form action="/insertDemandInfo" method="post" enctype="multipart/form-data">
		<div class="frame">
			<h1>发布我的需求</h1>
			<div class="frame-bottom">
				<div class="textarea-box">
					<textarea id="content" maxlength="150"
						placeholder="请输入您的需求,例如：我需要一个懂算法，且懂架构的工程师。俩月" name="content"></textarea>
					<p class="contentEmpty">需求内容不能为空</p>
					<p class="sensitive-words">需求内容有敏感词</p>
				</div>
				<div id="file-headPortrait-parent">
					<label for="file-headPortrait"> <i></i> <img
						src="../../../images/picture-bg.jpg" title="点击上传图片"
						class="headPortrait-img" />
					</label> <a href="#" class="headPortrait-photo" style="display: none;">
						<input id="file-headPortrait" type="file" name="demandInfoFile"
						class="headPortrait-form" />
					</a>
				</div>

				<div class="reward">
					<input type="text" placeholder="请输入酬劳(元)" id="reward" name="reward"
						class="salary">
					<span>——</span>
					<input type="text" placeholder="请输入结束酬劳(元)" id="endReward" name="endReward"
					class="salary"> 
					<span class="choose-ok">
						<input id="isMeeting" type="checkbox" name="meeting" class="checkbox">
						<label for="isMeeting">可面议</label>
					</span>
					<p class="rewardEmpty" id="rewardEmpty">酬劳填写错误</p>
				</div>

				<div class="reward">
					<input type="text" placeholder="请填写备注信息 &nbsp;&nbsp; 例如：最好会吹口琴"
						id="note" name="note">
					<p class="note" style="display:none">备注信息填写错误</p>
				</div>
				<div class="reward">
					<input type="text" placeholder="请输入联系人&nbsp;&nbsp; 例如：王二"
						id="contact" name="contact">
					<p class="contact" style="display:none">联系人填写错误</p>
				</div>

				<div class="phone">
					<input type="text" placeholder="请输入电话号&nbsp;&nbsp; 例如：158xxxx0000"
						id="phone" name="phone">
					<p class="phoneEmpty">电话号填写错误</p>
				</div>
				<!--<div class="contact">
                <input type="text" placeholder="请输入电话号" id="contact" name="contact">
                <p class="phoneEmpty">联系人填写错误</p>
            </div>-->
				<div class="address">
					<div id="distpicker">
						<div class="form-group">
							<div style="position: relative; border: 1px solid #ccc;">
								<input id="city-picker3" class="form-control" readonly
									type="text" placeholder="省 / 市 / 区" id="address" name="address"
									data-toggle="city-picker">
							</div>
						</div>
					</div>
					<p class="addressEmpty">地址不能为空</p>
				</div>
				<div class="phone">
					<input type="text"
						placeholder="详细地址&nbsp;&nbsp; 例如：北京市朝阳区A路A楼5单元208"
						id="detailedAddress" name="detailedAddress">
					<p class="detailedAddress-p">详细地址填写错误</p>
				</div>

				<button id="reset" type="reset">重置</button>
				<button type="submit" name="releaseRequirementsButton">发布</button>
			</div>
		</div>
	</form>


	<jsp:include page="../common/footer.jsp" />
</body>
<script src="/js/demand/release.js"></script>
<script src="/js/city-picker.data.js"></script>
<script src="/js/city-picker.js"></script>
<script>
 $("#isMeeting").attr("checked",false)
</script>
</html>