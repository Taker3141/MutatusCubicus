package raycasting;

import org.lwjgl.util.vector.Vector3f;
import raycasting.IHitBox.CollisionData;

public interface ICollidable
{	
	public CollisionData isInsideHitBox(Vector3f point);
	public CollisionData isInsideHitBox(IHitBox box);
	public IHitBox getHitBox();
	public void hover();
	public void click();
}
