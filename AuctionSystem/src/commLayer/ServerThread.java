package commLayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
	Comms comms;


	public ServerThread(Comms comms)
	{
		this.comms = comms;
	}
	
	@Override
	public void run() {
		int portNumber = 62666;

		try
		{
			System.out.println("Initialsing server socket");
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			{
				Message inputMessage;
				while (true)
				{
					try
					{
						if ((inputMessage = (Message) in.readObject()) != null)
						{
							comms.recieveMessage(inputMessage);
							out.writeObject(new Message(MessageType.NOTIFICATION, "Item recieved"));
						}
					}
					catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
    }
	
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
