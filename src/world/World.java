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
import entity.vehicle.Rocketship;
import gui.Overlay;

public abstract class World
{
	public static Random r = new Random();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Overlay> overlays = new ArrayList<Overlay>();
	public List<Orbit> orbitList = new ArrayList<Orbit>();
	protected MasterRenderer renderer = new MasterRenderer();
	protected Loader loader = MainManagerClass.loader;
	protected Input input = new Input(Display.getHeight());
	protected Camera c;
	protected Raycaster ray;
	protected List<Light> lights = new ArrayList<Light>();
	protected Player player;
	
	public World()
	{
		Particle.init();
		Rocketship.init();
		Item.init();
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
		c.update();
		ray.castRay(input.getAbsoluteMouseX(), Display.getHeight() - input.getAbsoluteMouseY(), renderer, c);
		lights.get(1).position = new Vector3f(player.position.x, player.position.y + 0.5F, player.position.z);
		for(Light l : lights) l.update();
		return true;
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
