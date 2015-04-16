package commLayer;

import applications.ClientGUI;
import applications.ServerGUI;
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
	 * 
	 * @see commLayer.AbstractComms#sendMessage(commLayer.Message)
	 */
	@Override
	public boolean sendMessage(Message message)
	{
		System.out.println("client send");
		return clientThread.sendToOutputStream(message);
	}


	/**
	 * 
	 * @see commLayer.AbstractComms#recieveMessage(commLayer.Message)
	 */
	@Override
	public boolean recieveMessage(Message message)
	{
		boolean recieveSuccesful = false;
		System.out.println("client recieve");
		switch (message.getHeader())
		{
		case ITEM_DELIVERY:
			recieveSuccesful = client.addAuctionToCache((Item) message.getPayload());
			recieveSuccesful = client.refreshAuctionList();
			break;
		case PROPERTY_DELIVERY:
			// Use the property received for required function
			break;
		case USER_DELIVERY:
			// Use requested users details
			break;
		case NOTIFICATION:
			System.out.println(message.getPayload().toString());
			break;
		default:
			break;
		}
		return recieveSuccesful;
	}

}
