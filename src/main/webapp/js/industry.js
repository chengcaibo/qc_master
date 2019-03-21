(function() {
	var industry = {
		query : function(parentCode, nextLevel) {
			var t = 3;
			$.ajax({
				url : "/industry/query/child",
				type : "GET",
				dataType : "JSON",
				data : {
					parentCode : parentCode
				},
				beforeSend : function() {
					for (var i = nextLevel; i <= 3; i++) {
						$("select.industry-" + i).empty();
					}
					$("select.industry-" + nextLevel).append($('<option value="-1">查询中...</option>'));
				},
				success : function(r) {
					t = 4;
					if (r.ret == 0) {
						// 查询成功
						industry.setValue(nextLevel, r.list);
						// 重新绑定方法
						$("select.industry-" + nextLevel).change(industry.bindChange);
					} else {
						layer.alert(r.msg);
					}
				}
			})
		},
		setValue : function(level, list, selected) {
			var obj = $("select.industry-" + level).empty();
			obj.append($("<option value='-1'>请选择...</option>"))
			for (var i = 0; i < list.length; i++) {
				var temp = $("<option value='" + list[i].industryCode + "'>" + list[i].industryName + "</option>");
				if (selected && list[i].industryCode == selected) {
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
			industry.query($(this).val(), level);
		},
		industryCode : function(code) {
			$.ajax({
				url : "/industry/query/parent",
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
							industry.setValue(level, r.list[i].all, r.list[i].selected);
							// 重新绑定方法
							$("select.industry-" + (i + 1)).change(industry.bindChange);
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
	if (industryCode) {
		if (industryCode == "0") {
			industry.query("0", 1);
		} else {
			// 反向查询父级
			industry.industryCode(industryCode);

		}
	} else {
		// 正向查询
		industry.query("0", 1);
	}
})();