package com.cts.learning.scheduling.model;

public class Truck {
	
	private Integer id;
	private String truckNumber;
	private String truckName;
	private TruckType truckType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTruckNumber() {
		return truckNumber;
	}
	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}
	
	public String getTruckName() {
		return truckName;
	}
	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}
	
	public TruckType getTruckType() {
		return truckType;
	}
	public void setTruckType(TruckType truckType) {
		this.truckType = truckType;
	}
	
}
