package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.IHitBox;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;
import world.World;

public class Slime extends Entity
{
	protected float time = 0;
	
	public Slime(Player p, List<Entity> list)
	{
		super(World.createModel("square", "texture/cube/veins", 0.3F), Vector3f.add(p.position, new Vector3f(0, 0.1F, 0), null), 0, p.rotY, 0, 0.2F, list);
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		super.update(w, t);
		time += DisplayManager.getFrameTimeSeconds();
		if(time > 60) unregister();
	}
}
