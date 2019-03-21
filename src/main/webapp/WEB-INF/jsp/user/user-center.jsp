<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>个人中心</title>
<link rel="stylesheet" href="/css/user/center.css">
<link rel="stylesheet" href="/css/user/personal-center-new.css">
<link rel="stylesheet" href="/css/user/basic-information.css">
<link rel="stylesheet" href="/css/user/my-advantage.css">
<link rel="stylesheet" href="/css/user/comprehensive-skills.css">
<link rel="stylesheet" href="/css/user/success-stories.css">
<link rel="stylesheet" href="/css/user/reservation-record.css">
<link rel="stylesheet" href="/css/user/team-recorded.css">
<link rel="stylesheet" href="/css/user/advertising-record.css">
<link rel="stylesheet" href="/css/user/release-time.css">
<link rel="stylesheet" href="/css/user/my-attention.css">
<link rel="stylesheet" href="/css/user/head-portrait.css">
<link rel="stylesheet" href="/css/user/change-password.css">
<link rel="stylesheet" href="/css/lq.datetimepick.css">
<link rel="stylesheet" href="/css/bootstrap-fileinput.css">
<link rel="stylesheet" href="/css/user/identity-documents.css">
<link rel="stylesheet" href="/css/user/demand-record.css">
<link rel="stylesheet" href="/css/user/starTwo.css">
</head>
<body id="temp">
	<jsp:include page="/header" />
	<!--个人中心-->
	<div class="personal" id="personal-dd">
		<div class="return-icon-error" id="icon-div-error"
			style="display: none;">
			<i class="return-close-error icon-close" id="close-div-error"></i> <i
				class="return-error icon-shangxin"></i>
			<h3 class="suc">数据不可以添加三条哦</h3>
			<span><a href="/">返回首页>></a></span>
		</div>
		<div class="return-icon" id="icon-div" style="display: none;">
			<i class="return-close icon-close" id="close-div"></i> <i
				class="return-success icon-success"></i>
			<h3 class="suc">恭喜您,保存成功</h3>
			<span><a onclick="window.location.reload()">确定</a></span>
		</div>
		<ul class="personal-button">
			<li><a href="#local1"><i class="icon-jibenxinxi">&nbsp;&nbsp;&nbsp;基本信息</i></a></li>
			<li><a href="#local11"><i class="icon-icon-test">&nbsp;&nbsp;&nbsp;实名认证</i></a></li>
			<li><a href="#local12"><i class="icon-photo">&nbsp;&nbsp;&nbsp;我的头像</i></a></li>
			<li><a href="#local2"><i class="icon-jineng">&nbsp;&nbsp;&nbsp;综合技能</i></a></li>
			<li><a href="#local10"><i class="icon-nickname">&nbsp;&nbsp;&nbsp;我的资质</i></a></li>
			<li><a href="#local3"><i class="icon-youshi">&nbsp;&nbsp;&nbsp;我的优势</i></a></li>
			<li><a href="#local4"><i class="icon-anli"><span>&nbsp;&nbsp;&nbsp;成功案例</span></i></a></li>
			<li><a href="#local5"><i class="icon-yuyue1">&nbsp;&nbsp;&nbsp;预约记录</i></a></li>
			<li><a href="#local13"><i class="icon-fabu1">&nbsp;&nbsp;&nbsp;发布时间</i></a></li>
			<li><a href="#local6"><i class="icon-tuandui">&nbsp;&nbsp;&nbsp;团队记录</i></a></li>
			<li><a href="#local7"><i
					class="icon-jinlingyingcaiwangtubiao86">&nbsp;&nbsp;&nbsp;广告记录</i></a></li>
			<li><a href="#local14"><i class="icon-fabuxuqiu">&nbsp;&nbsp;&nbsp;需求记录</i></a></li>
			<li><a href="#local8"><i class="icon-guanzhu">&nbsp;&nbsp;&nbsp;我的关注</i></a></li>
			<li><a href="#local9"><i class="icon-buchongiconsvg06">&nbsp;&nbsp;&nbsp;修改密码</i></a></li>
		</ul>
		<div class="personal-page">

			<form action="/center/update" method="post" class="basic-form">
				<!--基本信息-->
				<ul class="basic-information basic" style="display: none"
					id="basicInformation">
					<li style="margin-bottom: 30px;"><div class="basic-info">
							<h3>基本信息</h3>
						</div></li>
					<li>
						<p>用户名：</p> <c:if test='${userInfo.user.username.indexOf("default") != -1}'>
							<p class="user-name">
								未设置 <a id="changeUsername" href="javascript:;" title="点击更改用户名">点击设置</a>
							</p>
						</c:if> <c:if test='${userInfo.user.username.indexOf("default") == -1}'>
							<p class="user-name">${userInfo.user.username}</p>
						</c:if>
					</li>
					<li>
						<p>昵称：</p> <input type="text" name="nickName"
						value="${userInfo.nickName}" id="basicNickname">
						<p class="nick-name" id="sensitive-words">昵称有敏感词</p>
					</li>

					<li class="gender">
						<p>性别：</p>
						<div>
							<p>
								<input type="radio" name="gender.id" value="1"
									<c:if test="${userInfo.gender.id==1}">checked</c:if> />保密
							</p>
						</div>
						<div>
							<p>
								<input type="radio" name="gender.id" value="2"
									<c:if test="${userInfo.gender.id==2}">checked</c:if> />男
							</p>
						</div>
						<div>
							<p>
								<input type="radio" name="gender.id" value="3"
									<c:if test="${userInfo.gender.id==3}">checked</c:if> />女
							</p>
						</div>
					</li>

					<li>
						<p>生日：</p>
						<div class="form-group float-left w140"
							style="display: inline-block">
							<input style="background: #fff; " type="text" name="birthday"
								id="datetimepicker3" class="form-control" readonly="readonly"
								value="<fmt:formatDate value="${userInfo.birthday}"/>" />
						</div>
					</li>

					<li>
						<p>工作开始时间：</p> <input type="text" id="basicWorkAge"
						name="jobBeginTime" value="${userInfo.jobBeginTime}">
						<p class='nickname-empty'></p>
					</li>
					<li>
						<p>时薪：</p>
						<c:if test="${userInfo.hourlyWage == -1.0}">
							<input type="text" name="hourlyWage" value="0">
							<p class='nickname-empty'></p>
						</c:if>
						<c:if test="${userInfo.hourlyWage != -1.0}">
							<input type="text" name="hourlyWage" value="${userInfo.hourlyWage}">
						</c:if>
						<p class='nickname-empty'></p>
					</li>
					<li>
						<p style="position: absolute;left: 0;">介绍：</p> <textarea
							style="margin: 10px 115px;width: 466px;height: 89px; resize:none; color: #565656;"
							name="introduce" class="textarea-introduce" id="introduce">${userInfo.introduce}</textarea>
						<p class="introduce-prompt" id="sensitive-words">内容有敏感词</p>
					</li>
					<li class="scoring">
						<p>评分数：</p> <c:if test='${userInfo.score == -1.0}'>
							<p>暂无评分</p>
						</c:if> <c:if test='${userInfo.score != -1.0}'>
							<p>${userInfo.score}分</p>
						</c:if>
					</li>
					<li>
						<p>职业：</p> <!-- <div class="region-mask">查询中...</div>  --> 
						<select class="job-1"></select> 
						<select class="job-2"></select>
						<select class="job-3" name="jobInfo.jobCode"></select>
					</li>
					<li>
						<p>所在地：</p> <!-- <div class="region-mask">查询中...</div>  --> <select
						class="region-1" name="joiningProvince"></select> <select
						class="region-2" name="joiningCity"></select> <select
						class="region-3" name="region.regionCode"></select>
					</li>
					<li>
						<p>详细地址：</p> <input type="text" id="basicDetail" name="address"
						value="${userInfo.address}">
						<p class="address-prompt" id="sensitive-words">详细地址敏感词</p>
					</li>
					<li class="preservation">
						<button type="reset">重置</button>
						<button type="submit" name="basicInformationSave">保存</button>
					</li>
				</ul>

			</form>

			<!--实名认证-->
			<div class="identity-documents" id="identityDocuments"
				style="display: none">
				<h3>实名认证</h3>
				<c:if test="${certificationInfo == null}">
					<form action="/audit/realname/upload_pc" method="post"
						enctype="multipart/form-data">
						<ul class="basic-information identity-card">
							<li><input type="hidden" value="${certificationInfo.id}"
								name="fullId"> <!-- <p>真实姓名：</p>
						<p class="change-name">蔡国栋</p> -->
								<p>真实姓名：</p> <input type="text" name="realName"></li>

							<li>
								<p class="id-card-number">身份证号：</p> <input type="text"
								id="basicIdentity" name="identity" value="">
							</li>

							<li>
								<p>身份证正面照：</p>
								<div id="file-headPortrait-parent" class="iDcard-box">
									<label for="file-headPortrait"> <i></i> <img src=""
										title="点击上传图片" class="headPortrait-img iDcard-imges1">
									</label> <a href="#" class="headPortrait-photo" style="display: none;">
										<input id="file-headPortrait" name="image_aFile" type="file"
										class="headPortrait-form" value="">
									</a>
								</div>
							</li>

							<li>
								<p>身份证反面照：</p>
								<div id="file-headPortrait-parent2" class="iDcard-box">
									<label for="file-headPortrait2"> <i></i> <img src=""
										title="点击上传图片" class="headPortrait-img2 iDcard-imges2">
									</label> <a href="#" class="headPortrait-photo" style="display: none;">
										<input id="file-headPortrait2" name="image_bFile" type="file"
										class="headPortrait-form" value="">
									</a>
								</div>
							</li>

							<li>
								<p>手持正面照：</p>
								<div id="file-headPortrait-parent3" class="iDcard-box">
									<label for="file-headPortrait3"> <i></i> <img src=""
										title="点击上传图片" class="headPortrait-img3 iDcard-imges3">
									</label> <a href="#" class="headPortrait-photo" style="display: none;">
										<input id="file-headPortrait3" name="image_cFile" type="file"
										class="headPortrait-form" value="">
									</a>
								</div>
							</li>


							<li class="preservation">
								<button type="reset">重置</button>
								<button type="submit" name="save-identity">上传</button>
							</li>
						</ul>
					</form>
				</c:if>
				<c:if test="${certificationInfo.state.id == 6}">
					<form action="/audit/realname/upload_pc" method="post"
						enctype="multipart/form-data">
						<ul class="basic-information identity-card">
							<li><input type="hidden" value="${certificationInfo.id}"
								name="fullId"> <!-- <p>真实姓名：</p>
						<p class="change-name">蔡国栋</p> -->
								<p>真实姓名：</p> <input type="text" name="realName"></li>

							<li>
								<p class="id-card-number">身份证号：</p> <input type="text"
								id="basicIdentity" name="identity" value="">
							</li>

							<li>
								<p>身份证正面照：</p>
								<div id="file-headPortrait-parent" class="iDcard-box">
									<label for="file-headPortrait"> <i></i> <img src=""
										title="点击上传图片" class="headPortrait-img iDcard-imges1">
									</label> <a href="#" class="headPortrait-photo" style="display: none;">
										<input id="file-headPortrait" name="image_aFile" type="file"
										class="headPortrait-form" value="">
									</a>
								</div>
							</li>

							<li>
								<p>身份证反面照：</p>
								<div id="file-headPortrait-parent2" class="iDcard-box">
									<label for="file-headPortrait2"> <i></i> <img src=""
										title="点击上传图片" class="headPortrait-img2 iDcard-imges2">
									</label> <a href="#" class="headPortrait-photo" style="display: none;">
										<input id="file-headPortrait2" name="image_bFile" type="file"
										class="headPortrait-form" value="">
									</a>
								</div>
							</li>

							<li>
								<p>手持正面照：</p>
								<div id="file-headPortrait-parent3" class="iDcard-box">
									<label for="file-headPortrait3"> <i></i> <img src=""
										title="点击上传图片" class="headPortrait-img3 iDcard-imges3">
									</label> <a href="#" class="headPortrait-photo" style="display: none;">
										<input id="file-headPortrait3" name="image_cFile" type="file"
										class="headPortrait-form" value="">
									</a>
								</div>
							</li>


							<li class="preservation">
								<button type="reset">重置</button>
								<button type="submit" name="save-identity">上传</button>
							</li>
						</ul>
					</form>
				</c:if>
				<c:if test="${certificationInfo.state.id == 5}">
					<h1 class="qcicon-h1">
						<i class="qc-icon icon-gerenrenzheng"></i>
					</h1>
					<h1 class="examine-h1">审核通过</h1>
					<h4 class="time-item">
						通过时间：
						<fmt:formatDate type="both" value="${certificationInfo.editTime}" />
					</h4>
				</c:if>
				<c:if test="${certificationInfo.state.id == 4}">
					<h1 class="qcicon-h1">
						<i class="qc-icon icon-success"></i>
					</h1>
					<h1 class="examine-h1">待审核</h1>
					<h4 class="time-item">
						提交审核时间：
						<fmt:formatDate type="both"
							value="${certificationInfo.createTime}" />
					</h4>
				</c:if>

			</div>

			<!--我的头像-->
			<div class="head-portrait" id="head-portrait" style="display: none">
				<h3>我的头像</h3>
				<ul>
					<li>
						<div class="head-show">
							<img src="${userInfo.avatar}">
							<p>200 * 200像素</p>
						</div>
						<div class="head-show-three">
							<img src="${userInfo.avatar}">
							<p>250 * 250像素</p>
						</div>
					</li>
					<li style="padding: 0; border: 0;">
						<!-- <p>头像：</p> -->
						<div id="file-headPortrait-parent" class="head-info">
							<img id="up-img-touch" src="${userInfo.avatar}" title="点击修改头像"
								class="img-avatar">
							<p id="up-img-p">点击修改头像</p>
						</div>
					</li>
				</ul>
			</div>
			<!--综合技能-->
			<div class="comprehensive-skills" id="comprehensiveSkills">

				<div class="skill-info-user">
					<h4>我的技能</h4>
					<h3 style="text-align: center;display: none;margin: 80px auto;">你还暂时没有综合技能呢，赶紧去添加一个吧！</h3>
					<ul class="skills-label">
						<c:forEach items="${skills}" var="item">
							<li><a href="javascript:;" data-id="${item.id}">×</a>${item.skillName}</li>
						</c:forEach>
					</ul>
					<ul class="comprehensive-skills-top"></ul>
					<div class="push-button">
						<button id="addSkills">添加一个技能</button>
						<button id="sub-skillInfo">保存</button>
					</div>
				</div>
			</div>
			<!--我的资质-->
			<div class="my-qualification" id="myQualification" style="display: none;">
				<!-- 添加资质 -->
				<div class="qualifications-info add-ia common-hide">
					<i class="close-iaInfo icon-close" id="close-ia"></i>
					<h4>添加资质</h4>
					<form method="post" enctype="multipart/form-data" id="form-ia">
						<ul>
							<li><span>资质名称：</span> <input type="text" name="text"
								placeholder="请输入资质名称"></li>
							<li>
								<div class="page-header">
									<div class="form-group" id="uploadForm">
										<div class="fileinput fileinput-new" data-provides="fileinput"
											id="exampleInputUpload">
											<span class="case-picture">项目图片：</span>
											<div class="fileinput-new thumbnail">
												<img id='picImg' class="img-group" alt=""
													style="height: 90px;" />
											</div>
											<div class="fileinput-preview fileinput-exists thumbnail"></div>
											<div class="pic">
												<span class="btn btn-primary btn-file"> <span
													class="fileinput-new">选择项目图片</span> <span
													class="fileinput-exists">重新选择</span> <input type="file"
													name="aptitudeFile" id="picIDb"
													accept="image/gif,image/jpeg,image/x-png" />
												</span>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li>
								<button type="reset">重置</button>
								<button type="button" id="ia-btn">保存</button>
							</li>
						</ul>
					</form>
				</div>

				<!-- 修改资质 -->
				<c:forEach items="${industryAptitudeList}" var="item"
					varStatus="status">
					<div data-id="${item.id}"
						class="qualifications-info modify-ia common-hide">
						<i class="close-iaInfo icon-close" id="close-ia"></i>
						<h4>修改资质</h4>
						<form data-index="${status.index}" id="ia-form-mol" method="post"
							enctype="multipart/form-data">
							<ul>
								<li>
								<input type="hidden" name="id" value="${item.id}">
								<input type="hidden" name="pictureName" value="${item.picture.name}">
									<span>资质名称：</span> <input type="text" name="text"
									value="${item.text}" placeholder="请输入资质名称"></li>
								<li>
									<div class="page-header">
										<div class="form-group" id="uploadForm">
											<div class="fileinput fileinput-new"
												data-provides="fileinput" id="exampleInputUpload">
												<span class="case-picture">项目图片：</span>
												<div class="fileinput-new thumbnail">
													<img id='picImg' class="img-group" src="${item.picture}"
														alt="" style="height: 90px;" />
												</div>
												<div class="fileinput-preview fileinput-exists thumbnail"></div>
												<div class="pic">
													<span class="btn btn-primary btn-file"> <span
														class="fileinput-new">选择项目图片</span> <span
														class="fileinput-exists">重新选择</span> <input type="file"
														name="aptitudeFile" id="picID"
														accept="image/gif,image/jpeg,image/x-png" />
													</span>
												</div>
											</div>
										</div>
									</div>
								</li>
								<li>
									<button type="reset">重置</button>
									<button type="button" name="ia-add-ok"
										data-index="${status.index}">保存</button>
								</li>
							</ul>
						</form>
					</div>
				</c:forEach>

				<div class="qualification-content">
					<h4>我的资质</h4>
					<div class="add-iaInfo-btn">
						<i id="add-to-ia" class="iaInfo-btn icon-fabu"><span>添加一个资质</span></i>
					</div>
					<hr>
					<div class="qf-con">
						<ul class="ia-content-info-ul">
							<c:forEach items="${industryAptitudeList}" var="item">
								<li class="ia-content-info">
									<div class="qf-img">
										<img src="${item.picture}"> <span class="ia-con-span">资质称谓：${item.text}</span>
										<span class="ia-con-span">姓名：${item.ui.realName}</span>
										<div class="ia-a">
											<a href="javascript:;" data-id="${item.id}" class="ia-mol">编辑</a>
											<a href="javascript:;" data-id="${item.id}" data-name="${item.picture.name}" class="ia-del">删除</a>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>

			</div>
			<!--我的优势-->
			<ul class="my-advantage" id="myAdvantage">
				<li class="my-advantage-top">
					<h3>优势</h3>
					<form action="/modifyascendancy" method="post">
						<textarea placeholder="请输入..." id="InputBox" name="ascendancy"
							maxlength="1000" style="background:#fff; border: 1px solid #ccc;"></textarea>
						<p>
							<span class="limit">0</span>/1000字以内
						</p>
						<p class="advantage" id="advantage-sensitive">详细地址敏感词</p>
						<div class="my-advantage-top-button">
							<button id="advantageOk">确认</button>
							<button type="button" id="advantageError">取消</button>
						</div>
					</form>
				</li>
				<li class="my-advantage-top my-advantage-bottom">
					<h3>优势</h3> <!-- <h1>我的优势</h1> --> <textarea placeholder="请输入..."
						id="receive" maxlength="200" name="ascendancy" disabled="disabled"
						style="background:#fff">${userInfo.ascendancy}</textarea>
					<button id="advantageCancel">编辑</button>
				</li>
				<li class="my-advantage-middle" style="margin-bottom:25px">
					<h1>示例</h1>
					<p>
						1、本人责任心强，责任心是工作的动力，是创造力。有了责任心才会有完成工作任务的信心，几年来我一直坚持原则，按时、按质、按量完成工作任务。<br>
						2、本人思维缜密，工作认真仔细。TPM
						工作比较繁杂，涉及的方面比较多，在有强烈责任感的同时还需要一颗在全神贯注的心，专心致志方能得心应手。<br>
						3、本人领悟能力较强，有执行力。能准确的领会上级的指示精神，并且不折不扣的执行上级分配的任务。无论多么宏伟的蓝图，多么正确的决策，多么严
						谨的计划，如果没有高效的执行，最终的结果都是纸上谈兵。 <br>
					</p>
				</li>
			</ul>

			<!--成功案例-->
			<div class="success-stories" id="successStories">
				<div class="add-caseInfo-div" id="add-case-div"
					style="display: none;">
					<form method="post" enctype="multipart/form-data" id="form-case">
						<h3>添加案例</h3>
						<i class="close-caseInfo icon-close" id="close-case"></i>
						<ul>
							<li><span class="case-name">案例名称：</span> <span><input
									type="text" name="caseName" value="" placeholder="请输入案例名称"></span>
								<p class="empty-prompt">案例名称不能为空</p>
								<p class="empty-error">案例名称填写错误</p></li>
							<li>
								<div class="page-header">
									<div class="form-group" id="uploadForm">
										<div class="fileinput fileinput-new" data-provides="fileinput"
											id="exampleInputUpload">
											<span class="case-name">项目图片：</span>
											<div class="fileinput-new thumbnail">
												<img id='picImg' class="img-group" src="" alt=""
													style="height: 90px;" />
											</div>
											<div class="fileinput-preview fileinput-exists thumbnail"></div>
											<div class="pic">
												<span class="btn btn-primary btn-file"> <span
													class="fileinput-new">选择项目图片</span> <span
													class="fileinput-exists">重新选择</span> <input type="file"
													name="caseFile" id="picIDa"
													accept="image/gif,image/jpeg,image/x-png" />
												</span>
											</div>
										</div>
									</div>
								</div>
							</li>
							<li><span class="case-name">客户姓名：</span> <span><input
									type="text" name="clientName" value="" placeholder="请输入客户姓名"></span>
								<p class="empty-prompt">姓名不能为空</p>
								<p class="text-restriction">姓名最多10个字节</p>
								<p class="empty-error">姓名填写错误</p></li>
							<li><span class="case-name">开始时间：</span> <input type="text"
								name="startTime" readonly="readonly" class="addTime startTimer"
								placeholder="开始时间" class="laydate-icon"
								onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
							</li>
							<li><span class="case-name">结束时间：</span> <input type="text"
								name="endTime" readonly="readonly" class="addTime endTimer"
								placeholder="结束时间" class="laydate-icon"
								onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
							</li>
							<li><span class="case-name">客户电话：</span> <span><input
									type="text" name="clientTelephone" value=""
									placeholder="请输入客户电话"></span>
								<p class="empty-prompt">电话不能为空</p>
								<p class="empty-error">电话填写错误</p></li>
							<li><span class="case-name">详情地址：</span> <span><input
									type="text" name="clientAddress" value="" placeholder="请输入详情地址"></span>
								<p class="empty-prompt">详情地址不能为空</p>
								<p class="empty-error">详情地址填写错误</p>
								<p class="text-restriction">详情最多50个字节</p></li>
							<li><span class="case-textarea-name">项目介绍：</span> <span>
									<textarea class="case-textarea" name="caseContent"
										placeholder="请输入...." maxlength="200"></textarea>
							</span>
								<p class="empty-prompt">项目介绍不能为空</p>
								<p class="empty-error">项目介绍填写错误</p></li>
							<li>
								<button class="preservation-info" id="pi-caseInfo" type="button"
									name="button-preservation">保存信息</button>
							</li>
						</ul>
					</form>
				</div>
				<div class="add-caseInfo-btn">
					<i id="add-to-case" class="caseInfo-btn icon-fabu"><span>添加一个案例</span></i>
					<!-- <button type="button" ></button> -->
				</div>
				<h1>成功案例</h1>
				<c:forEach items="${caseList}" var="item" varStatus="status">
					<div class="case">
						<div class="according-to">
							<div class="case-img">
								<img src="${item.picture}" alt="示例图片"
									style=" height: 100px;width: 100px; ">
							</div>
							<div class="case-content">
								<div class="case-content-headings">
									<p class="case-content-headings-left">${item.caseName}</p>
									<p class="case-content-headings-right">
										<span><fmt:formatDate type="date"
												value="${item.startTime}" /></span> <span>-</span> <span><fmt:formatDate
												type="date" value="${item.endTime}" /></span>
									</p>
								</div>
								<p class="case-content-content">${item.caseContent}</p>
								<div class="case-content-edit">
									<button name="edit-btn">编辑</button>
									<span>|</span> <a href="javascript:;" data-id="${item.id}">
										<button type="button" class="del-caseInfo">删除</button>
									</a>
								</div>
							</div>
						</div>
						<div class="case-add">
							<form data-uk="${status.index}" enctype="multipart/form-data">
								<input type="hidden" name="id" value="${item.id}">
								<div class="page-header">
									<div class="form-group" id="uploadForm">
										<div class="fileinput fileinput-new" data-provides="fileinput"
											id="exampleInputUpload">
											<span class="case-picture">项目图片：</span>
											<div class="fileinput-new thumbnail">
												<img id='picImg' class="img-group" src="${item.picture}"
													alt="" style="height: 90px;" />
											</div>
											<div class="fileinput-preview fileinput-exists thumbnail"></div>
											<div class="pic">
												<span class="btn btn-primary btn-file"> <span
													class="fileinput-new">选择项目图片</span> <span
													class="fileinput-exists">重新选择</span> <input type="file"
													name="caseFile" id="picIDd"
													accept="image/gif,image/jpeg,image/x-png" />
												</span>
											</div>
										</div>
									</div>
								</div>

								<ul class="case-add-fill">
									<li class="caseInfoName">
										<p>项目名称：</p> <input name="caseName" value="${item.caseName}"
										type="text" placeholder="xxx系统">
										<p id="case-sensitive-word" class="project-name">内容有敏感词</p>
									</li>
									<li>
										<p>客户名称：</p> <input type="text" name="clientName"
										value="${item.clientName}" placeholder="姓名">
										<p id="case-sensitive-word" class="customer-name">内容有敏感词</p>
									</li>
									<li>
										<p>开始时间：</p> <input
										value="<fmt:formatDate type="date" value="${item.startTime}" />"
										type="text" id="field_1_a" name="startTime"
										class="addTime startTimer" placeholder="开始时间"
										class="laydate-icon"
										onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									</li>
									<li>
										<p>结束时间：</p> <input
										value="<fmt:formatDate type="date" value="${item.endTime}" />"
										type="text" id="field_1_b" name="endTime"
										class="addTime endTimer" placeholder="结束时间"
										class="laydate-icon"
										onClick="laydate({istime: true, format: 'YYYY-MM-DD'})">
									</li>
									<li>
										<p>客户电话：</p> <input type="text" name="clientTelephone"
										value="${item.clientTelephone}" placeholder="电话">
									</li>
									<li>
										<p>地 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</p> <input
										name="clientAddress" value="${item.clientAddress}" type="text"
										placeholder="地址">
										<p id="case-sensitive-word" class="successful-address">内容有敏感词</p>
									</li>
								</ul>

								<div class="case-introduced">
									<p>案例介绍</p>

									<textarea class="inputting" maxlength="250" name="caseContent">${item.caseContent}</textarea>
									<p id="sensitive" class="case-introduced-sensitive">内容有敏感词</p>
								</div>
								<div class="case-add-button">
									<button type="button" name="case-add-cancel">取消</button>
									<button type="button" data-uk="${status.index}"
										name="case-add-ok" class="caseInfo-sub">保存</button>
								</div>
							</form>
						</div>

					</div>
				</c:forEach>
			</div>

			<!--预约记录-->
			<%-- <div class="reservation-record" id="reservationRecord">
				<h3>预约记录</h3>
				<div class="reservation-record-top">
					<p id="myReservation">我的预约</p>
					<p id="myBooking">预约我的人</p>
				</div>
				<ul class="reservation-record-content" id="myAbout">
					<li>
						<p>预约方</p>
						<p>被预约方</p>
						<p>开始时间</p>
						<p>结束时间</p>
					</li>
					<c:forEach items="${apintmentListA}" var="item">
						<li>
							<p>${item.uiA.nickName}</p>
							<p>${item.uiB.nickName}</p>
							<p>
								<fmt:formatDate type="both" value="${item.startTime}" />
							</p>
							<p>
								<fmt:formatDate type="both" value="${item.endTime}" />
							</p>
						</li>
					</c:forEach>
				</ul>
				<ul class="reservation-record-content" id="aboutMy">
					<li>
						<p>预约方</p>
						<p>被预约方</p>
						<p>开始时间</p>
						<p>结束时间</p>
					</li>
					<c:forEach items="${apintmentListB}" var="item">
						<li>
							<p>${item.uiA.nickName}</p>
							<p>${item.uiB.nickName}</p>
							<p>
								<fmt:formatDate type="both" value="${item.startTime}" />
							</p>
							<p>
								<fmt:formatDate type="both" value="${item.endTime}" />
							</p>
						</li>
					</c:forEach>
				</ul>
			</div> --%>
			<div class="reservation-record" id="reservationRecord">
				<div class="reservation-record-top">
					<p id="myReservation">我的预约</p>
					<p id="myBooking">预约我的人</p>
				</div>
				<ul class="reservation-record-content" id="myAbout">

					<li>
						<p>被预约方</p>
						<p>开始时间</p>
						<p>结束时间</p>
						<p>状态</p>
					</li>

					<c:forEach items="${apintmentListAList}" var="item">
						<li>
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
						</li>
					</c:forEach>
					<c:forEach items="${completeApintmentListAList}" var="item">
						<li>
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
						</li>
					</c:forEach>
					<c:forEach items="${apintmentEAUlList}" var="item">
						<li>
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
						</li>
					</c:forEach>
					<c:forEach items="${apintmentEAUcancellList}" var="item">
						<li>
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
						</li>
					</c:forEach>
				</ul>
				<ul class="reservation-record-content" id="aboutMy">
					<li>
						<p>预约方</p>
						<p>开始时间</p>
						<p>结束时间</p>
						<p>状态</p>
						<p>评价</p>
					</li>
					<c:forEach items="${apintmentListBList}" var="item">
						<li>
							<p>${item.uiA.nickName}
								<input type="hidden" name="uiAId" value="${item.uiA.user.id}" />
								<input type="hidden" name="item-startTime"
									value="<fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
								<input type="hidden" name="item-endTime"
									value="<fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
							</p>
							<p>
								<fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</p>
							<p>
								<fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</p>
							<p>${item.state.state}</p>
						</li>
					</c:forEach>
					<c:forEach items="${completeApintmentListBList}" var="item">
						<li>
							<p>${item.uiA.nickName}
								<input type="hidden" name="uiAId" value="${item.uiA.user.id}" />
								<input type="hidden" name="item-startTime"
									value="<fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
								<input type="hidden" name="item-endTime"
									value="<fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
							</p>
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
								<a href="#" class="assess">评价</a>
							</p>
						</li>

					</c:forEach>
					<c:forEach items="${apintmentEAlList}" var="item">
						<li>
							<p>${item.uiA.nickName}
								<input type="hidden" name="uiAId" value="${item.uiA.user.id}" />
								<input type="hidden" name="item-startTime"
									value="<fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
								<input type="hidden" name="item-endTime"
									value="<fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
							</p>
							<p>
								<fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</p>
							<p>
								<fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm" />
							</p>
							<p>${item.state.state}</p>
						</li>

					</c:forEach>
					<c:forEach items="${apintmentEAcancellList}" var="item">
						<li>
							<p>${item.uiA.nickName}
								<input type="hidden" name="uiAId" value="${item.uiA.user.id}" />
								<input type="hidden" name="item-startTime"
									value="<fmt:formatDate value="${item.startTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
								<input type="hidden" name="item-endTime"
									value="<fmt:formatDate value="${item.endTime}"
									pattern="yyyy-MM-dd HH:mm" />" />
							</p>
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
								<a href="#" class="assess">评价</a>
							</p>
						</li>

					</c:forEach>
				</ul>
			</div>
			<!--发布可预约时间-->
			<div class="team-recorded releaseTime-info" id="releaseTime">
				<form action="/insertTime" method="post" id="release-form">
					<div class="timeAll">
						<h3>发布预约时间</h3>
						<table id="InputsWrapper">
							<tr>
								<td>
									<div>
										<table class="time-info-in">
											<tr style="line-height: 65px;">
												<td><input type="text" id="field_1 time-one"
													name="startTime" readonly="readonly" placeholder="开始时间"
													class="laydate-icon startTime-info"
													onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm'})">
													<p style="position: absolute; font-size: 12px; color: red;"
														class="start-time"></p></td>
												<td>--</td>
												<td><input type="text" id="field_2" name="endTime"
													readonly="readonly" placeholder="结束时间"
													class="laydate-icon endTime-info"
													onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm'})">
													<p style="position: absolute; font-size: 12px; color: red;"
														class="end-time"></p></td>
												<td></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
						<input id="AddMoreFileBox" class="addSub" type="button"
							value="添加一行"> <input class="addSub sub-btn" type="submit"
							value="添加预约">
					</div>
				</form>
				<div class="calendar-container">
					<h1>已发布的时间</h1>
					<c:forEach items="${appintmentTimeList}" var="item">
						<div class="calendar">
							<table>
								<tr>
									<td><input type="hidden" name="AppintmentTimeId"
										value="${item.id}"> <input type="text"
										name="startTime" readonly="readonly"
										value="<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm" />"
										class="laydate-icon startTime-info input-box"
										onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm'})">
										<!-- <p style="position: absolute; font-size: 12px; color: red;"
											class="start-time-on"></p> --></td>
									<td>--</td>
									<td><input type="text" name="endTime" readonly="readonly"
										value="<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm"/>"
										class="laydate-icon endTime-info input-box"
										onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm'})">
										<!-- <p style="position: absolute; font-size: 12px; color: red;"
											class="start-time-in"></p> --></td>
									<td>
										<!-- <a class="modify">修改</a><a style="display: none;">保存</a><a class="remove">删除</a> -->
										<a class="modify">修改</a> <a style="display: none;"
										class="save-on">保存</a> <a class="remove">删除</a>
									</td>
								</tr>
							</table>
							<div class="mask"></div>
						</div>
					</c:forEach>
				</div>
			</div>

			<!--团队记录-->
			<div class="team-recorded" id="teamRecorded">
				<input name="userId" class="userId-group" type="hidden"
					value="${userInfo.user.id}">
				<h3>团体记录</h3>
				<ul class="team-recorded-ul"></ul>
				<div id="paging-group" style="text-align: center;margin-top: 30px;"></div>
			</div>
			<!--广告记录-->
			<ul class="advertising-record" id="advertisingRecord">
				<li><h3>广告记录</h3></li>
				<li>

					<p>图片</p>
					<p>内容</p>
					<p>详细地址</p>
					<p>发布时间</p>
					<p>删除</p>
				</li>
				<c:forEach items="${publicList}" var="item">
					<li>
						<p>
							<img src="${item.picture}">
						</p>
						<p class="advertisement-content">${item.content}</p>
						<p>${item.address}</p>
						<p>
							<fmt:formatDate type="date" value="${item.time}" />
						</p>
						<p class="delete">
							<a href="javascript:;" data-id="${item.id}">
								<button type="button" class="btn_public">删除</button>
							</a> <a href="/query/one/ad?adPublicId=${item.id}">修改</a>
						</p>
					</li>
				</c:forEach>
			</ul>
			<!--需求记录-->
			<div class="demand-record" id="demandRecord">
				<h1>需求记录</h1>
				<ul class="demand-record-ul">
					<c:forEach items="${demandList}" var="item">
						<li>
							<div class="advertising-box">
								<div class="advertising-img">
									<a href="#"> <img src="${item.picture}" alt="">
									</a>
								</div>
								<div class="advertising-content">
									<p>
										<a>${item.content}</a>
									</p>
									<p class="personal-details">需求详细信息</p>
									<p>酬劳：${item.reward} — ${item.endReward}元</p>
									<p>地址：${item.address}</p>
								</div>
							</div>
							<div class="button-box">
								<a href="/demand/delete?id=${item.id}&pictureName=${item.picture.name}">删除</a> <a
									href="/update/demand?id=${item.id}">修改</a>
							</div>
						</li>
					</c:forEach>

				</ul>
			</div>
			<!--我的关注-->
			<div class="my-attention" id="myAttention">
				<input name="userId" class="userId-follow" type="hidden"
					value="${userInfo.user.id}">
				<h3>我的关注</h3>
				<ul class="follow-item-ul"></ul>
				<div id="paging-follow" style="text-align: center;margin-top: 30px;"></div>
			</div>
			<!--修改密码-->
			<div class="change-password" id="changePassword">
				<h3>修改密码</h3>
				<table class="tab-info">
					<tr class="tel_modify">
						<td class="left">电话:</td>
						<td class="aaa">
							<p id="tel" class="pass">${userInfo.telephone}</p>
						</td>
						<td>
							<button class="modify_btn Change-the-distance" data-type="phone">更改绑定手机号</button>
						</td>
					</tr>
					<tr class="tel_modify">
						<td class="left">邮箱:</td>
						<td class="aaa">
							<p id="email" class="pass">${userInfo.email}</p>
						</td>
						<td>
							<button class="modify_btn Change-the-distance" data-type="email">更改绑定邮箱</button>
						</td>
					</tr>
					<tr class="tel_modify">
						<td class="left">用户名:</td>
						<td class="aaa">
							<p id="password" class="pass">${userInfo.nickName}</p>
						</td>
						<td>
							<button class="modify_btn Change-the-distance"
								data-type="password">更改密码</button>
						</td>
					</tr>
				</table>
				<!-- <div class="spacing"></div> -->
			</div>
		</div>
	</div>

	<div class="dialog-mask" style="display: none;">
		<div class="dialog-parent">
			<div class="dialog-title">
				<h1>修改信息</h1>
				<i class="icon-close dialog-close"></i>
			</div>
			<div class="dialog-child" data-type="phone">
				<div class="form-item">
					<h3 class="pass_h3">更改绑定手机号</h3>
					<ul class="modify_phone">
						<li><span class="phone_span">手机号：</span> <input id="tels"
							type="text" readonly="readonly" value="${userInfo.telephone}">
							<input name="phoneOld" type="hidden"
							value="${userInfo.telephone}"></li>
						<li><span class="phone_span">新手机号：</span> <input id="phone"
							type="text" name="telephone" placeholder="请输入新的手机号"
							onblur="checkIsExist();"> <span id="phoneResult"></span>
						</li>
						<li><span class="phone_span">验证码：</span> <input type="text"
							name="vCode" id="code" placeholder="请输入验证码" autocomplete="off"
							onclick="vCodes()"> <span id="codeResult"></span>
							<button class="code_btn" id="btn_code" type="button">免费获取验证码</button>
						</li>
						<li><input id="btn_phone" class="phone_btn" type="button"
							value="确认修改"></li>
					</ul>
				</div>
			</div>
			<div class="dialog-child" data-type="email">
				<div class="form-item">
					<h3 class="pass_h3">更改绑定邮箱</h3>
					<ul class="modify_phone">
						<li><span class="phone_span">邮箱号：</span> <input type="text"
							readonly="readonly" value="${userInfo.email}"></li>
						<li><span class="phone_span">新邮箱：</span> <input type="text"
							name="email" placeholder="请输入新邮箱" onblur="checkIsExist();">
							<span></span></li>
						<li><span class="phone_span">验证码：</span> <input type="text"
							name="vCode" placeholder="请输入验证码" autocomplete="off"
							onclick="vCode()"> <span></span>
							<button class="code_btn" id="btn_code" type="button">免费获取验证码</button>
						</li>
						<li><input class="phone_btn" type="button" value="确认修改">
						</li>
					</ul>
				</div>
			</div>
			<div class="dialog-child" data-type="password">
				<h3 class="pass_h3">修改密码</h3>
				<ul class="pass_ul">
					<li><span class="pass_span">用户名：</span> <input type="hidden"
						value="${userInfo.user.id}" name="id" id="id"> <input
						type="text" readonly="readonly" value="${userInfo.user.username }">
					</li>
					<li><span class="pass_span">请输入旧密码：</span> <input id="old_pwd"
						type="password" name="password"> <span class="prompt_j"></span>
					</li>
					<li><span class="pass_span">请输入新密码：</span> <input id="new_pwd"
						type="password" name="password"> <span class="prompt_n"></span>
					</li>
					<li><input id="btn_pwd" class="pass_btn" type="button"
						value="确认修改"></li>
				</ul>
			</div>

		</div>
	</div>


	<div id="bodyMask"></div>
	<div class="box" id="box">
		<div class="shut-down-button">x</div>
		<div class="evaluation-box">
			<p>我的评价：</p>
			<textarea name="evaluation" placeholder="请输入您的评价" maxlength="120"></textarea>
		</div>
		<div class="submit-button">
			<a>提交评论</a>
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
	<div class="avatar-upload">
		<iframe src="/page/update-avatar/upload.html"></iframe>
	</div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script>


	var regionCode = "${userInfo.region.regionCode}";
	var jobCode = "${userInfo.jobInfo.jobCode}";

	// 修改头像按钮绑定事件
	$("#up-img-touch").click(function() {
		$(".avatar-upload").css("display", "block");
	});
	//up-img-p
	$("#up-img-p").click(function() {
		$(".avatar-upload").css("display", "block");
	});
	// 关闭修改头像事件
	function avaterClose() {
		$(".avatar-upload").css("display", "none");
	}
</script>
<script src="/lib/timeJs/laydate.js"></script>
<script src="/lib/timeJs/time.js"></script>
<script src="/js/lq.datetimepick.js"></script>
<script src="/js/user/personal-center-new.js"></script>
<script src="/js/user/basic-information.js"></script>
<script src="/js/user/comprehensive-skills.js"></script>
<script src="/js/user/reservation-record.js"></script>
<script src="/js/user/advertising-record.js"></script>
<script src="/js/user/my-attention.js"></script>
<script src="/js/user/my-advantage.js"></script>
<script src="/js/user/change-password.js"></script>
<script src="/js/user/success-stories.js"></script>
<script src="/js/user/release-time.js"></script>
<script src="/js/user/group-record.js"></script>
<!-- <script src="/js/user/evaluation-star.js"></script> -->

<script src="/js/selectUi.js"></script>
<script src="/js/jobInfo.js"></script>
<script src="/js/regions.js"></script>
<script src="/js/xinxijs.js"></script>
<script src="/js/bootstrap-fileinput.js"></script>
<script src="/js/modifyPass.js"></script>
<script src="/js/user/modifyPassword.js"></script>
<script src="/js/user/startScoreTwo.js"></script>
<script>
	scoreFun($("#startone"))
	scoreFun($("#starttwo"), {
		fen_d : 22, //每一个a的宽度
		ScoreGrade : 5 //a的个数 10或者
	})
</script>
</html>