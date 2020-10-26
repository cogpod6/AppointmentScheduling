package com.cts.learning.scheduling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity(name = "dc_type")
public class DCTypeEO {
	
	@Id
	@NotNull
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "type")
	private String dcType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDcType() {
		return dcType;
	}

	public void setDcType(String dcType) {
		this.dcType = dcType;
	}
	
}
