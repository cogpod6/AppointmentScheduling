package com.cts.learning.scheduling.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class Appointment {
	
	private Integer id;
	private Truck truck;
	private String dcNumber;
	private DCSlots dcSlots;
	private String date;
	private List<AppointmentPO> appointmentPOs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Truck getTruck() {
		return truck;
	}
	public void setTruck(Truck truck) {
		this.truck = truck;
	}
	
	public String getDcNumber() {
		return dcNumber;
	}
	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}
	
	public DCSlots getDcSlots() {
		return dcSlots;
	}
	public void setDcSlots(DCSlots dcSlots) {
		this.dcSlots = dcSlots;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public List<AppointmentPO> getAppointmentPOs() {
		return appointmentPOs;
	}
	public void setAppointmentPOs(List<AppointmentPO> appointmentPOs) {
		this.appointmentPOs = appointmentPOs;
	}
	
}
