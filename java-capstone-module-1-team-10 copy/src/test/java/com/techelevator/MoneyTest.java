package com.techelevator;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class MoneyTest {

	@Test
	public void dispenseChangeWorksCorrectly() {
		// Arrange
		Money myMoney = new Money();
		// Act
		myMoney.addMoney(BigDecimal.valueOf(5.90));
		myMoney.dispenseChange(myMoney);
		// Assert
		Assert.assertEquals(BigDecimal.valueOf(0), myMoney.getMoney());
	}

}
