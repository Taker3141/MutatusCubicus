package level;

import world.World;

public class Level
{
	public final String name;
	public final Class<? extends World> startWorld;
	public final String levelFile;
	public boolean available;
	
	public Level(String name, Class<? extends World> startWorld, boolean available, String levelFile)
	{
		this.name = name;
		this.startWorld = startWorld;
		this.available = available;
		this.levelFile = levelFile;
	}
	
	public World loadLevel()
	{
		try
		{
			World w = startWorld.newInstance();
			return w;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}