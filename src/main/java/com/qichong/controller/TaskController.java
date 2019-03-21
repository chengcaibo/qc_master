package com.qichong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.qichong.service.TaskService;

@Controller
public class TaskController {
	//
	// public static TimedTask timedTask;
	//
	// @Autowired
	// public static void setTimedTask(TimedTask timedTask) {
	// TaskController.timedTask = timedTask;
	// }

	public static TaskService taskService;

	@Autowired
	public void setTaskService(TaskService taskService1) {
		taskService = taskService1;
	}

	static {
	}

}
