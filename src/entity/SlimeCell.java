package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ComplexAABB;
import raycasting.IHitBox;
import renderer.models.TexturedModel;

public class SlimeCell extends Entity
{
	protected boolean isOpen = false;
	
	public SlimeCell(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
	}
	
	public void configureHitbox(float length)
	{
		AABB[] walls = new AABB[6];
		Vector3f offsetPosition = new Vector3f(position.x - length, position.y, position.z - length);
		float thickness = length * 0.1F;
		walls[0] = new AABB(offsetPosition, new Vector3f(length, length, thickness), new Vector3f(0, 0, 0));
		walls[1] = new AABB(offsetPosition, new Vector3f(length, length, thickness), new Vector3f(0, 0, length));
		
		walls[2] = new AABB(offsetPosition, new Vector3f(thickness, length, length), new Vector3f(0, 0, 0));
		walls[3] = new AABB(offsetPosition, new Vector3f(thickness, length, length), new Vector3f(length, 0, 0));
		
		walls[4] = new AABB(offsetPosition, new Vector3f(length, thickness, length), new Vector3f(0, 0, 0), IHitBox.Type.FLOOR, false);
		walls[5] = new AABB(offsetPosition, new Vector3f(length, thickness * 0.5F, length), new Vector3f(0, length, 0), IHitBox.Type.OBJECT, true);
		hitBox = new ComplexAABB(offsetPosition, new Vector3f(length, length, length), new Vector3f(), walls);
	}
	
	public void setOpen(boolean open)
	{
		isOpen = open;
	}
}
