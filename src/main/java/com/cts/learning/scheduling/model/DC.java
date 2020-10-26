package com.cts.learning.scheduling.model;

public class DC {
	
	private Integer id;
	private String dcNumber;
	private String city;
	private DCType dcType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDcNumber() {
		return dcNumber;
	}
	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public DCType getDcType() {
		return dcType;
	}
	public void setDcType(DCType dcType) {
		this.dcType = dcType;
	}
	
}
