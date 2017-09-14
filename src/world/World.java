package world;

import inventory.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Input;
import raycasting.Raycaster;
import renderer.Loader;
import renderer.MasterRenderer;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import entity.*;
import entity.character.Player;
import entity.vehicle.Rocketship;
import gui.overlay.Overlay;
import gui.overlay.OverlayCharacterInfo;

public abstract class World
{
	public static Random r = new Random();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Overlay> overlays = new ArrayList<Overlay>();
	public List<Orbit> orbitList = new ArrayList<Orbit>();
	public List<Light> lights = new ArrayList<Light>();
	public float timeOfDay = 0;
	protected MasterRenderer renderer = new MasterRenderer();
	protected Loader loader = MainManagerClass.loader;
	protected Input input = new Input(Display.getHeight());
	public Camera c;
	protected Raycaster ray;
	public Player player;
	public OverlayCharacterInfo characterInfo;
	
	public World()
	{
		Particle.init();
		Rocketship.init();
		Item.init();
		characterInfo = new OverlayCharacterInfo();
		Entity.w = this;
		loadEntities();
		ray = new Raycaster(player);
		ray.setList(entities);
	}
	
	public abstract void loadEntities();
	public abstract Vector3f getGravityVector(Entity e);
	public abstract float height(float x, float z);
	
	public boolean tick()
	{
		input.poll(Display.getWidth(), Display.getHeight());
		characterInfo.update();
		c.update();
		ray.castRay(input.getAbsoluteMouseX(), Display.getHeight() - input.getAbsoluteMouseY(), renderer, c);
		lights.get(1).position = new Vector3f(player.position.x, player.position.y + 0.5F, player.position.z);
		for(Light l : lights) l.update();
		return true;
	}
	
	public void generateDecoration(TexturedModel model, int number, float x, float z, float scaleMin, float scaleMax, float rad, boolean duplicate)
	{
		for(int i = 0; i < number; i++)
		{
			float angle = r.nextFloat() * 360;
			float radius = r.nextFloat() * rad;
			float rotation = r.nextFloat() * 90;
			float size = scaleMin + r.nextFloat() * (scaleMax - scaleMin);
			new Entity(model, hVector((float)(x + radius * Math.cos(angle)), (float)(z + radius * Math.sin(angle))), 0, rotation, 0, size, entities);
			if(duplicate) new Entity(model, hVector((float)(x + radius * Math.cos(angle)), (float)(z + radius * Math.sin(angle))), 0, rotation + 180, 0, size, entities);
		}
	}
	
	public Vector3f hVector(float x, float z)
	{
		return new Vector3f(x, height(x, z), z);
	}
	
	public void cleanUp()
	{
		renderer.cleanUp();
		loader.cleanUp();
	}
	
	public static TexturedModel createModel(String modelName, String textureName, float reflect)
	{
		return new TexturedModel(OBJLoader.loadOBJModel(modelName), new ModelTexture(MainManagerClass.loader.loadTexture(textureName), false, reflect));
	}
	
	public void updateRaycaster()
	{
		ray.setList(entities);
	}
	
	public Vector3f getCoordinateOffset()
	{
		return new Vector3f();
	}
}
