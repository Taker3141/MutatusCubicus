package entity;

import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;

public class Organ extends Entity
{
	public Entity parent;
	
	public Organ(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, Entity parent)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.parent = parent;
	}
	
	@Override
	public Matrix4f getTransformationMatrix()
	{
		return Matrix4f.mul(parent.getTransformationMatrix(), super.getTransformationMatrix(), null);
	}
}
