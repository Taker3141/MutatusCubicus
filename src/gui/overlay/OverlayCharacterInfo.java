package gui.overlay;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import entity.character.ICharacter;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;

public class OverlayCharacterInfo extends Overlay
{
	protected GuiElement background;
	protected GuiElement picture;
	protected GUIText[] infoText = new GUIText[4];
	protected ICharacter character;
	protected static final Input input = new Input(Display.getHeight());
	
	public OverlayCharacterInfo()
	{
		position = new Vector2f(input.getMouseX(), input.getMouseY());
		size = new Vector2f(384, 128);
		background = new GuiElement(loader.loadTexture("texture/gui/box"), position, size, null);
		picture = new GuiElement(loader.loadTexture("texture/octanitrocubane"), getPicturePosition(), new Vector2f(108, 108), null);
		for(int i = 0; i < infoText.length; i++)
		{
			infoText[i] = new GUIText("" + i, 1, font, getTextPosition(i), 1, false);
		}
		setVisible(false);
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
	
	public void setCharacter(ICharacter c)
	{
		boolean notNull = c != null;
		setVisible(notNull);
		picture.setTextureID(notNull ? c.getFaceTexture() : 0);
		infoText[0].setText("Name: " + (notNull ? c.getFullName() : ""));
		infoText[1].setText("Geburtsdatum: " + (notNull ? c.getBirthday()[0] + "." + c.getBirthday()[1] + "." + c.getBirthday()[2] : ""));
		infoText[2].setText("Geschlecht: " + (notNull ? c.getGender().name : ""));
		infoText[3].setText("Beruf: " + (notNull ? c.getProfession() : ""));
	}
		
	public void setVisible(boolean v)
	{
		background.isVisible = v;
		picture.isVisible = v;
		for(int i = 0; i < infoText.length; i++)
		{
			infoText[i].isVisible = v;
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
