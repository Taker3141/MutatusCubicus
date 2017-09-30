package gui.element;

import java.io.File;
import org.lwjgl.util.vector.Vector2f;
import font.fontMeshCreator.FontType;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.menu.Menu;

public class Label extends GuiElement
{
	protected GUIText text;
	protected FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
	protected boolean visible = true;
	
	public Label(String t, Vector2f position, Menu parent)
	{
		super(0, position, new Vector2f(), parent);
		text = new GUIText(t, 1, font, position, 1000, false);
		text.setColour(1, 1, 1);
	}
	
	public void setText(String t)
	{
		TextMaster.removeText(text);
		if(!visible) return;
		text = new GUIText(t, 1, font, position, 1000, false);
		text.setColour(1, 1, 1);
	}
	
	@Override
	public void setVisible(boolean v)
	{
		visible = v;
		if(v) text = new GUIText(text.getTextString(), 1, font, position, 1000, false);
		else TextMaster.removeText(text);
	}
}
