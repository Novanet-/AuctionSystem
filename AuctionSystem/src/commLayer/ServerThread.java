package commLayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread
{

	ServerSocket serverSocket;
	Socket clientSocket;
	DataOutputStream out;
	ObjectInputStream in;


	public ServerThread(Comms comms)
	{
		int portNumber = 6266;

		try
		{
			serverSocket = new ServerSocket(62666);
			clientSocket = serverSocket.accept();
			out = new DataOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			{
				Object inputObject;
				while (true)
				{
					try
					{
						if ((inputObject = in.readObject()) != null)
							comms.recieveMessage(inputObject);
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
}
