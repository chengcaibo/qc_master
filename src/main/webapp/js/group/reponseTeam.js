$(function() {
	var num = $("input[name='quantity']").val();
	$(".response-btn a").click(function() {
		layer.open({
			title : '信息',
			btn : [],
			closeBtn : 1,
			shadeClose : true,
			offset : '200px',
			shade : 0.3,
			content : "<div class='prompt-box'><p>您需要多少人？</p><input type='text' class='personal-num'><input type='button' class='all-num' value='全部'><p><a class='submit'>提交</a></p></div>",
			success () {
				let tip = -1;
				$("#tips").mousemove(function() {
					layer.close(tip)
				});
				$("#tips li").click(function(event) {
					new Clipboard(this);
				});
				$("#tips li").dblclick(function(event) {
					tip = layer.tips('复制成功~', this, {
						tips : 1
					});
				});

				$(".submit").click(function() {
					var teamId = $("input[name='team-id']").val();
					var userBId = $("input[name='user-b-id']").val();
					var RequiredNumber = $(".personal-num").val();

					$.ajax({
						url : "/insert/theResponseteam",
						type : "POST",
						data : {
							userBId : userBId,
							theResponseteamId : teamId,
							RequiredNumber : RequiredNumber
						},
						dataType : "json",
						error : erryFunction, //错误执行方法    
						success : function(data) {
							console.log(data);
							if (data.ret == 0) {
								layer.alert("您已成功响应！请您耐心等待", {
									offset : '100px'
								});
							}
							if (data.ret == 110) {
								layer.alert(data.msg, {
									offset : '100px'
								});
							}
							if (data.ret == 100) {
								layer.alert(data.msg, {
									offset : '100px'
								});
							}
						}
					});
				});
			}
		});
		$(".all-num").click(function() {
			$(".personal-num").val(num)
		})

	})

	function erryFunction() {
	}


})