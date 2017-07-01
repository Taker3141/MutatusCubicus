package gui.handler;

import world.World;
import gui.menu.Menu;

public class HandlerStartGame implements IClickHandler
{
	private Class<? extends World> startWorld;
	
	public HandlerStartGame(Class<? extends World> world)
	{
		startWorld = world;
	}
	
	@Override
	public void click(Menu parent)
	{
		parent.requestGameStart(startWorld);
	}
}
