package gui.overlay;

import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import gui.element.GuiElement;

public class OverlayCommunication extends Overlay
{
	protected GuiElement background;
	protected float vY = 0;
	
	public OverlayCommunication()
	{
		position = new Vector2f(0, H -256);
		background = new GuiElement(loader.loadTexture("texture/gui/communication/background"), position, new Vector2f(1024, 256), null);
		
		elements.add(background);
	}
	
	public boolean hidden()
	{
		return position.y >= H;
	}
	
	public void hide()
	{
		if(vY == 0) vY = 150;
	}
	
	public void show()
	{
		if(vY == 0) vY = -150;
	}
	
	public void update()
	{
		float dy = DisplayManager.getFrameTimeSeconds() * vY;
		if(position.y > H - 256 && dy < 0)
		{
			position.y += dy;
			background.position = position;
		}
		else if(position.y < H && dy > 0)
		{
			position.y += dy;
			background.position = position;
		}
		else vY = 0;
	}
}
