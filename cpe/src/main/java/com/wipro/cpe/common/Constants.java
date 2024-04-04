package com.wipro.cpe.common;

public class Constants {

	// ----------------- Endpoint Constants --------------
	public static final String CPE_ENDPOINT = "https://cradlepointecm.com";
	
	public static final String LOCATION_URL = CPE_ENDPOINT + "/api/v2/locations";
	public static final String ROUTER_URL = CPE_ENDPOINT + "/api/v2/routers";
	
	
	public static final String X_ECM_API_ID = "X-ECM-API-ID";
	public static final String X_ECM_API_KEY = "X-ECM-API-KEY";
	public static final String X_CP_API_ID = "X-CP-API-ID";
	public static final String X_CP_API_KEY = "X-CP-API-KEY";

	public static final String X_ECM_API_ID_VAL = "e632aea3-e3e1-454d-969f-1492ce58d107";
	public static final String X_ECM_API_KEY_VAL = "e2f76c296ca9671e77d601f076361d4d7838bbd7";
	public static final String X_CP_API_ID_VAL = "2490a3c5";
	public static final String X_CP_API_KEY_VAL = "2f3cfac16e6399ec2fb0d54b377985e4";

	


	// ----------------- Operation Constants --------------
	public static final String LOCATION = "Location";
	public static final String ROUTER = "Router";
	public static final String NETDEVICE = "NetDevice";

	// --------------- Poll Interval Constants ---------------

	public static final int POLL_INTERVAL_5_SEC = 5000;
	public static final int POLL_INTERVAL_10_SEC = 10000;
	public static final int POLL_INTERVAL_15_SEC = 15000;
	public static final int BETWEEN_10_SEC = 10000;
	public static final int BETWEEN_100_SEC = 100000;


	public static final String[] SEVIRITY = { "Critical", "Major", "Warning", "Minor" };
	public static final String[] ALARM_STATUS = { "Closed", "Open" };

	// ---------------utils ----------------------

	public static final String DETACHED = "DETACHED";
	public static final String ATTACHED = "ATTACHED";

	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";
	public static final String OFFLINE = "Offline";
	
	public static final String CONNECTED = "Connected";
	public static final String DISCONNECTED = "Disconnected";

}
