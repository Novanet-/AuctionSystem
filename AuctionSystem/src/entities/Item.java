package entities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicLong;

import commLayer.Hasher;
import utilities.Category;
import utilities.Money;

public class Item
{

	private BigInteger itemId;
	private String name;
	private String description;
	private Category category;

	private int userId;
	private int startTime, endTime;

	private Money reservePrice;
	private ArrayList<Bid> bids;

	private static AtomicLong counter = new AtomicLong(0);


	/**
	 * @param itemId
	 * @param name
	 * @param description
	 * @param category
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param reservePrice
	 * @param bids
	 */
	public Item(String name, String description, Category category, int userId, int startTime, int endTime, Money reservePrice,
			ArrayList<Bid> bids)
	{
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reservePrice = reservePrice;
		this.bids = bids;
		try
		{
			itemId = new BigInteger(1, Hasher.getItemHash(this.toString()));
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public String toString()
	{
		return (name + description + category.toString() + String.valueOf(userId) + String.valueOf(startTime) + String.valueOf(endTime)
				+ reservePrice.toString() + bids.toString());
	}


	public BigInteger getItemId()
	{
		return itemId;
	}


	public void setItemId(BigInteger itemId)
	{
		this.itemId = itemId;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public Category getCategory()
	{
		return category;
	}


	public void setCategory(Category category)
	{
		this.category = category;
	}


	public int getUserId()
	{
		return userId;
	}


	public void setUserId(int userId)
	{
		this.userId = userId;
	}


	public int getStartTime()
	{
		return startTime;
	}


	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}


	public int getEndTime()
	{
		return endTime;
	}


	public void setEndTime(int endTime)
	{
		this.endTime = endTime;
	}


	public Money getReservePrice()
	{
		return reservePrice;
	}


	public void setReservePrice(Money reservePrice)
	{
		this.reservePrice = reservePrice;
	}


	public ArrayList<Bid> getBids()
	{
		return bids;
	}


	public void setBids(ArrayList<Bid> bids)
	{
		this.bids = bids;
	}


	public static long nextId()
	{
		return counter.incrementAndGet();
	}

}
