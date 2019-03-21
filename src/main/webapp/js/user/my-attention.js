var userId = $(".userId-follow").val();

$(function() {
	function cancelFollow() {
		var this_ = this;
		var userId = $(this_).attr("data-user-id");
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
	/**查询我已关注的人*/
	function checkFollow(pageNum) {
		//当前登录的userId
		$.ajax({
			url : `/query/follow_follows/${userId}`,
			type : "GET",
			dataType : "json",
			data : {
				pageSize : 7,
				pageNum : pageNum
			},
			success (data) {
				if (data.ret == qc.retEnum.SUCCESS) {
					var list = data.list;
					$(".follow-item-ul").empty();
					for (var i in data.list) {
						//拼接查询的内容
						var img = $(`<div class="my-attention-img"><a href="/user/${list[i].ui.user.id}" target="_blank" title="点击查看详情"><img src="${list[i].ui.avatar.url}" alt="头像"></a></div>`);
						var div1 = $('<div class="spacing"></div>');
						var p1 = $('<p><span>' + list[i].ui.nickName + '</span></p>');
						if (!list[i].ui.jobInfo) {
							list[i].ui.jobInfo = {
								jobName : "未填写"
							}
						}
						var p2 = $('<p><span>' + list[i].ui.jobInfo.jobName + '</span></p>');
						var p3 = $('<p>性质：<span>个人</span></p>');
						var div2 = $('<div class="my-attention-content"></div>');
						div2.append(p1);
						div2.append(p2);
						div2.append(p3);
						var input = $('<input class="follow-id" type="hidden" value="' + list[i].id + '"name="id"> <a href="javascript:;" data-user-id="' + list[i].ui.user.id + '"><button class="Switch">已关注</button></a>');
						var div3 = $('<div class="focus-on-button"></div>');
						div3.append(input);
						var li = $('<li class="follow-li"></li>');
						li.append(img);
						li.append(div1);
						li.append(div2);
						li.append(div3);
						$(".follow-item-ul").append(li);
					}
					$(".focus-on-button a").click(cancelFollow);

				} else {
					layer.alert(data.msg)
				}
			},
			error : function() {
				layer.alert("服务器匆忙，请稍后重试...");
			}
		})
	}

	//查询 个人total
	(function() {
		$.ajax({
			url : "/query/total/follows",
			data : {
				userId ,
			},
			success : (res) => {
				if (res.ret == qc.retEnum.SUCCESS) {
					var total = res.object;
					layui.use('laypage', function() {
						var laypage = layui.laypage;
						laypage.render({
							elem : 'paging-follow',
							limit : 7,
							count : total,
							theme : '#1E9FFF',
							jump : function(obj, first) {
								checkFollow(obj.curr);
							}
						});
					});
				}
			},
			error : function() {
				console.log("b");
			}
		});
	})();

});