package utilities;

public enum CategoryEnum
{
	CLOTHING("Clothing"), FURNITURE("Furniture"), ART("Art"), BOOKS("Books"), COINS("Coins"), POTTERY("Pottery");

	private final String name;

	private CategoryEnum(String s)
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
