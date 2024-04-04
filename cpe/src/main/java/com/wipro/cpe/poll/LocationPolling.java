package com.wipro.cpe.poll;

import java.util.TimerTask;

import com.wipro.cpe.services.LocationService;

public class LocationPolling extends TimerTask {

	@Override
	public void run() {
		try {
			new LocationService().pull_LocationDetailsFromRaemisAPI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
