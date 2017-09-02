package level;

import java.io.*;
import main.MainGameLoop.WorldCreator;
import world.World;

public class Level implements WorldCreator
{
	public final String name;
	public final Class<? extends World> startWorld;
	public final String levelFileName;
	public boolean available;
	
	public Level(String name, Class<? extends World> startWorld, boolean available, String levelFile)
	{
		this.name = name;
		this.startWorld = startWorld;
		this.available = available;
		this.levelFileName = levelFile;
	}
	
	public World createWorld()
	{
		try
		{
			World w = startWorld.newInstance();
			LevelParser.parseLevelFile(new File(levelFileName), w);
			return w;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
