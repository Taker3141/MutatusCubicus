package raycasting;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Floor implements IHitBox
{
	public Vector3f location, size, offset;
	protected Vector2f[] polygon;
	protected float height;

	public Floor(Vector3f location, float height, Vector3f offset, Vector2f[] polygon)
	{
		this.location = location;
		this.height = height;
		this.offset = offset;
		this.polygon = polygon;
		for(int i = 0; i < polygon.length; i++)
		{
			polygon[i] = Vector2f.add(polygon[i], new Vector2f(location.x, location.z), null);
		}
	}
	
	@Override
	public CollisionData isInside(Vector3f point)
	{
		Vector2f p = new Vector2f(point.x, point.z);
		int i, j = polygon.length - 1;
		boolean oddNodes = false;
		for (i = 0; i < polygon.length; i++)
		{
			if (polygon[i].y < p.y && polygon[j].y >= p.y || polygon[j].y < p.y && polygon[i].y >= p.y)
			{
				if (polygon[i].x + (p.y - polygon[i].y) / (polygon[j].y - polygon[i].y) * (polygon[j].x - polygon[i].x) < p.x)
				{
					oddNodes = !oddNodes;
				}
			}
			j = i;
		}
		if(oddNodes) return new CollisionData(new Vector3f(point.x, location.y, point.z), new Vector3f(0, 1, 0), true, Type.FLOOR);
		return null;
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		return isInside(box.getCenter(location));
	}
	
	@Override
	public Vector3f getCenter(Vector3f point)
	{
		return location;
	}

	@Override
	public boolean isPlatform()
	{
		return true;
	}
}
