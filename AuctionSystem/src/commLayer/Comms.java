package commLayer;

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

}
