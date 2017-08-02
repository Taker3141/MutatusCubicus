package gui;

import org.lwjgl.util.vector.Vector2f;
import gui.element.GuiElement;

public class OverlayChemicalReactor extends Overlay
{
	public OverlayChemicalReactor()
	{
		position = new Vector2f(W / 2 - 256, H / 2 - 128);
		size = new Vector2f(512, 256);
		elements.add(new GuiElement(loader.loadTexture("texture/gui/chemistry/background"), new Vector2f(W / 2 - 256, H / 2 - 128), new Vector2f(512, 256), null));
	}
	
	@Override
	public boolean isOver(int x, int y)
	{
		boolean ret = super.isOver(x, y);
		return ret;
	}
}
