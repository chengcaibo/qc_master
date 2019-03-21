package com.qichong.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.DeskDisplay;
import com.qichong.entity.DeskLocation;
import com.qichong.entity.Reservation;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.JSONResult;
import com.qichong.service.DeskDisplayService;
import com.qichong.service.ReservationService;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@Controller
public class ReservationController {
	@Autowired
	ReservationService reservationService;
	@Autowired
	DeskDisplayService deskdisplayService;

	/**
	 * 点击预定
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(path = "insert/reservation", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String addReservation(HttpSession session) {
		Users user = (Users) session.getAttribute("currentUser");
		JSONResult jr = new JSONResult();
		if (user == null) {
			return jr.setValue(RetEnum.NO_LOGIN, "您还没有登录!");
		} else {
			if (user.getTypeId() == 2) {
				EnterpriseTopOneModel enterprise = deskdisplayService.queryEnterpriseTop();
				// 计算预定时间是否到结束时间
				// 获得平台开始预定时间
				Date createTime = Utils.calcDate(enterprise.getDd().getEndTime(), "-", 10, "天");
				// 计算结束周期
				Date endTime = Utils.calcDate(createTime, "+", 2, "天");
				Long endTime1 = endTime.getTime();
				long createTime1 = createTime.getTime();
				long thisTime = System.currentTimeMillis();
				// 如果用户预订时间-开始预定时间，小于周期的话
				if ((thisTime - createTime1) < endTime1 && thisTime > createTime1) {
					if (reservationService.byLocationIdCount() < 5) {
						// 用户什么时候开始预定
						Date creatTime = new Date();
						// 查询目前置顶位置的结束时间
						Reservation reservation = new Reservation();
						reservation.setUser(user);
						reservation.setLocation(new DeskLocation(2));
						reservation.setCreationTime(creatTime);
						reservation.setEndTime(enterprise.getDd().getEndTime());
						boolean flag = reservationService.insertReservation(reservation);
						if (flag) {
							return jr.setValue(RetEnum.SUCCESS, "预定成功！");
						} else {
							return jr.setValue(RetEnum.ERROR, "预定失败！");
						}
					} else {
						return jr.setValue(RetEnum.VCODE_NOT_EFFECTIVE, "预定已满请等待下一轮");
					}
				} else if (thisTime < createTime1) {
					return jr.setValue(RetEnum.VALUE_NOT_EXIST, "预定系统还未开启，请在结束的前十天预定。");
				} else if ((thisTime - createTime1) > endTime1) {
					return jr.setValue(RetEnum.PARAM_LACK, "已经过了预定时间。");
				} else {
					return jr.setValue(RetEnum.ERROR, "预定失败！");
				}
			} else {
				return jr.setValue(RetEnum.PARAM_ERROR, "您不是企业用户！");
			}
		}
	}

	@RequestMapping(path = "query/reservation", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String queryAll() {
		JSONResult jr = new JSONResult();
		return jr.setValue(RetEnum.SUCCESS, reservationService.selectReservation());
	}

	@RequestMapping(path = "update/reservation/deskDisplay", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String updateDeskdisplaay(Integer entId) {
		JSONResult jr = new JSONResult();

		if (deskdisplayService.queryEnterpriseTop() == null) {
			Date endTime = Utils.calcDate(new Date(), "+", 30, "天");
			DeskDisplay deskDisplay = new DeskDisplay();
			deskDisplay.setUser(new Users(entId));
			deskDisplay.setSatrtTime(new Date());
			deskDisplay.setEndTime(endTime);
			deskDisplay.setLocation(new DeskLocation(2));
			boolean flag = deskdisplayService.reservationUpdateDeskdisplay(deskDisplay);
			if (flag) {
				return jr.setValue(RetEnum.SUCCESS);
			} else {
				return jr.setValue(RetEnum.ERROR);
			}
		} else {
			return jr.setValue(RetEnum.VALUE_EXIST);
		}
	}

}
