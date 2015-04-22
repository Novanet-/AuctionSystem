package commLayer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import applications.ClientGUI;
import entities.Item;

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
		System.out.println("Client Send at " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
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
		System.out.println("Client Recieve at " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
		switch (message.getHeader())
		{
		case ITEM_DELIVERY:
			recieveSuccesful = client.addAuctionToCache((Item) message.getPayload());
			recieveSuccesful = client.refreshAuctionList(RequestType.ALL_OPEN_ITEMS);
			break;
		case BID_DELIVERY:
			recieveSuccesful = client.addAuctionToCache((Item) message.getPayload());
			recieveSuccesful = client.refreshAuctionList(RequestType.ALL_OPEN_ITEMS);
			break;
		case PROPERTY_DELIVERY:
			// Use the property received for required function
			break;
		case USER_DELIVERY:
			// Use requested users details
			break;
		case NOTIFICATION:
			switch ((Notification) message.getPayload())
			{
			case ITEM_RECIEVED:
				System.out.println("Item Recieved by server at " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
				client.clearCache();
				sendMessage(new Message(MessageType.ITEM_REQUEST, RequestType.ALL_OPEN_ITEMS));
				break;
			case BID_RECIEVED:
				System.out.println("Bid Recieved by server at " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
				client.clearCache();
				sendMessage(new Message(MessageType.ITEM_REQUEST, RequestType.ALL_OPEN_ITEMS));
				break;
			case PROPERTY_RECIEVED:
				break;
			case USER_RECIEVED:
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return recieveSuccesful;
	}

}
