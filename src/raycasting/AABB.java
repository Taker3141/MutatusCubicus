package raycasting;

import org.lwjgl.util.vector.Vector3f;

/**
 * Axis-aligned bounding box
 * @author Taker
 */

public class AABB implements IHitBox
{
	public Vector3f location, size, offset;

	public AABB(Vector3f location, Vector3f size, Vector3f offset)
	{
		this.location = location;
		this.size = size;
		this.offset = offset;
	}
	
	@Override
	public CollisionData isInside(Vector3f point)
	{
		CollisionData ret = new CollisionData(location, false, Type.OBJECT);
		Vector3f corner = Vector3f.add(location, offset, null);
		if(point.x < corner.x || point.x > corner.x + size.x) return null;
		if(point.y < corner.y || point.y > corner.y + size.y) return null;
		if(point.z < corner.z || point.z > corner.z + size.z) return null;
		return ret;
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		CollisionData ret = new CollisionData(location, false, Type.OBJECT);
		Vector3f c1 = Vector3f.add(location, offset, null);
		Vector3f c2 = Vector3f.add(c1, size, null);
		ret = box.isInside(c1); if(ret != null) return ret;
		ret = box.isInside(new Vector3f(c1.x, c1.y, c2.z)); if(ret != null) return ret;
		ret = box.isInside(new Vector3f(c1.x, c2.y, c1.z)); if(ret != null) return ret;
		ret = box.isInside(new Vector3f(c1.x, c2.y, c2.z)); if(ret != null) return ret;
		ret = box.isInside(new Vector3f(c2.x, c1.y, c1.z)); if(ret != null) return ret;
		ret = box.isInside(new Vector3f(c2.x, c1.y, c2.z)); if(ret != null) return ret;
		ret = box.isInside(new Vector3f(c2.x, c2.y, c1.z)); if(ret != null) return ret;
		ret = box.isInside(c2); if(ret != null) return ret;
		return null;
	}
	
	@Override
	public Vector3f getCenter(Vector3f point)
	{
		return Vector3f.sub(location, offset, null);
	}

	@Override
	public boolean isPlatform()
	{
		return false;
	}
}
