package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class BuildingBox implements IHitBox
{
	public Floor floor;
	public Wall[] walls;
	public AABB bigBox;
	
	public BuildingBox(Floor floor, Wall[] walls, AABB bigBox)
	{
		this.floor = floor;
		this.walls = walls;
		this.bigBox = bigBox;
	}

	@Override
	public CollisionData isInside(Vector3f point)
	{
		if(bigBox.isInside(point) != null)
		{
			CollisionData ret;
			for(Wall w : walls)
			{
				ret = w.isInside(point);
				if(ret != null) return ret;
			}
			ret = floor.isInside(point);
			if(ret != null) return ret;
		}
		return null;
	}

	@Override
	public CollisionData isInside(IHitBox box)
	{
		if(bigBox.isInside(box) != null)
		{
			CollisionData ret;
			for(Wall w : walls)
			{
				ret = w.isInside(box);
				if(ret != null) return ret;
			}
			ret = floor.isInside(box);
			if(ret != null) return ret;
		}
		return null;
	}

	@Override
	public boolean isPlatform()
	{
		return true;
	}

	@Override
	public Vector3f getCenter(Vector3f point)
	{
		return walls[0].location;
		//return floor.getCenter(point);
	}
}
