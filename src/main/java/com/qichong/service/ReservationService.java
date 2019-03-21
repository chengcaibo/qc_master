package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.ReservationDao;
import com.qichong.entity.Reservation;

@Service
public class ReservationService {
	@Autowired
	ReservationDao reservationDao;
	
	/**
	 * 新增一条预定信息
	 * @param reservation
	 * @return
	 */
	public boolean insertReservation(Reservation reservation){
		return reservationDao.insertReservation(reservation)>0;
	}
	/**
	 * 预定成功后删除多余的预定信息
	 * @param locationId
	 * @return
	 */
	public boolean deleteLocation(int locationId){
		return reservationDao.deleteLocation(locationId)>0;
	}
	/**
	 *  查询预定位置为几依此来添加结束时间
	 * @return
	 */
	public int  byLocationIdCount(){
		return reservationDao.byLocationIdCount();
	}
	/**
	 * 查询待审核的预定位置数据
	 * @return
	 */
	public List<Reservation> selectReservation(){
		return reservationDao.selectReservation();
	}
}
