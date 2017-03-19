package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class NoHitbox implements IHitBox
{
	
	@Override
	public CollisionData isInside(Vector3f point)
	{
		return null;
	}
	
	@Override
	public CollisionData isInside(IHitBox box)
	{
		return null;
	}
	
	@Override
	public boolean isPlatform()
	{
		return false;
	}
	
	@Override
	public Vector3f getCenter(Vector3f point)
	{
		return new Vector3f();
	}
	
}
