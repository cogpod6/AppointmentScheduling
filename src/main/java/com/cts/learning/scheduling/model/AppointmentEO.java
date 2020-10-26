package com.cts.learning.scheduling.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

@Entity(name="appointment")
public class AppointmentEO {
	
	@Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
	private TruckEO truck;
	
	@NotNull
    @Column(name = "dc_number")
	private String dcNumber;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
	private DCSlotsEO dcSlots;
	
	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    List<AppointmentPOEO> appointmentPOs;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TruckEO getTruck() {
		return truck;
	}

	public void setTruck(TruckEO truck) {
		this.truck = truck;
	}

	public String getDcNumber() {
		return dcNumber;
	}

	public void setDcNumber(String dcNumber) {
		this.dcNumber = dcNumber;
	}

	public DCSlotsEO getDcSlots() {
		return dcSlots;
	}

	public void setDcSlots(DCSlotsEO dcSlots) {
		this.dcSlots = dcSlots;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	@NotNull
	@Column(name = "appointment_date")
	private Date appointmentDate;

	public List<AppointmentPOEO> getAppointmentPOs() {
		return appointmentPOs;
	}

	public void setAppointmentPOs(List<AppointmentPOEO> appointmentPOs) {
		this.appointmentPOs.clear();
		if (null != appointmentPOs) {
			this.appointmentPOs.addAll(appointmentPOs);
		}
	}

}
