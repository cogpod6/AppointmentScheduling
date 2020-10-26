package com.cts.learning.scheduling.model;

public class DCSlots {
	
	private Integer id;
	private DC dc;
	private String timeSlots;
	private Integer maxTrucks;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public DC getDc() {
		return dc;
	}
	public void setDc(DC dc) {
		this.dc = dc;
	}
	
	public String getTimeSlots() {
		return timeSlots;
	}
	public void setTimeSlots(String timeSlots) {
		this.timeSlots = timeSlots;
	}
	
	public Integer getMaxTrucks() {
		return maxTrucks;
	}
	public void setMaxTrucks(Integer maxTrucks) {
		this.maxTrucks = maxTrucks;
	}

}
