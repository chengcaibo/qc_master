<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="../common/head.jsp" />

<title>修改广告信息</title>

<link rel="stylesheet" type="text/css" href="/css/group.css">
<link rel="stylesheet" href="/css/bootstrap-fileinput.css">
</head>

<body>
	<jsp:include page="/header" />
	<form action="/updateGroup" method="post" enctype="multipart/form-data">
		<div class="con_ad">
			<h3>修改团体信息</h3>
			<table>
				<tr>
					<td>团体名称：<input type="hidden" name="id" value="${groupInfo.id}"></td>
					<td><input type="text" class="groupName" name="groupName" value="${groupInfo.groupName}"></td>
				</tr>
				<tr>
					<td>人员数量：</td>
					<td><input type="text" name="quantity" value="${groupInfo.quantity}"></td>
				</tr>
				<tr>
					<td>团体封面：</td>
					<td style="margin: 0;border: 0;">
						<div class="page-header">
							<div class="form-group" id="uploadForm">
								<div class="fileinput fileinput-new" data-provides="fileinput" id="exampleInputUpload">
									<div class="fileinput-new thumbnail">
										<img id='picImg' src="${groupInfo.picture}" alt="" />
									</div>
									<div class="fileinput-preview fileinput-exists thumbnail"></div>
									<div>
										<span class="btn btn-primary btn-file"> 
										<span class="fileinput-new">选择团队封面</span> 
										<span class="fileinput-exists">重新选择</span> 
										<input type="file" name="groupFile" id="picID"
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
					<td><textarea name="introduce">${groupInfo.introduce}</textarea></td>
				</tr>
				<tr>
					<td>工具费用：</td>
					<td><input type="text" name="toolCost" value="${groupInfo.toolCost}"></td>
				</tr>
				<tr>
					<td>人员费用：</td>
					<td><input type="text" name="personnelCost" value="${groupInfo.personnelCost}"></td>
				</tr>

				<tr style="line-height: 50px;">
					<td>行业：</td>
					<td class="right">
						<select class="industry-1"></select> 
						<select class="industry-2"></select>
						<select class="industry-3" name="industry.industryCode"></select>
					</td>
				</tr>
				<tr>
					<td class="left">*所在地:</td>
					
					<td class="right">
						<div class="region-mask">查询中...</div> 
						<select class="region-1" name="joiningProvince"></select>
						<select class="region-2" name="joiningCity"></select> 
						<select class="region-3" name="region.regionCode"></select>
					</td>
				</tr>
				<tr>
					<td>详情地址：</td>
					<td><input type="text" name="address" value="${groupInfo.address}"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="保存"></td>
				</tr>
			</table>
		</div>
	</form>
	<jsp:include page="../common/footer.jsp" />
</body>
<script type="text/javascript" src="/timeJs/time.js"></script>
<script src="/js/bootstrap-fileinput.js"></script>
<script type="text/javascript">
	var regionCode = "${groupInfo.region.regionCode}";
	var industryCode = "${groupInfo.industry.industryCode}";
	//console.log(regionCode);
	console.log(industryCode);
</script>
<script src="/js/industry.js"></script>
<script src="/js/regions.js"></script>
</html>
