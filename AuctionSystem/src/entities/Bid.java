package entities;

import java.io.Serializable;
import java.util.Currency;

import utilities.Money;

/**
 * Created using Java 8
 *
 */
/**
 * A class to represent a bid on an auction, containing the userID of the bid creator, the itemID of the item bid on,
 * and the value of the bid
 *
 */
public class Bid implements Serializable
{

	private long userId;
	private long itemId;
	private Money amount;


	/**
	 * @param userId
	 * @param itemID
	 * @param amount
	 */
	public Bid(int userId, long itemID, Money amount)
	{
		super();
		this.userId = userId;
		this.itemId = itemID;
		this.amount = amount;
	}


	public long getUserId()
	{
		return userId;
	}


	public long getItemID()
	{
		return itemId;
	}


	public Money getAmount()
	{
		return amount;
	}

}
