package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.*;
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
		walls = new AABB[3];
		walls[0] = new AABB(position, new Vector3f(100, 0.1F, -100), new Vector3f(0, 0, 0));
		walls[1] = new Floor(new Vector3f(position.x, position.y + 5, position.z), new Vector3f(100, 0.1F, -100), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 5, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
		walls[2] = new Floor(new Vector3f(position.x, position.y + 10, position.z), new Vector3f(100, 0.1F, -100), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 10, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
	}
}
