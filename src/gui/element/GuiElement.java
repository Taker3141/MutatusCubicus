package gui.element;

import java.io.File;
import font.fontMeshCreator.FontType;
import gui.menu.Menu;
import main.MainManagerClass;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderer.Loader;

public class GuiElement
{
	protected int texture;
	public Vector2f position;
	public Vector2f size;
	public boolean isEnabled = true;
	public Menu parent;
	public Vector3f color = new Vector3f(1, 1, 1);
	public boolean isVisible = true;
	
	public static Loader loader = MainManagerClass.loader;
	protected static FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
	
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
	
	public void setTextureID(int id)
	{
		texture = id;
	}
	
	public void setVisible(boolean visible)
	{
		isVisible = visible;
	}
}
