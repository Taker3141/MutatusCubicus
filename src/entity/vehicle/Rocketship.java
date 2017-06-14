package entity.vehicle;

import java.util.List;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import entity.Entity;
import entity.Movable;
import entity.SubEntity;
import raycasting.AABB;
import renderer.DisplayManager;
import renderer.Loader;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;

public class Rocketship extends Movable
{
	private static TexturedModel HULL;
	private static TexturedModel DECKS;
	private static TexturedModel ENGINE;
	
	private boolean thrusting = false;
	
	public static void init()
	{
		Loader l = MainManagerClass.loader;
		HULL = new TexturedModel(OBJLoader.loadOBJModel("rocketship/rocketship"), new ModelTexture(l.loadTexture("texture/rocketship")));
		DECKS = new TexturedModel(OBJLoader.loadOBJModel("rocketship/decks"), new ModelTexture(l.loadTexture("texture/metal")));
		ENGINE = new TexturedModel(OBJLoader.loadOBJModel("rocketship/engine"), new ModelTexture(l.loadTexture("texture/color/red")));
	}
	
	public Rocketship(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(HULL, position, rotX, rotY, rotZ, 10, list, 1000000);
		new SubEntity(DECKS, new Vector3f(), 0, 0, 0, 1, list, this);
		new SubEntity(ENGINE, new Vector3f(), 0, 0, 0, 1, list, this);
		hitBox = new AABB(position, new Vector3f(70, 20, 80), new Vector3f(-30, 0, -40));
	}
	
	@Override
	public void update(Terrain t)
	{
		if(thrusting)
		{
			v.y = GRAVITY * -1.1F;
			v.x += (float)(10000 * Math.cos(Math.toRadians(-rotY)) * DisplayManager.getFrameTimeSeconds());
			v.z += (float)(10000 * Math.sin(Math.toRadians(-rotY)) * DisplayManager.getFrameTimeSeconds());
			System.out.println(v.x);
		}
		super.update(t);
	}
	
	@Override
	public void click()
	{
		launch();
	}
	
	public void launch()
	{
		thrusting = true;
		collisionOff = true;
	}
}
