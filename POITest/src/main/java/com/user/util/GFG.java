package com.user.util;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
// Java program to demonstrates ScheduleThreadPoolExecutor
// class
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class GFG {
	public static void main(String[] args) {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime nextRun = now.withHour(5).withMinute(0).withSecond(0);
		if (now.compareTo(nextRun) > 0)
			nextRun = nextRun.plusDays(1);

		Duration duration = Duration.between(now, nextRun);
		long initialDelay = duration.getSeconds();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new MyRunnableTask(), initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
	}
}

// Class that implements the Runnable interface
class MyRunnableTask implements Runnable {
	String taskName;

	public MyRunnableTask(){
		
	}
	public MyRunnableTask(String taskName) {
		this.taskName = taskName;
	}

	public void run() {
		System.out.println(
				"Task name : " + this.taskName + " Current time: " + Calendar.getInstance().get(Calendar.SECOND));
	}
}
