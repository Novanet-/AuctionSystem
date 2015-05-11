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

	private JFrame frame;

	private ArrayList<Item> auctionList;
	private ArrayList<User> userList;

	private ServerThread serverThread;
	private ServerComms serverComms;
	private ExecutorService ioThreadPool;


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
					window.frame.setVisible(true);
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
					if ((i.getAuctionStatus() == entities.AuctionStatus.OPEN) && (!isAuctionOpen(i)))
					{
						i.setAuctionStatus(AuctionStatus.WON);
						serverComms.sendMessage(new Message(MessageType.AUCTION_FINISHED, i));
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		};
		timerThread.scheduleAtFixedRate(checkForFinishedAuctions, 10, 10, TimeUnit.SECONDS);

		frame = new JFrame();
		frame.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pnlOuter = new JPanel();
		frame.getContentPane().add(pnlOuter, BorderLayout.CENTER);
		GridBagLayout gbl_pnlOuter = new GridBagLayout();
		gbl_pnlOuter.columnWidths = new int[]
		{ 30, 0, 30 };
		gbl_pnlOuter.rowHeights = new int[]
		{ 30, 0, 30 };
		gbl_pnlOuter.columnWeights = new double[]
		{ 0.0, 0.0, 0.0 };
		gbl_pnlOuter.rowWeights = new double[]
		{ 0.0, 0.0, 0.0 };
		pnlOuter.setLayout(gbl_pnlOuter);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(e ->
		{
			for (Item i : auctionList)
			{
				displayAuctionsWon();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		pnlOuter.add(btnNewButton, gbc_btnNewButton);

		JLabel lblServer = new JLabel("ServerGUI");
		lblServer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.weighty = 2.0;
		gbc_lblServer.weightx = 2.0;
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 1;
		gbc_lblServer.gridy = 1;
		pnlOuter.add(lblServer, gbc_lblServer);
	}


	private void displayAuctionsWon()
	{
		for (Item i : auctionList)
		{
			if (i.getAuctionStatus() == AuctionStatus.WON || i.getAuctionStatus() == AuctionStatus.CLOSED)
			{
				for (User u : userList)
				{
					if (!i.getBids().isEmpty())
					{
						if (u.getUserId() == i.getBids().peek().getUserId())
						{
							System.out.println(u.getFirstName() + " " + u.getSurname() + " " + i.getItemId() + " " + i.getName());
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
			Runnable ioTask = () ->
			{
				DataPersistence.writeListToFile(auctionList, EntityType.ITEM);
			};

			ioThreadPool.submit(ioTask);
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
			Runnable ioTask = () ->
			{
				DataPersistence.writeListToFile(userList, EntityType.USER);
			};

			ioThreadPool.submit(ioTask);
		}
		return addSuccessful;
	}


	synchronized public boolean addBidToSystem(Bid bid, Item auction)
	{
		auction.getBids().push(bid);
		boolean addSuccessful = (auction.getBids().peek() == bid);
		if (addSuccessful)
		{
			Runnable ioTask = () ->
			{
				DataPersistence.writeListToFile(auctionList, EntityType.ITEM);
			};

			ioThreadPool.submit(ioTask);
		}
		return addSuccessful;
	}


	/**
	 * Fetch all auctions that match the specified RequestType
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


	private boolean isAuctionOpen(Item auction)
	{
		LocalDateTime currentDateTime = LocalDateTime.now();
		return ((auction.getStartTime().isBefore(currentDateTime)) && (auction.getEndTime().isAfter(currentDateTime)));
	}


	public boolean fetchUsers(RequestType allUsers)
	{
		// TODO Auto-generated method stub
		return false;
	}


	synchronized public boolean checkBidValid(Bid payload)
	{
		for (Item auction : auctionList)
		{
			if (auction.getItemId() == payload.getItemID())
			{
				if (auction.getBids().isEmpty())
				{
					auction.getBids().push(payload);
					return true;
				}
				else if (payload.getAmount().getValue() > auction.getBids().peek().getAmount().getValue())
				{
					auction.getBids().push(payload);
					return true;
				}
			}
		}
		return false;
	}


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


	public ArrayList<Item> getAuctionList()
	{
		return auctionList;
	}


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


	public void closeAuction(Item auction)
	{
		auction.setAuctionStatus(AuctionStatus.CLOSED);
	}
}
