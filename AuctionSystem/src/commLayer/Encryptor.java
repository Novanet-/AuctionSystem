package commLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

	private static final SecretKey	key64	= new SecretKeySpec(new byte[]
											{ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 }, "Blowfish");	;
	private static Cipher		cipher = Encryptor.initCipher();;


	public static void writeToEncryptedStream(OutputStream plainOutStream, Message message) throws IOException, InvalidKeyException, IllegalBlockSizeException
	{
		cipher.init(Cipher.ENCRYPT_MODE, key64);
		CipherOutputStream cipherOutputStream = new CipherOutputStream(plainOutStream, cipher);
		ObjectOutputStream objOutStream = new ObjectOutputStream(cipherOutputStream);
		objOutStream.writeObject(sealMessage(message));
		objOutStream.close();
		objOutStream.flush();
		cipherOutputStream.flush();
		cipherOutputStream.close();
	}


	public static Message readFromEncryptedStream(InputStream plainInStream) throws IOException, ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException
	{
		cipher.init(Cipher.DECRYPT_MODE, key64);
		CipherInputStream cipherInputStream = new CipherInputStream(plainInStream, cipher);
		ObjectInputStream objInStream = new ObjectInputStream(cipherInputStream);
		SealedObject sealedObject = (SealedObject) objInStream.readObject();
		objInStream.close();
		cipherInputStream.close();
		return unsealMessage(sealedObject);
	}


	private static SealedObject sealMessage(Message message) throws IllegalBlockSizeException,
			IOException
	{
		SealedObject sealedObject = new SealedObject(message, cipher);
		return sealedObject;
	}


	private static Message unsealMessage(SealedObject message) throws ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		Message plainMessage = (Message) message.getObject(cipher);
		return plainMessage;
	}


	private static Cipher initCipher()
	{
		try
		{
			return Cipher.getInstance("Blowfish");
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	//	@Test
	//	public void testEncrypt() {
	//	  try {
	//	    String s = "Hello there. How are you? Have a nice day.";
	//
	//	    // Generate key
	//	    KeyGenerator kgen = KeyGenerator.getInstance("AES");
	//	    kgen.init(128);
	//	    SecretKey aesKey = kgen.generateKey();
	//
	//	    // Encrypt cipher
	//	    Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	//	    encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);
	//
	//	    // Encrypt
	//	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	//	    CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
	//	    cipherOutputStream.write(s.getBytes());
	//	    cipherOutputStream.flush();
	//	    cipherOutputStream.close();
	//	    byte[] encryptedBytes = outputStream.toByteArray();
	//
	//	    // Decrypt cipher
	//	    Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	//	    IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
	//	    decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
	//
	//	    // Decrypt
	//	    outputStream = new ByteArrayOutputStream();
	//	    ByteArrayInputStream inStream = new ByteArrayInputStream(encryptedBytes);
	//	    CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);
	//	    byte[] buf = new byte[1024];
	//	    int bytesRead;
	//	    while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
	//	        outputStream.write(buf, 0, bytesRead);
	//	    }
	//
	//	    System.out.println("Result: " + new String(outputStream.toByteArray()));
	//
	//	  } catch (Exception ex) {
	//	    ex.printStackTrace();
	//	  }
	//	}

	//Looks to me like you are not dealing properly with your Initialization Vector (IV). It's been a long time since I last read about AES, IVs and block chaining, but your line
	//
	//IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
	//
	//does not seem to be OK. In the case of AES, you can think of the initialization vector as the "initial state" of a cipher instance, and this state is a bit of information that you can not get from your key but from the actual computation of the encrypting cipher. (One could argue that if the IV could be extracted from the key, then it would be of no use, as the key is already given to the cipher instance during its init phase).
	//
	//Therefore, you should get the IV as a byte[] from the cipher instance at the end of your encryption
	//
	//  cipherOutputStream.close();
	//  byte[] iv = encryptCipher.getIV();
	//
	//and you should initialize your Cipher in DECRYPT_MODE with this byte[] :
	//
	//  IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	//
	//Then, your decryption should be OK. Hope this helps.

}
