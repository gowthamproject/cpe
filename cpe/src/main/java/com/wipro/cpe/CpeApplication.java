package com.wipro.cpe;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wipro.cpe.common.Constants;
import com.wipro.cpe.poll.LocationPolling;
import com.wipro.cpe.poll.RouterPolling;

@SpringBootApplication
public class CpeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpeApplication.class, args);

		System.out.println("CBE Client Application Starting...");

		new Timer().schedule(new RouterPolling(), 0, Constants.POLL_INTERVAL_10_SEC);
		System.out.println("Router Polling Polling Activated...");

		/*
		 * new Timer().schedule(new LocationPolling(), 0,
		 * Constants.POLL_INTERVAL_10_SEC);
		 * System.out.println("Location Polling Polling Activated...");
		 */

	}

}
