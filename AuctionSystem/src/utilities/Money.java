package utilities;

import java.io.Serializable;
import java.util.Currency;

/**
 * Created using Java 8
 *
 */
/**
 * A class to represent a monetary value along with it's currency type
 *
 */
public class Money implements Serializable
{

	private static final long serialVersionUID = -6458682124885030639L;

	private Currency currencyType;
	private double value;


	/**
	 * @param currencyType
	 * @param amount
	 */
	public Money(Currency currencyType, double amount)
	{
		super();
		this.currencyType = currencyType;
		this.value = amount;
	}


	public double getValue()
	{
		return value;
	}


	public void setValue(double amount)
	{
		this.value = amount;
	}


	public Currency getCurrencyType()
	{
		return currencyType;
	}

}
