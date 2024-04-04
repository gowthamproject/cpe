package com.wipro.cpe.model;

public class Location {

	private long id;
	private long latitude;
	private long longitude;
	private int router;
	private String name;
	private String region_state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public int getRouter() {
		return router;
	}

	public void setRouter(int router) {
		this.router = router;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion_state() {
		return region_state;
	}

	public void setRegion_state(String region_state) {
		this.region_state = region_state;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", router=" + router
				+ ", name=" + name + ", region_state=" + region_state + "]";
	}

	
}
