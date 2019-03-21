package com.qichong.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qichong.entity.IndustryTop;
import com.qichong.entity.Users;
import com.qichong.service.IndustryTopService;
import com.qichong.util.Utils;

/**
 * 行业置顶
 * 
 * @创建人：陈文瑾 @创建时间：2017-12-19 13:13:24
 */
@Controller
public class IndustryTopController {
	@Autowired
	IndustryTopService industryTopService;

	@RequestMapping(path = "/enterpriseTop")
	public String EnPosition(HttpSession session, String industryCount, String industryCode, Model model) {
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null) {
			return "sign/login";
		}
		if (user.getTypeId() == 2) {
			IndustryTop industryTop = null;
			if (industryTopService.industryCount(industryCount) <= 2) {
				List<IndustryTop> list = industryTopService.industryCheck(industryCode);
				int i = 0;
				for (i = 0; i < list.size(); i++) { // 获取时间戳
					long endTime = list.get(i).getEndTime().getTime();
					long thisDate = System.currentTimeMillis();
					if (thisDate > endTime) {
						industryTop = list.get(i);
						break;
					}
				}
				if (industryTop != null) { // 计算结束时间
					Date endTime = Utils.calcDate(new Date(), "+", 30, "天");
					industryTop = new IndustryTop(list.get(i).getId(), user.getId(), endTime);
					List<IndustryTop> listTop = industryTopService.queryAllUserId();
					for (int j = 0; j < listTop.size(); j++) {
						if (user.getId() == listTop.get(j).getUser().getId()) {
							long endTime1 = listTop.get(j).getEndTime().getTime();
							long thisDate = System.currentTimeMillis();
							if (thisDate < endTime1) {
								continue;
							} else {
								return "function/seckillError";
							}
						}
					}
					if (industryTopService.updateIndustryTop(industryTop)) {
						return "function/success-includes";
					} else {
						return "fail";
					}
				} else {
					List<IndustryTop> industryList = industryTopService.industryCheck(industryCode);
					model.addAttribute("industryList", industryList);
					return "function/position";
				}
			} else {
				return "index";
			}
		}
		return "common/four-zero-four";

	}

}