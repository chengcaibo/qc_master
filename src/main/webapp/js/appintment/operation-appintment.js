function content() {
	$(".stay-confirmed-content").css("display", "none");
	$(".stay-cancel-content").css("display", "none");
	$(".already-confirmed-content").css("display", "none");
	$("#response-My-stay-confirmed").css("display", "none");
	$("#already-confirmed-content").css("display", "none");
	$("#my-stay-confirmed").css("color", "#333");
	$("#my-stay-cancel").css("color", "#333");
	$("#my-already-confirmed").css("color", "#333");
	$("#others-stay-confirmed").css("color", "#333");
	$("#others-stay-cancel").css("color", "#333");
	$("#others-already-confirmed").css("color", "#333");
	$("#response-stay-confirmed").css("color", "#333");
	$("#response-already-confirmed").css("color", "#333");
	$("#responseMy-stay-confirmed").css("color", "#333");
	$("#response-My-confirmed-content").css("color", "#333");
	$("#responseMy-already-confirmed").css("color", "#333");
}
$(function() {

	function erryFunction(e) {
		console.log(e);
	}
	//我的预约-待确认
	$("#my-stay-confirmed").click(function() {
		content();
		$(".stay-confirmed-content").css("display", "block");
		$(this).css("color", "#0099ff");
	});
	//我的预约-已确认
	$("#my-already-confirmed").click(function() {
		content();
		$(".already-confirmed-content").css("display", "block");
		$(this).css("color", "#0099ff");
	});
	//我的预约-待取消
	$("#my-stay-cancel").click(function() {
		content();
		$(".stay-cancel-content").css("display", "block");
		$(this).css("color", "#0099ff");
	});
	//预约我的人-待确认
	$("#others-stay-confirmed").click(function() {
		content();
		$(".stay-confirmed-content").css("display", "block");
		$(this).css("color", "#0099ff");
	});
	//预约我的人-已确认
	$("#others-already-confirmed").click(function() {
		content();
		$(".already-confirmed-content").css("display", "block");
		$(this).css("color", "#0099ff");
	});
	//预约我的人-待取消
	$("#others-stay-cancel").click(function() {
		content();
		$(".stay-cancel-content").css("display", "block");
		$(this).css("color", "#0099ff");
	});
	
	//我的响应-待确认
		$("#response-stay-confirmed").click(function() {
			content();
			$("#response-stay-confirmed-content").css("display", "block");
			$("#already-confirmed-content").css("display", "none");
			$(this).css("color", "#0099ff");
		});
	//我的响应-已确认
		$("#response-already-confirmed").click(function() {
			content();
			$("#already-confirmed-content").css("display", "block");
			$("#response-stay-confirmed-content").css("display", "none");
			$(this).css("color", "#0099ff");
		});
		
		
	//响应我的-待确认
				$("#responseMy-stay-confirmed").click(function() {
					content();
					$("#response-My-stay-confirmed").css("display", "block");
					$("#response-My-confirmed-content").css("display", "none");
					$(this).css("color", "#0099ff");
				});
	//响应我的-已确认
				$("#responseMy-already-confirmed").click(function() {
					content();
					$("#response-My-confirmed-content").css("display", "block");
					$("#response-My-stay-confirmed").css("display", "none");
					$(this).css("color", "#0099ff");
				});

	$(".myStayConfirmButton-Cancel").click(function() {
		var this_ = this;
		layer.confirm('您确定要取消预约吗?', {
			icon : 3,
			title : '提示'
		}, function(index) {
			var uAUId = $(this_).parent().parent().find("input[name=uAUId]").val();
			$.ajax({
				url : "/cancel/a/state",
				type : "post",
				data : {
					uAUId : uAUId
				},
				dataType : "json",
				error : erryFunction, //错误执行方法    
				success : function() {
					layer.msg("<span style='color:#fff'>您已取消预约！正在等待对方确认！</span>")
					setTimeout(function(){window.location.reload()},800);
				}
			})
			layer.close(index);
		});

	});


	$(".bookingStayConfirmButtonRefused").click(function() {
		layer.confirm("你确定要拒绝他的预约吗？", () => {
			var uAUId = $(this).parent().parent().find("input[name=uAUId]").val();
			var startTime = $(".p-start-time").text();
			var endTime = $(".p-end-time").text();
			$.ajax({
				async : false,
				url : "/decline/delete",
				type : "post",
				data : {
					uAUId : uAUId
				},
				dataType : "json",
				error : erryFunction, //错误执行方法    
				success : function(data) {
					if (data.ret == 0) {
						$.ajax({
							async : false,
							url : "/decline/insert",
							type : "post",
							data : {
								startTime : startTime.trim(),
								endTime : endTime.trim()
							},
							dataType : "json",
							error : erryFunction, //错误执行方法    
							success : function(data) {
								if (data.ret == 0) {
									$(this).parent().remove();
									layer.msg("<span style='color:#fff'>已拒绝本次预约!</span>")
									setTimeout(function(){window.location.reload()},800);
								}
							}
						})
					}

				}
			})
		});
	});
	//预约我的 --已确认----取消
	$(".beStayConfirmButton-Cancel").click(function() {
		layer.confirm("您确定要取消预约吗？", () => {
			var uAUId = $(this).parent().parent().find("input[name=uAUId]").val();
			$.ajax({
				url : "/confirm/b/cancel",
				type : "post",
				data : {
					uAUId : uAUId
				},
				dataType : "json",
				error : erryFunction, //错误执行方法    
				success : function() {
					$(this).parent().remove();
					layer.msg("<span style='color:#fff'>取消请求已通知，请您耐心等待！</span>")
					setTimeout(function(){window.location.reload()},800);
				}
			})
			layer.close(index);
		});
	});
	$(".bookingStayConfirmButtonConfirm").click(function() {
		var uAUId = $(this).parent().parent().find("input[name=uAUId]").val();
		$.ajax({
			url : "/update/state",
			type : "post",
			data : {
				uAUId : uAUId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function() {
				$(this).parent().parent().parent().remove();
				layer.msg("<span style='color:#fff'>预约已达成，请您等待！</span>")
				setTimeout(function(){window.location.reload()},800);
			}
		})
	});
	var reviewersId;
	var satrtTime;
	var endTime;
	$(".complete").click(function() {
		var uAUId = $(this).parent().parent().find("input[name=uAUId]").val();
		reviewersId = $(this).parent().parent().find("input[name=reviewersId]").val();
		satrtTime = $(this).parent().parent().find("input[name=startTime]").val();
		endTime = $(this).parent().parent().find("input[name=endTime]").val();
		console.log(satrtTime)
		$.ajax({
			url : "/complete/state",
			type : "post",
			data : {
				uAUId : uAUId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function() {
				$(".box").css({
					"display" : "block",
					"position" : "fixed",
					"top" : "50%",
					"left" : "50%",
					"margin-top" : "-234px",
					"margin-left" : "-150px"
				})
			/*
							$(this).parent().parent().parent().remove();
							$("#box").css({
								"display" : "block",
								"z-index" : "2"
							});
							$("#bodyMask").css({
								"display" : "block",
								"z-index" : "1"
							});*/
			//				window.location.reload();
			}
		});
	});
	$(".submit-button-cao").click(function() {
		//		var evaluation = document.getElementById("evaluation").text();
		var evaluation = $("textarea[name='evaluation']").val();
		var score = $(".fenshu").html();
		$.ajax({
			url : "/insert/commentaries",
			type : "post",
			data : {
				commentariesScore : score,
				reviewersId : reviewersId,
				commentariesContent : evaluation,
				startTime :satrtTime.trim(),
				endTime : endTime.trim()
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if (data.ret == 0) {
					$(".box").css("display", "none");
					window.location.reload();
				}
				if(data.ret == 110){
					layer.alert("您已评价过！不能重复评价");
				}
			}
		});
	});
	$(".confirm-to-cancel").click(function() {
		var uAUId = $(this).parent().parent().find("input[name=uAUId]").val();
		$.ajax({
			url : "/confirm/cancel",
			type : "post",
			data : {
				uAUId : uAUId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function() {
				$(this).parent().parent().parent().remove();
				layer.msg("<span style='color:#fff'>已取消</span>")
				setTimeout(function(){window.location.reload()},800);
			}
		});
	});
	//我的预约--已确认--取消
	$(".aStayConfirmButton-Cancel").click(function() {
		layer.confirm("您确定要取消预约吗？", (index) => {
			var uAUId = $(this).parent().parent().find("input[name=uAUId]").val();
			$.ajax({
				url : "/cancel/a/state",
				type : "post",
				data : {
					uAUId : uAUId
				},
				dataType : "json",
				error : erryFunction, //错误执行方法    
				success : function() {
					$(this).parent().remove();
					layer.msg("<span style='color:#fff'>取消请求已通知，请您耐心等待</span>")
					setTimeout(function(){window.location.reload()},800);
					
					
				}
			})
			layer.close(index);
		})
	});
	function none(){
		
	}
	
//	我响应的人
	$("#re").click(function() {
		$(".my").css("display", "none");
		$(".others").css("display", "none");
		$(".my-response").css("display", "block");
		$("#response-stay-confirmed").css("display", "block");
		$(".responseMy").css("display", "none");
		$(this).css({
			"background" : "#fff",
			"color" : "#333"
		});
		$("#my-about").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#others-about").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#re-my").css({
			"background" : "none",
			"color" : "#fff"
		});
	});
//	响应我的人
	$("#re-my").click(function() {
		$(".my").css("display", "none");
		$(".others").css("display", "none");
		$(".my-response").css("display", "none");
		$(".responseMy").css("display", "block");
		$("#response-My-stay-confirmed").css("display", "block");
		$(this).css({
			"background" : "#fff",
			"color" : "#333"
		});
		$("#my-about").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#others-about").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#re").css({
			"background" : "none",
			"color" : "#fff"
		});
	});
	
//	我的预约
	$("#my-about").click(function() {
		$(".my").css("display", "block");
		$(".others").css("display", "none");
		$(".my-response").css("display", "none");
		$(".stay-confirmed-content").css("display", "block");
		$(".responseMy").css("display", "none");
		$(this).css({
			"background" : "#fff",
			"color" : "#333"
		});
		$("#others-about").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#re").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#re-my").css({
			"background" : "none",
			"color" : "#fff"
		});
	});
