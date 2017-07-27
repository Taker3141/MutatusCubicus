package raycasting;

import java.util.HashMap;
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
		Vector3f corner1 = Vector3f.add(location, offset, null);
		Vector3f corner2 = Vector3f.add(corner1, size, null);
		if(corner1.x > corner2.x) {float x = corner1.x; corner1.x = corner2.x; corner2.x = x;}
		if(corner1.y > corner2.y) {float y = corner1.y; corner1.y = corner2.y; corner2.y = y;}
		if(corner1.z > corner2.z) {float z = corner1.z; corner1.z = corner2.z; corner2.z = z;}
		if(point.x < corner1.x || point.x > corner2.x) return null;
		if(point.y < corner1.y || point.y > corner2.y) return null;
		if(point.z < corner1.z || point.z > corner2.z) return null;
		return new CollisionData(location, findNormal(point), false, Type.OBJECT);
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		CollisionData ret;
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
	
	protected Vector3f findNormal(Vector3f point)
	{
		Vector3f corner = Vector3f.add(location, offset, null);
		HashMap<Vector3f, Float> dist = new HashMap<Vector3f, Float>();
		dist.put(new Vector3f(-1, 0, 0), Math.abs(point.x - corner.x));
		dist.put(new Vector3f(1, 0, 0), Math.abs(point.x - (corner.x + size.x)));
		//dist.put(new Vector3f(0, -1, 0), Math.abs(point.y - corner.y));
		dist.put(new Vector3f(0, 1, 0), Math.abs(point.y - (corner.y + size.y)));
		dist.put(new Vector3f(0, 0, -1), Math.abs(point.z - corner.z));
		dist.put(new Vector3f(0, 0, 1), Math.abs(point.z - (corner.z + size.z)));
		HashMap.Entry<Vector3f, Float> minEntry = null;
		for (HashMap.Entry<Vector3f, Float> entry : dist.entrySet())
		{
		    if (minEntry == null || entry.getValue() <= minEntry.getValue()) minEntry = entry; 
		}
		return minEntry.getKey();
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
