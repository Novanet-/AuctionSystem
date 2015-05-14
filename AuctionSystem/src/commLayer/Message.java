package commLayer;

import java.io.Serializable;

/**
 * Created using Java 8
 *
 */
public class Message implements Serializable
{

	private static final long	serialVersionUID	= -1402974963399342516L;

	private MessageType			header;
	private Object				payload;


	/**
	 * Creates a new message, with a header identifying the message type and a payload containing the data of the
	 * message
	 * 
	 * @param header
	 *            The MessageType of the message
	 * @param payload
	 *            The data of the message
	 */
	public Message(MessageType header, Object payload)
	{
		super();
		this.header = header;
		this.payload = payload;
	}


	/**
	 * @return The MessageType of the message
	 */
	public MessageType getHeader()
	{
		return header;
	}


	/**
	 * @return The data of the message
	 */
	public Object getPayload()
	{
		return payload;
	}

}
