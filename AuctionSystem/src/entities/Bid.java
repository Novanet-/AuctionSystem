package entities;

import java.util.Currency;

/**
 * Created using Java 8
 *
 */
public class Bid
{
	private int userId;
	private int itemID;
	private Currency amount;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public int getItemID()
	{
		return itemID;
	}

	public void setItemID(int itemID)
	{
		this.itemID = itemID;
	}

	public Currency getAmount()
	{
		return amount;
	}

	public void setAmount(Currency amount)
	{
		this.amount = amount;
	}

}
