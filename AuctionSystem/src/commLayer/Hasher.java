package commLayer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created using Java 8
 *
 */
public final class Hasher
{

	/**
	 * Creates an MD5 hash of a given password
	 * 
	 * @param password
	 *            The plaintext password to be encrypted
	 * @return The hashed password
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getPasswordHash(char[] password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] input = digest.digest(toBytes(password));
		return input;
	}


	/**
	 * Converts a char[] to a byte[] using UTF-8 encoding
	 * 
	 * @param chars
	 *            The char array to be converted
	 * @return The byte array created from the char array
	 */
	private static byte[] toBytes(char[] chars)
	{
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
		Arrays.fill(charBuffer.array(), '\u0000'); // Clear sensitive data
		Arrays.fill(byteBuffer.array(), (byte) 0); // Clear sensitive data
		return bytes;
	}

}
