package entity;

import java.util.List;
import java.util.Random;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;
import world.World;

public class FuelGenerator extends Entity
{
	public static TexturedModel MAIN_MODEL;
	public static TexturedModel FUEL_MODEL;
	
	private float timeLeft = 0;
	private boolean generating = false;
	private final float Y;
	private Random rand = new Random();
	
	public static void init()
	{
		MAIN_MODEL = new TexturedModel(OBJLoader.loadOBJModel("fuel_generator/tank"), new ModelTexture(MainManagerClass.loader.loadTexture("texture/metal"), false, 0.2F));
		FUEL_MODEL = new TexturedModel(OBJLoader.loadOBJModel("pile"), new ModelTexture(MainManagerClass.loader.loadTexture("texture/octanitrocubane")));
	}
	
	public FuelGenerator(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(MAIN_MODEL, position, rotX, rotY, rotZ, 2, list);
		Y = position.y;
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		if(generating)
		{
			if(timeLeft > 0)
			{
				timeLeft -= DisplayManager.getFrameTimeSeconds();
			}
			if(timeLeft < 0)
			{
				generating = false;
				Octanitrocubane fuel = new Octanitrocubane(FUEL_MODEL, Vector3f.add(position, new Vector3f(0.4F, 2.8F, -4F), null), 0, 360 * rand.nextFloat(), 0, 0.1F, entityList);
				fuel.v = new Vector3f(10 * rand.nextFloat() - 5, 0, -2 - 1 * rand.nextFloat());
			}
			position.y = (float)(Math.sin(DisplayManager.getTime() * 100) * 0.05F + Y);
		}
		else
		{
			position.y = Y;
		}
	}
	
	@Override
	public void click()
	{
		timeLeft = 2;
		generating = true;
	}
}
