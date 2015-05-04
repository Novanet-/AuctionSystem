package commLayer;

public class Encryptor
{

	public Encryptor()
	{
		// TODO Auto-generated constructor stub
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
