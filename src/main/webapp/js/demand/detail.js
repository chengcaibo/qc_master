$(function() {
	function erryFunction() {
	}
	$(".response").click(function() {
		// alert("123456");
		var demandUser = $("input[name='demandUser']").val();
		var demandId = $("input[name='demandId']").val();
		$.ajax({
			url : "/insert/theResponse",
			type : "POST",
			data : {
				userBId : demandUser,
				demandId : demandId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if (data.ret == 0) {
					layer.alert("您已成功响应！请您耐心等待",{
						offset : '100px'
					});
				}
				if (data.ret == 110) {
					layer.alert(data.msg,{
						offset : '100px'
					});
				}
				if(data.ret ==100){
					layer.alert(data.msg, {
						offset : '100px'
					});
				}
			}
		});
	});
})