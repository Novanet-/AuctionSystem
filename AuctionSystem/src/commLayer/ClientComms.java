package commLayer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

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

	ClientGUI client;
	ClientThread clientThread;


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
	}


	/**
	 * Forwards a message to the client thread
	 * 
	 * @see commLayer.AbstractComms#sendMessage(commLayer.Message)
	 */
	@Override
	public boolean sendMessage(Message message)
	{
		System.out
				.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Client Send " + message.getHeader().toString() + " " + message.getPayload().toString());
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
		System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Client Recieve " + message.getHeader().toString() + "  "
				+ message.getPayload().toString());
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
		case PROPERTY_DELIVERY:
			// Use the property received for required function
			break;
		case USER_DELIVERY:
			// Use requested users details
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
				break;
			case PROPERTY_RECIEVED:
				break;
			case USER_RECIEVED:
				break;
			case ITEM_REQUEST_RECIEVED:
				client.clearCache();
				break;
			case USER_REQUEST_RECIEVED:
				break;
			case USER_NOT_FOUND:
				// Display user not found dialog
				break;
			case PASSWORD_CORRECT:
				// Display login successful dialog
				client.loginUser();
				break;
			case PASSWORD_INCORRECT:
				// Display password incorrect dialog
				break;
			default:
				break;
			}
			break;
		case AUCTION_FINISHED:
			Item finishedAuction = (Item) message.getPayload();
			// Display auction finished dialog
			if (client.getCurrentUser() != null)
			{
				if (finishedAuction.getUserId() == client.getCurrentUser().getUserId())
				{
					// Display finished dialog
				}
				if (!finishedAuction.getBids().empty())
				{
					if (finishedAuction.getBids().peek().getUserId() == client.getCurrentUser().getUserId())
						sendMessage(new Message(MessageType.WIN_RECIEVED, finishedAuction));
					// Display won dialog
				}
			}
			break;
		default:
			break;
		}
		return recieveSuccesful;
	}
}
