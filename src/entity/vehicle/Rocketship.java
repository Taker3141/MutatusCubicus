package entity.vehicle;

import java.util.List;
import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import entity.Entity;
import entity.Movable;
import entity.Player;
import entity.SubEntity;
import gui.OverlaySpaceship;
import raycasting.AABB;
import renderer.DisplayManager;
import renderer.Loader;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;
import world.MoonLabWorld;
import world.World;

public class Rocketship extends Movable
{
	private static TexturedModel HULL;
	private static TexturedModel DECKS;
	private static TexturedModel ENGINE;
	private static TexturedModel FLAMES;
	
	private boolean thrusting = false;
	private float thrustingDistance = 0;
	private SubEntity[] flames = new SubEntity[2];
	public Player passenger;
	public OverlaySpaceship info = new OverlaySpaceship(this);
	
	public static void init()
	{
		Loader l = MainManagerClass.loader;
		HULL = new TexturedModel(OBJLoader.loadOBJModel("rocketship/rocketship"), new ModelTexture(l.loadTexture("texture/rocketship")));
		DECKS = new TexturedModel(OBJLoader.loadOBJModel("rocketship/decks"), new ModelTexture(l.loadTexture("texture/metal")));
		ENGINE = new TexturedModel(OBJLoader.loadOBJModel("rocketship/engine"), new ModelTexture(l.loadTexture("texture/color/red")));
		FLAMES = new TexturedModel(OBJLoader.loadOBJModel("grass"), new ModelTexture(l.loadTexture("texture/flames")));
	}
	
	public Rocketship(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(HULL, position, rotX, rotY, rotZ, 10, list, 1000000);
		new SubEntity(DECKS, new Vector3f(), 0, 0, 0, 1, list, this);
		new SubEntity(ENGINE, new Vector3f(), 0, 0, 0, 1, list, this);
		flames[0] = new SubEntity(FLAMES, new Vector3f(-2, 1, 0), 0, 0, 90, 1F, list, this);
		flames[0].invisible = true;
		flames[1] = new SubEntity(FLAMES, new Vector3f(-2, 1, 0), 180, 0, 90, 1F, list, this);
		flames[1].invisible = true;
		hitBox = new AABB(position, new Vector3f(70, 20, 80), new Vector3f(-30, 0, -40));
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		if(thrusting)
		{
			float dt = DisplayManager.getFrameTimeSeconds();
			if (thrustingDistance < 200)
			{
				v = Vector3f.add(v, (Vector3f)w.getGravityVector(this).scale(-1.1F), null);
				v.x += (float) (10000 * Math.cos(Math.toRadians(-rotY)) * dt);
				v.z += (float) (10000 * Math.sin(Math.toRadians(-rotY)) * dt);
				float dx = v.x * dt, dz = v.z * dt;
				thrustingDistance += Math.sqrt(dx * dx + dz * dz);
			}
			for (int i = 0; i < flames.length; i++)
			{
				flames[i].rotX += 1000 * dt;
				flames[i].rotY = (float)(MoonLabWorld.r.nextGaussian() * 10);
			}
		}
		super.update(w, t);
		if(passenger != null) {passenger.position = new Vector3f(position); passenger.position.y += 20;}
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
		flames[0].invisible = false;
		flames[1].invisible = false;
	}
}
