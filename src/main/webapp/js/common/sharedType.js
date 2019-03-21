var shared = {
	query : function(code, nextLevel) {
		var t = 3;
		$.ajax({
			url : `/shared_type/query/child`,
			type : "GET",
			dataType : "JSON",
			data : {
				parentCode : code
			},
			beforeSend : function() {
				for (var i = nextLevel; i <= 3; i++) {
					$("select.shared-" + i).empty();
				}
				$("select.shared-" + nextLevel).append($('<option value="-1">查询中...</option>'));
			},
			success : function(r) {
				t = 4;
				if (r.ret == 0) {
					// 查询成功
					shared.setValue(nextLevel, r.list);
					// 重新绑定方法
					$("select.shared-" + nextLevel).change(shared.bindChange);
				} else {
					alert(r.msg);
				}
			}
		})
	},
	setValue : function(level, list, selected) {
		var obj = $("select.shared-" + level).empty();
		list.unshift({
			code : -1,
			name : "请选择..."
		})
		for (var i = 0; i < list.length; i++) {
			var temp = $("<option value='" + list[i].code + "'>" + list[i].name + "</option>");
			if (selected && list[i].code == selected) {
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
		shared.query($(this).val(), level);
	},
	queryParent : function(code) {
		$.ajax({
			url : `/shared_type/query/parent`,
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
						shared.setValue(level, r.list[i].all, r.list[i].selected);
						// 重新绑定方法
						$("select.shared-" + (i + 1)).change(shared.bindChange);
					}
				} else {
					alert(r.msg);
				}
			},
			complete : function() {
				$(".shared-mask").css("display", "none");
			}
		});
	}
}
