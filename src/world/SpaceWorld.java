package world;

import static org.lwjgl.input.Keyboard.*;
import main.MainGameLoop;
import org.lwjgl.util.vector.Vector3f;
import renderer.fbo.PostProcessing;
import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;

public class SpaceWorld extends World
{
	private Entity moon;
	
	@Override
	public void loadEntities()
	{
		moon = new Entity(createModel("moon", "texture/moon_dust", 0), new Vector3f(), 0, 0, 0, 3476000, entities);
		player = new Player(new Vector3f(0, 1739000, 0), 0, 180, 0, 0.02F, entities);
		c = new Camera(player, this, true);
		lights.add(new Light(new Vector3f(0, 100000000, 100000000), new Vector3f(1, 1, 1)));
		lights.add(new Light(new Vector3f(0, 0, 0), new Vector3f(0, 0.6F, 0), new Vector3f(1, 0.01F, 0.2F)));
		lights.add(new Light(new Vector3f(0, -100000000, -100000000), new Vector3f(1, 1, 1)));
	}
	
	@Override
	public boolean tick()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(this, null);
			if(!e.invisible) renderer.processEntities(e);
		}
		super.tick();
		MainGameLoop.fbo.bindFrameBuffer();
		renderer.render(lights, c, player);
		MainGameLoop.fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(MainGameLoop.fbo.getColorTexture());

		if(isKeyDown(KEY_ESCAPE)) return false;
		
		return true;
	}

	@Override
	public Vector3f getGravityVector(Entity e)
	{
		return new Vector3f();
	}

	@Override
	public float height(float x, float z)
	{
		return 0;
	}
	
	@Override
	public Vector3f getCoordinateOffset()
	{
		return player != null ? player.position.negate(null) : new Vector3f(0, -1739000, 0);
	}
}
