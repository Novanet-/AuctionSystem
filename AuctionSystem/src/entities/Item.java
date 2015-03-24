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
}
