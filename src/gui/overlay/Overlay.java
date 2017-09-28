package gui.overlay;

import font.fontMeshCreator.FontType;
import gui.element.GuiElement;
import gui.element.IClickable;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;

public abstract class Overlay implements IClickable
{
	protected List<GuiElement> elements;
	protected final int W, H;
	protected Loader loader = MainManagerClass.loader;
	public boolean isActive = true;
	public FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
	public Vector2f position = new Vector2f();
	public Vector2f size = new Vector2f();
	
	public Overlay()
	{
		elements = new LinkedList<GuiElement>();
		W = Display.getWidth();
		H = Display.getHeight();
	}
	
	public List<GuiElement> getElements()
	{
		return elements;
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void leftReleased(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}

	@Override
	public boolean isOver(int x, int y)
	{
		return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
	}
}
