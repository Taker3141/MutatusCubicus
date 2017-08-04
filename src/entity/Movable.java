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
import world.World;
import raycasting.IHitBox;

public class Movable extends Entity
{
	public final float mass;
	protected boolean isInAir = false;
	public Vector3f v = new Vector3f();
	public List<Vector3f> forces = new ArrayList<Vector3f>();
	protected float terrainHeight = 0;
	protected boolean collisionOff = false;
	private boolean isOnFloor = false;
	
	public Movable(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, float mass)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		this.mass = mass;
	}
	
	@Override
	public void update(World w, Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		
		for (Vector3f force : forces) v = Vector3f.add(v, force, v);
		forces.clear();
		v = Vector3f.add(v, (Vector3f)w.getGravityVector(this).scale(delta * getGravityFactor()), null);
		if (!collisionOff &&!canMove(v.x * delta, v.z * delta, terrain))
		{
			v.x *= -1;
			v.y = 0;
			v.z *= -1;
		}
		if((terrain != null && position.y < terrain.getHeight(position.x, position.z) + 0.2F) || isOnFloor) calculateFriction(delta);
		position.x += v.x * delta;
		position.y += v.y * delta;
		position.z += v.z * delta;
		checkTerrain(terrain);
		if(hitBox instanceof AABB) ((AABB)hitBox).location = position;
		if (position.x != position.x)
		{
			System.out.println("Oh, shit! Position is Not A Number!");
			position = new Vector3f();
		}
	}
	
	protected boolean noCollision()
	{
		isOnFloor = false;
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
					o1.v = (Vector3f) data.normal.normalise(null).scale(0.1F * o1.v.length());
					o2.v = (Vector3f) data.normal.normalise(null).scale(-0.1F * o2.v.length());
					//o1.v = (Vector3f)Vector3f.sub(position, o2.getHitBox().getCenter(o1.position), null).normalise(null).scale(0.1F * o2.mass / o1.mass);
					//o2.v = (Vector3f)Vector3f.sub(position, o1.getHitBox().getCenter(o2.position), null).normalise(null).scale(0.1F * o1.mass / o2.mass);
				}
				else if(!(c instanceof Movable) && this instanceof Movable)
				{
					if(data.type == IHitBox.Type.OBJECT)
					{
						((Movable)this).v = data.normal.normalise(null);
					}
					else if(data.type == IHitBox.Type.FLOOR && ((Movable)this).v.y < 0) 
					{
						((Movable)this).v.y = 0;
						((Movable)this).isInAir = false;
						isOnFloor = true;
						this.position.y = data.height;
					}
					if(data.type == IHitBox.Type.WALL)
					{
//						Vector3f v = Vector3f.sub(position, c.getHitBox().getCenter(position), null).normalise(null);
//						v.y = 0;
//						((Movable)this).v = v;
						((Movable)this).v = data.normal.normalise(null);
					}
				}
			}
		}
		return true;
	}
	
	protected void calculateFriction(float delta)
	{
		double factor = Math.pow(0.9F, delta * 50);
		if (Math.abs(v.x) < 0.05F) v.x = 0;
		else v.x *= factor;
		if (Math.abs(v.z) < 0.05F) v.z = 0;
		else v.z *= factor;
	}
	
	protected void checkTerrain(Terrain terrain)
	{
		if(terrain == null) return;
		terrainHeight = terrain == null ? 0 : terrain.getHeight(position.x, position.z);
		if (position.y <= terrainHeight)
		{
			v.y = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	protected boolean canMove(float x, float z, Terrain terrain)
	{
		float ty = terrain == null ? 0 : terrain.getHeight(position.x + x, position.z + z);
		return (position.y > terrainHeight || (ty - terrainHeight) < 0.2F) && noCollision();
	}
	
	protected float getGravityFactor()
	{
		return 1;
	}
}
