package com.qichong.dao;

import java.util.List;

import com.qichong.entity.CertificationInfo;
import com.qichong.model.Filters;
import org.apache.ibatis.annotations.Update;

/**
 * 认证表Dao层接口
 */
public interface CertificationInfoDao {

	/** 查询出所有的信息 */
	List<CertificationInfo> selectAll(Filters filter);

	int selectAllCount(Filters filter);

	/** 查询一条信息，根据UserId */
	// CertificationInfo selectOneByUserId(int userId);

	/** 插入一条信息 */
	int insertOne(CertificationInfo ci);

	/** 更新一条信息 */
	int updateOne(CertificationInfo ci);

	/** 删除一条信息 */
	int deleteOne(int id);
	
	/**查询没有发布过广告的认证用户（admin）*/
	List<CertificationInfo> selectUserByadPublic(Filters filter);
	@Update("UPDATE certification_info set isAgree=1 where user_id=#{userId}")
    Integer updateIsAgree(Integer userId);
}
