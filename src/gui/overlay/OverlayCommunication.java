package gui.overlay;

import static org.lwjgl.input.Keyboard.*;
import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;

public class OverlayCommunication extends Overlay
{
	protected GuiElement background;
	protected float vY = 0;
	protected GUIText[] textLines = new GUIText[5];
	protected boolean arrowKeyFlag = true;
	protected int selectedIndex = 0;
	
	public OverlayCommunication()
	{
		position = new Vector2f(0, H -256);
		background = new GuiElement(loader.loadTexture("texture/gui/communication/background"), position, new Vector2f(1024, 256), null);
		for(int i = 0; i < textLines.length; i++) 
		{
			textLines[i] = new GUIText("", 1.8F, font, textPosition(i), 734F / W, false);
			if(i == 0) textLines[i].setColour(0, 0.812F, 0.38F);
			else textLines[i].setColour(0.6F, 1F, 0.24F);
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
		
		if(arrowKeyFlag && isKeyDown(KEY_DOWN) && textLines[selectedIndex + 2].getTextString().length() > 0 && selectedIndex < 3) {selectedIndex++; arrowKeyFlag = false;}
		if(arrowKeyFlag && isKeyDown(KEY_UP) && textLines[selectedIndex].getTextString().length() > 0 && selectedIndex > 0) {selectedIndex--; arrowKeyFlag = false;}
		arrowKeyFlag = !isKeyDown(KEY_DOWN) && !isKeyDown(KEY_UP);
		for(int i = 0; i < textLines.length; i++)
		{
			if(i == 0) textLines[i].setColour(0, 0.812F, 0.38F);
			else if(i == selectedIndex + 1) textLines[selectedIndex + 1].setColour(0F, 1F, 0F);
			else textLines[i].setColour(0.6F, 1F, 0.24F);
		}
				
		if(dy != 0 || force)
		{
			background.position = position;
			for(int i = 0; i < textLines.length; i++) textLines[i].position = textPosition(i);
		}
	}
	
	public int getSelectedIndex()
	{
		return selectedIndex;
	}
	
	private Vector2f textPosition(int line)
	{
		return new Vector2f(position.x + 263, (position.y + 230) - line * 41);
	}

	public void reset()
	{
		for(int i = 0; i < textLines.length; i++) textLines[i].setText("");
		selectedIndex = 0;
	}
}
