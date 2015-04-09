package utilities;

import java.util.Currency;

public class Money
{

	private Currency currencyType;
	private double amount;


	/**
	 * @param currencyType
	 * @param amount
	 */
	public Money(Currency currencyType, double amount)
	{
		super();
		this.currencyType = currencyType;
		this.amount = amount;
	}


	public double getAmount()
	{
		return amount;
	}


	public void setAmount(double amount)
	{
		this.amount = amount;
	}


	public Currency getCurrencyType()
	{
		return currencyType;
	}

}
