package raycasting;

import org.lwjgl.util.vector.Vector3f;

public interface IHitBox
{
	public CollisionData isInside(Vector3f point);
	public CollisionData isInside(IHitBox box);
	public boolean isPlatform();
	public Vector3f getCenter(Vector3f point);
	
	public static class CollisionData
	{
		public final Vector3f origin;
		public final Vector3f normal;
		public final boolean isPlatform;
		public final Type type;
		public final float height;
		
		protected CollisionData(Vector3f o, Vector3f n, boolean platform, Type t)
		{
			origin = o;
			normal = n;
			isPlatform = platform;
			type = t;
			height = 0;
		}
		
		protected CollisionData(Vector3f o, Vector3f n, boolean platform, Type t, float h)
		{
			origin = o;
			normal = n;
			isPlatform = platform;
			type = t;
			height = h;
		}
	}

	public static enum Type
	{
		FLOOR, WALL, OBJECT
	}
}
