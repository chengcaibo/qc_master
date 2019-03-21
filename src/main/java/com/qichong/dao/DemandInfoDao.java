package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.DemandInfo;
import com.qichong.model.DemandInfoModel;
import com.qichong.model.Filters;

/**
 * 需求（demand）持久层接口
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2018年4月21日
 */
public interface DemandInfoDao {

	/** 查询所有【需求】信息并且筛选 */
	List<DemandInfo> selectAllByFilter(Filters filters);

	/** 查询所有【需求】信息并且筛选的总数量 */
	int selectAllByFilterTotal(Filters filters);

	/** 查询最新注册的三用户发布的需求 */
	public List<DemandInfoModel> demandPreferred();

	/** 新增需求 */
	public int insertOne(DemandInfo demandInfo);

	/** 修改需求 */
	public int updateOne(DemandInfo demandInfo);

	public int deleteOne(@Param("id") int id, @Param("userId") Integer loginUserId);

	/** 根据开始和结束查询需求要求 */
	public List<DemandInfoModel> maxFive(@Param("limit") int limit, @Param("offset") int offset,
			@Param("city") String city);

}
