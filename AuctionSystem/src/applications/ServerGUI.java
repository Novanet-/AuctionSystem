package applications;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import persistenceLayer.DataPersistence;
import persistenceLayer.EntityType;
import utilities.Category;
import utilities.Money;
import commLayer.Message;
import commLayer.MessageType;
import commLayer.Notification;
import commLayer.Request;
import commLayer.RequestType;
import commLayer.ServerComms;
import commLayer.ServerThread;
import entities.AuctionStatus;
import entities.Bid;
import entities.Item;
import entities.User;

/**
 * Created using Java 8
 *
 */
public class ServerGUI
{

	private JFrame			frmServer;

	private ArrayList<Item>	auctionList;
	private ArrayList<User>	userList;

	private ServerThread	serverThread;
	private ServerComms		serverComms;
	private ExecutorService	ioThreadPool;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					ServerGUI window = new ServerGUI();
					window.frmServer.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public ServerGUI()
	{
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		serverComms = new ServerComms(this, serverThread);
		ioThreadPool = Executors.newCachedThreadPool();

		ScheduledThreadPoolExecutor timerThread = new ScheduledThreadPoolExecutor(1);

		auctionList = new ArrayList<Item>();
		userList = new ArrayList<User>();

		Runnable ioTask = () ->
		{
			retrieveAuctionSystemData();
		};

		ioThreadPool.submit(ioTask);

		Runnable checkForFinishedAuctions = () ->
		{
			try
			{
				System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Polling for finished auctions");
				for (Item i : auctionList)
				{
					checkIfAuctionClosed(i);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		};
		timerThread.scheduleAtFixedRate(checkForFinishedAuctions, 10, 10, TimeUnit.SECONDS);

		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frmServer.setBounds(100, 100, 450, 300);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pnlOuter = new JPanel();
		frmServer.getContentPane().add(pnlOuter, BorderLayout.CENTER);
		GridBagLayout gbl_pnlOuter = new GridBagLayout();
		gbl_pnlOuter.columnWidths = new int[]
		{ 30, 30, 30 };
		gbl_pnlOuter.rowHeights = new int[]
		{ 30, 30, 30 };
		gbl_pnlOuter.columnWeights = new double[]
		{ 0.0, 1.0, 0.0 };
		gbl_pnlOuter.rowWeights = new double[]
		{ 0.0, 1.0, 0.0 };
		pnlOuter.setLayout(gbl_pnlOuter);

		JButton btnWinnerReport = new JButton("Display Winners");
		btnWinnerReport.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnWinnerReport.addActionListener(e ->
		{
			displayAuctionsWon();
		});
		GridBagConstraints gbc_btnWinnerReport = new GridBagConstraints();
		gbc_btnWinnerReport.fill = GridBagConstraints.BOTH;
		gbc_btnWinnerReport.insets = new Insets(0, 0, 5, 5);
		gbc_btnWinnerReport.gridx = 1;
		gbc_btnWinnerReport.gridy = 1;
		pnlOuter.add(btnWinnerReport, gbc_btnWinnerReport);
		
		frmServer.getRootPane().setDefaultButton(btnWinnerReport);
		btnWinnerReport.requestFocusInWindow();
		
		pack();
	}


	/**
	 * Checks an auction to see if it should be closed, and whether it has been won by a bidder or not, sending a
	 * relevant message to the client
	 * 
	 * @param i
	 *            The item that will be check if it is won/closed
	 */
	private void checkIfAuctionClosed(Item i)
	{
		if ((i.getAuctionStatus() == entities.AuctionStatus.OPEN) && (!isAuctionOpen(i))) //Checks if an open auction has expired
		{
			if (!i.getBids().isEmpty()) //Check that the item has had bids
			{
				if (i.getBids().peek().getAmount().getValue() > i.getReservePrice().getValue()) //Check that the highest bid is larger than the reserve price
				{
					i.setAuctionStatus(AuctionStatus.WON);
					serverComms.sendMessage(new Message(MessageType.AUCTION_FINISHED, i));
					saveAuctionList();
				}
				else
				{
					i.setAuctionStatus(AuctionStatus.CLOSED);
					serverComms.sendMessage(new Message(MessageType.AUCTION_FINISHED, i));
					saveAuctionList();
				}
			}
			else
			{
				i.setAuctionStatus(AuctionStatus.CLOSED);
				serverComms.sendMessage(new Message(MessageType.AUCTION_FINISHED, i));
				saveAuctionList();
			}
		}
	}


	/**
	 * Displays a list of all auction winners along with the item that they won
	 */
	private void displayAuctionsWon()
	{
		for (Item i : auctionList)
		{
			if (i.getAuctionStatus() == AuctionStatus.WON || i.getAuctionStatus() == AuctionStatus.CLOSED)
			{
				if (!i.getBids().isEmpty())
				{
					for (User u : userList)
					{

						if (u.getUserId() == i.getBids().peek().getUserId())
						{
							System.out.println("Winner: " + u.getFirstName() + " " + u.getSurname() + "    Won Item: ID- " + i.getItemId() + " Name- " + i.getName());
						}

					}
				}
			}
		}

	}


	/**
	 * Adds an auction to the system
	 * 
	 * @param item
	 *            The auction to be added
	 * @return boolean - isAuctionAddSuccesful
	 */
	synchronized public boolean addAuctionToSystem(Item item)
	{
		item.setItemId(Item.nextId());
		boolean addSuccessful = auctionList.add(item);
		if (addSuccessful)
		{
			saveAuctionList();
		}
		return addSuccessful;
	}


	/**
	 * Adds a user to the system
	 * 
	 * @param user
	 *            The user to be added
	 * @return boolean - isUserAddSuccesful
	 */
	synchronized public boolean addUserToSystem(User user)
	{
		user.setUserId(User.nextId());
		boolean addSuccessful = userList.add(user);
		if (addSuccessful)
		{
			saveUserList();
		}
		return addSuccessful;
	}


	/**
	 * Adds a bid to the given auction
	 * 
	 * @param bid
	 * @param auction
	 * @return
	 */
	synchronized public boolean addBidToSystem(Bid bid, Item auction)
	{
		auction.getBids().push(bid);
		boolean addSuccessful = (auction.getBids().peek() == bid);
		if (addSuccessful)
		{
			saveAuctionList();
		}
		return addSuccessful;
	}


	/**
	 * Fetch all auctions that match the specified RequestType and send a message to the client for each item found
	 * matching this request
	 * 
	 * @param requestType
	 *            The filter used to find the required auctions
	 * @return boolean - true if any matching auctions found
	 */
	public boolean fetchAuctions(Request request)
	{
		boolean openAuctionFound = false;
		switch (request.getRequestType())
		{
			case ALL_OPEN_ITEMS:
				for (Item auction : auctionList)
				{
					openAuctionFound = isAuctionOpen(auction);
					if (openAuctionFound)
						serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
				}
				break;
			case ALL_SOLD_ITEMS:
				for (Item auction : auctionList)
				{
					LocalDateTime currentDateTime = LocalDateTime.now();
					if (auction.getEndTime().isBefore(currentDateTime))
					{
						serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
						openAuctionFound = true;
					}
				}
				break;
			case ITEM_BY_CATEGORY:
				for (Item auction : auctionList)
				{
					openAuctionFound = isAuctionOpen(auction);
					if (openAuctionFound)
					{
						if (auction.getCategory().toString().equals(request.getRequestParameter()))
						{
							serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
						}
					}
				}
				break;
			case ITEM_BY_ID:
				for (Item auction : auctionList)
				{
					openAuctionFound = isAuctionOpen(auction);
					if (openAuctionFound)
					{
						if (auction.getItemId() == Long.valueOf(request.getRequestParameter()).longValue())
						{
							serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
						}
					}
				}
				break;
			case ITEM_BY_SELLER:
				for (Item auction : auctionList)
				{
					openAuctionFound = isAuctionOpen(auction);
					if (openAuctionFound)
					{
						if (auction.getUserId() == Long.valueOf(request.getRequestParameter()).longValue())
						{
							serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
						}
					}
				}
				break;
			case ITEM_CONTAINING_BID_BY_CURRENT_USER:
			{
				long requestedUserId = Long.parseLong(request.getRequestParameter());
				for (Item auction : auctionList)
				{
					openAuctionFound = isAuctionOpen(auction);
					if (openAuctionFound)
					{
						Stack<Bid> auctionBids = auction.getBids();
						int noOfBids = auctionBids.size();
						if (noOfBids > 0)
						{
							for (int i = noOfBids - 1; i >= 0; --i)
							{
								Bid bid = auctionBids.get(i);
								if (bid.getUserId() == requestedUserId)
								{
									serverComms.sendMessage(new Message(MessageType.ITEM_DELIVERY, auction));
									break;
								}
							}
						}
					}
				}
				break;
			}
			case ITEMS_WON_BY_USER:
				for (Item auction : auctionList)
				{

					if (auction.getAuctionStatus() == AuctionStatus.WON)
					{
						if (!auction.getBids().isEmpty())
							if (auction.getBids().peek().getUserId() == Long.valueOf(request.getRequestParameter()).longValue())
							{
								serverComms.sendMessage(new Message(MessageType.AUCTION_FINISHED, auction));
							}
					}
				}
				break;
			default:
				break;
		}
		return openAuctionFound;

	}


	/**
	 * Checks whether the given auction is open
	 * 
	 * @param auction
	 * @return true if the the auction is open
	 */
	private boolean isAuctionOpen(Item auction)
	{
		LocalDateTime currentDateTime = LocalDateTime.now();
		return ((auction.getStartTime().isBefore(currentDateTime)) && (auction.getEndTime().isAfter(currentDateTime)));
	}


	/**
	 * Fetch all users that match the specified RequestType and send a message to the client for each user found
	 * matching this request
	 * 
	 * @param request
	 *            The request used to determine which users to find
	 * @return true if a matching user has been found
	 */
	public boolean fetchUsers(Request request)
	{
		switch (request.getRequestType())
		{

			case ALL_USERS:
				for (User u : userList)
				{
					serverComms.sendMessage(new Message(MessageType.USER_DELIVERY, u));
				}
				break;
			case DATABASE_HAS_A_USER:
				if (!userList.isEmpty())
				{
					serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.DATABASE_HAS_USER));
				}
				else
				{
					serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.DATABASE_DOES_NOT_HAVE_USER));

				}
				break;
			default:
				break;
		}
		return false;
	}


	/**
	 * Checks that the bid is valid before appyling it to an auction
	 * 
	 * @param payload
	 * @return
	 */
	synchronized public boolean checkBidValid(Bid payload)
	{
		for (Item auction : auctionList)
		{
			if (auction.getItemId() == payload.getItemID())
			{

				if (auction.getUserId() != payload.getUserId()) //Check that bidder is not the seller
				{
					if (!auction.getBids().isEmpty())
					{
						if (auction.getBids().peek().getAmount().getValue() < payload.getAmount().getValue()) //Check that new bid is higher than any current bids
						{
							addBidToSystem(payload, auction);
							return true;
						}
						else
						{
							serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.BID_LOWER_THAN_CURRENT));
							break;
						}
					}
					else
					{
						addBidToSystem(payload, auction);
						return true;
					}

				}
				else
				{
					serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.BID_ON_OWN_ITEM));
					break;
				}

			}
		}
		return false;
	}


	/**
	 * Retrieves the the data of the system from its persistent storage
	 */
	@SuppressWarnings("unchecked")
	public void retrieveAuctionSystemData()
	{

		auctionList = (ArrayList<Item>) DataPersistence.readListFromFile(EntityType.ITEM);
		userList = (ArrayList<User>) DataPersistence.readListFromFile(EntityType.USER);

		AtomicLong auctionID, userID;

		if (!auctionList.isEmpty())
			auctionID = new AtomicLong(auctionList.get(auctionList.size() - 1).getItemId());
		else
			auctionID = new AtomicLong(0);
		Item.setCounter(auctionID);

		if (!userList.isEmpty())
			userID = new AtomicLong(userList.get(auctionList.size() - 1).getUserId());
		else
			userID = new AtomicLong(0);
		User.setCounter(userID);
	}


	/**
	 * Validates a login request by checking that the username + surname matches an existing user, and that the password
	 * is correct
	 * 
	 * @param loginRequest
	 *            The details of the user that is attempting to be logged in
	 * @return If the login request is succesful, return the user that has been logged in
	 */
	public User validateLoginRequest(User loginRequest)
	{
		for (User user : userList)
		{
			if ((user.getFirstName().equals(loginRequest.getFirstName())) && (user.getSurname().equals(loginRequest.getSurname())))
			{
				if (Arrays.equals(user.getPassword(), loginRequest.getPassword()))
				{
					serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.PASSWORD_CORRECT));
					return user;
				}
				else
				{
					serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.PASSWORD_INCORRECT));
					return null;
				}
			}
		}
		serverComms.sendMessage(new Message(MessageType.NOTIFICATION, Notification.USER_NOT_FOUND));
		return null;
	}


	/**
	 * Changes the given auction's status to closed
	 * 
	 * @param auction
	 */
	public void closeAuction(Item auction)
	{
		Item selectedAuction = null;
		for (Item i : auctionList)
		{

			if (i.getItemId() == auction.getItemId())
			{
				selectedAuction = i;
				break;
			}
		}

		if (selectedAuction == null)
			return;

		selectedAuction.setAuctionStatus(AuctionStatus.CLOSED);
		saveAuctionList();
	}


	/**
	 * Saves the auction list to file using a thread pool
	 */
	private void saveAuctionList()
	{
		Runnable ioTask = () ->
		{
			DataPersistence.writeListToFile(auctionList, EntityType.ITEM);
		};

		ioThreadPool.submit(ioTask);
	}


	/**
	 * Saves the user list to file using a thread pool
	 */
	private void saveUserList()
	{
		Runnable ioTask = () ->
		{
			DataPersistence.writeListToFile(userList, EntityType.USER);
		};

		ioThreadPool.submit(ioTask);
	}


	public void pack()
	{
		frmServer.pack();
		frmServer.setLocationRelativeTo(null);
	}


	public ArrayList<Item> getAuctionList()
	{
		return auctionList;
	}
}
