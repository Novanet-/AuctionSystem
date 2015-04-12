package utilities;

/**
 * Created using Java 8
 *
 */
public enum Category
{
	CLOTHING("Clothing"), FURNITURE("Furniture"), ART("Art"), BOOKS("Books"), COINS("Coins"), POTTERY("Pottery");

	private final String name;

	private Category(String s)
	{
		name = s;
	}

	public boolean equalsName(String otherName)
	{
		if (otherName == null)
			return false;
		else
			return name.equals(otherName);
	}

	public String toString()
	{
		return name;
	}
}
