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

public class Comms
{

	ClientThread clientThread;
	ServerThread serverThread;


	/**
	 * @param clientThread
	 */
	public Comms(ClientThread clientThread)
	{
		super();
		this.clientThread = clientThread;
	}


	/**
	 * @param serverThread
	 */
	public Comms(ServerThread serverThread)
	{
		super();
		this.serverThread = serverThread;
	}


	public boolean sendMessage(Object message)
	{
		return false;
	}


	public boolean recieveMessage(Object message)
	{
		return false;
	}


	public static void initClientSocket()
	{

		try (Socket echoSocket = new Socket("127.0.0.1", 62666);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)))
		{
			String userInput;
			while ((userInput = stdIn.readLine()) != null)
			{
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host " + "127.0.0.1");
			System.exit(1);
		}
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to " + "127.0.0.1");
			System.exit(1);
		}
	}


	/**
	 * Initialises the server, creating a server socket and a client sucket bound to this, and input and output streams
	 * for this client socket
	 * Then listens for any incoming data
	 */
	public void initServerSocket()
	{

		int portNumber = 6266;

		try (ServerSocket serverSocket = new ServerSocket(62666);
				Socket clientSocket = serverSocket.accept();
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());)
		{
			Object inputObject;

			// Listens for any data received from the client, if data has been sent, then receive the data and process
			// it based on what type of data it is
			while (true)
			{
				try
				{
					if ((inputObject = in.readObject()) != null)
						recieveMessage(inputObject);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
