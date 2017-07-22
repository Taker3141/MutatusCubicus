package gui.menu;

import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import gui.element.GuiElement;
import gui.element.IClickable;
import gui.element.VerticalScrollBar;

public class LevelSelector extends GuiElement implements IClickable
{
	public VerticalScrollBar scroll;
	
	public LevelSelector(Vector2f position, Vector2f size, Menu parent)
	{
		super(loader.loadTexture("texture/gui/story_box"), position, size, parent);
		final float ratio = size.x / 1024;
		scroll = new VerticalScrollBar(new Vector2f(position.x + 977 * ratio, position.y + 15 * ratio), new Vector2f(32 * ratio, 354 * ratio), parent);
	}
	
	@Override
	public void leftReleased(int x, int y)
	{
		
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		isVisible = visible;
		scroll.setVisible(visible);
	}
	
	@Override
	public boolean isOver(int x, int y)
	{
		if(!isVisible) return false;
		boolean ret = x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
		return ret;
	}
	
	public void addComponents(List<GuiElement> list)
	{
		scroll.addComponents(list);
		list.add(this);
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
