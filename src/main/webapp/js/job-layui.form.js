(function() {
	var job = {
		query : function(parentCode, nextLevel) {
			var t = 3;
			$.ajax({
				url : "/job_info/query/child",
				type : "GET",
				dataType : "JSON",
				data : {
					parentCode : parentCode
				},
				beforeSend : function() {
					for (var i = nextLevel; i <= 3; i++) {
						$("select.job-" + i).empty();
					}
					$("select.job-" + nextLevel).append($('<option value="-1">查询中...</option>'));
					form.render();
				},
				success : function(r) {
					t = 4;
					if (r.ret == 0) {
						// 查询成功
						job.setValue(nextLevel, r.list);
						// 重新绑定方法
						form.on('select(job)', function(data) {
							job.bindChange(data.elem);
						});
					} else {
						layer.alert(r.msg);
					}
				}
			})
		},
		setValue : function(level, list, selected) {
			var obj = $("select.job-" + level).empty();
			obj.append($("<option value='-1'>请选择...</option>"))
			for (var i = 0; i < list.length; i++) {
				var temp = $("<option value='" + list[i].jobCode + "'>" + list[i].jobName + "</option>");
				if (selected && list[i].jobCode == selected) {
					temp.attr("selected", true);
				}
				obj.append(temp);
			}
			form.render();
		},
		bindChange : function(elem) {
			// 获取当前级别
			var this_ = $(elem);
			var name = this_.attr("class");
			var level = parseInt(name.substring(name.length - 1, name.length));
			if (++level > 3 || this_.val() == "-1") {
				return;
			}
			job.query(this_.val(), level);
		},
		queryParent : function(code) {
			$.ajax({
				url : "/job_info/query/parent",
				type : "GET",
				dataType : "JSON",
				data : {
					childCode : code
				},
				error : r => {
					console.log(r);
				},
				success : function(r) {
					if (r.ret == 0) {
						var level = 1;
						console.info(r.list);
						for (var i = 0; i < r.list.length; i++, level++) {
							// 查询成功
							job.setValue(level, r.list[i].all, r.list[i].selected);
						}
					} else {
						layer.alert(r.msg);
					}
				},
				complete : function() {
					$(".region-mask").css("display", "none");
				}
			});
		}
	}
	if (typeof jobCode != "undefined") {
		if (jobCode == "0") {
			job.query("0", 1);
		} else {
			// 反向查询父级
			job.queryParent(jobCode);

		}
	} else {
		// 正向查询
		job.query("0", 1);
	}
})();