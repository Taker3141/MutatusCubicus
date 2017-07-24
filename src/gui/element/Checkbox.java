package gui.element;

import gui.handler.IClickHandler;
import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class Checkbox extends GuiElement implements IClickable
{
	private static int textureCheckbox;
	private static int textureCheckboxHover;
	private static int textureCheckboxClick;
	private static int textureCheckboxChecked;
	private static int textureCheckboxHoverChecked;
	private static int textureCheckboxClickChecked;
	
	private boolean isChecked = false;
	private IClickHandler handler;
	
	public Checkbox(Vector2f position, Vector2f size, Menu parent)
	{
		super(textureCheckbox, position, size, parent);
	}

	@Override
	public void leftClick(int mouseX, int mouseY) 
	{
		texture = isChecked ? textureCheckboxClickChecked : textureCheckboxClick;
	}

	@Override
	public void rightClick(int mouseX, int mouseY) {}

	@Override
	public void leftReleased(int mouseX, int mouseY)
	{
		isChecked = !isChecked;
		texture = isChecked ? textureCheckboxHoverChecked : textureCheckboxHover;
		if (handler != null) handler.click(parent);
	}

	@Override
	public void rightReleased(int mouseX, int mouseY) {}

	@Override
	public void entered(int mouseX, int mouseY)
	{
		texture = isChecked ? textureCheckboxHoverChecked : textureCheckboxHover;
	}

	@Override
	public void left(int mouseX, int mouseY)
	{
		texture = isChecked ? textureCheckboxChecked : textureCheckbox;
	}
	
	public boolean isChecked()
	{
		return isChecked;
	}
	
	static
	{
		textureCheckbox = loader.loadTexture("/texture/gui/checkbox");
		textureCheckboxHover = loader.loadTexture("/texture/gui/checkbox_hover");
		textureCheckboxClick = loader.loadTexture("/texture/gui/checkbox_click");
		textureCheckboxChecked = loader.loadTexture("/texture/gui/checkbox_checked");
		textureCheckboxHoverChecked = loader.loadTexture("/texture/gui/checkbox_hover_checked");
		textureCheckboxClickChecked = loader.loadTexture("/texture/gui/checkbox_click_checked");
	}
	
	public Checkbox setChecked(boolean checked)
	{
		isChecked = checked;
		texture = isChecked ? textureCheckboxChecked : textureCheckbox;
		return this;
	}
	
	public Checkbox setClickHandler(IClickHandler h)
	{
		handler = h;
		return this;
	}

	@Override
	public boolean isOver(int x, int y) 
	{
		return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
	}
}
