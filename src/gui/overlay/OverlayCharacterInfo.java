package gui.overlay;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;

public class OverlayCharacterInfo extends Overlay
{
	protected GuiElement background;
	protected GuiElement picture;
	protected GUIText[] infoText = new GUIText[4];
	protected static final Input input = new Input(Display.getHeight());
	
	public OverlayCharacterInfo()
	{
		position = new Vector2f(input.getMouseX(), input.getMouseY());
		size = new Vector2f(256, 128);
		background = new GuiElement(loader.loadTexture("texture/gui/box"), position, size, null);
		picture = new GuiElement(loader.loadTexture("texture/octanitrocubane"), getPicturePosition(), new Vector2f(108, 108), null);
		for(int i = 0; i < infoText.length; i++)
		{
			infoText[i] = new GUIText("info" + i, 1, font, getTextPosition(i), 1, false);
		}
		elements.add(background);
		elements.add(picture);
	}
	
	public void update()
	{
		position = new Vector2f(input.getMouseX(), H - input.getMouseY());
		background.position = position;
		picture.position = getPicturePosition();
		for(int i = 0; i < infoText.length; i++)
		{
			infoText[i].position = getTextPosition(i);
		}
	}
	
	protected Vector2f getTextPosition(int index)
	{
		return new Vector2f(position.x + 140, position.y + size.y - (10 + index * 20));
	}
	
	protected Vector2f getPicturePosition()
	{
		return new Vector2f(position.x + 10, position.y + 10);
	}
}
