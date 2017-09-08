package entity.character;

public interface Character
{
	public String getID();
	public String getFirstName();
	public String getLastName();
	
	public default String getFullName()
	{
		return getFirstName() + " " + getLastName();
	}
	
	public int getFaceTexture();
}
