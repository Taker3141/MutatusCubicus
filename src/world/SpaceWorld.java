package world;

import static org.lwjgl.input.Keyboard.*;
import main.MainGameLoop;
import org.lwjgl.util.vector.Vector3f;
import renderer.fbo.PostProcessing;
import entity.*;
import entity.vehicle.Rocketship;

public class SpaceWorld extends World
{
	private Entity moon;
	private Orbit shipOrbit;
	private Rocketship ship;
	
	@Override
	public void loadEntities()
	{
		Rocketship.init();
		MainGameLoop.reportProgress(30);
		moon = new Entity(createModel("moon", "texture/moon_dust", 0), new Vector3f(), 0, 0, 0, 3476000, entities);
		player = new Player(new Vector3f(0, 1739000, 0), 0, 180, 0, 0.02F, entities);
		overlays.add(player.organs);
		MainGameLoop.reportProgress(60);
		ship = new Rocketship(new Vector3f(1, 1739000, 0), 0, 0, 0, entities);
		player.clickAt(ship, new Vector3f());
		c = new Camera(player, this, true);
		MainGameLoop.reportProgress(90);
		lights.add(new Light(new Vector3f(0, 100000000, 100000000), new Vector3f(1, 1, 1)));
		lights.add(new Light(new Vector3f(0, 0, 0), new Vector3f(0, 0.6F, 0), new Vector3f(1, 0.01F, 0.2F)));
		lights.add(new Light(new Vector3f(0, -100000000, -100000000), new Vector3f(1, 1, 1)));
		MainGameLoop.reportProgress(100);
	}
	
	@Override
	public boolean tick()
	{
		orbitList.remove(shipOrbit);
		shipOrbit = ship.calculateOrbit();
		orbitList.add(shipOrbit);	
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.update(this, null);
			if(!e.invisible) renderer.processEntities(e);
		}
		super.tick();
		MainGameLoop.fbo.bindFrameBuffer();
		renderer.render(this);
		MainGameLoop.fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(MainGameLoop.fbo.getColorTexture());

		if(isKeyDown(KEY_ESCAPE)) return false;
		
		return true;
	}

	@Override
	public Vector3f getGravityVector(Entity e)
	{
		final float strength = 2.80383e+6F;
		float factor = strength / e.position.lengthSquared();
		return (Vector3f)Vector3f.sub(new Vector3f(), e.position, null).scale(factor);
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
