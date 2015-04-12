package commLayer;

/**
 * Created using Java 8
 *
 */
public class Message<T>
{

	private MessageType header;
	private T payload;


	/**
	 * @param header
	 * @param payload
	 */
	public Message(MessageType header, T payload)
	{
		super();
		this.header = header;
		this.payload = payload;
	}

}
