<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="ZH">
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>发布团体</title>
<link rel="stylesheet" href="/css/group.css">
<link rel="stylesheet" href="/css/bootstrap-fileinput.css">
</head>
<script>
	
</script>
<body style="backgroud-color:#f5f5f5;">
	<jsp:include page="/header" />
	<form id="form" method="post" enctype="multipart/form-data">
		<div class="return-icon" id="icon-div" style="display: none;">
			<i class="return-close icon-close" id="close-div"></i> <i
				class="return-success icon-success"></i>
			<h3>恭喜您,发布团体信息成功！</h3>
			<span><a href="/">返回首页>></a></span>
		</div>
		<div class="con_all">
			<h3>团体信息</h3>
			<table>
				<tr>
					<td>团体名称：</td>
					<td><input type="text" id="group-name" class="groupName"
						name="groupName" value="${groupInfo.groupName}">
						<p class="prompt group-name">团体名称不能为空</p>
						<p class="prompt sensitive-words">团体名称有敏感词</p></td>
				</tr>
				<tr>
					<td>人员数量：</td>
					<td><input type="text" id="group-quantity" name="quantity"
						value="${groupInfo.quantity}">
						<p class="prompt personnel">人员数量有误</p></td>
				</tr>
				<tr>
					<td>团体封面：</td>
					<td>
						<div class="page-header">
							<div class="form-group" id="uploadForm">
								<div class="fileinput fileinput-new" data-provides="fileinput"
									id="exampleInputUpload">
									<div class="fileinput-new thumbnail">
										<img id='picImg' class="img-group" src="${groupInfo.picture}"
											alt="" />
									</div>
									<div class="fileinput-preview fileinput-exists thumbnail"></div>
									<div>
										<span class="btn btn-primary btn-file"> <span
											class="fileinput-new">选择团队封面</span> <span
											class="fileinput-exists">重新选择</span> <input type="file"
											name="coverFile" id="picID"
											accept="image/gif,image/jpeg,image/x-png" />
										</span>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>团体介绍：</td>
					<td><textarea id="group-introduce" name="introduce">${groupInfo.introduce}</textarea>
						<p class="prompt introduce">团体介绍不能为空</p>
						<p class="prompt sensitive-words-introduce">团体介绍有敏感词</p></td>
				</tr>
				<tr>
					<td>工具费用：</td>
					<td><input type="text" id="group-toolCost" name="toolCost"
						value="${groupInfo.toolCost}">
						<p class="prompt tool">工具费用有误</p></td>
				</tr>
				<tr>
					<td>工具说明：</td>
					<td><textarea id="group-toolExplain" name="toolExplain"></textarea>
						<p class="prompt toolExplain">工具说明不能为空</p>
						<p class="prompt sensitive-words-toolExplain">工具说明内有敏感词</p></td>
				</tr>
				<tr>
					<td>人员费用：</td>
					<td><input type="text" id="group-personnelCost"
						name="personnelCost" value="${groupInfo.personnelCost}">
						<p class="prompt personnel-money">人员费用有误</p></td>
				</tr>
				<tr>
					<td>人员介绍：</td>
					<td><textarea id="group-personnelDivision"
							name="personnelExplain"></textarea>
						<p class="prompt personnelDivision">人员介绍不能为空</p>
						<p class="prompt sensitive-words-personnelDivision">人员介绍内有敏感词</p></td>
				</tr>
				<tr style="line-height: 50px;">
					<td>所属行业：</td>
					<td class="right">
						<input type="hidden" name="id" value="${industry.id}">
						<div class="region-mask">查询中...</div>
						<select class="industry-1"></select>
						<select class="industry-2"></select>
						<select class="industry-3" name="industry.industryCode"></select>
					</td>
				</tr>
				<tr style="line-height: 50px;">
					<td>所属地区：</td>
					<td class="right" >
						<div class="region-mask">查询中...</div>
						<select class="region-1" name="joiningProvince"></select>
						<select class="region-2" name="joiningCity"></select>
						<select class="region-3" id="group-region" name="region.regionCode"></select>
					</td>
				</tr>
				<tr>
					<td>详情地址：</td>
					<td><input type="text" id="group-personnelCost-change"
						name="address" value="${groupInfo.address}">
						<p class="prompt detailed-address">详情地址不能为空</p>
						<p class="prompt sensitive-words-detailed-address">详情地址有敏感词</p></td>
				</tr>
				<tr>
					<td colspan="2"><input class="group-btn" id="btn-sub"
						type="button" value="发布信息"></td>
				</tr>
			</table>
		</div>
	</form>
	<div id="bodyMask"></div>
	<jsp:include page="../common/footer.jsp" />
</body>
<script>
	var industryCode = "${industry.industryTypeCode}";
	var regionCode = "${region.regionCode}";
</script>
<script src="/js/group.js"></script>
<script src="/js/industry.js"></script>
<script src="/js/bootstrap-fileinput.js"></script>
</html>