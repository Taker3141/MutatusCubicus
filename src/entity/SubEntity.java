package entity;

import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import animation.KeyframeAnimation;
import renderer.models.IModel;
import terrain.Terrain;
import world.World;

public class SubEntity extends Entity
{
	public Entity parent;
	public KeyframeAnimation a;
	
	public SubEntity(IModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, Entity parent)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.parent = parent;
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		if(a != null) a.tick();
	}
	
	@Override
	public Matrix4f getTransformationMatrix(boolean correct)
	{
		return Matrix4f.mul(parent.getTransformationMatrix(true), super.getTransformationMatrix(false), null);
	}
}
