package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.DeskDisplayDao;
import com.qichong.entity.DeskDisplay;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.PersonalTopOneModel;

/**
 * DeskDisplayService
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午5:42:30
 */
@Service
public class DeskDisplayService {

	@Autowired
	DeskDisplayDao deskDisplayDao;

	/**
	 * 查询个人置顶
	 */
	public PersonalTopOneModel queryPersonalTop() {

		return deskDisplayDao.queryPersonalTop();
	}

	/**
	 * 查询企业置顶
	 */
	public EnterpriseTopOneModel queryEnterpriseTop() {

		return deskDisplayDao.queryEnterpriseTop();
	}

	/**
	 * 查询三个的企业
	 */
	public List<EnterpriseTopOneModel> queryEnterpriseThree() {
		return deskDisplayDao.queryEnterpriseThree();
	}

	/**
	 * 查询六个的企业
	 */
	public List<EnterpriseTopOneModel> queryEnterpriseSix() {

		return deskDisplayDao.queryEnterpriseSix();
	}

	/**
	 * 查询十个的企业
	 */
	public List<EnterpriseTopOneModel> queryEnterpriseTen() {

		return deskDisplayDao.queryEnterpriseTen();
	}

	/**
	 * 新增用户置顶
	 */
	public boolean insertDeskDisplay(DeskDisplay deskDisplay) {

		return deskDisplayDao.insertDeskDisplay(deskDisplay);
	}

	/**
	 * 
	 * @param locationId
	 * @return int 查看是否有可抢位置
	 */
	public int loctionCount(int locationId) {
		return deskDisplayDao.locationCount(locationId);
	}

	/**
	 * 
	 * @param locationId
	 * @return 查询结束时间
	 */
	public List<DeskDisplay> locationList(int locationId) {
		return deskDisplayDao.locationList(locationId);
	}

	/**
	 * 
	 * @param deskDisplay
	 * @return boolean 修改位置信息
	 */
	public boolean updateDeskDisplay(DeskDisplay deskDisplay) {
		return deskDisplayDao.updateDeskDisplay(deskDisplay);
	}

	public List<DeskDisplay> deskDisplayCheck() {
		return deskDisplayDao.deskDisplayCheck();
	}

	/**
	 * 
	 * @param deskDisplay
	 * @return boolean 修改位置信息
	 */
	public boolean reservationUpdateDeskdisplay(DeskDisplay deskDisplay) {
		return deskDisplayDao.reservationUpdateDeskdisplay(deskDisplay);
	}
	
}
