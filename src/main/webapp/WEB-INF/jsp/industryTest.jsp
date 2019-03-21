<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ZH">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>：jQuery省市区三级联动插件city-picker</title>
<!--必要样式-->
<link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.css" rel="stylesheet">
<link href="/css/industry.css" rel="stylesheet">
</head>
<body>
	<!-- Content -->
	<div class="container">
		<h2 class="page-header">演示</h2>
		<div class="docs-methods">
			<form class="form-inline">
				<div id="distpicker">
					<div class="form-group">
						<div style="position: relative;">
							<input id="city-picker3" class="form-control" readonly type="text" value="江苏省/常州市/溧阳市" data-toggle="city-picker">
						</div>
					</div>
					<div class="form-group">
						<button class="btn btn-warning" id="reset" type="button">重置</button>
						<button class="btn btn-danger" id="destroy" type="button">确定</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/js/industry_in.js"></script>
<script src="/js/industry_p.js"></script>
<script src="/js/industry.js"></script>
</html>
