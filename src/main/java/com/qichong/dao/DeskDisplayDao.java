package com.qichong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.DeskDisplay;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.PersonalTopOneModel;

/**
 * 前台显示实体类接口
 * 
 * @author Administrator
 *
 */

public interface DeskDisplayDao {

	/**
	 * 查询个人置顶
	 */
	PersonalTopOneModel queryPersonalTop();

	/**
	 * 查询企业置顶
	 * 
	 * @return EnterpriseTopOneModel
	 */
	EnterpriseTopOneModel queryEnterpriseTop();
	/**
	 * 
	 * @return List<EnterpriseTopOneModel>
	 * 查询三个位置的企业信息
	 * 
	 */
	List<EnterpriseTopOneModel> queryEnterpriseThree();
	/**
	 * 
	 * @return List<EnterpriseTopOneModel>
	 * 查询六个位置的企业信息
	 * 
	 */
	List<EnterpriseTopOneModel> queryEnterpriseSix();
	/**
	 * 
	 * @return List<EnterpriseTopOneModel>
	 * 查询十个位置的企业信息
	 * 
	 */
	List<EnterpriseTopOneModel> queryEnterpriseTen();
	/**
	 * 新增用户置顶
	 * 
	 * @param deskDisplay
	 * @return boolean
	 */
	boolean insertDeskDisplay(@Param("deskDisplay") DeskDisplay deskDisplay);
	/**
	 * 
	 * @param loctionId
	 * @return int数值 
	 * 用来判断是否有可抢的位置
	 */
	int locationCount(@Param("locationId")int locationId);
	/**
	 * 
	 * @param locationId
	 * @return list
	 * 查询某个位置的结束时间
	 */
	List<DeskDisplay> locationList(@Param("locationId")int locationId);
	/**
	 * 
	 * @param deskDisplay
	 * @return boolean
	 */
	boolean updateDeskDisplay(DeskDisplay deskDisplay);
	
	/**
	 * 审核通过修改
	 * @param deskDisplay
	 * @return boolean
	 */
	boolean reservationUpdateDeskdisplay(DeskDisplay deskDisplay);
	/**
	 * 查询是否已有所抢位置
	 * @param userId
	 * @return
	 */
	List<DeskDisplay> deskDisplayCheck();

}
