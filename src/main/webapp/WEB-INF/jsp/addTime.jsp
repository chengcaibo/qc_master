<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<%-- 引入公共头部 --%>
<jsp:include page="common/head.jsp" />
<title>发布预约时间</title>
<link rel="stylesheet" type="text/css" href="/css/addTime.css" />
<script type="text/javascript" src="/lib/timeJs/laydate.js"></script>
</head>
<body>
	<jsp:include page="/header" />
	<form action="/insertTime" method="post">
		<div class="timeAll">
			<h3>发布预约时间</h3>
			<table id="InputsWrapper">
				<tr>
					<td>
						<div>
							<table>
								<tr>
									<td><input type="text" id="field_1" name="startTime" class="addTime" placeholder="开始时间"
										class="laydate-icon" onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
									<td>--</td>
									<td><input type="text" id="field_1" name="endTime" class="addTime" placeholder="结束时间" class="laydate-icon"
										onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"></td>
									<td><a href="#" class="removeclass">×</a></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<!-- <a href="#" id="AddMoreFileBox" class="btn btn-info">添加</a> -->
			<input id="AddMoreFileBox" class="addSub" type="button" value="添加一行"> <input class="addSub" type="submit"
				value="添加预约">
		</div>


	</form>
	<jsp:include page="common/footer.jsp" />
	<script type="text/javascript" src="/timeJs/time.js"></script>
	<script type="text/javascript" src="/js/jquery.js"></script>
	<script>
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
</body>


</html>
