package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.AppintmentTime;
import com.qichong.model.Filters;

/**
 * 预约时间Dao接口
 * 
 * @author Administrator
 */

public interface AppintmentTimeDao {

	/** 查询所有【预约时间】并且筛选 */
	List<AppintmentTime> selectAllByFilter(Filters filters);

	/** 根据用户id和选择的开始日期和结束日期查询 */
	AppintmentTime selAppintmentByTime(@Param("userId") Integer userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	int updateSelectedTime(AppintmentTime at);

	/** 添加一个预约时间 */
	int insertOne(AppintmentTime appintmentTime);

	/** 添加多个预约时间 */
	int insertList(@Param("times") List<AppintmentTime> times);

	/** 删除一个预约时间 */
	int deleteOne(@Param("id") int id, @Param("userId") Integer userId);

	/** 修改一个预约时间 */
	int updateOne(AppintmentTime appintmentTime);

	/** 根据id查询用户的可预约时间 */
	List<AppintmentTime> selectListByUserId(int userId);

	/** 根据个人id 查询出个人预约时间 */
	List<AppintmentTime> queryUserTime(int userId);

	/** 修改预约时间 */
	boolean updateAppintmentTime(AppintmentTime appintmentTime);

	/** 修改预约时间 */
	boolean updateATime(AppintmentTime appintmentTime);
}
