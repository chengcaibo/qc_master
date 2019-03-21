package com.qichong.controller;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qichong.entity.Users;
import com.qichong.entity.WalletDetail;
import com.qichong.entity.Wallets;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.UsersService;
import com.qichong.service.WalletService;
import com.qichong.token.LoginToken;
import com.qichong.util.web.ServletUtil;

/**
 * 钱包控制层
 * 
 * @author 孙建雷
 *
 */
@RestController
@RequestMapping(path = "/wallet", produces = ServletUtil.JSON_PRODUCES)
public class WalletController {

	@Autowired
	UsersService usersService;

	@Autowired
	WalletService walletService;

	/** 删除明细 */
	@RequestMapping(path = "/detail/delete", method = RequestMethod.POST)
	public String doDetailDelete(Integer id, HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		if (id == null)
			return JSONResult.paramLack().toJSON();
		return walletService.removeOneDetail(id, loginUser.getId()).toJSON();
	}

	// Wallet

	/**
	 * 
	 * @param jsCode
	 *            wx.login获取的jsCode
	 * @param money
	 *            要充值的金额
	 * 
	 * @param tokenKey
	 * @param keepCode
	 * @return
	 */
	@RequestMapping(path = "/recharge/balance", method = RequestMethod.POST)
	public String doRechargeBalance(String jsCode, Double money, HttpSession session, LoginToken token) {
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.noLogin().toJSON();
		if (isEmpty(jsCode) || money == null)
			return JSONResult.paramLack().toJSON();
		try {
			return walletService.rechargeBalance(loginUser.getId(), jsCode, money).toJSON();
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}
	}

	/**
	 * 查询我的余额 <br>
	 * 所需参数：
	 * 
	 * @param tokenKey
	 * @param keepCode
	 */
	@RequestMapping(path = "/balance/self")
	public String doBalance(HttpSession session, LoginToken token) {
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		try {
			Wallets wallet = walletService.queryOneMustHaveValue(loginUser.getId());
			return JSONResult.builder(RetEnum.SUCCESS).setResult(wallet).toJSON();
		} catch (Exception e) {
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage()).toJSON();
		}
	}

	/**
	 * 查询余额明细 <br>
	 * 所需参数：
	 * 
	 * @param tokenKey
	 * @param keepCode
	 */
	@RequestMapping(path = "/detail/self")
	public String doDetail(HttpSession session, LoginToken token) {
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null)
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		List<WalletDetail> wds = walletService.queryBalanceDetailByUserId(loginUser.getId());
		return JSONResult.builder(RetEnum.SUCCESS).setList(wds).toJSON();
	}

}
