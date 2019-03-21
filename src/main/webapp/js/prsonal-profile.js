


function erryFunction(e) {
	console.log(e);
}

var hourlWage = $(".salary-frame-top .hourlyWage-info").text();
if(hourlWage.length >= 6){
	$(".salary-frame-top p:nth-child(2)").css({"font-size":"45px"})
}

var userId = $(".follow-user").val();
$(function() {

//取消关注
function cancelFollowUser() {
//	var this_ = this;
//	var userId = $(this_).attr("data-user-id");
	var userId = $(".follow-user").val();
	layer.confirm('是否取消关注?', {
		icon : 3,
		title : '提示'
	}, function(index) {
		// 这里执行 Ajax 进行取消关注
		$.ajax({
			url : "/cancel_follow",
			type : "POST",
			dataType : "JSON",
			data : {
				userId
			},
			success : function(data) {
				if (data.ret == qc.retEnum.SUCCESS) {
					window.location.reload();
				} else {
					layer.alert(data.msg);
				}
			},
			error : function() {
				layer.alert("服务器匆忙，请稍后重试...");
			}
		})
		layer.close(index);
	});

}


function success(){
	
	console.log(userId);
    $.ajax({
    	url : "/insertLikesInfo",
		type : "GET",
		data :{ userId:userId},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : function(data){
			if(data.ret == 110){
				$("#bodyMask").css("display","block");
				$(".focus-on-close h3").text(data.msg);
				$(".focus-on-close").show().css("z-index:99");
			}
			if(data.ret == 0){
				$("#bodyMask").css("display","block");
				$(".focus-on-close h3").text("恭喜您，点赞成功");
				$(".focus-on-close").show().css("z-index:99");
			}
		}
    })
}
function successFollow(){
	//var userId = $("#userId").val(); 
    $.ajax({
    	url : "/add_follow",
		type : "POST",
		data :{ userId:userId},
		dataType : "json",
		error : erryFunction, //错误执行方法    
		success : function(data){
			if(data.ret == 110){
				$(".information-top-addition").text("11")
				$("#bodyMask").css("display","block");
				$(".focus-on-success h3").text(data.msg);
				$(".focus-on-success").show().css("z-index:99");
			}
			if(data.ret == 0){
				$("#bodyMask").css("display","block");
				$(".focus-on-success").show().css("z-index:99");
				 var a = document.getElementById("#addition");
				  a.textContent = "已关注";				
			}
			
		}
    })
}


//$(function () {
	var textFollow = $("#addition").text();
	
    //加关注
    $("#addition").click(function () {
    	if(textFollow == "+关注"){
    		successFollow();
    	}
    	if(textFollow == "已关注"){
    		cancelFollowUser();
    	}
//        if ($("#addition").hasClass("click")) {
//            $("#addition").removeClass("click");
//        } else {
//        	
//            $("#addition").addClass("click");
//        }
    	//判断是否已关注
    	
        
        //cancelFollow()
        
    });

    //点赞
    $("#giveLike").click(function () {
        if ($("#giveLikeI").hasClass("icon-zan1")) {
            $("#giveLikeI").removeClass("icon-zan1");
        } else {
        	success();
            $("#giveLikeI").addClass("icon-zan1");
        }
        
    });

    for (var i = 0; i < times.length; i++) {
        var li = $('<li></li>');
        var a = $("<a></a>");
        // a.attr("href", "javascript:;");
        a.attr("data-index", i);
        a.text(times[i].time);
        // li 中 加入生成的 a
        li.append(a);
        // 向父级中加入li
        $(".available-time-content-top").append(li);
    }

    $(".available-time-content-top li a").click(function () {
        // 获取index

        $(".available-time-content-top li a").removeClass("bg-color")
        $(this).addClass("bg-color");
        var index = $(this).attr("data-index");
        if (index) {
            var list = times[index].list;

            $(".available-time-content-bottom ul").empty();

            for (var i = 0; i < list.length; i++) {

                var li = $('<li><a href="javascript:;">' + list[i].value + '</a></li>');
                if (list[i].disbaled) {
                    li.find("a").css("color", "red");
                }
                $(".available-time-content-bottom ul").append(li);
            }
        }
    })[0].click();
    //默认自动点击第一个 时间
     $(".available-time-content-top li a")

});


var times = [
    {
        time: "12/1",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/2",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/3",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: true
        }, {
            value: "03:00 -- 04:00",
            disbaled: true
        }, {
            value: "04:00 -- 05:00",
            disbaled: true
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/4",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/5",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/6",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/7",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/8",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/9",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/10",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/11",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/12",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/13",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/14",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/15",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/16",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/17",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/18",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/19",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/20",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/21",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/22",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/23",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/24",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/25",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/26",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/27",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/28",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/29",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }, {
        time: "12/30",
        list: [{
            value: "00:00 -- 01:00",
            disbaled: false
        }, {
            value: "01:00 -- 02:00",
            disbaled: false
        }, {
            value: "02:00 -- 03:00",
            disbaled: false
        }, {
            value: "03:00 -- 04:00",
            disbaled: false
        }, {
            value: "04:00 -- 05:00",
            disbaled: false
        }, {
            value: "05:00 -- 06:00",
            disbaled: false
        }, {
            value: "07:00 -- 08:00",
            disbaled: false
        }, {
            value: "08:00 -- 09:00",
            disbaled: false
        }, {
            value: "10:00 -- 11:00",
            disbaled: false
        }, {
            value: "12:00 -- 13:00",
            disbaled: false
        }, {
            value: "13:00 -- 14:00",
            disbaled: false
        }, {
            value: "14:00 -- 15:00",
            disbaled: false
        }, {
            value: "15:00 -- 16:00",
            disbaled: false
        }, {
            value: "17:00 -- 18:00",
            disbaled: false
        }, {
            value: "19:00 -- 20:00",
            disbaled: false
        }, {
            value: "21:00 -- 22:00",
            disbaled: false
        }, {
            value: "22:00 -- 23:00",
            disbaled: false
        }, {
            value: "23:00 -- 00:00",
            disbaled: false
        }]
    }
];


