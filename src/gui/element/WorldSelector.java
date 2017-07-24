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
	private WorldIcon[] worldIcons;
	private GUIText text;
	private GUIText nameText;
	
	public WorldSelector(Vector2f position, Vector2f size, Menu parent)
	{
		super(loader.loadTexture("texture/gui/box"), position, size, parent);
		text = new GUIText("Ort auswählen:", 1.5F, font, Vector2f.add(position, new Vector2f(30, size.y - 10), null), 1, false);
		nameText = new GUIText("", 2, font, new Vector2f(position.x + 30, position.y + 60), 1, false);
		worldIcons = new WorldIcon[2];
		worldIcons[0] = new WorldIcon("texture/gui/moon_lab", "Mond-Labor", new Vector2f(position.x + 30, position.y + size.y - 180), new Vector2f(128, 128), MoonLabWorld.class);
		worldIcons[1] = new WorldIcon("texture/gui/moon_orbit", "Weltraum", new Vector2f(position.x + 190, position.y + size.y - 180), new Vector2f(128, 128), SpaceWorld.class);
	}

	@Override
	public void leftReleased(int x, int y)
	{
		float clickY = (Display.getHeight() - y) - position.y + 64;
		for(int i = 0; i < worldIcons.length; i++)
		{
			GuiElement e = worldIcons[i].element;
			if(x >= e.position.x && x <= e.position.x + e.size.x && clickY >= e.position.y && clickY <= e.position.y + e.size.y)
			{
				parent.requestGameStart(worldIcons[i].startWorld); break;
			}
		}
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		isVisible = visible;
		for(WorldIcon wi : worldIcons) wi.element.isVisible = visible;
		text.isVisible = visible;
	}

	@Override
	public boolean isOver(int x, int y)
	{
		if(!isVisible) return false;
		boolean ret = x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
		for(int i = 0; i < worldIcons.length; i++)
		{
			GuiElement e = worldIcons[i].element;
			if(x >= e.position.x && x <= e.position.x + e.size.x && y >= e.position.y && y <= e.position.y + e.size.y)
			{
				if(e.size.x < 150) 
				{
					e.size = new Vector2f(150, 150);
					e.position.x -= 11;
					e.position.y -= 11;
					nameText.setText(worldIcons[i].name);
				}
			}
			else if(e.size.x > 128) 
			{
				e.size = new Vector2f(128, 128);
				e.position.x += 11;
				e.position.y += 11;
				nameText.setText("");
			}
		}
		return ret;
	}
	
	public class WorldIcon
	{
		public GuiElement element;
		public final String name;
		public Class<? extends World> startWorld;
		
		public WorldIcon(String textureName, String name, Vector2f position, Vector2f size, Class<? extends World> startWorld)
		{
			this.name = name;
			element = new GuiElement(loader.loadTexture(textureName), position, size, parent);
			this.startWorld = startWorld;
		}
	}
	
	public void addComponents(List<GuiElement> list)
	{
		for(WorldIcon wi : worldIcons) list.add(wi.element);
		list.add(this);
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
