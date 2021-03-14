package com.techelevator;

import java.math.BigDecimal;

public class Item {
	private String itemName;
	private String itemType;
	private String slotLocation;
	private int amountLeft;
	private double price;

	public Item(String slotLocation, String name, double price, String type) {
		this.itemName = name;
		this.itemType = type;
		this.slotLocation = slotLocation;
		this.amountLeft = 5;
		this.price = price;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public String getSlotLocation() {
		return slotLocation;
	}

	public int getAmountLeft() {
		return amountLeft;
	}

	public BigDecimal getPrice() {
		return BigDecimal.valueOf(price);
	}

	public void setAmountLeft(int amountLeft) {
		this.amountLeft = amountLeft;
	}
	
	public void setName(String name) {
		this.itemName = name;
	}
	
	public void setType(String type) {
		this.itemType = type;
	}

	public void updateItemAmount(Item item) {
		item.setAmountLeft(item.getAmountLeft() - 1);
	}
	
	public void itemIsSoldOut(Item item) {
		item.setName("SOLD OUT");
		item.setType("SOLD OUT");
		item.setAmountLeft(0);
	}
	
}
