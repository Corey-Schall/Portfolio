package com.techelevator.model;

import java.math.BigDecimal;

public class FixtureType {
	private String fixtureType = "";
	private int length = 0;
	private int width = 0;
	private BigDecimal economicCost;
	private BigDecimal averageCost;
	private BigDecimal highEndCost;
	
	public String getFixtureType() {
		return fixtureType;
	}
	public void setFixtureType(String fixtureType) {
		this.fixtureType = fixtureType;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public BigDecimal getEconomicCost() {
		return economicCost;
	}
	public void setEconomicCost(BigDecimal economicCost) {
		this.economicCost = economicCost;
	}
	public BigDecimal getAverageCost() {
		return averageCost;
	}
	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}
	public BigDecimal getHighEndCost() {
		return highEndCost;
	}
	public void setHighEndCost(BigDecimal highEndCost) {
		this.highEndCost = highEndCost;
	}
}
