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
		super(World.createModel("living_building", "texture/building", 0), position, rotX, rotY, rotZ, 10, list);
	}
	
	@Override
	protected void initializeWalls()
	{
		walls = new AABB[9];
		walls[0] = new AABB(new Vector3f(position.x, position.y + 0.5F, position.z), new Vector3f(100, 0.1F, -100), new Vector3f(0, 0, 0), IHitBox.Type.FLOOR);
		walls[1] = new Floor(new Vector3f(position.x, position.y + 5, position.z), new Vector3f(100, 0.1F, -100), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 5, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
		walls[2] = new Floor(new Vector3f(position.x, position.y + 10, position.z), new Vector3f(100, 0.1F, -100), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 10, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
		walls[3] = new Floor(new Vector3f(position.x + 30, position.y + 15, position.z - 35), new Vector3f(70, 0.1F, -65), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 15, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0)),
				new AABB(new Vector3f(position.x + 30, position.y + 15, position.z - 35), new Vector3f(35F, 0.1F, -35F), new Vector3f(0, 0, 0))});
		walls[4] = new Floor(new Vector3f(position.x + 30, position.y + 20, position.z - 35), new Vector3f(70, 0.1F, -65), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 20, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0)),
				new AABB(new Vector3f(position.x + 30, position.y + 20, position.z - 35), new Vector3f(35F, 0.1F, -35F), new Vector3f(0, 0, 0))});
		walls[5] = new Floor(new Vector3f(position.x + 30, position.y + 20, position.z - 70), new Vector3f(70, 0.1F, -30), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 20, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
		walls[6] = new Floor(new Vector3f(position.x + 30, position.y + 25, position.z - 70), new Vector3f(70, 0.1F, -30), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 25, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
		walls[7] = new Floor(new Vector3f(position.x + 30, position.y + 30, position.z - 70), new Vector3f(70, 0.1F, -30), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 30, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
		walls[8] = new Floor(new Vector3f(position.x + 65, position.y + 35, position.z - 70), new Vector3f(35, 0.1F, -30), new Vector3f(0, 0, 0),
				new AABB[]{new AABB(new Vector3f(position.x + 95, position.y + 35, position.z - 95), new Vector3f(3F, 0.1F, -3F), new Vector3f(0, 0, 0))});
	}
}
