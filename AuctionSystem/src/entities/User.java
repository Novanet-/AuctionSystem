package entities;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created using Java 8
 *
 */
public class User implements Serializable
{

	private static final long	serialVersionUID	= -5122910007155743127L;

	private long				userId;
	private String				firstName;
	private String				surname;
	private byte[]				password;

	private static AtomicLong	counter				= new AtomicLong(0);


	/**
	 * @param firstName
	 * @param surname
	 * @param password
	 */
	public User(String firstName, String surname, byte[] password, boolean incrementId)
	{
		super();
		if (incrementId)
			this.userId = nextId();
		else
			this.userId = 0;
		this.firstName = firstName;
		this.surname = surname;
		this.password = password;
	}


	public long getUserId()
	{
		return userId;
	}


	public void setUserId(long userId)
	{
		this.userId = userId;
	}


	public String getFirstName()
	{
		return firstName;
	}


	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}


	public String getSurname()
	{
		return surname;
	}


	public void setSurname(String surname)
	{
		this.surname = surname;
	}


	public byte[] getPassword()
	{
		return password;
	}


	public void setPassword(byte[] password)
	{
		this.password = password;
	}


	public static void setCounter(AtomicLong counter)
	{
		User.counter = counter;
	}


	/**
	 * Increments the counter used to determine the next unique id of a created User
	 * 
	 * @return The incremented id
	 */
	public static long nextId()
	{
		return counter.incrementAndGet();
	}

}
