$(function() {

	$("#add-to-ia").click(function() {
		$("#bodyMask").css("display", "block");
		$(".add-ia").removeClass("common-hide").css("z-index:99");
	});

	$(".close-iaInfo").click(function() {
		$("#bodyMask").css("display", "none");
		$(this).parent().addClass("common-hide");
	});

	// 绑定【修改】按钮的点击事件
	$(".qf-con .ia-mol").click(function() {
		var id = $(this).attr("data-id");
		var obj = $("div.modify-ia[data-id='" + id + "']");
		obj.removeClass("common-hide").css("z-index:99");
		$("#bodyMask").css("display", "block");
	});

	$("button[name='ia-add-ok']").click(function() {
		$.ajax({
			url : `/center/aptitude/update`,
			type : "POST",
			dataType : "JSON",
			async : false, // 这是一个同步请求
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			data : getFormMolIa(), // 获取 data
			success : function(data) {
				if (data.ret == qc.retEnum.PAR_LACK) {
					layer.alert("请填写完整！");
				} else if (data.ret == qc.retEnum.SUCCESS) {
					layer.msg("保存成功")
					window.location.reload();
				} else {
					layer.alert(data.msg);
				}
			}
		})
	});

	function getFormMolIa() {
		//<form>标签需要添加 enctype="multipart/form-data"属性。
		var data = new FormData($("#ia-form-mol")[0]);
		return data;
	}


	// 绑定删除资质的方法
	$(".qf-con .ia-del").click(function() {
		var this_ = this;
		var id = $(this_).attr("data-id");
		var name = $(this_).attr("data-name");
		layer.confirm('是否删除?', {
			icon : 3,
			title : '提示'
		}, function(index) {
			// 这里执行 Ajax 进行删除
			$.ajax({
				url : "/center/aptitude/delete",
				type : "POST",
				dataType : "JSON",
				data : {
					id ,
					pictureName : name
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

	/**新增资质*/
	$("#ia-btn").click(function() {

		var iaCount = $(".ia-content-info-ul .ia-content-info").length;
		if (iaCount >= 3) {
			layer.alert("数据不能超过三条哦！");
			return false;
		}

		$.ajax({
			url : "/center/aptitude/insert",
			type : "POST",
			dataType : "JSON",
			async : false, // 这是一个同步请求
			cache : false, // 上传文件不需要缓存
			processData : false, // 因为data值是FormData对象，不需要对数据做处理。
			contentType : false, // 使用 form 表单构造的contentType
			data : getFormIA(), // 获取 data
			success : function(data) {
				if (data.ret == qc.retEnum.PAR_LACK) {
					layer.alert("请填写完整！");
				} else if (data.ret == qc.retEnum.SUCCESS) {
					layer.msg("保存成功")
					window.location.reload();
				} else {
					layer.alert(data.msg)
				}
			}
		})
	});

	$("#close-div").click(function() {
		$("#icon-div").hide();
		window.location.reload();
	});

	function getFormIA() {
		//<form>标签需要添加 enctype="multipart/form-data"属性。
		var data = new FormData($("#form-ia")[0]);

		return data;
	}

	// 绑定删除技能的方法
	$(".skills-label li a").click(function() {
		var this_ = this;
		var id = $(this_).attr("data-id");
		layer.confirm('是否删除?', {
			icon : 3,
			title : '提示'
		}, function(index) {
			$(this_).parent().remove();
			//			// 这里执行 Ajax 进行删除
			$.ajax({
				url : "/center/skill/del/" + id,
				type : "POST",
				dataType : "JSON",
				success : function(data) {
					if (data.ret == qc.retEnum.SUCCESS) {
						//window.location.reload();
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


	$("#sub-skillInfo").click(function() {
		var data = [];
		$("input[name=skillName]").each(function(idx, obj) {
			if (idx < 8) {
				if ($(obj).val() != "") {
					data.push($(obj).val());
				}
			}
		});


		if (data.length < 1) {
			layer.alert("一个都没有伦家怎么给你添加啊！");
			return;
		}

		$.ajax({
			url : "/center/skill/add",
			type : "POST",
			dataType : "JSON",
			traditional : true,
			data : {
				skills : data
			},
			success : function(data) {
				if (data.ret == qc.retEnum.PAR_LACK) {
					layer.alert("请填写完整！");
				} else if (data.ret == qc.retEnum.SUCCESS) {
					layer.msg("保存成功")
					window.location.reload();
				} else {
					layer.alert(data.msg)
				}
			}
		})
	});

	$("#close-div").click(function() {
		$("#icon-div").hide();
		window.location.reload();
	});

	// 添加一个技能按钮
	$("#addSkills").click(function() {
		var skillsCount = $(".skills-label li").length;
		var inputsCount = $(".comprehensive-skills-top li").length;
		if ((skillsCount + inputsCount) >= 8) {
			layer.alert("不能再添加了，技能最多只能添加八个呦！");
		} else {
			$(".comprehensive-skills-top ").append('<li><input type="text" name="skillName" placeholder="例：java开发"><button><i class="icon-chuyidong"></i></button></li>');
			// 新增加的button沒有click事件，所以需要重新綁定
			$(".comprehensive-skills-top li button").unbind().click(removeLi);
			isEmpty();
		}
	});


	var removeLi = function() {
		if ($(this).prev().val() == "" || confirm("确定要删除吗？")) {
			$(this).parent().remove();
			isEmpty();
		}
	};

	$(".comprehensive-skills-top li button").click(removeLi);

	// 判断技能是否为空
	function isEmpty() {
		var skillsCount = $(".skills-label li").length;
		var inputsCount = $(".comprehensive-skills-top li").length;
		if (skillsCount + inputsCount == 0) {
			$("#comprehensiveSkills h3").css("display", "block");
		} else {
			$("#comprehensiveSkills h3").css("display", "none");
		}
	}
	isEmpty();
});