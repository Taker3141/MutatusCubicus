package entity;

import java.util.*;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector3f;
import raycasting.IHitBox;
import raycasting.IHitBox.CollisionData;
import renderer.DisplayManager;
import renderer.models.SimpleModel;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;
import world.World;

public class Particle extends Movable
{
	protected static final float PARTICLE_SIZE = 0.2F;
	protected static SimpleModel MODEL;
	protected float lifetime = 2F;
	
	public Particle(String textureName, Vector3f position, List<Entity> list, Random r)
	{
		super(new TexturedModel(MODEL, new ModelTexture(MainManagerClass.loader.loadTexture("texture/particle/" + textureName))), new Vector3f(position), 0, 0, 0, PARTICLE_SIZE, list, 0.1F);
		model.getTexture().setUseFakeLightning(true);
		rotX = r.nextInt(360);
		rotY = r.nextInt(360);
		rotZ = r.nextInt(360);
		v = new Vector3f((r.nextFloat() * 2F) - 1, r.nextFloat() + 1, (r.nextFloat() * 2F) - 1);
		v.scale(2);
	}
	
	@Override
	public void update(World w, Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		position.x += v.x * delta;
		position.y += v.y * delta;
		position.z += v.z * delta;
		v = Vector3f.add(v, (Vector3f)w.getGravityVector(this).scale(delta * getGravityFactor()), null);
		lifetime -= DisplayManager.getFrameTimeSeconds();
		if(lifetime <= 0) unregister();
	}
	
	@Override
	public CollisionData isInsideHitBox(IHitBox box)
	{
		return null;
	}
	
	@Override
	public CollisionData isInsideHitBox(Vector3f point)
	{
		return null;
	}
	
	@Override
	protected void checkTerrain(Terrain terrain)
	{
		terrainHeight = 0;
	}
	
	@Override
	protected float getGravityFactor()
	{
		return 0.1F;
	}
	
	public static void init()
	{
		MODEL = MainManagerClass.loader.loadToVAO
				(new float[] {0, 0, 0,  0, 1, 0,  1, 0, 0,  1, 1, 0}, 
				new float[] {0, 0,  0, 1,  1, 0,  1, 1}, 
				new float[] {0, 0, 1,  0, 0, 1,  0, 0, 1,  0, 0, 1}, 
				new int[] {1, 2, 3,  1, 2, 4});
	}
}
