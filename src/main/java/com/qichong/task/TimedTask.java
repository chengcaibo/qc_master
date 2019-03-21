package com.qichong.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.qichong.controller.TaskController;
import com.qichong.service.TaskService;

public class TimedTask {

	// @Autowired
	// OrderService orderService;

	Map<String, TaskItem> taskMap = new HashMap<String, TaskItem>();

	public void init() {
		taskMap.put("task1", new TaskItem(new RunTask1()));
		System.out.println("[DEBUG:TIMED_TASK]：初始化完成");
	}

	public void runAll() {
		for (String key : taskMap.keySet()) {
			if (taskMap.containsKey(key)) {
				TaskItem task = taskMap.get(key);
				runOne(task);
			}
		}
		System.out.println("[DEBUG:TIMED_TASK]：已开始运行所有Timed task");
		// 开启监控
		runOne(new RunMonitor());
		System.out.println("[DEBUG:TIMED_TASK]：已开启监控");
	}

	private ScheduledExecutorService runOne(TaskItem task) {
		task.setLastRunTime(System.currentTimeMillis());
		return runOne(task.getRunnable());
	}

	private ScheduledExecutorService runOne(Runnable runnable) {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		//  第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
		return service;
	}

	/** 定时关闭没有在24小内确认的订单，并将支付的金额返还到原账户中 */
	class RunTask1 implements Runnable {

		@Override
		public void run() {
			TaskService ser = TaskController.taskService;
			if (ser != null) {
				ser.update24HourNotConfirmOrderAndBack();
			}
			taskMap.get("task1").setLastRunTime(System.currentTimeMillis());
		}
	}

	/** 监控 */
	class RunMonitor implements Runnable {

		@Override
		public void run() {
			long thisTime = System.currentTimeMillis();
			for (String key : taskMap.keySet()) {
				if (taskMap.containsKey(key)) {
					TaskItem task = taskMap.get(key);
					long diff = thisTime - task.getLastRunTime();
					if (diff >= 3000) {
						System.out.println("[DEBUG:TIMED_TASK]：监控到 " + key + " 三秒未运行，已自动重启。");
						// runOne(task.getRunnable());
						runOne(task);
					}
				}
			}
		}
	}

}

class TaskItem {
	private Runnable runnable;
	private long lastRunTime = System.currentTimeMillis();

	public TaskItem() {
	}

	public TaskItem(Runnable runnable) {
		this.runnable = runnable;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	public long getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(long lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

}
