package gui.menu;

import org.lwjgl.util.vector.Vector2f;
import gui.element.GuiElement;
import gui.element.IClickable;

public class LevelSelector extends GuiElement implements IClickable
{
	
	public LevelSelector(Vector2f position, Vector2f size, Menu parent)
	{
		super(loader.loadTexture("texture/gui/story_box"), position, size, parent);
	}
	
	@Override
	public void leftReleased(int x, int y)
	{
		
	}
	
	public void setVisible(boolean visible)
	{
		isVisible = visible;
	}
	
	@Override
	public boolean isOver(int x, int y)
	{
		if(!isVisible) return false;
		boolean ret = x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
		return ret;
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
