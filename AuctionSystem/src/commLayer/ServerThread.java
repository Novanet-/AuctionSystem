package commLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created using Java 8
 *
 */
public class ServerThread extends Thread
{

	ServerSocket serverSocket;
	Socket clientSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	AbstractComms comms;


	public ServerThread(AbstractComms comms)
	{
		this.comms = comms;
	}


	@Override
	public void run()
	{
		int portNumber = 62666;

		try
		{
			createServerSocket(portNumber);
			listenForInput();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}


	/**
	 * Creates a server with a with a specified port number, with input and output streams
	 * 
	 * @param portNumber
	 *            The specified port number
	 * @throws IOException
	 */
	private void createServerSocket(int portNumber) throws IOException
	{
		System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Initialising server socket");
		serverSocket = new ServerSocket(portNumber);
		clientSocket = serverSocket.accept();
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
	}


	/**
	 * Waits for a message to come through on the input stream, when it does, forward it to the comms module to receive
	 * it
	 * 
	 * @throws IOException
	 */
	private void listenForInput() throws IOException
	{
		Message inputMessage;
		while (true)
		{
			try
			{
				if ((inputMessage = (Message) in.readObject()) != null)
				{
					comms.recieveMessage(inputMessage);
				}
//				else
//				{
//					//clientSocket.close();
//				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}


	/**
	 * Sends a message to the output stream of the server
	 * 
	 * @param message
	 *            The message to be sent
	 * @return boolean - isMessageSendSuccesful
	 */
	public boolean sendToOutputStream(Message message)
	{
		try
		{
			out.writeObject(message);
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
