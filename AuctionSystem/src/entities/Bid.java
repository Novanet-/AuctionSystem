package entities;

import java.util.Currency;

/**
 * Created using Java 8
 *
 */
/**
 * A class to represent a bid on an auction, containing the userID of the bid creator, the itemID of the item bid on,
 * and the value of the bid
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
