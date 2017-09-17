package entity.vehicle;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import entity.Entity;
import entity.Movable;
import entity.SubEntity;
import entity.character.Player;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import raycasting.AABB;
import terrain.Terrain;
import world.MoonLabWorld;
import world.World;

public class Car extends Movable
{
	public Player passenger;
	protected SubEntity wheel1;
	protected SubEntity wheel2;
	protected SubEntity wheel3;
	protected SubEntity wheel4;
	public float curveTime = 0;
	
	public Car(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(MoonLabWorld.createModel("car/car", "texture/metal", 0.2F), position, rotX, rotY, rotZ, scale, list, 1000);
		Entity roof = new SubEntity(MoonLabWorld.createModel("car/roof", "texture/glass", 0.2F), new Vector3f(0, 0, 0), 0, 0, 0, 1, list, this);
		roof.model.getTexture().setHasTransparency(true);
		new SubEntity(MoonLabWorld.createModel("car/door", "texture/metal", 0.2F), new Vector3f(0.86F, 0.7F, 1.03F), 0, 0, 0, 1, list, this);
		new SubEntity(MoonLabWorld.createModel("car/doorR", "texture/metal", 0.2F), new Vector3f(-0.76F, 0.7F, 1.03F), 0, 0, 0, 1, list, this);
		wheel1 = new SubEntity(MoonLabWorld.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(0.7F, 0.2F, -1.5F), 0, 0, 0, 1, list, this);
		wheel2 = new SubEntity(MoonLabWorld.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(-0.7F, 0.2F, -1.5F), 0, 0, 0, 1, list, this);
		wheel3 = new SubEntity(MoonLabWorld.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(0.7F, 0.2F, 1.2F), 0, 0, 0, 1, list, this);
		wheel4 = new SubEntity(MoonLabWorld.createModel("car/wheel", "texture/wheel", 0.2F), new Vector3f(-0.7F, 0.2F, 1.2F), 0, 0, 0, 1, list, this);
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
	
	@Override
	public void update(World w, Terrain t)
	{
		super.update(w, t);
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
		double factor = Math.pow(0.80F + (curveTime < 0.1F ? 0.15F : 0), delta * 50);
		if (Math.abs(v.x) < 0.05F) v.x = 0;
		else v.x *= factor;
		if (Math.abs(v.z) < 0.05F) v.z = 0;
		else v.z *= factor;
	}
}
