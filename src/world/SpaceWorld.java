package world;

import org.lwjgl.util.vector.Vector3f;
import entity.Entity;

public class SpaceWorld extends World
{
	@Override
	public void loadEntities()
	{
		
	}

	@Override
	public Vector3f getGravityVector(Entity e)
	{
		return new Vector3f();
	}
}
