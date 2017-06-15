package entity.vehicle;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import entity.Entity;
import entity.Movable;
import entity.Player;
import entity.SubEntity;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import raycasting.AABB;
import terrain.Terrain;
import world.World;

public class Car extends Movable
{
	public Player passenger;
	SubEntity wheel1;
	SubEntity wheel2;
	SubEntity wheel3;
	SubEntity wheel4;
	
	public Car(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, float mass)
	{
		super(World.createModel("car/car", "texture/metal", 0.2F), position, rotX, rotY, rotZ, scale, list, mass);
		Entity roof = new SubEntity(World.createModel("car/roof", "texture/glass", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, list, this);
		roof.model.getTexture().setHasTransparency(true);
		new SubEntity(World.createModel("car/door", "texture/metal", 0.2F), new Vector3f(0.86F, 0.7F, 1.03F), 0, 0, 0, 1, list, this);
		new SubEntity(World.createModel("car/doorR", "texture/metal", 0.2F), new Vector3f(-0.76F, 0.7F, 1.03F), 0, 0, 0, 1, list, this);
		wheel1 = new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(0.7F, 0.2F, -1.5F), 0, 0, 0, 1, list, this);
		wheel2 = new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(-0.7F, 0.2F, -1.5F), 0, 0, 0, 1, list, this);
		wheel3 = new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(0.7F, 0.2F, 1.2F), 0, 0, 0, 1, list, this);
		wheel4 = new SubEntity(World.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(-0.7F, 0.2F, 1.2F), 0, 0, 0, 1, list, this);
		Keyframe[] k = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.1F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(90, 0, 0), 0, 0.1F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(180, 0, 0), 0, 0.1F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(270, 0, 0), 0, 0.1F),
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(360, 0, 0), 0, 0.01F)
			};
		wheel1.a = new KeyframeAnimation(wheel1, k);
		wheel2.a = new KeyframeAnimation(wheel2, k);
		wheel3.a = new KeyframeAnimation(wheel3, k);
		wheel4.a = new KeyframeAnimation(wheel4, k);
		hitBox = new AABB(new Vector3f(0, 0, 0), new Vector3f(1.5F, 1.2F, 2.5F), new Vector3f(-0.75F, 0, -1.25F));
	}
	
	public void update(Terrain t)
	{
		super.update(t);
		if(v.lengthSquared() > 0.1F)
		{
			wheel1.a.isRunning = true;
			wheel2.a.isRunning = true;
			wheel3.a.isRunning = true;
			wheel4.a.isRunning = true;
		}
		else
		{
			wheel1.a.isRunning = false;
			wheel2.a.isRunning = false;
			wheel3.a.isRunning = false;
			wheel4.a.isRunning = false;
		}
		if(passenger != null)
		{
			passenger.position = new Vector3f(position); 
			passenger.position.y += 0.2F;
		}
	}
	
	@Override
	protected void calculateFriction(float delta)
	{
		double factor = Math.pow(0.95F, delta * 50);
		if (Math.abs(v.x) < 0.05F) v.x = 0;
		else v.x *= factor;
		if (Math.abs(v.z) < 0.05F) v.z = 0;
		else v.z *= factor;
	}
}
