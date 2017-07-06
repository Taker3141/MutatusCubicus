package gui.element;

import gui.menu.Menu;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;

public class GuiElement
{
	protected int texture;
	public Vector2f position;
	public Vector2f size;
	public boolean isEnabled = true;
	public Menu parent;
	public static Loader loader = MainManagerClass.loader;
	
	public GuiElement(int texture, Vector2f position, Vector2f size, Menu parent)
	{
		this.texture = texture;
		this.position = position;
		this.size = size;
		this.parent = parent;
	}
	
	public int getTextureID()
	{
		return texture;
	}
}
