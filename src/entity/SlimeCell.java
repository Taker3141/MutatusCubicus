package entity;

import java.util.LinkedList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ComplexAABB;
import raycasting.IHitBox;
import renderer.models.TexturedModel;
import world.World;

public class SlimeCell extends Entity
{
	protected boolean isOpen = false;
	protected AABB doorHitBox;
	protected Entity door;
	protected final float length;
	
	public SlimeCell(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float length, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.length = length;
	}
	
	public void configureHitbox(int sideToOpen)
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
		doorHitBox = walls[sideToOpen];

		if(sideToOpen == 2) door = addField(new Vector3f(1, 1, 0), new Vector3f(0, 0, -1), new Vector3f(90, 0, 0));
		else if(sideToOpen == 3) door = addField(new Vector3f(1, 1, 0), new Vector3f(), new Vector3f(90, 0, 0));
		else door = new SubEntity(null, new Vector3f(), 0, 0, 0, 0, new LinkedList<Entity>(), this);
		door.model.getTexture().setHasTransparency(true);
		door.model.transparencyNumber = 1;
	}
	
	public SubEntity addField(Vector3f p, Vector3f o, Vector3f r)
	{
		SubEntity field = new SubEntity(World.createModel("double_square", "texture/glass", 0.1F), Vector3f.add((Vector3f)p.scale(length / 2), (Vector3f)o.scale(length), null), r.x, r.y, r.z, 2.5F, entityList, this);
		field.model.getTexture().setHasTransparency(true);
		field.model.transparencyNumber = 1;
		return field;
	}
	
	public void setOpen(boolean open)
	{
		isOpen = open;
		door.invisible = open;
		doorHitBox.enabled = !open;
	}
}
