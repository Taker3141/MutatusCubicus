package gui.handler;

import gui.menu.Menu;

public class HandlerStartGame implements IClickHandler
{
	@Override
	public void click(Menu parent)
	{
		parent.requestGameStart();
	}
}
