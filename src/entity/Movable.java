package entity;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import raycasting.IHitBox.CollisionData;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;
import raycasting.IHitBox;

public class Movable extends Entity
{
	protected static final float GRAVITY = -5F;
	public final float mass;
	protected boolean isInAir = false;
	public Vector3f v = new Vector3f();
	public List<Vector3f> forces = new ArrayList<Vector3f>();
	protected float terrainHeight = 0;
	
	public Movable(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, float mass)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.mass = mass;
	}
	
	@Override
	public void update(Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		
		for (Vector3f force : forces) v = Vector3f.add(v, force, v);
		forces.clear();
		v.y += getGravity() * delta;
		
		if (!canMove(v.x * delta, v.z * delta, terrain))
		{
			v.x *= -1;
			v.y = 0;
			v.z *= -1;
		}
		calculateFriction(delta);
		position.x += v.x * delta;
		position.y += v.y * delta;
		position.z += v.z * delta;
		checkTerrain(terrain);
		((AABB)hitBox).location = position;
		if (position.x != position.x)
		{
			System.out.println("Oh, shit! Position is Not A Number!");
			position = new Vector3f();
		}
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
	
	private void calculateFriction(float delta)
	{
		double factor = Math.pow(0.9F, delta * 50);
		if (Math.abs(v.x) < 0.05F) v.x = 0;
		else v.x *= factor;
		if (Math.abs(v.z) < 0.05F) v.z = 0;
		else v.z *= factor;
	}
	
	protected void checkTerrain(Terrain terrain)
	{
		terrainHeight = terrain.getHeight(position.x, position.z);
		if (position.y <= terrainHeight)
		{
			v.y = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	protected boolean canMove(float x, float z, Terrain terrain)
	{
		return (position.y > terrainHeight || (terrain.getHeight(position.x + x, position.z + z) - terrainHeight) < 0.2F) && noCollision();
	}
	
	protected float getGravity()
	{
		return GRAVITY;
	}
}
