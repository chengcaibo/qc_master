package com.qichong.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.MessageOfflineDao;
import com.qichong.entity.MessageOffline;
import com.qichong.entity.State;
import com.qichong.model.Filters;

/**
 * 离线消息Service层
 *
 * @创建人 孙建雷
 */
@Service
public class MessageOfflineService {

	@Autowired
	MessageOfflineDao dao;

	/** 搜索 */
	public List<MessageOffline> search(Filters filters) {
		return this.selectList(filters);
	}

	/** 根据userId查询多条消息 */
	public List<MessageOffline> queryListByUserId(Integer userIdA, Integer userIdB) {
		if (userIdA == null && userIdB == null)
			return null;
		// 构造Filters
		Filters f = Filters.builder().setUserIdA(userIdA).setUserIdB(userIdB);
		return this.selectList(f);
	}

	/** 根据userIdB查询多条离线消息消息 */
	public List<MessageOffline> queryListOfflineMessageByUserIdB(Integer userIdB) {
		if (userIdB == null)
			return null;
		// 构造Filters
		Filters f = Filters.builder().setStateId(1).setUserIdB(userIdB);
		return this.selectList(f);
	}

	/** 根据id查询离线消息 */
	public MessageOffline queryOneById(int id) {
		Filters f = Filters.builder().setId(id);
		return this.selectOne(f);
	}

	/** 插入一条消息 */
	public boolean insert(MessageOffline msgoff) {
		// 设置默认值
		msgoff.setCreateTime(new Date());
		msgoff.setState(new State(1));
		return dao.insertOne(msgoff) > 0;
	}

	/** 删除消息 */
	public boolean delete(int id) {
		return dao.deleteOne(id) > 0;
	}

	/** 删除多条消息 */
	public boolean delete(List<Integer> ids) {
		return dao.deleteList(ids) > 0;
	}

	public boolean deleteByUserId(int userId) {
		return dao.deleteListByUserIdB(userId) > 0;
	}

	/*- - - - - - - - - - - - 私有方法 - - - - - - - - - - -*/

	/** 选择多条记录 */
	private List<MessageOffline> selectList(Filters filters) {
		return dao.selectAllByFilter(filters);
	}

	/** 选择一条记录 */
	private MessageOffline selectOne(Filters filters) {
		List<MessageOffline> list = this.selectList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}
}
