package cutscene;

import renderer.DisplayManager;
import world.World;

public abstract class Cutscene
{
	protected float time;
	protected boolean isRunning = false;
	protected final World w;
	
	public Cutscene(World world)
	{
		w = world;
	}
	
	public void start()
	{
		isRunning = true;
		time = 0;
	}
	
	public void play()
	{
		time += DisplayManager.getFrameTimeSeconds();
		update(time);
	}
	
	public boolean isDone()
	{
		return isRunning;
	}
	
	protected abstract void update(float dt);
	
	protected static void fadeIn(float duration)
	{
		
	}
	
	protected static void fadeOut(float duration)
	{
		
	}
}
