package gui;

import gui.element.GuiElement;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.Display;

public abstract class Overlay
{
	protected List<GuiElement> elements;
	protected final int W, H;
	public boolean isActive = true;
	
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
