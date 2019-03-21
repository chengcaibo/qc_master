package com.qichong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.ShareService;
import com.qichong.service.WalletService;
import com.qichong.util.web.ServletUtil;

/**
 * 福利分享的Controller
 * 
 * @创建人 徐龙洋
 * @修改人 徐龙洋
 * @修改时间 2018年9月14日
 */
@Controller
@RequestMapping(produces = ServletUtil.JSON_PRODUCES)
public class ShareController {

	@Autowired
	ShareService shareSer;
	@Autowired
	WalletService walletSer;

	/** 记录分享次数（Share） */
	@RequestMapping(path = "/share/add_share_num", method = RequestMethod.GET)
	@ResponseBody
	public String doShare(Integer user_id, String yaoqingma) {
		if (shareSer.addShareServiceNum(user_id, yaoqingma)) {
			return new JSONResult().setValue(RetEnum.SUCCESS);
		} else {
			return new JSONResult().setValue(RetEnum.ERROR, "未知错误");
		}
	}

	/** 邀请人获得5毛现金（Share_Money） */
	@RequestMapping(path = "/share/yaoqing_add_money")
	@ResponseBody
	public String doYaoqingma(String yaoqingma, Integer user_id) {

		// int update_userid = 0;
		if (shareSer.queryAddBalanceUseridCount(yaoqingma, user_id) > 0) {
			// update_userid = shareSer.queryAddBalanceUserid(yaoqingma,
			// user_id);

			if (shareSer.queryAddBalanceUserid(yaoqingma, user_id) > 0) {
				walletSer.plusBalance(shareSer.queryAddBalanceUserid(yaoqingma, user_id), 0.5, "新注册用户邀请人获得0.5元奖励");

				if (shareSer.addYaoqingMoney(user_id)) {
					return new JSONResult().setValue(RetEnum.SUCCESS);
				} else {
					return new JSONResult().setValue(RetEnum.ERROR, "未知错误");
				}
			} else {
				return new JSONResult().setValue(RetEnum.ERROR, "未知错误");
			}
		} else {
			return new JSONResult().setValue(RetEnum.ERROR, "你已填写过邀请码，不能再次填写");
		}

	}
}
