package commLayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread
{

	Socket echoSocket;
	PrintWriter out;
	BufferedReader in;
	Comms comms;


	public ClientThread(Comms comms)
	{
		this.comms = comms;
		this.start();
	}


	@Override
	public void run()
	{
		try
		{
			echoSocket = new Socket("127.0.0.1", 62666);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			Object userInput;
			while (true)
			{
				if ((userInput = in.readLine()) != null)
					out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
