package com.qichong.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qichong.dao.OrderBalanceDao;
import com.qichong.dao.WalletDetailDao;
import com.qichong.dao.WalletsDao;
import com.qichong.entity.OrderBalance;
import com.qichong.entity.WalletDetail;
import com.qichong.entity.Wallets;
import com.qichong.enums.OrderPrefix;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.util.Utils;
import com.qichong.util.wechat.mini.WeChatMiniUtil;
import com.qichong.util.wechat.mini.WeChatMiniPayUtil;

@Service
public class WalletService {

	@Autowired
	WalletsDao dao;

	@Autowired
	WalletDetailDao detailDao;

	@Autowired
	OrderBalanceDao orderBalanceDao;

	/** 删除订单详情 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult removeOneDetail(int id, int userId) {
		if (detailDao.deleteOne(id, userId) > 0) {
			return JSONResult.builder(RetEnum.SUCCESS, "删除成功");
		} else {
			return JSONResult.builder(RetEnum.AUTH_ERROR, "没有权限");
		}
	}

	/**
	 * 
	 * 充值余额下单接口
	 * 
	 * @param userId
	 *            要充值的用户id
	 * @param jsCode
	 *            从小程序获取的jsCode
	 * @param money
	 *            充值金额
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult rechargeBalance(int userId, String jsCode, double money) throws Exception {
		if (money <= 0)
			return JSONResult.builder(RetEnum.PARAM_ERROR, "充值的余额不能小于0");
		// 获取openID
		String openId = WeChatMiniUtil.codeToOpenId(jsCode).get("openId");
		String orderId = Utils.createNewOrderId(OrderPrefix.BALANCE);// 创建新的orderId
		String body = "余额充值(" + orderId + ")";
		// 预支付交易会话标识
		String prepayId = WeChatMiniPayUtil.placeOrder(openId, body, orderId, (double) money);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("orderId", orderId);
		resultMap.put("prepayId", prepayId);
		resultMap.put("paymentParams", WeChatMiniPayUtil.getClientSign(prepayId));
		OrderBalance ob = new OrderBalance(userId, orderId, prepayId, money);
		if (orderBalanceDao.insertOne(ob) <= 0)
			throw new RuntimeException("创建余额订单失败");
		return JSONResult.builder(RetEnum.SUCCESS).setResult(resultMap);
	}

	/**
	 * 减少余额
	 * 
	 * @param userId
	 *            要减少的用户
	 * @param diff
	 *            减少多少金额
	 * @param reason
	 *            减少的原因，例如预约付款
	 * @return true or false
	 */
	public boolean lessBalance(int userId, double diff, String reason) {
		/*
		 * Wallets wallet = new Wallets(userId); wallet.setLessBalance(diff);
		 * 
		 * WalletDetail walletDetail = new WalletDetail(userId, null, reason,
		 * null); return updateOne(wallet, walletDetail);
		 */
		return lessBalance(userId, null, diff, null, reason);
	}

	/**
	 * 减少余额
	 * 
	 * @param userId
	 *            要减少的用户
	 * @param orderId
	 *            订单Id
	 * @param diff
	 *            减少多少金额
	 * @param reason
	 *            减少的原因，例如预约付款
	 * @return true or false
	 */
	public boolean lessBalance(int userId, String orderId, double diff, String reason) {
		return lessBalance(userId, orderId, diff, null, reason);
	}

	/**
	 * 减少余额
	 * 
	 * @param userId
	 *            要减少的用户
	 * @param orderId
	 *            订单Id
	 * @param diff
	 *            减少多少金额
	 * @param reason
	 *            减少的原因，例如预约付款
	 * @param transactionId
	 *            微信支付订单号
	 * @return true or false
	 */
	public boolean lessBalance(int userId, String orderId, double diff, String transactionId, String reason) {
		Wallets wallet = new Wallets(userId);
		wallet.setLessBalance(diff);

		WalletDetail walletDetail = new WalletDetail(userId, orderId, reason, transactionId);
		return updateOne(wallet, walletDetail);
	}

