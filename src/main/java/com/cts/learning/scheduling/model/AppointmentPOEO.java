package com.cts.learning.scheduling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

@Entity(name="appointment_po")
public class AppointmentPOEO {
	
	@Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
	private AppointmentEO appointment;
   
    @NotNull
    @Column(name = "po_number")
    private String poNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppointmentEO getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentEO appointment) {
		this.appointment = appointment;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
    
}
