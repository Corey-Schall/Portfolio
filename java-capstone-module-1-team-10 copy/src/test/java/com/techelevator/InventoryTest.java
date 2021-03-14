package com.techelevator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

public class InventoryTest {
	
	
	@Test
	public void inventoryMapTest() {
		Inventory itemsList = new Inventory();
		Map<String, Item> items = itemsList.createHashMapInventory();
		for (Entry<String, Item> key : items.entrySet()) {
			String currentItem = key.getValue().getSlotLocation() + "|" + key.getValue().getItemName() + "|$" + key.getValue().getPrice() + "|" + key.getValue().getItemType() + "|" + key.getValue().getAmountLeft();
			System.out.println(currentItem);
		}
	}
	
	@Test
	public void updateInventoryItemRemaining() {
		//Arrange
		Inventory itemsList = new Inventory();
		Map<String, Item> items = new LinkedHashMap<String, Item>(itemsList.createHashMapInventory());
		//Act
		items.get("D4").updateItemAmount(items.get("D4"));
		//Assert
		Assert.assertEquals(4, items.get("D4").getAmountLeft());
	}
}
