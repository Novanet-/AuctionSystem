package commLayer;

public interface AbstractComms
{

	public abstract void initSocket();
	
	/**
	 * Sends a message to a socket
	 * 
	 * @param message
	 *            The message to be sent
	 * @return boolean - isSendSuccesful
	 */
	public abstract boolean sendMessage(Message message);


	/**
	 * Recieves a message from a socket
	 * 
	 * @param message
	 *            The message recieved
	 * @return boolean - isRecieveSuccesful
	 */
	public abstract boolean recieveMessage(Message message);

}