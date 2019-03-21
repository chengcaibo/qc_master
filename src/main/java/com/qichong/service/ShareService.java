package com.qichong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.ShareDao;

/**
 * 
 * Share 服务
 * 
 * @创建人 徐龙洋
 * @修改人 徐龙洋
 * @修改时间 2018-4-17
 */
@Service
public class ShareService {
	@Autowired
	private ShareDao shareDao;

	public boolean addShareServiceNum(Integer user_id, String yaoqingma) {
		return shareDao.addShareNum(user_id, yaoqingma) > 0;

	}

	public boolean addYaoqingMoney(Integer user_id) {

		return shareDao.addYaoqingMoney(user_id) > 0;

	}

	public int queryAddBalanceUserid(String yaoqingma, Integer user_id) {

		return shareDao.queryAddBalanceUserid(yaoqingma, user_id);

	}

	public int queryAddBalanceUseridCount(String yaoqingma, Integer user_id) {

		return shareDao.queryAddBalanceUseridCount(yaoqingma, user_id);

	}
}
