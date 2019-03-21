/**
 * Created by Administrator on 2017/12/24 0024.
 */

	
	$(".delete a").click(function() {
		var this_ = this;
		var id = $(this_).attr("data-id");
		layer.confirm('是否删除?', {
			icon : 3,
			title : '提示'
		}, function(index) {
			// 这里执行 Ajax 进行删除
			$.ajax({
				url : "/ad/delete",
				type : "POST",
				dataType : "JSON",
				data : {
					id : id
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
	
	
	
	