//	预约我的
	$("#others-about").click(function() {
		$(".my").css("display", "none");
		$(".others").css("display", "block");
		$(".my-response").css("display", "none");
		$(".stay-confirmed-content").css("display", "block");
		$(".responseMy").css("display", "none");
		$(this).css({
			"background" : "#fff",
			"color" : "#333"
		});
		$("#my-about").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#re").css({
			"background" : "none",
			"color" : "#fff"
		});
		$("#re-my").css({
			"background" : "none",
			"color" : "#fff"
		});
	});
	$(".refused-to").click(function (){
		var threResponseId = $(this).parent().parent().find("input[name=threResponse]").val();
		$.ajax({
			url : "/delete/theResponse",
			type : "post",
			data : {
				id : threResponseId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function() {
				$(this).parent().remove();
				layer.msg("<span style='color:#fff'>已拒绝</span>")
				setTimeout(function(){window.location.reload()},800);
			}
		})
	});
	$(".cananl-team").click(function (){
		var responseTeamId = $(this).parent().parent().find("input[name=threResponse]").val();
		$.ajax({
			url : "/delete/theResponseteam",
			type : "get",
			data : {
				teammId : responseTeamId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function() {
				$(this).parent().remove();
				layer.msg("<span style='color:#fff'>已拒绝！系统已自动帮您删除</span>")
				setTimeout(function(){window.location.reload()},800);
			}
		});
	});
	$(".confrim-team").click(function (){
		var responseTeamId = $(this).parent().parent().find("input[name=threResponse]").val();
		var groupId = $(this).parent().parent().find("input[name=groupId]").val();
		$.ajax({
			url : "/update/theResponseteam",
			type : "get",
			data : {
				teammId : responseTeamId,
				groupId:groupId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if(data.ret == 0){
					$(this).parent().remove();
					layer.msg("<span style='color:#fff'>您已确认接收本次聘请!</span>")
					setTimeout(function(){window.location.reload()},800);
				}else if(data.ret == 110){
					layer.msg("<span style='color:#fff'>您已确认过接收本次聘请!同一个不能重复俩次。</span>")
					setTimeout(function(){window.location.reload()},800);
				}			
			}
		});
	});
	
	$(".confirm-demand").click(function (){
		var responseId = $(this).parent().parent().find("input[name=threResponse]").val();
		var demandId = $(this).parent().parent().find("input[name=demandId]").val();
		$.ajax({
			url : "/confrim/theResponse",
			type : "get",
			data : {
				theResponseId : theResponseId,
				demandId:demandId
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if(data.ret == 0){
					$(this).parent().remove();
					layer.msg("<span style='color:#fff'>您已确认接收本次聘请!</span>")
					setTimeout(function(){window.location.reload()},800);
				}else if(data.ret == 110){
					layer.msg("<span style='color:#fff'>您已确认过接收本次聘请!同一个不能重复俩次。</span>")
					setTimeout(function(){window.location.reload()},800);
				}			
			}
		});
	});
	
	
	$(".complete-team").click(function (){
		var responseTeamId = $(this).parent().parent().find("input[name=threResponse]").val();
		$.ajax({
			url : "/complete/theResponseteam",
			type : "get",
			data : {
				teammId : responseTeamId,
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if(data.ret == 0){
					$(this).parent().remove();
					layer.msg("<span style='color:#fff'>本次团队已完成！</span>")
					setTimeout(function(){window.location.reload()},800);
				}else if(data.ret == 100){
					layer.msg("<span style='color:#fff'>更新失败!</span>")
					setTimeout(function(){window.location.reload()},800);
				}			
			}
		});
	});
	$(".complete-demand").click(function (){
		var responseId = $(this).parent().parent().find("input[name=threResponse]").val();
		$.ajax({
			url : "/complete/theResponseteam",
			type : "get",
			data : {
				theResponseId : responseId,
			},
			dataType : "json",
			error : erryFunction, //错误执行方法    
			success : function(data) {
				if(data.ret == 0){
					$(this).parent().remove();
					layer.msg("<span style='color:#fff'>本次需求已完成！</span>")
					setTimeout(function(){window.location.reload()},800);
				}else if(data.ret == 100){
					layer.msg("<span style='color:#fff'>更新失败， 请重新提交！带来的不便请谅解。</span>")
					setTimeout(function(){window.location.reload()},800);
				}			
			}
		});
	});
	//绝对定位样式
	var navTops = $(".confirmed-button"); //得到导航对象
	var sidebar = $(".select-button"); //得到侧边栏对象
	var win = $(window); //得到窗口对象
	var sc = $(document); //得到document文档对象。
	console.log(sc.scrollTop());
	win.scroll(function() {
		if (sc.scrollTop() >= 105) {
			navTops.addClass("add-fixedTop");
			sidebar.addClass("add-fixedSidebar");
		} else {
			navTops.removeClass("add-fixedTop");
			sidebar.removeClass("add-fixedSidebar");
		}
	})

	$("#close-div").click(function() {
		$("#icon-div").hide();
		window.location.reload();
	});
});