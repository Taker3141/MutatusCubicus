package level;

import org.lwjgl.util.vector.Vector3f;
import world.World;

public class Level
{
	public final String name;
	public final Class<? extends World> startWorld;
	public final Vector3f startPosition;
	public boolean available;
	
	public Level(String name, Class<? extends World> startWorld, Vector3f startPosition, boolean available)
	{
		this.name = name;
		this.startWorld = startWorld;
		this.startPosition = startPosition;
		this.available = available;
	}
	
	public World loadLevel()
	{
		try
		{
			World w = startWorld.newInstance();
			w.player.position = startPosition;
			return w;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
