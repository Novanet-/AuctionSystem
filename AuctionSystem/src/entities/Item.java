package entities;

import java.util.ArrayList;
import java.util.Currency;

import utilities.CategoryEnum;

public class Item
{
	private int itemId;
	private String name;
	private String description;
	private CategoryEnum category;

	private int userId;
	private int startTime, endTime;

	private Currency reservePrice;
	private ArrayList<Bid> bids;

	public int getItemId()
	{
		return itemId;
	}

	public void setItemId(int itemId)
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

	public CategoryEnum getCategory()
	{
		return category;
	}

	public void setCategory(CategoryEnum category)
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

	public Currency getReservePrice()
	{
		return reservePrice;
	}

	public void setReservePrice(Currency reservePrice)
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

}
