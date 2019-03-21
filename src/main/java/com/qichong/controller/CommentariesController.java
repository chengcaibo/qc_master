package com.qichong.controller;

import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qichong.entity.Commentaries;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.CommentariesService;
import com.qichong.service.UserInfoService;
import com.qichong.service.UsersService;
import com.qichong.util.web.ServletUtil;

/**
 * 用户评价信息操作的Controller
 * 
 * @创建人 吴志伟
 * @创建时间 2018年1月28日14:34:54
 */
@Controller
public class CommentariesController {
	@Autowired
	CommentariesService commentariesService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	UsersService userService;
	

	@RequestMapping(path = "/insert/commentaries", method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
	@ResponseBody
	public String insertCommentaries(Double commentariesScore, Integer reviewersId, String commentariesContent,
			HttpSession session, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime) {
		JSONResult jr = new JSONResult();
		Users currentUser = (Users) session.getAttribute("currentUser");
		if (currentUser != null) {
			Users user = userService.queryTypeId(reviewersId);
			if (user.getTypeId() == 1) {
				if (commentariesService.countCommentaries(startTime, endTime,currentUser.getId(),reviewersId) < 1) {
					Commentaries commentaries = new Commentaries();
					commentaries.setCommmentTime(new Date());
					commentaries.setUser(new Users(currentUser.getId()));
					commentaries.setBUsers(new Users(reviewersId));
					commentaries.setEvluationContent(commentariesContent);
					commentaries.setScore(commentariesScore);
					commentaries.setStartTime(startTime);
					commentaries.setEndTime(endTime);
					boolean flag = commentariesService.insertCommentaries(commentaries);
					Double score = commentariesService.avgScore(commentaries.getBUsers().getId());

					DecimalFormat format = new DecimalFormat("#.0");
					score = Double.parseDouble(format.format(score));

					UserInfo ui = new UserInfo(commentaries.getBUsers());
					ui.setScore(score);

					boolean updateScore = userInfoService.editUserInfo(ui);

					if (flag && updateScore) {
						return jr.setValue(RetEnum.SUCCESS);
					} else {
						return jr.setValue(RetEnum.ERROR);
					}
				} else {
					return jr.setValue(RetEnum.VALUE_EXIST);
				}
			} else {
				if (commentariesService.countCommentaries(startTime, endTime,currentUser.getId(),reviewersId) < 1) {

					Commentaries commentaries = new Commentaries();
					commentaries.setCommmentTime(new Date());
					commentaries.setUser(new Users(currentUser.getId()));
					commentaries.setBUsers(new Users(reviewersId));
					commentaries.setEvluationContent(commentariesContent);
					commentaries.setScore(commentariesScore);
					commentaries.setStartTime(startTime);
					commentaries.setEndTime(endTime);
					boolean flag = commentariesService.insertCommentaries(commentaries);
					if (flag) {
						return jr.setValue(RetEnum.SUCCESS);
					} else {
						return jr.setValue(RetEnum.ERROR);
					}
				} else {
					return jr.setValue(RetEnum.VALUE_EXIST);

				}
			}
		} else {
			return jr.setValue(RetEnum.NO_LOGIN);
		}

	}
}
