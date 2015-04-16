package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import utilities.Category;
import utilities.Money;


/**
 * Created using Java 8
 *
 */
public class Item implements Serializable
{
	private static final long serialVersionUID = -4247500001194895879L;
	
	private long itemId;
	private String name;
	private String description;
	private Category category;

	private int userId;
	private LocalDateTime startTime, endTime;

	private Money reservePrice;
	private ArrayList<Bid> bids;

	private static AtomicLong counter = new AtomicLong(0);

	
	public Item()
	{
		super();
	}
	
	
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
	public Item(String name, String description, Category category, int userId, LocalDateTime startTime, LocalDateTime endTime, Money reservePrice,
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
		itemId = nextId();

	}



	public long getItemId()
	{
		return itemId;
	}


	public void setItemId(long itemId)
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


	public LocalDateTime getStartTime()
	{
		return startTime;
	}


	public void setStartTime(LocalDateTime startTime)
	{
		this.startTime = startTime;
	}


	public LocalDateTime getEndTime()
	{
		return endTime;
	}


	public void setEndTime(LocalDateTime endTime)
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


	public static void setCounter(AtomicLong counter)
	{
		Item.counter = counter;
	}


	public static long nextId()
	{
		return counter.incrementAndGet();
	}

}
