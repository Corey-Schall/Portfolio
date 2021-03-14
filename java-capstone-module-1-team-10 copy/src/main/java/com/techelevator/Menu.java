package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	// Set up.
	Scanner input = new Scanner(System.in);
	Inventory itemsList = new Inventory();
	Map<String, Item> items = itemsList.createHashMapInventory();
	Money balance = new Money();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
	Date date = new Date();
	File logFile = new File("log.txt");

	public void deleteLogFile() {
		if (logFile.exists()) {
			logFile.delete();
		}
	}

	public void toMainMenu() {
		Scanner input = new Scanner(System.in);

		// loops until a valid selection is made.
		String output = "";
		do {
			System.out.println("-----------");
			System.out.println("(1) Display Vending Machine Items");
			System.out.println("(2) Purchase");
			System.out.println("(3) Exit");
			System.out.println("-----------");

			String selection = input.nextLine();

			if (selection.equals("1")) {
				output = "1";
				toDisplayList();

			} else if (selection.equals("2")) {
				output = "2";
				toPurchaseMenu();

			} else if (selection.equals("3")) {
				output = "3";
				System.exit(0);

			} else {
				System.out.println("-----------");
				System.out.println("Please enter a valid selection (1, 2, or 3)");
				System.out.println("-----------");
				output = "";
			}

		} while (output.equals(""));
	}

	public void toDisplayList() {
		// Displays Slot, Item Name, Price, and amount left.
		Scanner input = new Scanner(System.in);
		System.out.println("-----------");
		System.out.println("Slot|     Item   |  Price | # Remaining");

		for (String item : items.keySet()) {
			if (items.get(item).getAmountLeft() == 0) {
				System.out.println(items.get(item).getSlotLocation() + "  | " + "-------SOLD OUT---------");
			} else {
				System.out.println(items.get(item).getSlotLocation() + "  | " + items.get(item).getItemName() + " | $"
						+ items.get(item).getPrice().setScale(2) + " | " + items.get(item).getAmountLeft());
			}
		}

		System.out.println("-----------");
		toMainMenu();

	}

	public void toPurchaseMenu() {
		// Loops Purchase menu until a valid input is entered.
		String output = "";
		do {
			System.out.println("-----------");
			System.out.println("(1) Feed Money");
			System.out.println("(2) Select Product");
			System.out.println("(3) Finish Transaction");
			System.out.println("");
			System.out.println("Current Money Provided: $" + balance.getMoney().setScale(2));
			System.out.println("-----------");

			String selection = input.nextLine();

			if (selection.equals("1")) {
				output = "1";
				toFeedMoney();

			} else if (selection.equals("2")) {
				output = "2";
				toSelection();

			} else if (selection.equals("3")) {
				output = "3";
				finishTransaction();

			} else {
				System.out.println("-----------");
				System.out.println("Please enter a valid selection (1, 2, or 3)");
				System.out.println("-----------");
				output = "";
			}
		} while (output.equals(""));
	}

	private boolean isValid(BigDecimal insertedMoney) {
		if (insertedMoney.equals(BigDecimal.valueOf(1)) || insertedMoney.equals(BigDecimal.valueOf(2))
				|| insertedMoney.equals(BigDecimal.valueOf(5)) || insertedMoney.equals(BigDecimal.valueOf(10))) {
			return true;
		}
		return false;
	}


	public void toFeedMoney() {
		System.out.println("-----------");
		System.out.println("Please enter money fed into the machine in whole dollar amounts");
		System.out.println("-----------");

		// Gets input from user, then throws to addMoney().
		try {
			String selection = input.nextLine();
			BigDecimal insertedMoney = new BigDecimal(selection);
			if (isValid(insertedMoney)) {
				balance.addMoney(insertedMoney);
			} else {
				System.out.println("Please enter a correct dollar amount (1, 2, 5, 10).");
			}
		} catch (Exception e) {
			System.out.println("Error.  No money added.");
		}
		toPurchaseMenu();
	}

	public void toSelection() {

		// Displays a list of Slots and Item Names.
		System.out.println("-----------");
		System.out.println("Slot|     Item");

		for (String item : items.keySet()) {
			if (items.get(item).getAmountLeft() == 0) {
				System.out.println(items.get(item).getSlotLocation() + "  | " + "-------SOLD OUT---------");
			} else
				System.out.println(items.get(item).getSlotLocation() + "  | " + items.get(item).getItemName());
		}

		System.out.println("-----------");
		System.out.println("Enter Selection");
		System.out.println("-----------");

		String selection = input.nextLine();

		// Checks to make sure the key is in the map.
		if (!items.containsKey(selection)) {
			System.out.println("Selection does not exist.");
			toPurchaseMenu();
		}

		// Checks to make sure you have enough money inserted.
		if (balance.getMoney().compareTo(items.get(selection).getPrice()) <= 0) {
			System.out.println("Sorry.  You haven't entered enough money.");
			toPurchaseMenu();
		}

		// Checks if the item is sold out.
		if (items.get(selection).getAmountLeft() <= 0) {
			System.out.println("Sorry, That item is sold out.");
			toPurchaseMenu();
		}

		// Prints to log file
		try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
			pw.print(formatter.format(date) + " " + items.get(selection).getItemName() + " "
					+ items.get(selection).getSlotLocation() + " $" + balance.getMoney().setScale(2) + " $");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Updates balance
		balance.spendMoney(items.get(selection).getPrice());
		items.get(selection).updateItemAmount(items.get(selection));

		// Updates the balance in the log file
		try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
			pw.println(balance.getMoney().setScale(2));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("-----------");
		System.out.println(
				"Dispensing: " + items.get(selection).getItemName() + " costing $" + items.get(selection).getPrice().setScale(2));
		System.out.println("You have $" + balance.getMoney().setScale(2) + " remaining");

		// Checks for "fun" message.
		if (items.get(selection).getItemType().equals("Chip")) {
			System.out.println("Crunch Crunch, yum!");
		} else if (items.get(selection).getItemType().equals("Candy")) {
			System.out.println("Munch Munch, yum!");
		} else if (items.get(selection).getItemType().equals("Drink")) {
			System.out.println("Glug Glug, yum!");
		} else if (items.get(selection).getItemType().equals("Gum")) {
			System.out.println("Chew Chew, yum!");
		}
		System.out.println("-----------");

		toPurchaseMenu();

	}

	public void finishTransaction() {

		balance.dispenseChange(balance);
		toMainMenu();
	}
}
