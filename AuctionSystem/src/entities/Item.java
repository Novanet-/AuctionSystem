package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

import utilities.Category;
import utilities.Money;

/**
 * Created using Java 8
 *
 */
/**
 * A class to represent an auction stored in an auction system
 *
 */
public class Item implements Serializable
{

	private static final long	serialVersionUID	= -4247500001194895879L;

	private long				itemId;
	private String				name;
	private String				description;
	private Category			category;

	private long				userId;
	private LocalDateTime		startTime, endTime;

	private Money				reservePrice;
	private Stack<Bid>			bids;

	private AuctionStatus		auctionStatus;

	private static AtomicLong	counter				= new AtomicLong(0);


	/**
	 * @param name
	 *            The name of the item
	 * @param description
	 *            A description for the item
	 * @param category
	 *            The category to sell the item in
	 * @param userId
	 *            The userID of the auction creator
	 * @param startTime
	 *            The start time of the auction
	 * @param endTime
	 *            The end time of the auction
	 * @param reservePrice
	 *            The reserve price of the auction
	 * @param bids
	 *            A list of the bids on the auction
	 */
	public Item(String name, String description, Category category, long userId, LocalDateTime startTime, LocalDateTime endTime, Money reservePrice)
	{
		super();
		this.itemId = nextId();
		this.name = name;
		this.description = description;
		this.category = category;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reservePrice = reservePrice;
		this.bids = new Stack<Bid>();
		this.auctionStatus = AuctionStatus.OPEN;
	}


	public long getItemId()
	{
		return itemId;
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


	public void setItemId(long itemId)
	{
		this.itemId = itemId;
	}


	public long getUserId()
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


	public Stack<Bid> getBids()
	{
		return bids;
	}


	public AuctionStatus getAuctionStatus()
	{
		return auctionStatus;
	}


	public void setAuctionStatus(AuctionStatus auctionStatus)
	{
		this.auctionStatus = auctionStatus;
	}


	public static void setCounter(AtomicLong counter)
	{
		Item.counter = counter;
	}


	/**
	 * Increments the counter used to determine the next unique id of a created Item
	 * 
	 * @return The incremented id
	 */
	public static long nextId()
	{
		return counter.incrementAndGet();
	}

}
