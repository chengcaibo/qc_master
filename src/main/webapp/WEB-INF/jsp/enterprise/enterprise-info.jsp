<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>企业中心</title>
<link rel="stylesheet" type="text/css" href="/css/enterprise.css">
<link href="css/bootstrap-fileinput.css" rel="stylesheet">
</head>

<body>
	<jsp:include page="/header" />

	<div class="bg-top"></div>
	<div class="container"></div>
	<div class="LargeBox">
		<form action="/enterprise" method="post" enctype="multipart/form-data"
			class="basic-form">
			<div class="con_div">
				<h1 class="userInfo_h1">编辑企业信息</h1>
				<table class="table_en">
					<tr>
						<td class="left">LOGO：</td>
						<td class="right">
							<div class="page-header">
								<div class="form-group" id="uploadForm">
									<div class="fileinput fileinput-new" data-provides="fileinput"
										id="exampleInputUpload">
										<div class="fileinput-new thumbnail">
											<img style="height: 100px;" id='picImg' src="${enInfo.logo}"
												alt="" />
										</div>
										<div class="fileinput-preview fileinput-exists thumbnail"></div>
										<div>
											<span class="btn btn-primary btn-file"> <span
												class="fileinput-new">选择Logo</span> <span
												class="fileinput-exists">重新选择</span> <input type="file"
												name="logoFile" value="" id="picID"
												accept="image/gif,image/jpeg,image/x-png" />
											</span>
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left">营业执照：</td>
						<td class="right">
							<div class="page-header">
								<div class="form-group" id="uploadForm">
									<div class="fileinput fileinput-new" data-provides="fileinput"
										id="exampleInputUpload">
										<div class="fileinput-new thumbnail">
											<img id='picImg' src="${enInfo.businessLicense}" alt="" />
										</div>
										<div class="examine-info">
											<c:if test="${enInfo.certificationEnterprise.state.id == 6}">
												<p>${enInfo.certificationEnterprise.state.state}</p>
												<p>请重新上传！(营业执照必须是跟所填写的资料一致)</p>
											</c:if>
											<c:if test="${enInfo.certificationEnterprise.state.id == 4}">
												<p>${enInfo.certificationEnterprise.state.state}</p>
												<p>请您耐心等待！需要2-3天 的审核期</p>
											</c:if>
										</div>
										<div class="fileinput-preview fileinput-exists thumbnail"></div>
										<div>
											<span class="btn btn-primary btn-file"> <span
												class="fileinput-new">选择营业执照</span> <span
												class="fileinput-exists">重新选择</span> <input type="file"
												name="licenseFile" value="" id="picID"
												accept="image/gif,image/jpeg,image/x-png" />
											</span>
										</div>
									</div>
								</div>
							</div>

						</td>
					</tr>
					<tr>
						<td>图片上传</td>
						<td>
							<div class="images-upload">
							<div class="cover-img">
								<p>点击选择</p>
								<c:if test="${enInfo.banner1.name==null}">
									<img src="" id="img1" style="display:none"> 
								</c:if>
								<c:if test="${enInfo.banner1.name!=null}">
									<img src="${enInfo.banner1.url}" id="img1"> 
								</c:if>
								<%-- <img src="${enInfo.banner1}" id="img1" style="display:none">  --%>
								<input type="file" class="images-item" name="banner1File" value="" onchange="uploadImg1(this)">
							</div>
							<div class="cover-img-item">
								<div class="cover-img-2">
									<p>点击选择</p>
									<c:if test="${enInfo.banner2.name==null}">
										<img src="" id="img2" style="display:none"> 
									</c:if>
									<c:if test="${enInfo.banner2.name!=null}">
										<img src="${enInfo.banner2.url}" id="img2"> 
									</c:if>
									<%-- <img src="${enInfo.banner2}" id="img2" style="display:none"> --%>
									<input type="file" class="images-item" name="banner2File" onchange="uploadImg2(this)"> 
								</div>
								<div class="cover-img-3">
									<p>点击选择</p>
									<c:if test="${enInfo.banner3.name==null}">
										<img src="" id="img3" style="display:none"> 
									</c:if>
									<c:if test="${enInfo.banner3.name!=null}">
										<img src="${enInfo.banner3.url}" id="img3"> 
									</c:if>
									<%-- <img src="${enInfo.banner3}" id="img3" style="display:none"> --%>
									<input type="file" class="images-item" name="banner3File" onchange="uploadImg3(this)"> 
								</div>
							</div>
						</div>
						</td>
					</tr>
					<tr>
						<td>成品图</td>
						<td>
							<div class="cover-img-4">
									<p>点击选择</p>
									<c:if test="${enInfo.picture0.name==null}">
										<img src="" id="img4" style="display:none"> 
									</c:if>
									<c:if test="${enInfo.picture0.name!=null}">
										<img src="${enInfo.picture0.url}" id="img4"> 
									</c:if>
									<%-- <img src="${enInfo.picture0}" id="img4" style="display:none"> --%>
									<input type="file" class="images-item" name="picture0File" onchange="uploadImg4(this)">
								
							</div>
						</td>
					</tr>
					<tr>
						<td class="left">企业名称:</td>
						<td class="bigbox-table-right"><input type="text"
							id="anEnterpriseNameId" maxlength="49" name="enterpriseName"
							value="${enInfo.enterpriseName}">
							<div class="WebsiteError WebsiteError-one" id="anEnterpriseName">
								<p class="WebsiteError-p">企业名称有误</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">企业范围:</td>
						<td class="bigbox-table-right"><textarea id="scope"
								maxlength="1024" name="businessScope" maxlength="1000" cols="43"
								rows="5" placeholder="业务范围...">${enInfo.businessScope}</textarea>
							<div class="WebsiteError WebsiteError-scope"
								id="anEnterpriseName">
								<p class="WebsiteError-p">范围填写有误</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">企业介绍:</td>
						<td class="bigbox-table-right"><textarea name="introduce"
								cols="43" rows="5" placeholder="企业内容..." id="Team"
								maxlength="1000">${enInfo.introduce}</textarea>
							<div class="WebsiteError WebsiteError-two" id="anEnterpriseName">
								<p class="WebsiteError-p-two WebsiteError-p">企业介绍有误</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">企业邮箱:</td>
						<td class="bigbox-table-right"><input type="text" id="MailId"
							name="email" value="${enInfo.email}">
							<div class="WebsiteError WebsiteError-three" id="Mail">
								<p class="WebsiteError-p-three WebsiteError-p">邮箱有误</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">固定电话:</td>
						<td class="bigbox-table-right">
							<input type="text" id="fixedTelephone" name="fixedTelephone" value="${enInfo.fixedTelephone}">
							<div class="WebsiteError WebsiteError-three" id="fixedTelephoneTip">
								<p class="WebsiteError-p-three WebsiteError-p">固定电话有误</p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left">详细地址:</td>
						<td class="bigbox-table-right"><input type="text"
							id="DetailedAddressId" name="address" value="${enInfo.address}" />
							<div class="WebsiteError WebsiteError-four" id="DetailedAddress">
								<p class="WebsiteError-p-four WebsiteError-p">不能为空</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">法人名称:</td>
						<td class="bigbox-table-right"><input type="text"
							id="LegalPersonId" name="personName"
							value="${enInfo.personName }" />
							<div class="WebsiteError WebsiteError-five" id="LegalPerson">
								<p class="WebsiteError-p-five WebsiteError-p">法人名称有误</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">法人电话:</td>
						<td class="bigbox-table-right"><input type="text"
							name="telephone" value="${enInfo.telephone}"
							id="LegalPersonphoneId" />
							<div class="WebsiteError WebsiteError-six" id="LegalPersonphone">
								<p class="WebsiteError-p-six WebsiteError-p">法人电话有误</p>
							</div></td>
					</tr>
					<tr>
						<td class="left">企业网址:</td>
						<td class="bigbox-table-right WebsiteErrorRight"><input
							type="text" name="website" value="${enInfo.website}"
							id="input-website" placeholder="https://" />
							<div class="WebsiteError WebsiteError-seven" id="WebsiteErrorId">
								<p class="WebsiteError-p-seven WebsiteError-p">网址有误</p>
							</div>
							<p class="choose-item">
								<input type="checkbox" name="no-website" class="website-choose"><span>没有网址</span>
							</p></td>
					</tr>
					<tr>
						<td class="left">行业:</td>
						<td class="right" id="tab">
							<div class="sel_region">
								<select class="industry-1"></select> <select class="industry-2"></select>
								<select class="industry-3" name="industry.industryCode"></select>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left">*所在地:</td>
						<td class="right" id="tab">
							<!-- <div class="region-mask">查询中...</div> -->
							<div class="sel_region">
								<select class="region-1" name="joiningProvince"></select> <select
									class="region-2" name="joiningCity"></select> <select
									class="region-3" name="region.regionCode"></select>
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom-button">
					<input type="reset" value="重置"> <input type="submit"
						value="确定" class="disabled" id="disabled">
				</div>
			</div>
			<!-- <div class="Sselect" id="zong">
				<p class="SselectBanner" onclick="selectBanner()" id="anniu1"
					style="border-bottom:3px solid #6495ED;">广告</p>
				<p class="SselectGroup" onclick="selectGroupt()" id="anniu2">团体</p>
				<p class="SselectAbout" onclick="selectAbouty()" id="anniu3">预约</p>
			</div> -->
		</form>

		<%-- <div class="synthesise">
			<ul class="banner show" id="Banner">
				<c:forEach items="${publicList}" var="item">
					<li class="public_info">
						<div class="li_public">
							<div class="li_public">
								<div class="li_public_img">
									<img alt="" src="${item.picture}" class="public_show">
								</div>
								<div class="li_public_content">
									<p class="li_public_content-p">
										广告名称：<span>${item.content}</span>
									</p>
									<p class="li_public_content-p">
										发布地址：<span>${item.address}</span>
									</p>
									<p class="li_public_content-p">
										创建时间：<span><fmt:formatDate type="date" value="${item.time}" /></span>
									</p>
									<p class="li_public_content-p">
										<span><a href="#"><input type="button" value="修改" class="btn_modify"></a></span>
									</p>
								</div>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>

			<ul class="group" id="Group">
				<c:forEach items="${groupList}" var="item">
					<li class="group_info">
						<div class="li_group">
							<div class="li_group">
								<div class="li_group_img">
									<img alt="" src="${item.picture}" class="group_show">
								</div>
								<div class="li_group_content">
									<p class="li_group_content-p">
										团队名称：<span>${item.groupName}</span>
									</p>
									<p class="li_group_content-p">
										团队人数：<span>${item.quantity}人</span>
									</p>
									<p class="li_group_content-p">
										团队介绍：<span>${item.introduce}</span>
									</p>
									<p class="li_group_content-p">
										创建时间：<span><fmt:formatDate type="date" value="${item.createTime}" /></span>
									</p>
									<p class="li_group_content-p">
										<span><a href="#"><input type="button" value="修改" class="btn_modify"></a></span>
									</p>
								</div>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>

			<div class="about" id="About">
				<div class="aboutBottom">
					<ul class="appointmentA">
						<li class="app">预约</li>
						<c:forEach items="${apintmentListA}" var="item">
						<li class="app_info">
							<table>
								<tr>
									<td>预约方：${item.uiA.nickName}</td>
									<td>被预约方：${item.uiB.nickName}</td>
								</tr>
								<tr>
									<td>开始时间：<fmt:formatDate type="date" value="${item.createTime}"/></td>
								</tr>
								<tr>
									<td>结束时间：<fmt:formatDate type="date" value="${item.endTime}"/></td>
								</tr>
							</table>
						</li>
					</c:forEach>
						
					</ul>
					<ul class="appointmentB">
						<li class="app">被预约</li>
						<c:forEach items="${apintmentListB}" var="item">
						<li class="app_info">
							<table>
								<tr>
									<td>预约方：${item.uiA.nickName}</td>
									<td>被预约方：${item.uiB.nickName}</td>
								</tr>
								<tr>
									<td>开始时间：<fmt:formatDate type="date" value="${item.createTime}"/></td>
								</tr>
								<tr>
									<td>结束时间：<fmt:formatDate type="date" value="${item.endTime}"/></td>
								</tr>
							</table>
						</li>
					</c:forEach>
					</ul>
				</div>

			</div>
		</div> --%>
	</div>
	<div class="bg-bottom"></div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/js/bootstrap-fileinput.js"></script>
<script type="text/javascript">
	$(function() {
		//比较简洁，细节可自行完善
		$('#uploadSubmit').click(function() {
			var data = new FormData($('#uploadForm')[0]);
			$.ajax({
				url : 'xxx/xxx',
				type : 'POST',
				data : data,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					console.log(data);
					if (data.status) {
						console.log('upload success');
					} else {
						console.log(data.message);
					}
				},
				error : function(data) {
					console.log(data.status);
				}
			});
		});

	});
	var regionCode = "${enInfo.region.regionCode}";
	var industryCode = "${enInfo.industry.industryCode}";
</script>
<script src="/js/xinxijs.js"></script>
<script src="/js/industry.js"></script>
<script src="/js/regions.js"></script>
</html>