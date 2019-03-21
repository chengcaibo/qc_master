package com.qichong.dao;

import java.util.List;

import com.qichong.entity.CertificationEnterprise;
import com.qichong.model.Filters;
import org.apache.ibatis.annotations.Update;

public interface CertificationEnterpriseDao {

	/** 查询出所有的信息 */
	List<CertificationEnterprise> selectAll(Filters filter);

	int selectAllCount(Filters filter);

	/** 查询一条信息，根据UserId */
	// CertificationInfo selectOneByUserId(int userId);

	/** 插入一条信息 */
	int insertOne(CertificationEnterprise ci);

	/** 根据记录Id更新一条信息 */
	int updateOne(CertificationEnterprise ci);
	/**
	 * 根据userId更新一条信息
	 */
	int byUserIdUpdateOne(CertificationEnterprise ci);

	/** 删除一条信息 */
	int deleteOne(int id);
	@Update("UPDATE certification_info set isAgree=1 where user_id=#{userId}")
    Integer updateIsAgree(Integer userId);
}
