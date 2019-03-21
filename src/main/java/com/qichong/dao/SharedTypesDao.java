package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.SharedTypes;
import com.qichong.model.ChildParentModel;
import com.qichong.model.Filters;

/**
 * 
 * SharedTypes 持久层
 * 
 * @修改人 孙建雷
 * @修改时间 2018-4-17
 */
public interface SharedTypesDao {

	/** 根据关键字模糊选择所有的项，可进行分页 */
	List<ChildParentModel> selectLikeAll(Filters filters);

	/** 根据传入的code选择单个项 */
	SharedTypes selectOne(String code);

	/** 根据父级Code查询所有子级 */
	List<SharedTypes> selectChildByParentCode( //
			@Param("parentCode") String parentCode, // 根据单个parentCode进行查询
			@Param("parentCodes") List<String> parentCodes// 根据多个parentCode进行查询，如果没有多个，就可以传入null
	);
}
