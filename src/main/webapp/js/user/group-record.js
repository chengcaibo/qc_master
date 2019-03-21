function FormatDate(strTime) {
	var date = new Date(strTime);
	return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
}
var pageSize = 6;
var userId = $(".userId-group").val();

$(function() {
	function cancelGroup() {
		$(".group-item a").click(function() {
			var this_ = this;
			var id = $(this_).attr("data-id");
			var picName = $(this_).attr("data-pic-name");
			layer.confirm('是否删除?', {
				icon : 3,
				title : '提示'
			}, function(index) {
				// 这里执行 Ajax 进行删除
				$.ajax({
					url : "/group/delete",
					type : "POST",
					dataType : "JSON",
					data : {
						id ,
						"pictureName" : picName
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

		});

	}
	function checkGroup(pageNum) {
		//当前登录的userId
		$.ajax({
			url : "/query/all/group",
			type : "GET",
			dataType : "json",
			data : {
				pageSize : pageSize,
				pageNum : pageNum,
				userId : userId
			},
			success (data) {
				if (data.ret == qc.retEnum.SUCCESS) {
					var list = data.list;
					$(".team-recorded-ul").empty();
					for (var i in data.list) {
						//拼接查询的内容
						var p1 = $('<p><img src="' + list[i].picture.url + '"></p>');
						var p2 = $('<p>' + list[i].groupName + '</p>');
						var p3 = $('<p>' + list[i].quantity + '人</p>');
						var p4 = $('<p>' + FormatDate(list[i].createTime) + '</p>');
						var p5 = $('<p><a href="groupInfo/' + list[i].id + '"> <!-- <input type="button" value="修改" class="btn_modify"> --><button type="button" class="btn_modify">修改</button></a></p>');
						var p6 = $(`<p class="group-item"><a href="javascript:;" data-id="${list[i].id}" data-pic-name="${list[i].picture.name}" ><button type="button" class="del-group">删除</button></a></p>`);
						var li2 = $('<li></li>');
						li2.append(p1);
						li2.append(p2);
						li2.append(p3);
						li2.append(p4);
						li2.append(p5);
						li2.append(p6);
						//var ul = $('<ul class="team-recorded-ul"></ul>');
						$(".team-recorded-ul").append(li2);
					}
					$(".group-item a").click(cancelGroup);

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
			url : "/query/total/group",
			data : {
				userId : userId,
			},
			success : (res) => {
				if (res.ret == qc.retEnum.SUCCESS) {
					var total = res.object;
					layui.use('laypage', function() {
						var laypage = layui.laypage;
						laypage.render({
							elem : 'paging-group',
							limit : pageSize,
							count : total,
							theme : '#1E9FFF',
							jump : function(obj, first) {
								checkGroup(obj.curr);
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