package com.wipro.cpe.model;

public class Router {

	private String device_type;
	private int id;
	private String name;
	private String ipv4_address;
	private String locality;
	private String state;

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpv4_address() {
		return ipv4_address;
	}

	public void setIpv4_address(String ipv4_address) {
		this.ipv4_address = ipv4_address;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Router [device_type=" + device_type + ", id=" + id + ", name=" + name + ", ipv4_address=" + ipv4_address
				+ ", locality=" + locality + ", state=" + state + "]";
	}

}
