package commLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor
{

	private static final byte[]	key				= "MyDifficultPassw".getBytes();
	private static final String	transformation	= "AES";


	/**
	 * 
	 * Converts a normal Output stream and returns an ObjectOutputStream that includes a CipherOutputStream which uses
	 * AES to encrypt any data written to it
	 * 
	 * @param plainOutStream
	 *            The normal ouput stream to be converted
	 * @return The encrypting ObjectOutputStream
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static ObjectOutputStream createEncryptedOutputStream(OutputStream plainOutStream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException
	{
		// Length is 16 byte
		SecretKeySpec sks = new SecretKeySpec(key, transformation);

		// Create cipher
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, sks);

		// Wrap the output stream
		CipherOutputStream cos = new CipherOutputStream(plainOutStream, cipher);
		ObjectOutputStream outputStream = new ObjectOutputStream(cos);
		return outputStream;
	}


	/**
	 * Converts a normal Input stream and returns an ObjectInputStream that includes a CipherInputStream which uses AES
	 * to decrypt any data written to it
	 * 
	 * @param plainInStream
	 *            The normal input stream to be converted
	 * @return The decrypting ObjectInputStream
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static ObjectInputStream createEncryptedInputStream(InputStream plainInStream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException
	{
		SecretKeySpec sks = new SecretKeySpec(key, transformation);
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.DECRYPT_MODE, sks);

		CipherInputStream cipherInputStream = new CipherInputStream(plainInStream, cipher);
		ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
		return inputStream;
	}


	/**
	 * Writes a serializable message to an encrypting output stream
	 * 
	 * @param encryptingObjectPutStream
	 *            An ObjectOutputStream that contains a CipherOutputStream allowing it to encrypt data
	 * @param message
	 *            The message to be encrypted and sent
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	synchronized public static void writeToEncryptedStream(ObjectOutputStream objectOutStream, Serializable message) throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException
	{
		try
		{
			// Length is 16 byte
			SecretKeySpec sks = new SecretKeySpec(key, transformation);

			// Create cipher
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, sks);
			SealedObject sealedObject = new SealedObject(message, cipher);

			objectOutStream.writeObject(sealedObject);
			objectOutStream.flush();
			objectOutStream.close();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Reads a serializable message to an decrypting input stream
	 * 
	 * @param plainInStream
	 *            The decrypting input stream to be read from
	 * @return The decrypted object that has been read
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	synchronized public static Object readFromEncryptedStream(ObjectInputStream plainInStream) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException
	{
		SecretKeySpec sks = new SecretKeySpec(key, transformation);
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.DECRYPT_MODE, sks);

		SealedObject sealedObject;
		try
		{
			sealedObject = (SealedObject) plainInStream.readObject();
			//			inputStream.close();
			return sealedObject.getObject(cipher);
		}
		catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
