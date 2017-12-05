package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class ComplexAABB extends AABB
{
	protected AABB[] parts;

	public ComplexAABB(Vector3f location, Vector3f size, Vector3f offset, AABB[] parts)
	{
		super(location, size, offset);
		this.parts = parts;
	}
	
	@Override
	public CollisionData isInside(Vector3f point)
	{
		//if(super.isInside(point) == null) return null;
		for(int i = 0; i < parts.length; i++) 
		{
			if(parts[i] == null) continue;
			CollisionData data = parts[i].isInside(point);
			if(data != null) return data;
		}
		return null;
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		//if(super.isInside(box) == null) return null;
		for(int i = 0; i < parts.length; i++) 
		{
			if(parts[i] == null) continue;
			CollisionData data = parts[i].isInside(box);
			if(data != null) return data;
		}
		return null;
	}
}
