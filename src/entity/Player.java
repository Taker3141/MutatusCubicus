package entity;

import gui.OverlayOrgans;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import raycasting.NoHitbox;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Player extends Movable
{
	private static final float RUN_SPEED = 5F;
	private static final float TURN_SPEED = 40;
	private static final float JUMP_POWER = 10;
	
	private float currentTurnSpeed = 0;
	private float speed = RUN_SPEED;
	
	public OverlayOrgans organs = new OverlayOrgans(this);
	public float digestion = 100;
	public float energy = 200;
	public final float NORMAL_SIZE;
	public final float MAX_SIZE_FACTOR = 2;
	
	private Entity eating;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, float mass)
	{
		super(model, position, rotX, rotY, rotZ, scale, list, 0);
		hitBox = new AABB(position, new Vector3f(0.2F, 0.3F, 0.2F), new Vector3f(-0.1F, 0.15F, -0.1F));
		NORMAL_SIZE = scale;
	}
	
	@Override
	public void update(Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs(delta);
		
		rotY += currentTurnSpeed * delta;
		digestion -= DisplayManager.getFrameTimeSeconds();
		if(digestion < 0) digestion = 0;
		energy -= DisplayManager.getFrameTimeSeconds() / 50;
		if(energy < 0) energy = 0;
		organs.update();
		if(eating != null)
		{
			eating.position = new Vector3f(position);
			eating.position.y = position.y + 10 * scale;
			if(eating.scale > 0) eating.scale -= DisplayManager.getFrameTimeSeconds() * 0.1F;
			else {eating.unregister(); eating = null;};
		}
		
		super.update(terrain);
	}
	
	private void jump()
	{
		v.y = JUMP_POWER;
		isInAir = true;
	}
	
	private void checkInputs(float dt)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			v.x = (float) (speed * Math.sin(Math.toRadians(rotY)));
			v.z = (float) (speed * Math.cos(Math.toRadians(rotY)));
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			v.x = (float) (-speed * Math.sin(Math.toRadians(rotY)));
			v.z = (float) (-speed * Math.cos(Math.toRadians(rotY)));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;
		else currentTurnSpeed = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7) && (scale + 0.001F * dt) < NORMAL_SIZE * MAX_SIZE_FACTOR) scale += 0.001F * dt;
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) && scale > NORMAL_SIZE) scale -= 0.001F * dt;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) jump();
		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) speed = 2 * RUN_SPEED;
		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) speed = RUN_SPEED;
		if (Keyboard.isKeyDown(Keyboard.KEY_F12)) System.out.println(position);
		if (Keyboard.isKeyDown(Keyboard.KEY_Q) && eating == null)
		{
			final float distanceSq = 4;
			for(Entity e : entityList)
			{
				if(!(e instanceof IEdible)) continue;
				if(scale * 10 < e.scale) continue;
				float dx, dy, dz;
				dx = position.x - e.position.x; dy = position.y - e.position.y; dz = position.z - e.position.z;
				if(dx * dx + dy * dy + dz * dz < distanceSq) 
				{
					eating = e; eating.hitBox = new NoHitbox();
					break;
				}
			}
		}
	}

	public void clickAt(ICollidable e, Vector3f vec)
	{
		
	}
}