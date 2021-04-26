package com.techelevator.model;

import java.math.BigDecimal;

public class WallType {

	private String wallTypeName;
	private BigDecimal wallCost;
	
	public String getWallTypeName() {
		return wallTypeName;
	}
	public void setWallTypeName(String wallTypeName) {
		this.wallTypeName = wallTypeName;
	}
	public BigDecimal getWallCost() {
		return wallCost;
	}
	public void setWallCost(BigDecimal wallCost) {
		this.wallCost = wallCost;
	}
	
	
}
