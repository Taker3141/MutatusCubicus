package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class Floor extends AABB
{
	private AABB[] holes;
	
	public Floor(Vector3f location, Vector3f size, Vector3f offset, AABB[] holes)
	{
		super(location, size, offset, IHitBox.Type.FLOOR, false);
		this.holes = holes;
	}
	
	@Override
	public CollisionData isInside(Vector3f point)
	{
		CollisionData data = super.isInside(point);
		if(data == null) return null;
		for(AABB hole : holes) 
		{
			if(hole.isInside(point) != null) 
			{
				return null;
			}
		}
		return data;
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		CollisionData data = super.isInside(box);
		if(data == null) return null;
		for(AABB hole : holes) if(hole.isInside(box) != null) return null;
		return data;
	}
}
