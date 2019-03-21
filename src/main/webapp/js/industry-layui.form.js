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
					form.render();
				},
				success : function(r) {
					t = 4;
					if (r.ret == 0) {
						// 查询成功
						industry.setValue(nextLevel, r.list);
						// 重新绑定方法
						// $("select.industry-" + nextLevel).change(industry.bindChange);
						form.on('select(industry)', function(data) {
							//	console.log(data.elem); //得到select原始DOM对象
							// console.log(data.value); //得到被选中的值
							// console.log(data.othis); //得到美化后的DOM对象
							industry.bindChange(data.elem);
						});
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
			industry.query(this_.val(), level);
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
						// 监听select选择
						// $("select.industry-" + (i + 1)).change(industry.bindChange);
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
	if (typeof industryCode != "undefined") {
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