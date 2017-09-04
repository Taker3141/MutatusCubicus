package gui.overlay;

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
	protected GuiElement power;
	protected GuiBar fuel;
	protected boolean visible = false;
	private final int X = W - 1024;
	protected final Vector3f OFF_COLOR = new Vector3f(0, 0, 1);
	protected final Vector3f ON_COLOR = new Vector3f(1, 0, 0);
	
	public OverlaySpaceship(Rocketship s)
	{
		position = new Vector2f(W - 1024, H - 256);
		size = new Vector2f(1024, 256);
		elements.add(new GuiElement(loader.loadTexture("texture/gui/spaceship/background"), new Vector2f(W - 1024, H - 256), new Vector2f(1024, 256), null));
		verlocityLabel = new Label("o!v = 0 m/s", new Vector2f(X + 20, H - 20), null);
		verlocityLabel.setVisible(false);
		elements.add(verlocityLabel);
		engineBack = new GuiElement(loader.loadTexture("texture/gui/spaceship/main_engine"), new Vector2f(X + 274, H - 189), new Vector2f(128, 128), null);
		engineRight = new GuiElement(loader.loadTexture("texture/gui/spaceship/right_wing_engine"), new Vector2f(X + 309, H - 250), new Vector2f(128, 128), null);
		engineLeft = new GuiElement(loader.loadTexture("texture/gui/spaceship/left_wing_engine"), new Vector2f(X + 309, H - 129), new Vector2f(128, 128), null);
		elements.add(engineBack); elements.add(engineRight); elements.add(engineLeft);
		fuel = new GuiBar(loader.loadTexture("texture/octanitrocubane"), new Vector2f(X + 15, H - 84), new Vector2f(231, 30), null);
		elements.add(fuel);
		power = new GuiElement(loader.loadTexture("texture/gui/spaceship/bar"), new Vector2f(X + 15, H - 129), new Vector2f(8, 32), null);
		elements.add(power);
		ship = s;
	}
	
	public void update()
	{
		if(verlocityLabel != null) verlocityLabel.setText("v = " + String.format("%.1f", ship.v.length()) + "m/s");
		engineBack.color = ship.isAccelerating() ? ON_COLOR : OFF_COLOR;
		engineRight.color = ship.rotating > 0 ? ON_COLOR : OFF_COLOR;
		engineLeft.color = ship.rotating < 0 ? ON_COLOR : OFF_COLOR;
		ship.rotating = 0;
		fuel.width = 4F * ship.fuel / 1000;
		fuel.size.x = 231 * ship.fuel / 1000;
		power.position.x = X + 15 + 223 * ship.thrustingPower / 1000;
	}
	
	public void setVisible(boolean v)
	{
		visible = v;
		verlocityLabel.setVisible(v);
	}
}
