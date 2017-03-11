package gui.handler;

import gui.menu.Menu;

public class HandlerChangeMenu implements IClickHandler
{
	private Class<? extends Menu> nextMenu;
	
	public HandlerChangeMenu(Class<? extends Menu> next)
	{
		nextMenu = next;
	}
	
	@Override
	public void click(Menu parent)
	{
		parent.requestClose(nextMenu);
	}
}
