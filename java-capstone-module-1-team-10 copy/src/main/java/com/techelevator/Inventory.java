package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Inventory {

	public Map<String, Item> createHashMapInventory() {
		File inputFile = new File("vendingmachine.csv");
		Map<String, Item> itemMap = new LinkedHashMap<String, Item>();
		try (Scanner fileScanner = new Scanner(inputFile)) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				// Splits the line up
				String[] lineArray = line.split("\\|");
				// Creates a new item with the arguments from the split line
				Item myItem = new Item(lineArray[0], lineArray[1], Double.parseDouble(lineArray[2]), lineArray[3]);
				// Adds the item to the Map with the key being the Slot Location
				itemMap.put(lineArray[0], myItem);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemMap;
	}
}
