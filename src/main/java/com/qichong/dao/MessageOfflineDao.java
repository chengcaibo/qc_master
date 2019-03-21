package com.qichong.dao;

import java.util.List;

import com.qichong.entity.MessageOffline;
import com.qichong.model.Filters;

/**
 * 离线消息表持久层
 * 
 * @author 孙建雷
 */
public interface MessageOfflineDao {

	/** 查询所有的离线消息并筛选 */
	List<MessageOffline> selectAllByFilter(Filters filters);

	/** 插入一条记录 */
	int insertOne(MessageOffline msgoff);

	/** 根据Id删除一条记录 */
	int deleteOne(int id);

	/** 根据多个Id删除多条记录 */
	int deleteList(List<Integer> ids);

	/** 根据userIdB删除多条记录 */
	int deleteListByUserIdB(int userIdB);

}
