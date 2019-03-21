package com.qichong.controller;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qichong.entity.AppintmentTime;
import com.qichong.entity.State;
import com.qichong.entity.UserApintmentUser;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.AppintmentTimeService;
import com.qichong.service.EnterpriseInfoService;
import com.qichong.service.UserApintmentUserService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

@Controller
public class AppointmentController {

	@Autowired
	UsersService usersService;

	@Autowired
	EnterpriseInfoService enterpriseInfoService;

	@Autowired
	AppintmentTimeService service;
	@Autowired
	UserApintmentUserService userApintmentUserService;

	/** 查询当前登录用户某一天发布的时间 */
	@RequestMapping(path = "/time/query/date/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// ADDED_TO_DOCS
	@ResponseBody
	public String doQueryDateSelf(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, HttpSession session,
			LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		if (date == null)
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		List<AppintmentTime> list = service.queryListByDate(date, user.getId());
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 查询当前登录用户发布的时间 */
	@RequestMapping(path = "/time/query/self", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// ADDED_TO_DOCS
	@ResponseBody
	public String doTimeQuerySelf(HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		List<AppintmentTime> list = service.queryListByUserId(user.getId());
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}

	/** 删除可预约时间 */
	@RequestMapping(path = "/time/delete/{id}", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// ADDED_TO_DOCS
	@ResponseBody
	public String doTimeDelete(@PathVariable("id") int id, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN);
		}
		return service.deleteOne(id, user.getId()).toJSON();
	}

	/** 发布可预约时间 */
	@RequestMapping(path = "/time/submit", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// ADDED_TO_DOCS
	@ResponseBody
	public String doTimeSbumit(AppintmentTime time, HttpSession session, LoginToken token) {
		Users user = ServletUtil.getThisLoginUser(session, token, usersService);
		if (user.getId() == null)
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "未登录");
		time.setUser(user);
		return service.submitOne(time).toJSON();
	}


	/** 根据id查询用户的可预约时间 */
	@RequestMapping(path = "/query/time_byid", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// ADDED_TO_DOCS
	@ResponseBody
	public String doQueryTimeById(Integer userId) {
		if (userId == null)
			return new JSONResult().setValue(RetEnum.PARAM_LACK, "缺少参数");
		List<AppintmentTime> list = service.queryByUserId(userId);
		return new JSONResult().setValue(RetEnum.SUCCESS, list);
	}
	
	/* - - - - - - - - - - - - - - - - - - - - - */

	/** 根据用户id和选择的开始日期和结束日期查询 */
	@RequestMapping(path = "/selappintmentbytime", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String selAppintmentByTime(Integer userId, String startTime, String endTime, HttpSession session) {
		JSONResult jr = new JSONResult();
		return jr.setValue(RetEnum.SUCCESS, "执行成功", service.selAppintmentByTime(userId, startTime, endTime));
	}

	/** 根据id查询预约成功后的信息 */
	@RequestMapping(path = "/seluserapintment", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String selUserApintment(Integer userIdB, HttpSession session) {
		JSONResult jr = new JSONResult();
		List<UserApintmentUser> list = userApintmentUserService.selUserApintment(userIdB);
		if (list != null) {
			return jr.setValue(RetEnum.SUCCESS, "执行成功", list);
		} else {
			return jr.setValue(RetEnum.ERROR, "执行失败");
		}
	}

	/** 查询单个预约 */
	@RequestMapping(path = "/singlemake", method = RequestMethod.GET)// NOT_API
	public String singleMake(int id, Model model, HttpSession session) {
		AppintmentTime appintmentTime = service.queryOneById(id);
		model.addAttribute("appintmentTime", appintmentTime);
		return "user/user-info";
	}

	/** 添加可预约时间 */
	@RequestMapping(path = "/insertTime", method = RequestMethod.POST)// NOT_API
	public String insertTime(HttpServletRequest request, Model model,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date[] startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date[] endTime) {
		// 判断是否登录，会自动从Cookie中登录
		Users currentUser = (Users) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，没有权限访问此页面！");
			return "error";
		}
		if (startTime != null && endTime != null) {
			try {
				service.submitList(startTime, endTime, currentUser);
				return "redirect:/";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorTitle", "发生错误！");
				model.addAttribute("errorBody", "系统出现异常：" + e.getMessage());
			}
		} else {
			model.addAttribute("errorTitle", "发生错误！");
			model.addAttribute("errorBody", "参数出现问题，请稍后重试...");
		}
		return "error";
	}

	/** 添加可预约时间 --手机端 */
	@RequestMapping(path = "/releaseTimeInfo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String insertTimeInfo(HttpServletRequest request, Model model,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date[] startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date[] endTime) {

		// 判断是否登录，会自动从Cookie中登录
		Users currentUser = (Users) request.getSession().getAttribute("currentUser");
		if (currentUser == null) {
			return new JSONResult().setValue(RetEnum.NO_LOGIN, "尚未登录");
		}
		if (startTime != null && endTime != null) {
			// 这是一个事务方法，要么全都成功，要么全都失败
			try {
				service.submitList(startTime, endTime, currentUser);
				return new JSONResult().setValue(RetEnum.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return new JSONResult().setValue(RetEnum.PARAM_LACK);
		}
		return new JSONResult().setValue(RetEnum.ERROR);
	}

	/** 修改预约时间 */
	@RequestMapping(path = "/updateTime", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String update(AppintmentTime appintmentTime, String startTime, String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		Date newStartTime = sdf.parse(startTime);
		Date newEndTime = sdf.parse(endTime);
		appintmentTime.setStartTime(newStartTime);
		appintmentTime.setEndTime(newEndTime);
		boolean flag = service.updateAppintmentTime(appintmentTime);
		if (flag) {
			return "";
		} else {
			return "";
		}
	}

	/** 修改预约时间 */
	@RequestMapping(path = "/updateATime", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String updateATime(int id, String startTime, String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		AppintmentTime appintmentTime = new AppintmentTime();
		appintmentTime.setId(id);
		Date newStartTime = sdf.parse(startTime);
		Date newEndTime = sdf.parse(endTime);
		appintmentTime.setStartTime(newStartTime);
		appintmentTime.setEndTime(newEndTime);
		appintmentTime.setDateIssued(new Date());
		boolean flag = service.updateATIme(appintmentTime);
		JSONResult jr = new JSONResult();
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS, "修改成功！");
		} else {
			return jr.setValue(RetEnum.ERROR, "修改失败！！请重新修改");
		}
	}

	private AppintmentTime jsonTojson(AppintmentTime appintmentTime, String value) {
		List<String> selectedTimes = this.jsonToListString(appintmentTime.getSelectedTime());
		selectedTimes.add(value);
		appintmentTime.setSelectedTime(new Gson().toJson(selectedTimes));
		return appintmentTime;
	}

	private List<String> jsonToListString(String json) {
		List<String> selectedTimes = new Gson().fromJson(json, new TypeToken<List<String>>() {
		}.getType());
		return selectedTimes;
	}

	/** 新增预约记录 */
	@RequestMapping(path = "/insertReservationTwo", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String addReservationTwo(Integer userB, String startTime, String endTime, String ids, String all,
			HttpSession session, LoginToken token) throws ParseException {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		// 判断是否已登录
		Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
		if (loginUser.getId() == null) {
			return JSONResult.builder(RetEnum.NO_LOGIN, "尚未登录").toJSON();
		} else {
			if (loginUser.getId().equals(userB)) {
				return jr.setValue(RetEnum.PARAM_ERROR, "本平台不支持自己约自己！敬请谅解");
			} else {
				// 已选中的预约时间
				List<Map<String, Object>> idss = Utils.jsonToListMap(ids);

				List<AppintmentTime> listTime = new ArrayList<AppintmentTime>();
				for (Map<String, Object> map : idss) {
					String idStr = map.get("id").toString();
					if (!idStr.equalsIgnoreCase("on")) {
						int id = Integer.parseInt(idStr);
						boolean flag = false;
						for (int i = 0; i < listTime.size(); i++) {
							AppintmentTime appintmentTime = listTime.get(i);
							if (appintmentTime.getId() == id) {
								appintmentTime = jsonTojson(appintmentTime, map.get("value").toString());
								flag = true;
								break;
							}
						}
						if (!flag) {
							AppintmentTime tempTime = service.queryOneById(id);
							tempTime = jsonTojson(tempTime, map.get("value").toString());
							listTime.add(tempTime);
						}
					}
				}

				// 添加到数据库里面去
				for (AppintmentTime appintmentTime : listTime) {

					// 判断所有待选中的值
					Type type = new TypeToken<HashMap<String, String[]>>() {
					}.getType();
					Type typeArray = new TypeToken<String[]>() {
					}.getType();
					Map<String, String[]> alls = new Gson().fromJson(all, type);
					String[] allJson = alls.get(appintmentTime.getId().toString());
					if (allJson != null) {
						String[] selectedTimeArray = new Gson().fromJson(appintmentTime.getSelectedTime(), typeArray);
						// 判断待选中和已选中是否相等
						if (selectedTimeArray.length == allJson.length) {
							appintmentTime.setState(new State(3));
						}
					}
					service.setSelectedTime(appintmentTime);
				}
				return jr.setValue(RetEnum.SUCCESS);
				// 添加预约记录
				// int userIda = loginUser.getId();
				// UserApintmentUser uau = new UserApintmentUser();
				// State s = new State(14);
				// Users ua = new Users(userIda);
				// Users ub = new Users(userB);
				// uau.setUserIdA(ua);
				// uau.setUserIdB(ub);
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd
				// HH:mm");
				// Date time = sdf.parse(startTime);
				// Date timeB = sdf.parse(endTime);
				// uau.setStartTime(time);
				// uau.setEndTime(timeB);
				// uau.setState(s);
				// uau.setRemark("请准时来到指定地点");
				// boolean falg =
				// userApintmentUserService.insertReservation(uau);
				//
				// if (falg) {
				// return jr.setValue(RetEnum.SUCCESS);
				// } else {
				// return jr.setValue(RetEnum.ERROR);
				// }
			}
		}

	}

	/** 修改状态 为预约进行中 */
	@RequestMapping(path = "/update/state", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String confirim(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.updateState(uAUId, 13);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	/** 修改状态 为预约已完成并已评价 */
	@RequestMapping(path = "/completed/evaluated/state", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String completedEvaluated(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.updateState(uAUId, 21);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	// 修改状态为预约已完成
	@RequestMapping(path = "/complete/state", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String complete(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.updateState(uAUId, 11);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	// 改变状态为待取消
	@RequestMapping(path = "/cancel/b/state", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String cancelB(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.updateState(uAUId, 17);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	@RequestMapping(path = "/cancel/a/state", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String cancelA(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.updateState(uAUId, 16);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	/** 改变状态为已取消 */
	@RequestMapping(path = "/confirm/cancel", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String confirmCancel(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.updateState(uAUId, 10);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	/** 拒绝后 新增预约时间 */
	@RequestMapping(path = "/decline/insert", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String declineInsert(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime, HttpSession session) throws ParseException {
		Users user = (Users) session.getAttribute("currentUser");
		AppintmentTime appintmentTime = new AppintmentTime();
		appintmentTime.setUser(user);
		appintmentTime.setStartTime(startTime);
		appintmentTime.setEndTime(endTime);
		appintmentTime.setDateIssued(new Date());
		appintmentTime.setState(new State(2));
		return service.submitOne(appintmentTime).toJSON();
	}

	/** 删除预约记录 */
	@RequestMapping(path = "/decline/delete", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	@ResponseBody
	public String declineDelete(Integer uAUId) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		boolean flag = userApintmentUserService.delete(uAUId);
		if (flag) {
			return jr.setValue(RetEnum.SUCCESS);
		} else {
			return jr.setValue(RetEnum.ERROR);
		}
	}

	/** 新增预约记录 */
	@RequestMapping(path = "/insertReservation", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)// NOT_API
	public String addReservation(int userB, int[] timeList, HttpSession session, Model model) {
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser == null) {
			model.addAttribute("errorTitle", "尚未登陆");
			model.addAttribute("errorBody", "你还没有登陆，没有权限访问此页面！");
			return "error";
		}
		int userIda = currentUser.getId();
		if (timeList != null) {
			for (int i = 0; i < timeList.length; i++) {
				UserApintmentUser uau = new UserApintmentUser();
				State s = new State(14);
				Users ua = new Users(userIda);
				Users ub = new Users(userB);
				/* AppintmentTime at = new AppintmentTime(timeList[i]); */
				uau.setUserIdA(ua);
				uau.setUserIdB(ub);
				/*
				 * uau.setCreateTime(new Date()); uau.setTime(at);
				 */
				uau.setState(s);
				uau.setRemark("请准时来到指定地点");
				boolean falg = userApintmentUserService.insertReservation(uau);
				if (falg) {
					continue;
				} else {
					model.addAttribute("errorTitle", "发生错误！");
					model.addAttribute("errorBody", "为啥发生错误，我不知道。。。。");
					return "error";
				}
			}
			return "redirect:/";
		} else {
			model.addAttribute("errorTitle", "发生错误！");
			model.addAttribute("errorBody", "为啥发生错误，我不知道。。。。");
			return "error";
		}

	}

}
