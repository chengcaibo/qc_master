package com.qichong.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qichong.entity.DeskDisplay;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.DeskDisplayService;
import com.qichong.util.Utils;

/**
 * 抢置顶相关操作类
 * 
 * @author Administrator
 * @创建人 吴志伟
 */
@Controller
public class RobSetTopController {
	@Autowired
	DeskDisplayService deskDisplayService;

	/**
	 * 
	 * @param session
	 * @return 判断是否是企业用户，是否可以抢置顶
	 */
	@RequestMapping("/position")
	public String position(HttpSession session) {
		Users user = (Users) session.getAttribute("currentUser");

		if (user == null) {
			return "sign/login";
		} else {
			if (user.getTypeId() == 2) {
				return "function/rob-position";
			} else {
				return "index";
			}
		}
	}

	// 倒计时查询--个人
	@RequestMapping("/down-personal")
	public String downInfo(HttpSession session, int locationId) {
		JSONResult jr = new JSONResult();
		Users user = (Users) session.getAttribute("currentUser");
		if (user.getId() == 1) {
			DeskDisplay deskDisplay = null;
			List<DeskDisplay> list = deskDisplayService.locationList(locationId);
			int i = 0;
			for (i = 0; i < list.size(); i++) {
				// 获取时间戳
				long endTime = list.get(i).getEndTime().getTime();
				long thisDate = System.currentTimeMillis();
				if (thisDate > endTime) {
					deskDisplay = list.get(i);
					break;
				}
			}

			if (deskDisplay != null) {
				// 计算结束时间
				Date endTime = Utils.calcDate(new Date(), "+", 30, "天");
				deskDisplay = new DeskDisplay(list.get(i).getId(), user.getId(), endTime);
				return jr.setValue(RetEnum.ERROR);
			} else {
				List<DeskDisplay> downList = deskDisplayService.locationList(locationId);
				return jr.setValue(RetEnum.SUCCESS, downList);
			}
		}
		return jr.setValue(RetEnum.ERROR);
	}

	/**
	 * 
	 * @param session
	 * @return 判断是否是个人用户，是否可以抢置顶
	 */
	@RequestMapping("/userPosition")
	public String userPosition(HttpSession session, int locationId, Model model) {
		Users user = (Users) session.getAttribute("currentUser");
		if (user == null) {
			return "sign/login";
		} else {
			if (user.getTypeId() == 1) {
				DeskDisplay deskDisplay = null;
				if (deskDisplayService.loctionCount(locationId) <= 1) {
					List<DeskDisplay> list = deskDisplayService.locationList(locationId);
					int i = 0;
					for (i = 0; i < list.size(); i++) {
						// 获取时间戳
						long endTime = list.get(i).getEndTime().getTime();
						long thisDate = System.currentTimeMillis();
						if (thisDate > endTime) {
							deskDisplay = list.get(i);
							break;
						}
					}
					if (deskDisplay != null) {
						// 计算结束时间
						Date endTime = Utils.calcDate(new Date(), "+", 30, "天");
						deskDisplay = new DeskDisplay(list.get(i).getId(), user.getId(), endTime);
						List<DeskDisplay> DeskDisplaylist = deskDisplayService.deskDisplayCheck();
						for (int j = 0; j < DeskDisplaylist.size(); j++) {
							if (user.getId() == DeskDisplaylist.get(j).getUser().getId()) {
								long endTime1 = DeskDisplaylist.get(j).getEndTime().getTime();
								long thisDate = System.currentTimeMillis();
								if (thisDate < endTime1) {
									continue;
								} else {
									return "redirect:/seckillError";
								}
							}
						}
						if (deskDisplayService.updateDeskDisplay(deskDisplay)) {
							return "redirect:/seckillSuccess";
						} else {
							return "fail";
						}
					} else {
						List<DeskDisplay> list1 = deskDisplayService.locationList(locationId);
						model.addAttribute("topList", list1);
						return "function/position";
					}
				} else {
					return "index";
				}
			} else {
				return "index";
			}
		}
	}

