package gui.element;

import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import world.*;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;
import gui.menu.Menu;

public class WorldSelector extends GuiElement implements IClickable
{
	private GuiElement[] worldIcons;
	private GUIText text;
	
	public WorldSelector(Vector2f position, Vector2f size, Menu parent)
	{
		super(loader.loadTexture("texture/gui/box"), position, size, parent);
		text = new GUIText("Ort auswählen:", 1, font, Vector2f.add(position, new Vector2f(30, size.y - 20), null), 1, false);
		worldIcons = new GuiElement[2];
		worldIcons[0] = new GuiElement(loader.loadTexture("texture/gui/moon_lab"), Vector2f.add(position, new Vector2f(30, size.y - 180), null), new Vector2f(128, 128), parent);
		worldIcons[1] = new GuiElement(loader.loadTexture("texture/gui/moon_orbit"), Vector2f.add(position, new Vector2f(190, size.y - 180), null), new Vector2f(128, 128), parent);
	}

	@Override
	public void leftReleased(int x, int y)
	{
		float clickY = (Display.getHeight() - y) - position.y + 64;
		for(int i = 0; i < worldIcons.length; i++)
		{
			GuiElement e = worldIcons[i];
			if(x >= e.position.x && x <= e.position.x + e.size.x && clickY >= e.position.y && clickY <= e.position.y + e.size.y)
			{
				switch(i)
				{
					case 0: parent.requestGameStart(MoonLabWorld.class); break;
					case 1: parent.requestGameStart(SpaceWorld.class); break;
				}
			}
		}
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		isVisible = visible;
		for(GuiElement e : worldIcons) e.isVisible = visible;
		text.isVisible = visible;
	}

	@Override
	public boolean isOver(int x, int y)
	{
		if(!isVisible) return false;
		boolean ret = x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
		for(int i = 0; i < worldIcons.length; i++)
		{
			GuiElement e = worldIcons[i];
			if(x >= e.position.x && x <= e.position.x + e.size.x && y >= e.position.y && y <= e.position.y + e.size.y)
			{
				if(e.size.x < 150) 
				{
					e.size = new Vector2f(150, 150);
					e.position.x -= 11;
					e.position.y -= 11;
				}
			}
			else if(e.size.x > 128) 
			{
				e.size = new Vector2f(128, 128);
				e.position.x += 11;
				e.position.y += 11;
			}
		}
		return ret;
	}
	
	public void addComponents(List<GuiElement> list)
	{
		for(GuiElement e : worldIcons) list.add(e);
		list.add(this);
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
