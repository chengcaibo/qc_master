package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.JobInfo;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * JobInfo 持久层
 * 
 * @创建人 孙建雷
 * @修改时间 2018-4-13 15:55:54
 */
public interface JobInfoDao {

	/** 模糊查询职业 */
	List<ChildParentModel> selectLikeJob(Filters filters);

	/** 查询单个职业 */
	JobInfo selectOne(String jobCode);

	/** 根据父职业代码查询出全部的子职业 */
	List<JobInfo> selectChildByParentId( //
			@Param("parentCode") String parentCode, // 根据单个parentCode进行查询
			@Param("parentCodes") List<String> parentCodes // 根据多个parentCode进行查询，如果没有多个，就可以传入null
	);

}
