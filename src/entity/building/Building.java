package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.IHitBox;
import raycasting.IHitBox.CollisionData;
import renderer.models.TexturedModel;
import entity.Entity;

public abstract class Building extends Entity
{
	protected AABB[] walls;
	
	public Building(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		initializeWalls();
	}
	
	protected abstract void initializeWalls();
	
	@Override
	public CollisionData isInsideHitBox(Vector3f point)
	{
		for(AABB wall : walls)
		{
			CollisionData data = wall.isInside(point);
			if(data != null) return data;
		}
		return null;
	}
	
	@Override
	public CollisionData isInsideHitBox(IHitBox box)
	{
		for(AABB wall : walls)
		{
			CollisionData data = wall.isInside(box);
			if(data != null) return data;
		}
		return null;
	}
}
