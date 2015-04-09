package commLayer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import entities.Item;
import entities.User;

public final class Hasher
{

	public Hasher()
	{
	}


	public static byte[] getPasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.reset();
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		return input;
	}


	
	public static int convertByteArrayToInt(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getInt();
	}
	
	
}
