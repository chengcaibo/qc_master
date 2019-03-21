var state = [0,0,0,0,0]
var loading;
$(function() {
	var pageNum = 1; //显示那一页的数据
	var pageSize = 10; //显示几条数据
	//刷新整个页面
	(function() {
		var token = $("#adminToken").val();
		//		console.log(token.length);
		if (token == null || token == '' || token == undefined || token.length == 0) {
			layer.confirm("您尚未登录，请前去登录", {
				scrollbar : false,
				offset : "150px",
				closeBtn : false,
				shade : 1
			}, function() {
				window.location.href = "/admin"; // 实名认证页面
			}, function() {
				window.location.href = "/"
			});
		} else {
			$.ajax({
				url : `/admin/query/user_all`,
				data : {
					"pageNum" : pageNum,
					"pageSize" : pageSize
				},
				success (res) {
					if (res.ret == 0) {
						for (var i = 0; i < res.list.length; i++) {
							var option = `
								<option value="${res.list[i].user.id}">${res.list[i].realName}</option>
								`;
							$(".user-box select").append(option);
						}
					}
				}
			});

		}
	})();

	

	function uploadData() {
		let oj = dataTools.object;
		loading=layer.load(3);
		$.ajax({
			url : `/admin/tools/submit`,
			type : "POST",
			dataType : "JSON",
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			data : new FormData($("#form4")[0]), // 获取 data
			success : function(data) {
				if (data.ret == qc.retEnum.PAR_LACK) {
					layer.close(loading);
					layer.alert("请填写完整！");
				} else if (data.ret == qc.retEnum.SUCCESS) {
					$("input.toolId").val(data.object);
					uploadPicture(1);
					uploadPicture(2);
					uploadPicture(3);
					layer.alert("保存成功", function() {
						layer.close(loading);
						window.location.reload();
					})
				} else {
					layer.alert(data.msg)
				}
			}
		});
	}

	/*判断是否登录*/
	var objTools = {};
	var dataTools = {};

	function uploadPicture(index) {
//		layer.load(3);
		if (state[index] != 1) {
			console.log("忽略了一张图片");
			return;
		}
		$.ajax({
			url : `/admin/tools/picture`,
			type : "POST",
			dataType : "JSON",
			async : false, // 这是一个同步请求
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			data : new FormData($(`#form${index}`)[0]), // 获取 data
			success : function(data) {
//				console.log(data);
			}
		});
	}
	$(".submit-box button[name=release-info]").click(function() {
//		console.log("那么");
		
		var userId = $("select[name='user.id']").val();
		$("input[name='userId']").val(userId);
//		return false;
		var shared1 = $(".shared-1").val();
		var shared2 = $(".shared-2").val();
		var shared3 = $(".shared-3").val();
		if (shared1 == -1 || shared2 == -1) {
			layer.msg("请选择共享分类，最低两级");
			//		alert("请选择共享分类，最低两级");
			return false;
		}
		if (shared3 == -1 && shared2 != -1) {
			$(".shared-2").attr("name", "sharedType.code");
			$(".shared-3").attr("name", null);
		}
		uploadData();
	});
	
});

function uploadImg1(source) {
	var file1 = source.files[0];
	state[1] = 1;
	if (window.FileReader) {
		var fr = new FileReader();
		var img1 = document.getElementById('img1');
		fr.onloadend = function(e) {
			img1.src = e.target.result;
		};
		fr.readAsDataURL(file1);
		img1.style.display = 'block';

	}
}

function uploadImg2(source) {
	var file2 = source.files[0];
	state[2] = 1;
	if (window.FileReader) {
		var fr = new FileReader();
		var img2 = document.getElementById('img2');
		fr.onloadend = function(e) {
			img2.src = e.target.result;
		};
		fr.readAsDataURL(file2);
		img2.style.display = 'block';

	}
}
function uploadImg3(source) {
	var file3 = source.files[0];
	state[3] = 1;
	if (window.FileReader) {
		var fr = new FileReader();
		var img3 = document.getElementById('img3');
		fr.onloadend = function(e) {
			img3.src = e.target.result;
		};
		fr.readAsDataURL(file3);
		img3.style.display = 'block';

	}
}