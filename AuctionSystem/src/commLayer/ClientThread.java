package commLayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created using Java 8
 *
 */
public class ClientThread extends Thread
{

	Socket clientSocket;
	ObjectOutputStream out;
	ObjectInputStream in, userIn;
	Comms comms;


	public ClientThread(Comms comms)
	{
		this.comms = comms;
	}


	@Override
	public void run()
	{
		try
		{
			System.out.println("Initialsing client socket");
			clientSocket = new Socket("127.0.0.1", 62666);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			Message input;
			while (true)
			{
				if ((input = (Message) in.readObject()) != null)
					comms.recieveMessage(input);
			}
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
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
