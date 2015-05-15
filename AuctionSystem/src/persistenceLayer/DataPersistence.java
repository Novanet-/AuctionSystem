package persistenceLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;

import commLayer.Encryptor;
import entities.Item;
import entities.User;

public class DataPersistence
{

	static Path	dataDir	= Paths.get(System.getProperty("user.dir"), File.separator + "data" + File.separator);


	/**
	 * Writes a given arraylist to a file, identifying the type of the arraylist with the entity parameter
	 * 
	 * @param list
	 *            The arraylist to be written
	 * @param entity
	 *            The type of the objects in the arraylist
	 * @return true if the file is written succesfully
	 */
	public static boolean writeListToFile(ArrayList<?> list, EntityType entity)
	{
		FileOutputStream fout = null;
		try
		{
			Path filePath = Paths.get(dataDir + File.separator + entity.toString() + ".dat");
			fout = new FileOutputStream(filePath.toFile());
//			ObjectOutputStream oos = new ObjectOutputStream(fout);
			Encryptor.writeToEncryptedStream(Encryptor.createEncryptedOutputStream(fout), list);

			//oos.writeObject(list);
			fout.close();
			return true;
		}
		catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e)
		{
			e.printStackTrace();
			try
			{
				if (fout == null)
					return false;
				fout.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			return false;
		}
	}


	/**
	 * Reads a given arraylist fron a file, identifying the type of the arraylist with the entity parameter
	 * 
	 * @param entity
	 *            The type of the objects in the arraylist
	 * @return The read arraylist
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<?> readListFromFile(EntityType entity)
	{
		FileInputStream fin = null;
		try
		{
			if (checkDataStoreExists())
			{
				Path filePath = Paths.get(dataDir + File.separator + entity.toString() + ".dat");
				fin = new FileInputStream(filePath.toFile());
				if (fin.available() > 0)
				{
//					ObjectInputStream ois = new ObjectInputStream(fin);
					ArrayList<?> entityList;
					if ((entityList = (ArrayList<?>) Encryptor.readFromEncryptedStream(Encryptor.createEncryptedInputStream(fin))) != null)
					{
						fin.close();
						return entityList;
					}

					fin.close();
					if (entity == EntityType.ITEM)
					{
						return entityList = new ArrayList<Item>();
					}
					else if (entity == EntityType.USER)
					{
						return entityList = new ArrayList<User>();
					}
					else
						return entityList = new ArrayList<>();
				}

			}
		}
		catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e)
		{
			e.printStackTrace();
			try
			{
				fin.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return new ArrayList();
	}


	/**
	 * Checks that the database directory exists
	 * 
	 * @return true if the database directory exists
	 */
	private static boolean checkDataStoreExists()
	{
		Path test = dataDir.toAbsolutePath();
		if (Files.notExists(dataDir))
		{
			try
			{
				createDataStore();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		else
			return true;
	}


	/**
	 * Creates the database directory and the relevant datastore files
	 * 
	 * @throws IOException
	 */
	private static void createDataStore() throws IOException
	{
		Files.createDirectory(dataDir);
		Path itemDir = Paths.get(dataDir + File.separator + "ITEM" + ".dat");
		Path userDir = Paths.get(dataDir + File.separator + "USER" + ".dat");
		if (Files.notExists(itemDir))
			Files.createFile(itemDir);
		if (Files.notExists(userDir))
			Files.createFile(userDir);
	}

}
