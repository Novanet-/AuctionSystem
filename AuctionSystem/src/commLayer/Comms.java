package commLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import applications.Client;
import applications.Server;

public class Comms
{

	public boolean sendMessage(Client sender, Server receiever)
	{
		return false;
	}

	public boolean sendMessage(Server sender, Client receiever)
	{
		return false;
	}

	public boolean recieveMessage(Client sender, Server receiever)
	{
		return false;
	}

	public boolean recieveMessage(Server sender, Client receiever)
	{
		return false;
	}

	public void initClientSocket()
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

	public void initServerSocket()
	{

		int portNumber = 6266;

		try (ServerSocket serverSocket = new ServerSocket(62666);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
		{
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				out.println(inputLine);
			}
		}
		catch (IOException e)
		{
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}
