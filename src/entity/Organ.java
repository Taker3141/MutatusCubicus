package entity;

import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import animation.KeyframeAnimation;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Organ extends Entity
{
	public Entity parent;
	public KeyframeAnimation a;
	
	public Organ(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, Entity parent)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.parent = parent;
	}
	
	@Override
	public void update(Terrain t)
	{
		if(a != null) a.tick();
	}
	
	@Override
	public Matrix4f getTransformationMatrix()
	{
		return Matrix4f.mul(parent.getTransformationMatrix(), super.getTransformationMatrix(), null);
	}
}
