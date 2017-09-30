package entity;

import java.util.List;
import loader.Loader;
import main.MainManagerClass;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import raycasting.ICollidable;
import raycasting.IHitBox;
import raycasting.IHitBox.CollisionData;
import raycasting.NoHitbox;
import renderer.models.IModel;
import renderer.models.TexturedModel;
import terrain.Terrain;
import toolbox.Maths;
import world.World;

public class Entity implements ICollidable
{
	protected static Loader loader = MainManagerClass.loader;
	public IModel model;
	public Vector3f position;
	public float rotX, rotY, rotZ;
	public float scale;
	public IHitBox hitBox;
	protected List<Entity> entityList;
	public boolean invisible = false;
	public static World w;
	public final boolean astronomical;
	
	public Entity(IModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, IHitBox hitBox)
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.hitBox = hitBox;
		entityList = list;
		astronomical = scale > 1000;
		register();
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(model, position, rotX, rotY, rotZ, scale, list, new NoHitbox());
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, IHitBox hitBox)
	{
		this((IModel)model, position, rotX, rotY, rotZ, scale, list, hitBox);
	}
	
	public Entity(IModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(model, position, rotX, rotY, rotZ, scale, list, new NoHitbox());
	}

	public void update(World w, Terrain t)
	{
		
	}

	@Override
	public CollisionData isInsideHitBox(Vector3f point)
	{
		return hitBox.isInside(point);
	}
	
	@Override
	public CollisionData isInsideHitBox(IHitBox box)
	{
		return getHitBox().isInside(box);
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

	public Matrix4f getTransformationMatrix(boolean correct)
	{
		Vector3f correctedPosition = Vector3f.add(position, w.getCoordinateOffset(), null);
		if(correct) return Maths.createTransformationMatrix(correctedPosition, rotX, rotY, rotZ, scale);
		else return Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale); 
	}
}
