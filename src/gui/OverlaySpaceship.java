package gui;

import org.lwjgl.util.vector.Vector2f;
import entity.vehicle.Rocketship;
import font.fontMeshCreator.GUIText;
import gui.element.*;

public class OverlaySpaceship extends Overlay
{
	protected Rocketship ship;
	protected Label verlocityLabel;
	protected boolean visible = false;
	private final int X = W - 1024;
	
	public OverlaySpaceship(Rocketship s)
	{
		elements.add(new GuiElement(loader.loadTexture("texture/gui/spaceship/background"), new Vector2f(W - 1024, H - 256), new Vector2f(1024, 256), null));
		verlocityLabel = new Label("o!v = 0 m/s", new Vector2f(X + 20, H - 20), null);
		verlocityLabel.setVisible(false);
		elements.add(verlocityLabel);
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
