package com.cts.learning.scheduling.model;

import java.util.List;

public class DownstreamApp {
	
	private Integer appointmentId;
	private String truckNumber;
	private String dcNumber;
	private String timeSlot;
	private String appointmentDate;
	private List<String> pos;
	
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getTruckNumber() {
		return truckNumber;
	}
	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}
	public String getDcNumber() {
		return dcNumber;
	}
	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public List<String> getPos() {
		return pos;
	}
	public void setPos(List<String> pos) {
		this.pos = pos;
	}
	
}
