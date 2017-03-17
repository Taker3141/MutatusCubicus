package entity;

import gui.OverlayOrgans;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
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
	}

	public void clickAt(ICollidable e, Vector3f vec)
	{
		
	}
}