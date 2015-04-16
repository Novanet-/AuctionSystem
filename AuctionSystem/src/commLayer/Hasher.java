package commLayer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created using Java 8
 *
 */
public final class Hasher
{

	public Hasher()
	{
	}


	/**
	 * Creates an MD5 hash of a given password
	 * 
	 * @param password
	 *            The plaintext password to be encrypted
	 * @return The hashed password
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getPasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.reset();
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		return input;
	}

}
