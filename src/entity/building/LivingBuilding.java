package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import world.World;
import entity.Entity;

public class LivingBuilding extends Building
{
	
	public LivingBuilding(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(World.createModel("living_building", "texture/concrete", 0), position, rotX, rotY, rotZ, 10, list);
	}
	
	@Override
	protected void initializeWalls()
	{
		walls = new AABB[0];
	}
}
