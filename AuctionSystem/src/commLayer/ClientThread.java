package commLayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created using Java 8
 *
 */
public class ClientThread extends Thread
{

	Socket				clientSocket;
	ObjectOutputStream	out;
	ObjectInputStream	in;
	AbstractComms		comms;


	public ClientThread(AbstractComms comms)
	{
		this.comms = comms;
	}


	/**
	 * Creates a client socket and then listens for input
	 * 
	 * @see java.lang.Thread#run()
	 */
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
		System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Initialising client socket");
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
			try
			{
				if ((input = Encryptor.readFromEncryptedStream(clientSocket.getInputStream())) != null)
					comms.recieveMessage(input);
			}
			catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			//			out.writeObject(message);
			Encryptor.writeToEncryptedStream(clientSocket.getOutputStream(), message);
			return true;
		}
		catch (IOException | InvalidKeyException | IllegalBlockSizeException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
