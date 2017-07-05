package gui;

import org.lwjgl.util.vector.Vector2f;
import entity.vehicle.Rocketship;
import gui.element.*;

public class OverlaySpaceship extends Overlay
{
	protected Rocketship ship;
	
	public OverlaySpaceship(Rocketship s)
	{
		elements.add(new GuiElement(loader.loadTexture("texture/gui/spaceship/background"), new Vector2f(W / 2 - 512, H - 256), new Vector2f(1024, 256), null));
		ship = s;
	}
}
