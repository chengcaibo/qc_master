/**
 * ent-real-name-index.js 用于主页的企业实名认证js
 */

//layui.use('laypage', function() {
// layui 分页对象
// let laypage = layui.laypage;

// 查询对象
let query = {
	/** 获取地址 */
	getUrl (url) {
		if (url) {
			//https://img.qc1318.com/img/ent/license/383_license.jpg
			let index = url.lastIndexOf("?");
			if (index != -1) {
				var param = "&x-oss-process=image/resize,w_100";
			} else {
				var param = "?x-oss-process=image/resize,w_100";
			}
			return url + param;
		}
		return "";
	},
	// 公共的查询方法，method：要查询的内容
	commonQuery (method, this_) {
		let loading = layer.load(1); // layui 加载框
		$.ajax({
			url : `/admin/enterprise/query_certification_${method}`,
			data : {
				pageNum : this_.page.num,
				pageSize : this_.page.size
			},
			success (res) {
				if (res.ret == qc.retEnum.NO_LOGIN) {
					window.location.reload();
					return;
				}
				// layui 的分页
				if (this_.page.first) {
					this_.page.first = false;
					laypage.render({
						elem : this_.page.elem,
						count : res.total,
						limit : this_.page.size,
						jump : function(obj, first) {
							//首次不执行
							if (!first) {
								this_.page.num = obj.curr; // 当前点击的页码
								this_.queryNext();
							}
						}
					});
				}

				$(`#cert-enterprise-${method}-count`).text(res.total);
				// 遍历
				$(`#cert-enterprise-${method}-tbody`).empty();
				var list = res.list;
				if (list.length != 0) {
					for (let i in list)
						$(`#cert-enterprise-${method}-tbody`).append(this_.template.one(list[i]));
				} else {
					$(`#cert-enterprise-${method}-tbody`).append($(`#cert-enterprise-${method}-tbody`).append(this_.template.two()));
				}
			},
			complete () {
				layer.close(loading)
			}
		});
	},
	// wait，等待中的
	certWait : {
		page : {
			elem : "cert-enterprise-wait-page",
			size : 5,
			num : 1,
			first : true,
		},
		// 页面模板
		template : {
			// 有数据的
			one (info) {
				return `
							<tr>
								<td>${info.id}</td>
								<td>${info.enterpriseName}</td>
								<td>${info.personName}</td>
								<td class="cert-img"><img alt="企业营业执照" src="${query.getUrl(info.businessLicense.url)}"></td>
								<td>${new Date(info.createTime).format("yyyy年MM月dd日 hh:mm:ss")}</td>
								<td><a href="/admin/audits/enterprise/${info.id}" class="layui-btn layui-btn-normal">进入审核</a></td>
							</tr>`;
			},
			// 没有数据的
			two () {
				return `
							<tr>
								<td colspan="6" class="common-nodata">呼~休息一下，暂时没有任何数据了！</td>
							</tr>
							`;
			},
		},
		// 查询下一页
		queryNext () {
			query.commonQuery("wait", query.certWait);
		}
	},
	// passed 通过的
	certPassed : {
		page : {
			elem : "cert-enterprise-passed-page",
			size : 5,
			num : 1,
			first : true,
		},
		template : {
			one (info) {
				return `
							<tr>
								<td>${info.id}</td>
								<td>${info.enterpriseName}</td>
								<td>${info.personName}</td>
								<td class="cert-img"><img alt="企业营业执照" src="${query.getUrl(info.businessLicense.url)}"></td>
								<td>${new Date(info.createTime).format("yyyy年MM月dd日 hh:mm:ss")}</td>
								<td>${new Date(info.editTime).format("yyyy年MM月dd日 hh:mm:ss")}</td>
							</tr>`;
			},
			two () {
				return `
							<tr>
								<td colspan="6" class="common-nodata">呼~休息一下，暂时没有任何数据了！</td>
							</tr>
							`;
			},
		},
		queryNext () {
			query.commonQuery("passed", query.certPassed);
		}
	},
	// not passed 未通过（驳回）
	certNotPassed : {
		page : {
			elem : "cert-enterprise-not-passed-page",
			size : 5,
			num : 1,
			first : true,
		},
		template : {
			one (info) {
				return `
							<tr>
								<td>${info.id}</td>
								<td>${info.enterpriseName}</td>
								<td>${info.personName}</td>
								<td class="cert-img"><img alt="企业营业执照" src="${query.getUrl(info.businessLicense.url)}"></td>
								<td>${new Date(info.editTime).format("yyyy年MM月dd日 hh:mm:ss")}</td>
								<td>${info.reason}</td>
							</tr>`;
			},
			two () {
				return `
							<tr>
								<td colspan="6" class="common-nodata">呼~休息一下，暂时没有任何数据了！</td>
							</tr>
							`;
			},
		},
		queryNext () {
			query.commonQuery("not-passed", query.certNotPassed);
		}
	}
};


// 认证certification刷新按钮
$("#btn-reload").click(function() {
	query.certWait.queryNext();
	query.certPassed.queryNext();
	query.certNotPassed.queryNext();
	$(this).parent().removeClass("layui-this");
}).click().parent().css("display", "inline-block");

//});