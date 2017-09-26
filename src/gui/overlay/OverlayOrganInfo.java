package gui.overlay;

import entity.organism.Organism;
import gui.element.GuiElement;
import org.lwjgl.util.vector.Vector2f;

public class OverlayOrganInfo extends Overlay
{
	protected Organism o;
	
	public OverlayOrganInfo(Organism organism)
	{
		super();
		o = organism;
		position = new Vector2f(W / 2 - 256, H / 2 - 128);
		super.size = new Vector2f(512, 256);
		elements.add(new GuiElement(loader.loadTexture("texture/gui/box"), position, size, null));
	}
}
