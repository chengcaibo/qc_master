package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.ToolsInfo;
import com.qichong.model.Filters;

/** 工具共享的实体类接口 */
public interface ToolsInfoDao {

	/** 查询并筛选 */
	List<ToolsInfo> selectAllByFilter(Filters filters);

	/** 查询单个的工具共享的详细信息 */
	ToolsInfo selectOneById(Filters filters);

	/** 发布工具共享 */
	int insertOne(ToolsInfo toolsInfo);

	/** 修改工具共享信息 */
	int updateOne(ToolsInfo toolsInfo);

	/** 删除一个工具 */
	int deleteOne(@Param("id") Integer id, @Param("userId") Integer userId);

}
