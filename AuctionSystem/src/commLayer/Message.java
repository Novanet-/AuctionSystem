package commLayer;

import java.io.Serializable;

/**
 * Created using Java 8
 *
 */
public class Message implements Serializable
{
	private static final long serialVersionUID = -1402974963399342516L;
	
	private MessageType header;
	private Object payload;


	/**
	 * @param header
	 * @param payload
	 */
	public Message(MessageType header, Object payload)
	{
		super();
		this.header = header;
		this.payload = payload;
	}


	
	public MessageType getHeader()
	{
		return header;
	}


	
	public void setHeader(MessageType header)
	{
		this.header = header;
	}


	
	public Object getPayload()
	{
		return payload;
	}


	
	public void setPayload(Object payload)
	{
		this.payload = payload;
	}
	
	

}
