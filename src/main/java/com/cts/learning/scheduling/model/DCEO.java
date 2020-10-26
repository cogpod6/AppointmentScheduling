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


@Entity(name = "dc")
public class DCEO {
	
	@Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
    @NotNull
    @Column(name = "dc_number")
    private String dcNumber;
    
    @Column(name = "city")
    private String city;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dc_type_id")
    private DCTypeEO dcType;

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

	public DCTypeEO getDcType() {
		return dcType;
	}

	public void setDcType(DCTypeEO dcType) {
		this.dcType = dcType;
	}
    
}
