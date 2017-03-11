package raycasting;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

public class Wall extends AABB
{
	public float angle;
	
	public Wall(Vector3f location, Vector3f size, Vector3f offset, float angle)
	{
		super(location, size, offset);
		this.angle = angle;
	}
	
	public Wall(Vector3f p1, Vector3f p2, Vector3f size)
	{
		super(null, size, null);
		this.size = size;
		this.size.z = Vector3f.sub(p1, p2, null).length();
		this.location = new Vector3f((p1.x * 0.5F) + (p2.x * 0.5F), (p1.y * 0.5F) + (p2.y * 0.5F), (p1.z * 0.5F) + (p2.z * 0.5F));
		this.offset = new Vector3f(-size.x / 2, 0, -size.z / 2);
		this.angle = (float)Math.toDegrees(Vector3f.angle(Vector3f.sub(p2, p1, null), new Vector3f(1, 0, 0)));
		angle -= 90;
	}
	
	@Override
	public CollisionData isInside(Vector3f point)
	{
		Vector3f p = transform(point);
		CollisionData ret = new CollisionData(location, false, Type.WALL);
		Vector3f corner = Vector3f.add(location, offset, null);
		if(p.x < corner.x || p.x > corner.x + size.x) return null;
		if(p.y < corner.y || p.y > corner.y + size.y) return null;
		if(p.z < corner.z || p.z > corner.z + size.z) return null;
		return ret;
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		CollisionData ret = new CollisionData(location, false, Type.WALL);
		Vector3f c1 = Vector3f.add(location, offset, null);
		Vector3f c2 = Vector3f.add(c1, size, null);
		if(box.isInside(transform(new Vector3f(c1.x, c1.y, c1.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c1.x, c1.y, c2.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c1.x, c2.y, c1.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c1.x, c2.y, c2.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c2.x, c1.y, c1.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c2.x, c1.y, c2.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c2.x, c2.y, c1.z))) != null) return ret;
		if(box.isInside(transform(new Vector3f(c2.x, c2.y, c2.z))) != null) return ret;
		return null;
	}
	
	private Vector3f transform(Vector3f point)
	{
		return Vector3f.add(Maths.rotateVector(Vector3f.sub(point, location, null), angle), location, null);
	}
}
