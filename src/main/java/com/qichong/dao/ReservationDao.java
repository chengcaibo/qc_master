package com.qichong.dao;



import java.util.List;

import com.qichong.entity.Reservation;

/**
 * 预定位置表
 * @创建人 吴志伟
 */
public interface ReservationDao {
	/**
	 * 新增一条预定信息
	 * @param reservation
	 * @return
	 */
	int insertReservation(Reservation reservation);
	/**
	 * 删除未通过的预定信息
	 * @param locationaId
	 * @return
	 */
	int deleteLocation(int locationaId);
	/**
	 *  查询预定位置为几依次来添加结束时间
	 * @return
	 */
	int  byLocationIdCount();
	/**
	 * 查询待审核的预定信息
	 * @return
	 */
	List<Reservation> selectReservation();
}
