(function() {
	// jquery 查看大图插件
	$("#viewer").viewer();
	// 驳回按钮
	$("button[name='rejected']").click((event) => {
		let mu;
		layer.prompt({
			title : "请输入驳回理由",
			end () {
				layer.close(mu);
			}
		}, function(value, index, elem) {
			if (value) {
				layer.close(index);
				submit("驳回", "rejected", value);
				return;
			}
		});

		let tips = [];

		tips.push("您的身份证件照片不清晰");
		tips.push("您的身份证件正面照片不清晰");
		tips.push("您的身份证件反面照片不清晰");
		tips.push("您的手持身份证件照片不清晰");
		tips.push("您输入的身份证号码与照片中的号码不符");
		tips.push("您的身份证已过期");
		tips.push("请不要使用临时身份证");
		tips.push("请使用十八位的二代身份证");
		tips.push("请使用中华人民共和国身份证");

		let html = '<ul id="tips">';
		for (let i in tips) {
			html += `<li data-clipboard-text="${tips[i]}">${parseInt(i) + 1}.${tips[i]}</li>`;
		}
		html += '</ul>';

		mu = layer.open({
			title : '驳回理由模板示例（双击复制）',
			btn : [],
			closeBtn : 0,
			offset : 'l',
			shade : false,
			content : html,
			success () {
				let tip = -1;
				$("#tips").mousemove(function() {
					layer.close(tip)
				});
				$("#tips li").click(function(event) {
					new Clipboard(this);
				});
				$("#tips li").dblclick(function(event) {
					tip = layer.tips('复制成功~', this, {
						tips : 1
					});
				});
			}
		});

	});
	// 暂定按钮
	$("button[name='notSure']").click(() => {
		submit("审批下一条", "notSure");
	});
	// 通过按钮
	$("button[name='passed']").click(() => {
		submit("通过", "passed");
	});
	// 按钮统一事件
	function submit(text, button, reason) {
		layer.confirm(`确定要${text}吗？`, {
			icon : 0
		}, function(index) {
			layer.close(index);
			$.ajax({
				url : "/admin/cert_action",
				type : "POST",
				data : {
					id ,
					button ,
					reason
				},
				success (res) {
					if (res.ret == qc.retEnum.SUCCESS) {
						let common = (text, nextNow) => {
							if (res.result != -1) {
								if (nextNow) {
									window.location.href = `/admin/audits/realname/${res.result}`;
									return;
								}
								layer.alert(`${text},点击确定进入下一条审核。`, {
									icon : 1
								}, () => {
									window.location.href = `/admin/audits/realname/${res.result}`;
								});
							} else {
								let alertText = `${text}，所有待审核的资料都已经审核完了，点击确定返回主页`;
								if (button == "notSure") {
									alertText = `已经是最后一条审核了，点击确定返回主页`;
								}
								layer.alert(alertText, {
									icon : 6
								}, () => {
									window.location.href = "/admin/audits/realname";
								});
							}
						};
						if (button == "passed") {
							common("已成功通过审核");
						} else if (button == "notSure") {
							common("恭喜你", true);
						} else {
							common("已成功<b style='color:red;'>驳回</b>审核");
						}
					} else if (res.ret == qc.retEnum.NO_LOGIN) {
						window.location.href = "/admin/audits/realname";
					} else {
						alert("系统出现异常");
						window.location.reload();
					}
				},
				error () {
					alert("服务器出现异常");
					window.location.reload();
				}
			})
		});
	}
})()