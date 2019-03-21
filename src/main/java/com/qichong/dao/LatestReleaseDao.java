package com.qichong.dao;

import java.util.List;

import com.qichong.entity.DemandInfo;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.GroupInfo;
import com.qichong.entity.UserInfo;

/**
 * 查询最新发布信息表
 * 
 * @author 孙建雷
 */
public interface LatestReleaseDao {

	/** 查询新发布的个人 */
	List<UserInfo> selectLatestUserInfo();

	/** 查询新发布的企业 */
	List<EnterpriseInfo> selectLatestEnterpriseInfo();

	/** 查询新发布的需求 */
	List<DemandInfo> selectLatestDemandInfo();

	/** 查询新发布的团体 */
	List<GroupInfo> selectLatestGroupInfo();

}