	/**
	 * 
	 * @param locationId
	 * @param model
	 * @param session
	 * @return 返回页面 是否有可以抢占的位置，如果没有还有多长时间。如果有那么就直接抢占
	 */
	@RequestMapping("/locationId")
	public String loctionCount(int locationId, Model model, HttpSession session) {
		if (locationId == 2) {

			Users user = (Users) session.getAttribute("currentUser");
			if (user == null) {
				return "sign/login";
			} else {
				if (deskDisplayService.loctionCount(locationId) <= 1) {
					DeskDisplay deskDisplay = null;
					List<DeskDisplay> list = deskDisplayService.locationList(locationId);
					int i = 0;
					for (i = 0; i < list.size(); i++) {
						// 获取时间戳
						long endTime = list.get(i).getEndTime().getTime();
						long thisDate = System.currentTimeMillis();
						if (thisDate > endTime) {
							deskDisplay = list.get(i);
							break;
						}
					}

					if (deskDisplay != null) {
						// 计算结束时间
						Date endTime = Utils.calcDate(new Date(), "+", 30, "天");
						deskDisplay = new DeskDisplay(deskDisplay.getId(), user.getId(), endTime);
						List<DeskDisplay> DeskDisplaylist = deskDisplayService.deskDisplayCheck();
						for (int j = 0; j < DeskDisplaylist.size(); j++) {
							if (user.getId() == DeskDisplaylist.get(j).getUser().getId()) {
								long endTime1 = DeskDisplaylist.get(j).getEndTime().getTime();
								long thisDate = System.currentTimeMillis();
								if (thisDate < endTime1) {
									continue;
								} else {
									return "redirect:/seckillError";
								}
							}
						}
						if (deskDisplayService.updateDeskDisplay(deskDisplay)) {
							return "redirect:/seckillSuccess";
						} else {
							return "fail";
						}
					} else {
						List<DeskDisplay> list1 = deskDisplayService.locationList(locationId);
						model.addAttribute("topList", list1);
						return "function/position";
					}

				}

			}

		}
		if (locationId == 3) {
			Users user = (Users) session.getAttribute("currentUser");
			if (user == null) {
				return "sign/login";
			} else {
				if (deskDisplayService.loctionCount(locationId) <= 3) {
					DeskDisplay deskDisplay = null;
					List<DeskDisplay> list = deskDisplayService.locationList(locationId);
					int i = 0;
					for (i = 0; i < list.size(); i++) {
						// 获取时间戳
						long endTime = list.get(i).getEndTime().getTime();
						long thisDate = System.currentTimeMillis();
						if (thisDate > endTime) {
							deskDisplay = list.get(i);
							break;
						}
					}
					if (deskDisplay != null) {
						List<DeskDisplay> DeskDisplaylist = deskDisplayService.deskDisplayCheck();
						for (int j = 0; j < DeskDisplaylist.size(); j++) {
							if (user.getId() == DeskDisplaylist.get(j).getUser().getId()) {
								long endTime1 = DeskDisplaylist.get(j).getEndTime().getTime();
								long thisDate = System.currentTimeMillis();
								if (thisDate < endTime1) {
									continue;
								} else {
									return "redirect:/seckillError";
								}
							}
						}
						Date endTime = Utils.calcDate(new Date(), "+", 30, "天");
						deskDisplay = new DeskDisplay(deskDisplay.getId(), user.getId(), endTime);
						if (deskDisplayService.updateDeskDisplay(deskDisplay)) {
							return "redirect:/seckillSuccess";
						} else {
							return "fail";
						}
					} else {
						List<DeskDisplay> list1 = deskDisplayService.locationList(locationId);
						model.addAttribute("topList", list1);
						return "function/position";
						/*
						 * Date endTime = new Date(System.currentTimeMillis() +
						 * (1000L * 60L * 60L * 24L * 30L)); DeskDisplay
						 * deskDisplay = new DeskDisplay(new
						 * Users(user.getId()), new DeskLocation(2), new Date(),
						 * endTime); if
						 * (deskDisplayService.insertDeskDisplay(deskDisplay)) {
						 * return "function/success-includes"; } else { return
						 * "fail"; }
						 */
					}
					// 计算结束时间
				}

			}
		}
		if (locationId == 4) {
			Users user = (Users) session.getAttribute("currentUser");
			if (user == null) {
				return "sign/login";
			} else {
				if (deskDisplayService.loctionCount(locationId) <= 6) {
					DeskDisplay deskDisplay = null;
					List<DeskDisplay> list = deskDisplayService.locationList(locationId);
					int i = 0;
					for (i = 0; i < list.size(); i++) {
						// 获取时间戳
						long endTime = list.get(i).getEndTime().getTime();
						long thisDate = System.currentTimeMillis();
						if (thisDate > endTime) {
							deskDisplay = list.get(i);
							break;
						}
					}
					if (deskDisplay != null) {
						List<DeskDisplay> DeskDisplaylist = deskDisplayService.deskDisplayCheck();
						for (int j = 0; j < DeskDisplaylist.size(); j++) {
							if (user.getId() == DeskDisplaylist.get(j).getUser().getId()) {
								long endTime1 = DeskDisplaylist.get(j).getEndTime().getTime();
								long thisDate = System.currentTimeMillis();
								if (thisDate < endTime1) {
									continue;
								} else {
									return "redirect:/seckillError";
								}
							}
						}
						// 计算结束时间
						Date endTime = Utils.calcDate(new Date(), "+", 30, "天");

						deskDisplay = new DeskDisplay(deskDisplay.getId(), user.getId(), endTime);

						if (deskDisplayService.updateDeskDisplay(deskDisplay)) {
							return "redirect:/seckillSuccess";
						} else {
							return "fail";
						}

					} else {
						List<DeskDisplay> list1 = deskDisplayService.locationList(locationId);
						model.addAttribute("topList", list1);
						return "function/position";
						/*
						 * Date endTime = new Date(System.currentTimeMillis() +
						 * (1000L * 60L * 60L * 24L * 30L)); DeskDisplay
						 * deskDisplay = new DeskDisplay(new
						 * Users(user.getId()), new DeskLocation(2), new Date(),
						 * endTime); if
						 * (deskDisplayService.insertDeskDisplay(deskDisplay)) {
						 * return "function/success-includes"; } else { return
						 * "fail"; }
						 */
					}

				}
			}
		}
		if (locationId == 5) {
			Users user = (Users) session.getAttribute("currentUser");
			if (user == null) {
				return "sign/login";
			} else {
				if (deskDisplayService.loctionCount(locationId) <= 10) {
					DeskDisplay deskDisplay = null;
					List<DeskDisplay> list = deskDisplayService.locationList(locationId);
					int i = 0;
					for (i = 0; i < list.size(); i++) {
						// 获取时间戳
						long endTime = list.get(i).getEndTime().getTime();
						long thisDate = System.currentTimeMillis();
						if (thisDate > endTime) {
							deskDisplay = list.get(i);
							break;
						}
					}
					if (deskDisplay != null) {
						List<DeskDisplay> DeskDisplaylist = deskDisplayService.deskDisplayCheck();
						for (int j = 0; j < DeskDisplaylist.size(); j++) {
							if (user.getId() == DeskDisplaylist.get(j).getUser().getId()) {
								long endTime1 = DeskDisplaylist.get(j).getEndTime().getTime();
								long thisDate = System.currentTimeMillis();
								if (thisDate < endTime1) {
									continue;
								} else {
									return "redirect:/seckillError";
								}
							} else {
								break;
							}
						}
						Date endTime = Utils.calcDate(new Date(), "+", 30, "天");

						deskDisplay = new DeskDisplay(list.get(i).getId(), user.getId(), endTime);

						if (deskDisplayService.updateDeskDisplay(deskDisplay)) {
							return "redirect:/seckillSuccess";
						} else {
							return "fail";
						}
					} else {
						List<DeskDisplay> list1 = deskDisplayService.locationList(locationId);
						model.addAttribute("topList", list1);
						return "function/position";
						/*
						 * Date endTime = new Date(System.currentTimeMillis() +
						 * (1000L * 60L * 60L * 24L * 30L)); DeskDisplay
						 * deskDisplay = new DeskDisplay(new
						 * Users(user.getId()), new DeskLocation(2), new Date(),
						 * endTime); if
						 * (deskDisplayService.insertDeskDisplay(deskDisplay)) {
						 * return "function/success-includes"; } else { return
						 * "fail"; }
						 */
					}
				}

			}
		}
		return "common/four-zero-four";
	}
}
