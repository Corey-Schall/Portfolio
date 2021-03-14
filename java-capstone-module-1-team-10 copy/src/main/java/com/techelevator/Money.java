package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Money {
	private BigDecimal money;
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
	File myFile = new File("Log.txt");

	public Money() {
		this.money = BigDecimal.valueOf(0);
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public void addMoney(BigDecimal amountToAdd) {
		BigDecimal oldBalance = this.getMoney().setScale(2);
		this.setMoney(this.getMoney().add(amountToAdd));
		BigDecimal newBalance = this.getMoney().setScale(2);
		//if(myFile.exists()) {
			try (PrintWriter writer = new PrintWriter(new FileWriter(myFile, true))){ 
			writer.println(formatter.format(date) + " FEED MONEY: " + "$" + oldBalance + " $" + newBalance);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//}
	}

	public void spendMoney(BigDecimal amountToSpend) {
		this.setMoney(this.getMoney().subtract(amountToSpend));
	}

	public void dispenseChange(Money money) {
		BigDecimal oldBalance = this.getMoney().setScale(2);
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		while (money.getMoney().compareTo(BigDecimal.valueOf(0.25)) >= 0) {
			money.spendMoney(BigDecimal.valueOf(0.25));
			quarters++;
		}
		while (money.getMoney().compareTo(BigDecimal.valueOf(0.10)) >= 0) {
			money.spendMoney(BigDecimal.valueOf(0.10));
			dimes++;
		}
		while (money.getMoney().compareTo(BigDecimal.valueOf(0.05)) >= 0) {
			money.spendMoney(BigDecimal.valueOf(0.05));
			nickels++;
		}
		this.setMoney(BigDecimal.valueOf(0));
		BigDecimal newBalance = this.getMoney().setScale(2);
		//if(myFile.exists()) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(myFile, true))){   //Changed by Corey to make it a FileWriter
			writer.println(formatter.format(date) + " GIVE CHANGE: " + "$" + oldBalance + " $" + newBalance);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//}
		System.out.println("Change Dispensed: Quarters: " + String.valueOf(quarters) + " Dimes: " + String.valueOf(dimes)
				+ " Nickels: " + String.valueOf(nickels) + ".") ;
	}
}
