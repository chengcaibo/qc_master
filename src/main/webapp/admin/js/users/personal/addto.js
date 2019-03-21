form.render();

$("#avatarFile").change(function() {
	var file = $(this).get(0).files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$(".avatar").attr("src", e.target.result)
	}
	reader.readAsDataURL(file);
});

$("#btn-reset").click(function() {
	$(".avatar").attr("src", "/images/picture-bg.jpg")
})

//执行一个laydate实例
layui.laydate.render({
	elem : '#birthday' //指定元素
});

const copy_verify = {
	email : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	date : /^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/
}

// 监听表单提交
form.on('submit(submit)', function(data) {
	try {
		let field = data.field;
		let keys = [ "avatarFile", "nickName", "telephone", "gender.id", "birthday", "email", "hourlyWage", "introduce", "ascendancy", "jobInfo.jobCode", "region.regionCode" ];
		let formData = new FormData();
		for (let idx in keys) {
			let key = keys[idx];
			let value = field[key];
			if (key == "avatarFile" && value != "") {
				formData.append(key, new FormData(data.form).get(key));
			} else if (value != "") {
				if (key == "email") {
					if (!copy_verify.email.test(value)) {
						layer.alert("请输入正确的电子邮箱")
						return false;
					}
				} else if (key == "birthday") {
					if (!copy_verify.date.test(value)) {
						layer.alert("请输入正确的生日")
						return false;
					}
				} else if (key == "hourlyWage") {
					if (isNaN(value)) {
						layer.alert("时薪只能输入数字")
						return false;
					}
				}
				formData.append(key, value);
			}
		}
		/*	console.log("= = = = = = =")
			formData.forEach((value, key) => {
				console.log(key, ":", value)
			})
*/
		let loadingIndex = layer.load(1);
		$.ajax({
			url : "/admin/personal/addto",
			type : "POST",
			data : formData,
			cache : false,
			processData : false,
			contentType : false,
			success (res) {
				let title = "添加失败";
				let icon = 2; // 2 = 失败
				let fun = () => {
					layer.close(idx)
				}
				// 是否添加成功
				if (res.ret == qc.retEnum.SUCCESS) {
					title = "添加成功";
					icon = 1; // 1 = 成功
					fun = () => {
						window.location.reload();
					}
				}
				// 弹框
				let idx = layer.alert(res.msg, {
					title ,
					icon
				}, fun)
			},
			error (e) {
				layer.alert("添加失败，请稍后重试！")
				console.err("error:", e)
			},
			complete () {
				layer.close(loadingIndex);
			}
		})
	} catch (e) {
		console.error(e)
	}

	return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
});