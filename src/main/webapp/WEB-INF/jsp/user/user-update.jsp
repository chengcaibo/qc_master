<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>用户修改页面</title>
<link rel="stylesheet" type="text/css" href="css/xinxi.css">
<link rel="stylesheet" type="text/css" href="css/userInfo.css">
</head>
<body>
	<jsp:include page="/header" />

	<div class="bigbox">
		<form action="/updateUserInfo" method="POST">
			<table border="1">
				<tr>
					<th>修改信息</th>
				</tr>
				<tr>
					<td class="left">*昵称:</td>
					<td class="right"><input type="text" id="nick_name" name="nickName" value="${userInfo.nickName }" />
						<div class="red NameWrong" id="NameWrong">昵称有误</div></td>
				</tr>

				<tr>
					<td class="left">*真实姓名:</td>
					<td class="right"><input type="text" id="real_name" name="realName" value="${userInfo.realName }" />
						<div class="red NameWrong" id="NameWrong">姓名有误！</div></td>
				</tr>

				<tr class="LOGO">
					<td>头像：</td>
					<td id="file-logo-parent"><label for="file-logo"> <img src="" title="点击上传头像" class="LOGO-img" />
					</label> <a href="###" class="LOGO-photo" style="display: none;"> <input id="file-logo" type="file" class="LOGO-form"
							name="avatar" value="${userInfo.avatar }" />
					</a> <!--<div><p>LOGO</p></div>--></td>
				</tr>

				<tr>
					<td class="left">*身份证号码:</td>
					<td class="right"><input type="text" id="identity" name="identity" value="${userInfo.identity }" />
						<div class="red Identity" id="Identity">身份证有误</div></td>
				</tr>

				<tr>
					<td class="left">*电话:</td>
					<td class="right"><input type="text" id="phone" name="telephone" value="${userInfo.telephone }" />
						<div class="red iPhone" id="iPhone">电话有误</div></td>
				</tr>


				<tr>
					<td>性别：</td>
					<td><input type="radio" name="gender.id" <c:if test="${userInfo.gender.id==1}">checked="checked"</c:if> />保密&nbsp;
						<input type="radio" name="gender.id" <c:if test="${userInfo.gender.id==2}">checked="checked"</c:if> />男&nbsp; <input
						type="radio" name="gender.id" <c:if test="${userInfo.gender.id==3}">checked="checked"</c:if> />女</td>
					<%-- <c:if test="${userInfo.gender.id=='1'}">checked="checked"</c:if> --%>
				</tr>

				<tr>
					<td class="left">*生日:</td>
					<td class="right"><input type="text" id="birthday" name="birthday" value="${userInfo.birthday }" />
						<div class="red iPhone" id="birthday">生日</div></td>
				</tr>

				<tr>
					<td class="left">*时薪:</td>
					<td class="right"><input type="text" name="hourlyWage" value="${userInfo.hourlyWage }" /></td>
				</tr>


				<tr>
					<td class="left">*评分数:</td>
					<td class="right"><input type="text" name="score" value="${userInfo.score }" /></td>
				</tr>

				<tr>
					<td class="left">*余额:</td>
					<td class="right righty"><input type="text" class="money" name="balance" value="${userInfo.balance }" />
						<button class="recharge">充值</button>
						<button class="CashWithdrawal">提现</button></td>
				</tr>

				<tr>
					<td class="left">*邮箱:</td>
					<td class="right"><input type="text" id="email" name="email" value="${userInfo.email }" />
						<div class="red passWord" id="passWord">邮箱出错</div></td>
				</tr>

				<tr>
					<td class="left">*地区号:</td>
					<td class="right right2"><select name="regionCode" class="Select">
							<option value="${userInfo.ragionCode }">${userInfo.ragionCode }</option>
					</select></td>
				</tr>

				<tr>
					<td class="left">*所属地区:</td>
					<td class="right right2"><input type="text" name="address" value="${userInfo.address }"></td>
				</tr>

				<!-- <tr>
					<td class="left">*资质:</td>
					<td class="right right2">
						<select name="" class="Select">
							<option value="">--   高级   --</option>
							<option value="">--   中级   --</option>
							<option value="">--   初级   --</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="left">*预约时间:</td>
					<td>
						<div id = "money">
							<input type="text" class="leftInput leftInputOne"/>
							<p class="leftP">--</p>
							<input type="text" class="leftInput leftInputTwo"/>
						</div>
						
						<button class="leftJia" onclick="increase()" >+</button>
						<button class="leftJia leftJian" onclick="reducess()">-</button>
					</td>
					
				</tr> -->
				<tr>
					<td colspan="2"><input type="submit" value="修改"></td>
				</tr>
			</table>
		</form>


		<div class="Sselect" id="zong">
			<p class="SselectBanner" onclick="selectBanner()" id="anniu1">广告</p>
			<p class="SselectGroup" onclick="selectGroupt()" id="anniu2">团体</p>
			<p class="SselectAbout" onclick="selectAbouty()" id="anniu3">预约</p>
		</div>

		<div class="synthesise">

			<ul class="banner show" id="Banner">
				<li class="bannerOne">广告</li>
				<li class="bannerTwo">广告</li>
				<li class="bannerThree">广告</li>
				<li class="bannerFour">广告</li>
			</ul>

			<ul class="group" id="Group">
				<li class="groupOne">团体</li>
				<li class="groupTwo">团体</li>
				<li class="groupThree">团体</li>
			</ul>

			<div class="about" id="About">


				<div class="aboutBottom">
					<h1 class="aboutTop">
						<span class="aboutP">预约记录</span> <a href="###" class="aboutA">更多&gt;&gt;</a>
					</h1>


					<ul class="aboutUl">
						<li class="aboutUlH1">预约</li>
						<li class="aboutUlLi">
							<div class="aboutUlLiName">
								<div class="aboutUlLiLeft">
									<p class="aboutUlLiNameP">姓名:</p>
									<span class="aboutUlLiNameInput">张三</span>
								</div>
								<div class="aboutUlLiRight">
									<p class="aboutUlLiRightP">预约对象:</p>
									<span class="aboutUlLiRightInput">张三</span>
								</div>
							</div>
							<div class="aboutUlLiName aboutUlLiNameTwo">
								<p class="aboutUlLiNameP">时间:</p>
								<span class="aboutUlLiNameInput">2017年11月2日</span>
								<p class="aboutUlLiRightP  member">员工编号:</p>
								<span class="aboutUlLiRightInput">0-11-12</span>
							</div>
						</li>

						<li class="aboutUlLi">
							<div class="aboutUlLiName">
								<div class="aboutUlLiLeft">
									<p class="aboutUlLiNameP">姓名:</p>
									<span class="aboutUlLiNameInput">张三</span>
								</div>
								<div class="aboutUlLiRight">
									<p class="aboutUlLiRightP">预约对象:</p>
									<span class="aboutUlLiRightInput">李四</span>
								</div>

							</div>
							<div class="aboutUlLiName aboutUlLiNameTwo">
								<p class="aboutUlLiNameP">时间:</p>
								<span class="aboutUlLiNameInput">2017年11月2日</span>
								<p class="aboutUlLiRightP  member">员工编号:</p>
								<span class="aboutUlLiRightInput">0-11-12</span>

							</div>
						</li>


					</ul>

					<ul class="aboutUl aboutUlR">
						<li class="aboutUlH1">被预约</li>
						<li class="aboutUlLi">
							<div class="aboutUlLiName">
								<div class="aboutUlLiLeft">
									<p class="aboutUlLiNameP">姓名:</p>
									<span class="aboutUlLiNameInput">张三</span>
								</div>
								<div class="aboutUlLiRight">
									<p class="aboutUlLiRightP">预约对象:</p>
									<span class="aboutUlLiRightInput">张三</span>
								</div>
							</div>
							<div class="aboutUlLiName aboutUlLiNameTwo">
								<p class="aboutUlLiNameP">时间:</p>
								<span class="aboutUlLiNameInput">2017年11月2日</span>
								<p class="aboutUlLiRightP  member">员工编号:</p>
								<span class="aboutUlLiRightInput">0-11-12</span>
							</div>
						</li>

						<li class="aboutUlLi">
							<div class="aboutUlLiName">
								<div class="aboutUlLiLeft">
									<p class="aboutUlLiNameP">姓名:</p>
									<span class="aboutUlLiNameInput">张三</span>
								</div>
								<div class="aboutUlLiRight">
									<p class="aboutUlLiRightP">预约对象:</p>
									<span class="aboutUlLiRightInput">李四</span>
								</div>

							</div>
							<div class="aboutUlLiName aboutUlLiNameTwo">
								<p class="aboutUlLiNameP">时间:</p>
								<span class="aboutUlLiNameInput">2017年11月2日</span>
								<p class="aboutUlLiRightP  member">员工编号:</p>
								<span class="aboutUlLiRightInput">0-11-12</span>

							</div>
						</li>
					</ul>

				</div>

			</div>
		</div>
		<button class="submit" onclick="GerenTijiao()">提交</button>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script src="js/xinxijs.js" type="text/javascript"></script>
</html>