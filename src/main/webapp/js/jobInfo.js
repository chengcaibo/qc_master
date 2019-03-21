(function() {
	var jobInfo = {
		query : function(parentCode, nextLevel) {
			var t = 3;
			$.ajax({
				url : "/job_info/query/child",
				type : "GET",
				dataType : "JSON",
				data : {
					parentCode
				},
				beforeSend : function() {
					for (var i = nextLevel; i <= 3; i++) {
						$("select.job-" + i).empty();
					}
					$("select.job-" + nextLevel).append($('<option value="-1">查询中...</option>'));
				},
				success : function(r) {
					t = 4;
					if (r.ret == 0) {
						// 查询成功
						jobInfo.setValue(nextLevel, r.list);
						// 重新绑定方法
						$("select.job-" + nextLevel).change(jobInfo.bindChange);
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
		},
		bindChange : function() {
			// 获取当前级别
			var name = $(this).attr("class");
			var level = parseInt(name.substring(name.length - 1, name.length));
			if (++level > 3 || $(this).val() == "-1") {
				return;
			}
			jobInfo.query($(this).val(), level);
		},
		queryParent : function(jobCode) {
			$.ajax({
				url : "/job_info/query/parent",
				type : "GET",
				dataType : "JSON",
				data : {
					childCode : jobCode
				},
				success (r) {
					if (r.ret == 0) {
						var level = 1;
						for (var i = 0; i < r.list.length; i++, level++) {
							//						for (var i = (r.list.length - 1); (i >= 0 && level <= 3) && (i < r.list.length); i--, level++) {
							// 查询成功
							jobInfo.setValue(level, r.list[i].all, r.list[i].selected);
							// 重新绑定方法
							$("select.job-" + (i + 1)).change(jobInfo.bindChange);
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

	if (jobCode) {
		if (jobCode == "0") {
			// 正向查询
			jobInfo.query("0", 1);
		} else {
			// 反向查询父级
			jobInfo.queryParent(jobCode);
		}
	} else {
		// 正向查询
		jobInfo.query("0", 1);
	}
})();