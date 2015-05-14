package persistenceLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import entities.Item;
import entities.User;

public class DataPersistence
{

	static Path	dataDir	= Paths.get(System.getProperty("user.dir"), File.separator + "data" + File.separator);


	/**
	 * @param list
	 * @param entity
	 * @return
	 */
	public static boolean writeListToFile(ArrayList<?> list, EntityType entity)
	{
		FileOutputStream fout = null;
		try
		{
			Path filePath = Paths.get(dataDir + File.separator + entity.toString() + ".dat");
			fout = new FileOutputStream(filePath.toFile());
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(list);
			fout.flush();
			fout.close();
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			try
			{
				fout.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}


	/**
	 * @param entity
	 * @return
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
					ObjectInputStream ois = new ObjectInputStream(fin);
					ArrayList<?> entityList;
					if ((entityList = (ArrayList<?>) ois.readObject()) != null)
					{
						ois.close();
						return entityList;
					}

					ois.close();
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
		catch (IOException | ClassNotFoundException e)
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
	 * @return
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
