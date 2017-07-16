package gui;

import font.fontMeshCreator.FontType;
import gui.element.GuiElement;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import renderer.Loader;

public abstract class Overlay
{
	protected List<GuiElement> elements;
	protected final int W, H;
	protected Loader loader = MainManagerClass.loader;
	public boolean isActive = true;
	public FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
	
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
}
