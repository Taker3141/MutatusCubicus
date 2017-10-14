package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.IHitBox;
import terrain.Terrain;
import world.World;
import entity.Entity;

public class LabBuilding extends Building
{
	
	public LabBuilding(Vector3f position, List<Entity> list)
	{
		super(World.createModel("lab", "texture/concrete", 0), position, 0, 0, 0, 54.8F, list);
		new Entity(World.createModel("square", "texture/lab/final_assembly", 0), new Vector3f(position.x + scale, 0.01F + position.y + 0.9F * scale, position.z - scale), 0, 0, 0, scale, list);
	}
	
	@Override
	protected void initializeWalls()
	{
		walls = new AABB[1];
		walls[0] = new AABB(new Vector3f(position.x, position.y + 0.9F * scale + 0.15F, position.z - scale * 2), new Vector3f(scale * 2, 0.1F, scale * 2), new Vector3f(), IHitBox.Type.FLOOR);
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		
	}
}
