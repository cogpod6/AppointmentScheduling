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

@Entity(name = "po_line")
public class POLineEO {
	
	@Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "po_number")
    private POEO poNumber;
	
    @NotNull
    @Column(name = "po_line_number")
    private Integer poLineNumber;
    
    @NotNull
    @Column(name = "item_number")
    private String itemNumber;
    
    @NotNull
    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "quantity")
    private Integer quantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public POEO getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(POEO poNumber) {
		this.poNumber = poNumber;
	}

	public Integer getPoLineNumber() {
		return poLineNumber;
	}

	public void setPoLineNumber(Integer poLineNumber) {
		this.poLineNumber = poLineNumber;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
}
