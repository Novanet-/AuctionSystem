package commLayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import entities.Item;
import entities.User;
import applications.ClientGUI;
import applications.ServerGUI;

/**
 * Created using Java 8
 *
 */
public class Comms
{
	ClientGUI client;
	ServerGUI server;
	
	ClientThread clientThread;
	ServerThread serverThread;
	char commType;


	/**
	 * @param clientThread
	 */
	public Comms(ClientGUI client, ClientThread clientThread)
	{
		super();
		this.client = client;
		this.clientThread = clientThread;
	}


	/**
	 * @param serverThread
	 */
	public Comms(ServerGUI server, ServerThread serverThread)
	{
		super();
		this.server = server;
		this.serverThread = serverThread;
	}


	public boolean sendMessage(Message message)
	{
		//System.out.println("send message");
		if (commType == 'c')
		{
			//System.out.println("client send");
			return clientThread.sendToOutputStream(message);
		}
		else if (commType == 's')
		{
			System.out.println("server send");
			return serverThread.sendToOutputStream(message);
		}
		else
		{
			return false;
		}
	}


	public boolean recieveMessage(Message message)
	{
		boolean recieveSuccesful = false;
		//System.out.println("recieve message");
		if (commType == 'c')
		{
			System.out.println("client recieve");
			switch (message.getHeader())
			{
			case ITEM_DELIVERY:
				recieveSuccesful = client.addAuctionToCache((Item) message.getPayload());
				recieveSuccesful = client.refreshAuctionList();		
				break;
			case PROPERTY_DELIVERY:
				//Use the property received for required function		
				break;
			case USER_DELIVERY:
				//Use requested users details
				break;
			case NOTIFICATION:
				System.out.println(message.getPayload().toString());
				break;
			default:
				break;
			}
			return recieveSuccesful;
		}
		else if (commType == 's')
		{
			System.out.println("server recieve");
			switch (message.getHeader())
			{
			case ITEM_DELIVERY:
				recieveSuccesful =  server.addAuctionToSystem((Item) message.getPayload());
				break;
			case ITEM_REQUEST:
				//Filter auctionList to specified item
				//Send specified item
				if (message.getPayload() == RequestType.ALL_OPEN_ITEMS)
				{
					recieveSuccesful = server.fetchItems(RequestType.ALL_OPEN_ITEMS);
				}				
				break;				
			case USER_DELIVERY:
				recieveSuccesful = server.addUserToSystem((User) message.getPayload());
				break;
			case USER_REQUEST:
				//Filter userList to specified item
				//Send specified item
				break;				
			case PROPERTY_DELIVERY:
				//Change specified property
				break;
			case PROPERTY_REQUEST:
				//Fetch the specified property and send it
				break;
			default:
				break;
			}
			return recieveSuccesful;
		}
		else
		{
			return recieveSuccesful;
		}
	}

	/**
	 * Initialises the client thread, creating a client socket , and input and output streams
	 * for this client socket Then listens for any incoming data
	 */
	public void initClientSocket()
	{

		clientThread = new ClientThread(this);
		clientThread.start();
		commType = 'c';
	}


	/**
	 * Initialises the server thread, creating a server socket and a client sucket bound to this, and input and output streams
	 * for this client socket Then listens for any incoming data
	 */
	public void initServerSocket()
	{
		serverThread = new ServerThread(this);
		serverThread.start();;
		commType = 's';
	}
}
