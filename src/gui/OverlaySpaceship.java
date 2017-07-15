package gui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import entity.vehicle.Rocketship;
import gui.element.*;

public class OverlaySpaceship extends Overlay
{
	protected Rocketship ship;
	protected Label verlocityLabel;
	protected GuiElement engineBack;
	protected GuiElement engineRight;
	protected GuiElement engineLeft;
	protected boolean visible = false;
	private final int X = W - 1024;
	
	public OverlaySpaceship(Rocketship s)
	{
		elements.add(new GuiElement(loader.loadTexture("texture/gui/spaceship/background"), new Vector2f(W - 1024, H - 256), new Vector2f(1024, 256), null));
		verlocityLabel = new Label("o!v = 0 m/s", new Vector2f(X + 20, H - 20), null);
		verlocityLabel.setVisible(false);
		elements.add(verlocityLabel);
		engineBack = new GuiElement(loader.loadTexture("texture/gui/spaceship/main_engine"), new Vector2f(X + 274, H - 189), new Vector2f(128, 128), null);
		engineRight = new GuiElement(loader.loadTexture("texture/gui/spaceship/right_wing_engine"), new Vector2f(X + 309, H - 250), new Vector2f(128, 128), null);
		engineLeft = new GuiElement(loader.loadTexture("texture/gui/spaceship/left_wing_engine"), new Vector2f(X + 309, H - 129), new Vector2f(128, 128), null);
		elements.add(engineBack); elements.add(engineRight); elements.add(engineLeft);
		ship = s;
	}
	
	public void update()
	{
		if(verlocityLabel != null) verlocityLabel.setText("o!v = " + ship.v.length() + "m/s");
	}
	
	public void setVisible(boolean v)
	{
		visible = v;
		verlocityLabel.setVisible(v);
	}
}
