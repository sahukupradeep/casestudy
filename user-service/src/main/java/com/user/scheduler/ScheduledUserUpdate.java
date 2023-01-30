package com.user.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.user.service.UserService;

@Component
public class ScheduledUserUpdate {

	@Autowired
	private UserService userService;

	@Value("${scheduler.initiaDelayInMillisecond}")
	private Long initiaDelayInMillisecond;

	@Value("${scheduler.periodicExcutionInMillisecond}")
	private Long periodicExcutionInMillisecond;

	@PostConstruct
	public void init() {

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable userUpdateTask = () -> {
			userService.schedularUserUpate();
		};
		scheduler.scheduleWithFixedDelay(userUpdateTask, initiaDelayInMillisecond,
				periodicExcutionInMillisecond, TimeUnit.MILLISECONDS);

	}

}
