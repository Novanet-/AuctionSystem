package commLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created using Java 8
 *
 */
public class ClientThread extends Thread
{

	Socket clientSocket;
	ObjectOutputStream out;
	ObjectInputStream in, userIn;
	AbstractComms comms;


	public ClientThread(AbstractComms comms)
	{
		this.comms = comms;
	}


	@Override
	public void run()
	{
		try
		{
			createClientSocket();
			listenForInput();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Creates a client with a connection to a server, with input and output streams
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void createClientSocket() throws UnknownHostException, IOException
	{
		System.out.println("Initialsing client socket");
		clientSocket = new Socket("127.0.0.1", 62666);
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
	}


	/**
	 * Waits for a message to come through on the input stream, when it does, forward it to the comms module to receive
	 * it
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void listenForInput() throws IOException, ClassNotFoundException
	{
		Message input;
		while (true)
		{
			if ((input = (Message) in.readObject()) != null)
				comms.recieveMessage(input);
		}
	}


	/**
	 * Sends a message to the output stream of the client
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
