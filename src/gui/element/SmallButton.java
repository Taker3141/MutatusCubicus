package gui.element;

import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class SmallButton extends Button
{
	public SmallButton(Vector2f position, Vector2f size, Menu parent)
	{
		super(position, size, parent);
		textureButton = loader.loadTexture("texture/gui/checkbox");
		textureButtonClick = loader.loadTexture("texture/gui/checkbox_click");
		textureButtonHover = loader.loadTexture("texture/gui/checkbox_hover");
		texture = textureButton;
	}
}
