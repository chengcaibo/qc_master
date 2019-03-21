$(function(){
	$("#btnUser").click(function(){
		var post_data = $("from").serializeArray();
		$.post("/updateUserInfo", post_data, function (_datetext) {
			if (_datetext == 1) {
				layer.alert("添加成功");
			}
		});
	});
});