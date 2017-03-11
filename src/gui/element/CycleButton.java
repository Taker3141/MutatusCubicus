package gui.element;

import main.MainManagerClass;
import font.fontMeshCreator.FontType;
import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class CycleButton extends Button
{
	public String[] list;
	public int index = 0;
	
	public CycleButton(Vector2f position, Vector2f size, Menu parent)
	{
		super(position, size, parent);
	}
	
	@Override
	public void leftClick(int mouseX, int mouseY)
	{
		super.leftClick(mouseX, mouseY);
		index++;
		if(index >= list.length) index = 0;
		updateText();
	}

	public void updateText()
	{
		text.setText(MainManagerClass.localizer.localizeString(list[index]));
	}

	
	public Button setTextList(String[] strings, FontType font, float size)
	{
		super.setText(strings[0], font, size);
		list = strings;
		return this;
	}
	
	public String getCurrent()
	{
		return list[index];
	}
}
