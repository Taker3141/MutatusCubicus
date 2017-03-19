package entity;

import java.util.List;
import main.MainManagerClass;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import raycasting.ICollidable;
import raycasting.IHitBox;
import raycasting.IHitBox.CollisionData;
import raycasting.NoHitbox;
import renderer.Loader;
import renderer.models.TexturedModel;
import terrain.Terrain;
import toolbox.Maths;

public class Entity implements ICollidable
{
	protected static Loader loader = MainManagerClass.loader;
	public TexturedModel model;
	public Vector3f position;
	public float rotX, rotY, rotZ;
	public float scale;
	protected IHitBox hitBox;
	protected List<Entity> entityList;
	public boolean invisible = false;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, IHitBox hitBox)
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.hitBox = hitBox;
		entityList = list;
		register();
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(model, position, rotX, rotY, rotZ, scale, list, new NoHitbox());
	}
	
	public void update(Terrain t)
	{
		
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		rotX += dx;
		rotY += dy;
		rotZ += dz;
	}

	@Override
	public CollisionData isInsideHitBox(Vector3f point)
	{
		return hitBox.isInside(point);
	}
	
	@Override
	public CollisionData isInsideHitBox(IHitBox box)
	{
		return hitBox.isInside(box);
	}
	
	@Override
	public IHitBox getHitBox()
	{
		return hitBox;
	}
	
	public void setHitBox(IHitBox hitBox)
	{
		this.hitBox = hitBox;
	}

	@Override
	public void hover()
	{
		
	}

	@Override
	public void click()
	{
		
	}
	
	public void register()
	{
		entityList.add(this);
	}
	
	public void unregister()
	{
		entityList.remove(this);
	}

	public Matrix4f getTransformationMatrix()
	{
		return Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
	}
}
