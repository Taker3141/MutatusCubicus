package gui.overlay;

import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;

public class OverlayCommunication extends Overlay
{
	protected GuiElement background;
	protected float vY = 0;
	protected GUIText[] textLines = new GUIText[5];
	
	public OverlayCommunication()
	{
		position = new Vector2f(0, H -256);
		background = new GuiElement(loader.loadTexture("texture/gui/communication/background"), position, new Vector2f(1024, 256), null);
		for(int i = 0; i < textLines.length; i++) 
		{
			textLines[i] = new GUIText("", 1.8F, font, textPosition(i), 734F / W, false);
			textLines[i].setColour(0, 0.812F, 0.38F);
		}
		elements.add(background);
		
		position.y = H;
		update(true);
	}
	
	public boolean hidden()
	{
		return position.y >= H;
	}
	
	public void hide()
	{
		if(vY == 0) vY = 500;
	}
	
	public void show()
	{
		if(vY == 0) vY = -500;
	}
	
	public void setText(String text, int line)
	{
		textLines[line].setText(text);
	}
	
	public void update(boolean force)
	{
		float dy = DisplayManager.getFrameTimeSeconds() * vY;
		if((position.y > H - 256 && dy < 0) || (position.y < H && dy > 0)) position.y += dy;
		else vY = 0;
		
		if(dy != 0 || force)
		{
			background.position = position;
			for(int i = 0; i < textLines.length; i++) textLines[i].position = textPosition(i);
		}
	}
	
	private Vector2f textPosition(int line)
	{
		return new Vector2f(position.x + 263, (position.y + 230) - line * 41);
	}
}
