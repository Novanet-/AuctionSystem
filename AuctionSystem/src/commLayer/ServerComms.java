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
		System.out
				.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Server Send " + message.getHeader().toString() + " " + message.getPayload().toString());
		return serverThread.sendToOutputStream(message);
	}


	@Override
	public boolean recieveMessage(Message message)
	{
		Request request;
		System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Server Recieve " + message.getHeader().toString() + " "
				+ message.getPayload().toString());
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
			switch (request.getRequestType())
			{
			case ALL_OPEN_ITEMS:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case ALL_SOLD_ITEMS:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case ITEM_BY_CATEGORY:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case ITEM_BY_ID:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case ITEM_BY_SELLER:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case ITEM_CONTAINING_BID_BY_CURRENT_USER:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			case ITEMS_WON_BY_USER:
				recieveSuccessful = server.fetchAuctions(request);
				break;
			default:
				break;
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
			request = (Request) message.getPayload();
			switch (request.getRequestType())
			{
			case ALL_USERS:
				recieveSuccessful = server.fetchUsers(request);
				break;
			case DATABASE_HAS_A_USER:
				recieveSuccessful = server.fetchUsers(request);
				break;
			default:
				break;

			}
			break;
		case PROPERTY_DELIVERY:
			// Change specified property
			break;
		case PROPERTY_REQUEST:
			// Fetch the specified property and send it
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
