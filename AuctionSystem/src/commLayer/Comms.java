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


	public void initClientSocket()
	{

		clientThread = new ClientThread(this);
	}


	/**
	 * Initialises the server, creating a server socket and a client sucket bound to this, and input and output streams
	 * for this client socket
	 * Then listens for any incoming data
	 */
	public void initServerSocket()
	{
		serverThread = new ServerThread(this);
	}
}
