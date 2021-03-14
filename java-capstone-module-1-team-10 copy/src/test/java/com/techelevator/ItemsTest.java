package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

public class ItemsTest {

	@Test
	public void itemsConstructorWorks() {
		// Arrange
		Item myItem = new Item("A1", "Potato Crisps", 3.05, "Chip");
		// Act
		System.out.println(myItem.getItemName());
		System.out.println(myItem.getAmountLeft());
		System.out.println(myItem.getPrice());
		System.out.println(myItem.getSlotLocation());
		System.out.println(myItem.getItemType());
		// Assert
	}

	@Test
	public void itemAmountLeftUpdate() {
		// Arrange
		Item myItem = new Item("A1", "Potato Crisps", 3.05, "Chip");
		// Act
		myItem.updateItemAmount(myItem);
		// Assert
		Assert.assertEquals(4, myItem.getAmountLeft());
	}

	
	@Test
	public void soldOutWorksCorrectly() {
		//Arrange
		Item myItem = new Item("A1", "Potato Crisps", 3.05, "Chip");
		// Act
		myItem.itemIsSoldOut(myItem);
		//Assert
		Assert.assertEquals("SOLD OUT", myItem.getItemName());
		Assert.assertEquals("SOLD OUT", myItem.getItemType());
		Assert.assertEquals(0, myItem.getAmountLeft());
	}
}
