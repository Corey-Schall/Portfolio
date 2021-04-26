package com.techelevator.model;

import java.math.BigDecimal;

public class FloorType {
	private String floorTypeName;
	private BigDecimal cost;
	
	public String getFloorTypeName() {
		return floorTypeName;
	}
	public void setFloorTypeName(String floorTypeName) {
		this.floorTypeName = floorTypeName;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	
}
