package com.cts.learning.scheduling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity(name = "truck_type")
public class TruckTypeEO {
	
	@Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
   
    @NotNull
    @Column(name = "type")
    private String truckType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
    
}