	/**
	 * 增加余额
	 * 
	 * @param userId
	 *            要增加的用户
	 * @param diff
	 *            增加多少金额
	 * @param reason
	 *            增加的原因，例如用户手动充值余额
	 * @return true or false
	 */
	public boolean plusBalance(int userId, double diff, String reason) {
		// Wallets wallet = new Wallets();
		// wallet.setUserId(userId);
		// wallet.setPlusBalance(diff);
		//
		// WalletDetail walletDetail = new WalletDetail(userId, null, reason,
		// null);
		// return updateOne(wallet, walletDetail);
		return plusBalance(userId, null, diff, null, reason);
	}

	/**
	 * 增加余额
	 * 
	 * @param userId
	 *            要增加的用户
	 * @param orderId
	 *            订单Id
	 * @param diff
	 *            增加多少金额
	 * @param reason
	 *            增加的原因，例如用户手动充值余额
	 * 
	 * @return true or false
	 */
	public boolean plusBalance(int userId, String orderId, double diff, String reason) {
		return plusBalance(userId, orderId, diff, null, reason);
	}

	/**
	 * 增加余额
	 * 
	 * @param userId
	 *            要增加的用户
	 * @param orderId
	 *            订单Id
	 * @param diff
	 *            增加多少金额
	 * @param reason
	 *            增加的原因，例如用户手动充值余额
	 * @param transactionId
	 *            微信支付订单号
	 * @return true or false
	 */
	public boolean plusBalance(int userId, String orderId, double diff, String transactionId, String reason) {
		Wallets wallet = new Wallets(userId);
		wallet.setPlusBalance(diff);

		WalletDetail walletDetail = new WalletDetail(userId, orderId, reason, transactionId);
		return updateOne(wallet, walletDetail);
	}

	/** 更新一条数据，如果没有则插入 */
	@Transactional(rollbackFor = Exception.class)
	private boolean updateOne(Wallets wallet, WalletDetail walletDetail) {
		Wallets queryWallets = queryOneMustHaveValue(wallet.getUserId());
		double balance = queryWallets.getBalance(); // 查询出原余额
		Double plus = wallet.getPlusBalance();
		Double less = wallet.getLessBalance();
		// 计算余额
		if (plus != null)
			balance += plus;
		if (less != null)
			balance -= less;
		wallet.setPlusBalance(null);
		wallet.setLessBalance(null);
		wallet.setBalance(balance);

		int line = dao.updateOne(wallet);
		if (line == 0)
			throw new RuntimeException("更新" + wallet.getUserId() + "的Wallet信息失败");

		// 明细表中增加记录
		walletDetail.setPlus(plus == null ? 0 : plus);
		walletDetail.setLess(less == null ? 0 : less);
		walletDetail.setBalance(balance);
		line = detailDao.insertOne(walletDetail);
		if (line == 0)
			throw new RuntimeException("增加" + wallet.getUserId() + "的WalletDetail信息失败");
		return line > 0;
	}

	/** 根据userId查询余额明细 */
	public List<WalletDetail> queryBalanceDetailByUserId(int userId) {
		return detailDao.selectListByUserId(userId);
	}

	/** 查询一条数据并一定有值 */
	@Transactional(rollbackFor = Exception.class)
	public Wallets queryOneMustHaveValue(int userId) {
		Wallets wallet = dao.selectOneByUserId(userId);
		if (wallet == null) {
			Wallets temp = new Wallets();
			temp.setUserId(userId);
			dao.insertOne(temp);
			// 重新查询
			wallet = dao.selectOneByUserId(userId);
		}
		if (wallet == null)
			throw new RuntimeException("无法获取" + userId + "的Wallet信息");
		return wallet;
	}

	/**
	 *
	 * @param wallets 更新账户的余额，这里仅仅是通过app分享来获取红包的方式来更新余额
	 * @return
	 */
	public boolean updateAccount(Wallets wallets) {
		Integer ret = dao.updateOne(wallets);
		if(ret>0){
			return true;
		}else{
			return false;
		}
	}
}
