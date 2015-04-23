package commLayer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import applications.ServerGUI;
import entities.Item;
import entities.User;
import entities.Bid;

public class ServerComms implements AbstractComms
{

	ServerGUI server;
	ServerThread serverThread;


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


	@Override
	public boolean sendMessage(Message message)
	{
		System.out.println("Server Send " + message.getHeader().toString() + " " + message.getPayload().toString() + " at "
				+ LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
		return serverThread.sendToOutputStream(message);
	}


	@Override
	public boolean recieveMessage(Message message)
	{
		System.out.println("Server Recieve " + message.getHeader().toString() + " " + message.getPayload().toString() + " at "
				+ LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
		boolean recieveSuccessful = false;
		switch (message.getHeader())
		{
		case ITEM_DELIVERY:
			recieveSuccessful = server.addAuctionToSystem((Item) message.getPayload());
			if (recieveSuccessful)
				sendMessage(new Message(MessageType.NOTIFICATION, Notification.ITEM_RECIEVED));
			break;
		case ITEM_REQUEST:
			if (message.getPayload() == RequestType.ALL_OPEN_ITEMS)
			{
				recieveSuccessful = server.fetchAuctions(RequestType.ALL_OPEN_ITEMS);
			}
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
			// Filter userList to specified item
			// Send specified item
			if (message.getPayload() == RequestType.ALL_USERS)
			{
				recieveSuccessful = server.fetchUsers(RequestType.ALL_USERS);
			}
			break;
		case PROPERTY_DELIVERY:
			// Change specified property
			break;
		case PROPERTY_REQUEST:
			// Fetch the specified property and send it
			break;
		default:
			break;
		}
		return recieveSuccessful;
	}
}
