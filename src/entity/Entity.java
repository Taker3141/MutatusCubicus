package entity;

import java.util.List;
import main.MainManagerClass;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import raycasting.IHitBox;
import raycasting.IHitBox.CollisionData;
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
		this(model, position, rotX, rotY, rotZ, scale, list, new AABB(position, new Vector3f(), new Vector3f()));
	}
	
	public void update(Terrain t)
	{
		noCollision();
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		rotX += dx;
		rotY += dy;
		rotZ += dz;
	}
	
	protected boolean noCollision()
	{
		for (ICollidable c : entityList)
		{
			if (c == this) continue;
			CollisionData data = isInsideHitBox(c.getHitBox());
			if (data != null)
			{
				if(c instanceof Movable && this instanceof Movable)
				{
					Movable o1 = (Movable)this;
					Movable o2 = (Movable)c;
					Vector3f v1 = o1.v;
					Vector3f v2 = o2.v;
					o1.v = new Vector3f((Vector3f)v2.scale(o2.mass).scale(1 / o1.mass));
					o2.v = new Vector3f((Vector3f)v1.scale(o1.mass).scale(1 / o2.mass));
				}
				else if(!(c instanceof Movable) && this instanceof Movable)
				{
					if(data.type == IHitBox.Type.OBJECT)
					{
						((Movable)this).v = Vector3f.sub(position, c.getHitBox().getCenter(position), null).normalise(null);
					}
					else if(data.type == IHitBox.Type.FLOOR && ((Movable)this).v.y < 0) 
					{
						((Movable)this).v.y = 0;
						((Movable)this).isInAir = false;
					}
					if(data.type == IHitBox.Type.WALL)
					{
//						Vector3f v = Vector3f.sub(position, c.getHitBox().getCenter(position), null).normalise(null);
//						v.y = 0;
//						((Movable)this).v = v;
						((Movable)this).v = ((Movable)this).v.negate(null);
					}
				}
			}
		}
		return true;
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
