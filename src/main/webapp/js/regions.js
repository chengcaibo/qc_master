(function() {
	var region = {
		query : function(code, nextLevel) {
			var t = 3;
			$.ajax({
				url : "/region/query/child",
				type : "GET",
				dataType : "JSON",
				data : {
					parentCode : code
				},
				beforeSend : function() {
					for (var i = nextLevel; i <= 3; i++) {
						$("select.region-" + i).empty();
					}
					$("select.region-" + nextLevel).append($('<option value="-1">查询中...</option>'));
				},
				success : function(r) {
					t = 4;
					if (r.ret == 0) {
						// 查询成功
						region.setValue(nextLevel, r.list, "-1");
						// 重新绑定方法
						$("select.region-" + nextLevel).change(region.bindChange);
					} else {
						layer.alert(r.msg);
					}
				}
			})
		},
		setValue : function(level, list, selected) {
			var obj = $("select.region-" + level).empty();
			//			obj.append($("<option value='-1'>请选择...</option>"))
			list.unshift({
				regionCode : -1,
				regionName : "请选择..."
			})
			for (var i = 0; i < list.length; i++) {
				var temp = $("<option value='" + list[i].regionCode + "'>" + list[i].regionName + "</option>");
				if (selected && list[i].regionCode == selected) {
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
			region.query($(this).val(), level);
		},
		queryParent : function(code) {
			$.ajax({
				url : "/region/query/parent",
				type : "GET",
				dataType : "JSON",
				data : {
					childCode : code
				},
				success : function(r) {
					if (r.ret == 0) {
						var level = 1;
						for (var i = 0; i < r.list.length; i++, level++) {
							// 查询成功
							region.setValue(level, r.list[i].all, r.list[i].selected);
							// 重新绑定方法
							$("select.region-" + (i + 1)).change(region.bindChange);
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

	if (regionCode) {
		// 反向查询父级
		if (regionCode == "0") {
			region.query("0", 1);
		} else {
			region.queryParent(regionCode);
		}
	} else {
		// 正向查询
		region.query("0", 1);
	}
})();