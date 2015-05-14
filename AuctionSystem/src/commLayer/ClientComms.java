package commLayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import applications.ClientGUI;
import entities.AuctionStatus;
import entities.Bid;
import entities.Item;
import entities.User;

/**
 * Created using Java 8
 *
 */
public class ClientComms implements AbstractComms
{

	ClientGUI		client;
	ClientThread	clientThread;

	Logger			logger;
	String			logMessage;
	/**
	 * Creates a client comms module, which has references to the client and the client's socket thread
	 * 
	 * @param client
	 *            The client program
	 * @param clientThread
	 *            The thread that runs the client socket
	 */
	public ClientComms(ClientGUI client, ClientThread clientThread)
	{
		super();
		this.client = client;
		this.clientThread = clientThread;
		initSocket();
	}


	/**
	 * Initialises the client thread, creating a client socket , and input and output streams for this client socket
	 * Then listens for any incoming data
	 */
	@Override
	public void initSocket()
	{
		clientThread = new ClientThread(this);
		clientThread.start();
		try
		{
			createLogger();
		}
		catch (SecurityException | IOException e)
		{
			e.printStackTrace();
		}
	}


	public void createLogger() throws SecurityException, IOException
	{
		logger = Logger.getLogger(ServerComms.class.getName());
		logMessage = "{0} {1} {2} ";
		Path serverLogPath = Paths.get("log/");
		if (!Files.exists(serverLogPath))
		{
			Files.createDirectory(serverLogPath);
		}
		FileHandler fh = new FileHandler(System.getProperty("user.dir") + "/log/clientcomms-log.%u.%g.txt", 1024 * 1024, 10, true);
		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
	}


	/**
	 * Forwards a message to the client thread
	 * 
	 * @see commLayer.AbstractComms#sendMessage(commLayer.Message)
	 */
	@Override
	public boolean sendMessage(Message message)
	{
		logger.log(Level.INFO, logMessage, new Object[]
		{ "Send", message.getHeader().toString(), message.getPayload().toString() });
		return clientThread.sendToOutputStream(message);
	}


	/**
	 * Receives a message forwarded from the client thread, processing it based on the message type
	 * 
	 * @see commLayer.AbstractComms#recieveMessage(commLayer.Message)
	 */
	@Override
	public boolean recieveMessage(Message message)
	{
		boolean recieveSuccesful = false;
		logger.log(Level.INFO, logMessage, new Object[]
		{ "Recieve", message.getHeader().toString(), message.getPayload().toString() });
		switch (message.getHeader())
		{
			case ITEM_DELIVERY:
				recieveSuccesful = client.addAuctionToCache((Item) message.getPayload());
				recieveSuccesful = client.refreshAuctionList(RequestType.ALL_OPEN_ITEMS);
				break;
			case BID_DELIVERY:
				recieveSuccesful = client.updateAuctionInCache((Bid) message.getPayload());
				recieveSuccesful = client.refreshAuctionList(RequestType.ALL_OPEN_ITEMS);
				break;
			case USER_DELIVERY:
				client.setCurrentUser((User) message.getPayload());
				break;
			case NOTIFICATION:
				switch ((Notification) message.getPayload())
				{
					case ITEM_RECIEVED:
						System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Item " + message.getPayload().toString() + " Recieved by server");
						client.clearCache();
						sendMessage(new Message(MessageType.ITEM_REQUEST, new Request(RequestType.ALL_OPEN_ITEMS, "")));
						break;
					case BID_RECIEVED:
						System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Bid " + message.getPayload().toString() + " Recieved by server");
						JOptionPane.showMessageDialog(null, "Bid has been placed", "Bid Succesful", JOptionPane.INFORMATION_MESSAGE);
						break;
					case USER_RECIEVED:
						JOptionPane.showMessageDialog(null, "User has been registered", "Registration Succesful", JOptionPane.INFORMATION_MESSAGE);
						client.enableLogin();
						break;
					case ITEM_REQUEST_RECIEVED:
						client.clearCache();
						break;
					case USER_REQUEST_RECIEVED:
						break;
					case USER_NOT_FOUND:
						JOptionPane.showMessageDialog(null, "User Invalid", "User Not Found", JOptionPane.ERROR_MESSAGE);
						break;
					case PASSWORD_CORRECT:
						JOptionPane.showMessageDialog(null, "Password correct", "Login Succesful", JOptionPane.INFORMATION_MESSAGE);
						client.loginUser();
						break;
					case PASSWORD_INCORRECT:
						JOptionPane.showMessageDialog(null, "Password incorrect", "Login Failed", JOptionPane.ERROR_MESSAGE);
						break;
					case BID_LOWER_THAN_CURRENT:
						JOptionPane.showMessageDialog(null, "Requested bid amount lower than current highest bid on item", "Bid Failed", JOptionPane.ERROR_MESSAGE);
						break;
					case BID_ON_OWN_ITEM:
						JOptionPane.showMessageDialog(null, "You can't bid on your own item", "Bid Failed", JOptionPane.ERROR_MESSAGE);
						break;
					case DATABASE_DOES_NOT_HAVE_USER:
						client.disableLogin();
						break;
					case DATABASE_HAS_USER:
						client.enableLogin();
						break;
					default:
						break;
				}
				break;
			case AUCTION_FINISHED:
				Item finishedAuction = (Item) message.getPayload();
				if (client.getCurrentUser() != null)
				{
					processClosedStatus(finishedAuction);
				}
				break;
			default:
				break;
		}
		return recieveSuccesful;
	}


	/**
	 * Determines if the auction has any winners and if the current user is the seller/winner, and display a relevant
	 * message
	 * 
	 * @param finishedAuction
	 */
	private void processClosedStatus(Item finishedAuction)
	{
		if (finishedAuction.getUserId() == client.getCurrentUser().getUserId())
		{
			JOptionPane.showMessageDialog(null, generateClosedAuctionMessage(finishedAuction).toString(), "Auction Ended", JOptionPane.INFORMATION_MESSAGE);
		}
		if (finishedAuction.getAuctionStatus() == AuctionStatus.WON)
		{
			if (!finishedAuction.getBids().empty())
			{
				if (finishedAuction.getBids().peek().getUserId() == client.getCurrentUser().getUserId())
					sendMessage(new Message(MessageType.WIN_RECIEVED, finishedAuction));
				JOptionPane.showMessageDialog(null, "Your have won the auction for " + finishedAuction.getName(), "Auction Won", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	/**
	 * Looks at various properties of the finished auction to determine why it doesn't have a winner, and generates a
	 * message with the result to be displayed to the seller of the item
	 * 
	 * @param finishedAuction
	 * @return
	 */
	private StringBuilder generateClosedAuctionMessage(Item finishedAuction)
	{
		StringBuilder closeMessage = new StringBuilder();
		closeMessage.append("Your auction for " + finishedAuction.getName() + " has ended");
		if (finishedAuction.getAuctionStatus() == AuctionStatus.CLOSED)
		{
			if (finishedAuction.getBids().isEmpty())
			{
				closeMessage.append(" with no bidders");
			}
			else if (finishedAuction.getBids().peek().getAmount().getValue() < finishedAuction.getReservePrice().getValue())
			{
				closeMessage.append(" with no bids above your reserve price");
			}
		}
		return closeMessage;
	}
}
