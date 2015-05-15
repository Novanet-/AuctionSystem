package commLayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import applications.ServerGUI;
import entities.Bid;
import entities.Item;
import entities.User;

public class ServerComms implements AbstractComms
{

	ServerGUI		server;
	ServerThread	serverThread;

	Logger			logger;
	String			logMessage;


	/**
	 * Creates a server comms module, which has references to the server and the server's socket thread
	 * 
	 * @param server
	 *            The server program
	 * @param serverThread
	 *            The thread that runs the server socket
	 */
	public ServerComms(ServerGUI server, ServerThread serverThread)
	{
		super();
		this.server = server;
		this.serverThread = serverThread;
		initSocket();
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
		FileHandler fh = new FileHandler(System.getProperty("user.dir") + "/log/servercomms-log.%u.%g.txt", 1024 * 1024, 10, true);
		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
	}


	/**
	 * Initialises and starts the server thread
	 * 
	 * @see commLayer.AbstractComms#initSocket()
	 */
	@Override
	public void initSocket()
	{
		serverThread = new ServerThread(this);
		serverThread.start();
	}


	/**
	 * Send a message through the server's socket
	 * 
	 * @see commLayer.AbstractComms#sendMessage(commLayer.Message)
	 */
	@Override
	public boolean sendMessage(Message message)
	{
		logger.log(Level.INFO, logMessage, new Object[]
		{ "Send", message.getHeader().toString(), message.getPayload().toString() });
		return serverThread.sendToOutputStream(message);
	}


	/**
	 * Checks and processes a message based off it's MessageType
	 * 
	 * @see commLayer.AbstractComms#recieveMessage(commLayer.Message)
	 */
	@Override
	public boolean recieveMessage(Message message)
	{
		Request request;
		logger.log(Level.INFO, logMessage, new Object[]
		{ "Recieve", message.getHeader().toString(), message.getPayload().toString() });
		boolean recieveSuccessful = false;
		switch (message.getHeader())
		{
			case ITEM_DELIVERY:
				recieveSuccessful = server.addAuctionToSystem((Item) message.getPayload());
				if (recieveSuccessful)
					sendMessage(new Message(MessageType.NOTIFICATION, Notification.ITEM_RECIEVED));
				break;
			case ITEM_REQUEST:
				sendMessage(new Message(MessageType.NOTIFICATION, Notification.ITEM_REQUEST_RECIEVED));
				request = (Request) message.getPayload();
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case BID_DELIVERY:
				Bid newBid = (Bid) message.getPayload();
				recieveSuccessful = server.checkBidValid(newBid);
				if (recieveSuccessful)
				{
					sendMessage(new Message(MessageType.NOTIFICATION, Notification.BID_RECIEVED));
					sendMessage(new Message(MessageType.BID_DELIVERY, newBid));
				}
				break;
			case BID_REQUEST:
				break;
			case USER_DELIVERY:
				recieveSuccessful = server.addUserToSystem((User) message.getPayload());
				if (recieveSuccessful)
				{
					sendMessage(new Message(MessageType.NOTIFICATION, Notification.USER_RECIEVED));
				}
				break;
			case USER_REQUEST:
				request = (Request) message.getPayload();
				recieveSuccessful = server.fetchUsers(request);

				break;
			case LOGIN_REQUEST:
				User requestedUser = server.validateLoginRequest((User) message.getPayload());
				if (requestedUser != null)
				{
					sendMessage(new Message(MessageType.USER_DELIVERY, requestedUser));
					server.fetchAuctions(new Request(RequestType.ITEMS_WON_BY_USER, String.valueOf(requestedUser.getUserId())));
				}
				break;
			case WIN_RECIEVED:
				server.closeAuction((Item) message.getPayload());
				break;
			default:
				break;
		}
		return recieveSuccessful;
	}
}