package com.cts.learning.scheduling.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

@Entity(name = "truck")
public class TruckEO {
	
	@Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
    @NotNull
    @Column(name = "truck_number")
    private String truckNumber;
    
    @NotNull
    @Column(name = "truck_name")
    private String truckName;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "truck_type_id")
    private TruckTypeEO truckType;

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

	public TruckTypeEO getTruckType() {
		return truckType;
	}

	public void setTruckType(TruckTypeEO truckType) {
		this.truckType = truckType;
	}

}
