package com.qichong.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.AppintmentTimeDao;
import com.qichong.entity.AppintmentTime;
import com.qichong.entity.State;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;

/**
 * 
 * AppintmentTimeService
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午5:34:27
 */
@Service
public class AppintmentTimeService {

	@Autowired
	AppintmentTimeDao dao;

	/** 搜索多条记录 */
	private List<AppintmentTime> searchList(Filters filters) {
		return dao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private AppintmentTime searchOne(Filters filters) {
		List<AppintmentTime> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<AppintmentTime> search(Filters filters) {
		// 构造Filter
		return this.searchList(filters);
	}

	/** 根据Id查询单个预约 */
	public AppintmentTime queryOneById(int id) {
		Filters filters = new Filters();
		filters.setId(id);
		return this.searchOne(filters);
	}

	public List<AppintmentTime> queryListByUserId(int userId) {
		Filters filters = new Filters();
		filters.setUserId(userId);
		return this.searchList(filters);
	}

	/**  */
	public List<AppintmentTime> queryListByDate(Date date, int userId) {
		Filters filters = new Filters();
		filters.setUserId(userId);
		filters.setBeginTime(date);
		filters.setEndTime(new Date(date.getTime() + (1000 * 60 * 60 * 24) - 1000));
		return this.searchList(filters);
	}

	/** 根据用户id和选择的开始日期和结束日期查询 */
	public AppintmentTime selAppintmentByTime(Integer userId, String startTime, String endTime) {
		return dao.selAppintmentByTime(userId, startTime, endTime);
	}

	public boolean setSelectedTime(AppintmentTime at) {
		return dao.updateSelectedTime(at) > 0;
	}

	/** 根据userId查询发布的可预约时间 */
	public List<AppintmentTime> queryByUserId(int userId) {
		return dao.selectListByUserId(userId);
	}

	/** 根据用户id 查询出个人预约时间 */
	public List<AppintmentTime> queryUserTime(int userId) {
		return dao.queryUserTime(userId);
	}

	/** 发布多个预约时间 */
	public boolean submitList(Date[] startTimes, Date[] endTimes, Users user) {
		List<AppintmentTime> list = new ArrayList<AppintmentTime>();
		for (int i = 0; i < startTimes.length; i++) {
			list.add(new AppintmentTime(user, startTimes[i], endTimes[i], new Date(), new State(2)));
		}
		return dao.insertList(list) > 0;
	}

	/** 发布单个可预约时间 */
	public JSONResult submitOne(AppintmentTime time) {
		JSONResult jr = new JSONResult();
		if (time.getStartTime() == null || time.getEndTime() == null) {
			jr.setValue(RetEnum.PARAM_LACK);
		} else {
			// 默认值
			time.setDateIssued(new Date());
			time.setState(new State(2));
			RetEnum ret = dao.insertOne(time) > 0 ? RetEnum.SUCCESS : RetEnum.ERROR;
			jr.setValue(ret);
		}
		return jr;
	}

	/** 删除一个预约时间 */
	public JSONResult deleteOne(int id, Integer userId) {
		JSONResult jr = new JSONResult();
		if (dao.deleteOne(id, userId) > 0) {
			jr.setValue(RetEnum.SUCCESS);
		} else {
			jr.setValue(RetEnum.AUTH_ERROR, "没有权限");
		}
		return jr;
	}

	/** 修改预约时间 */
	public boolean updateAppintmentTime(AppintmentTime appintmenTime) {
		return dao.updateAppintmentTime(appintmenTime);
	}

	/** 修改预约时间 吴志伟 */
	public boolean updateATIme(AppintmentTime appintmentTime) {
		return dao.updateATime(appintmentTime);
	}
}
