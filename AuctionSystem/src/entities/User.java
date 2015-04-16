package entities;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created using Java 8
 *
 */
public class User implements Serializable
{

	private static final long serialVersionUID = -5122910007155743127L;

	private int userId;
	private String firstName;
	private String surname;
	private String password;

	private static AtomicLong counter = new AtomicLong(0);


	public int getUserId()
	{
		return userId;
	}


	public void setUserId(int userId)
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


	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
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
