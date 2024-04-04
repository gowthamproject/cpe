package com.wipro.cpe.poll;

import com.wipro.cpe.services.RouterService;

import java.util.TimerTask;

public class RouterPolling extends TimerTask {

	@Override
	public void run() {
		try {
			startPolling();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void startPolling() throws Exception {

		new RouterService().pull_RouterDetailsFromRaemisAPI();
	}
}
