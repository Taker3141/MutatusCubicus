package entity.character;

public interface ICharacter
{
	public String getID();
	public int[] getBirthday();
	public String getFirstName();
	public String getLastName();
	public Gender getGender();
	public String getProfession();
	
	public default String getTitle()
	{
		return getGender().defaultTitle;
	}
	
	public default String getFullName()
	{
		return getFirstName() + " " + getLastName();
	}
	
	public default String getFormalName()
	{
		return getGender().defaultTitle + " " + getLastName();
	}
	
	public int getFaceTexture();
	
	public enum Gender
	{
		MALE("männlich", "Mr."), FEMALE("weiblich", "Mrs."), OTHER("sonstiges", "");
		
		public final String name;
		public final String defaultTitle;
		
		private Gender(String name, String title)
		{
			this.name = name;
			defaultTitle = title;
		}
	}
}
