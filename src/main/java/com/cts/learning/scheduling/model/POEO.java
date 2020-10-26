package com.cts.learning.scheduling.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity(name = "po")
public class POEO {
	
	@Id
    @NotNull
    @Column(name = "po_number")
    private String poNumber;
	
    @NotNull
    @Column(name = "po_date")
    private Date poDate;
    
    @Column(name = "po_address")
    private String poAddress;
    
    @Column(name = "ordered_quantity")
    private Integer orderQuantity;

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public String getPoAddress() {
		return poAddress;
	}

	public void setPoAddress(String poAddress) {
		this.poAddress = poAddress;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
    
}
