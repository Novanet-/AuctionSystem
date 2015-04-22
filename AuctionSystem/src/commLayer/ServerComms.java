package commLayer;

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
		System.out.println("server send");
		return serverThread.sendToOutputStream(message);
	}


	@Override
	public boolean recieveMessage(Message message)
	{
		System.out.println("server recieve");
		boolean recieveSuccesful = false;
		switch (message.getHeader())
		{
		case ITEM_DELIVERY:
			if (recieveSuccesful = server.addAuctionToSystem((Item) message.getPayload()))
				sendMessage(new Message(MessageType.NOTIFICATION, Notification.ITEM_RECIEVED));
			break;
		case ITEM_REQUEST:
			if (message.getPayload() == RequestType.ALL_OPEN_ITEMS)
			{
				recieveSuccesful = server.fetchAuctions(RequestType.ALL_OPEN_ITEMS);
			}
			break;
		case BID_DELIVERY:
			if (recieveSuccesful = server.checkBidValid((Bid) message.getPayload()))
				sendMessage(new Message(MessageType.NOTIFICATION, Notification.BID_RECIEVED));
			break;
		case BID_REQUEST:
			break;
		case USER_DELIVERY:
			if (recieveSuccesful = server.addUserToSystem((User) message.getPayload()))
				sendMessage(new Message(MessageType.NOTIFICATION, Notification.USER_RECIEVED));
			break;
		case USER_REQUEST:
			// Filter userList to specified item
			// Send specified item
			if (message.getPayload() == RequestType.ALL_USERS)
			{
				recieveSuccesful = server.fetchUsers(RequestType.ALL_USERS);
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
		return recieveSuccesful;
	}
}
