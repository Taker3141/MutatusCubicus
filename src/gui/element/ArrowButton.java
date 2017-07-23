package gui.element;

import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class ArrowButton extends Button
{
	public ArrowButton(Vector2f position, Vector2f size, ButtonDirection direction, Menu parent)
	{
		super(position, size, parent);
		textureButton = direction.textureButton;
		textureButtonClick = direction.textureButtonClick;
		textureButtonHover = direction.textureButtonHover;
		texture = textureButton;
	}
	
	public enum ButtonDirection
	{
		LEFT(loader.loadTexture("texture/gui/arrow_left"), loader.loadTexture("texture/gui/arrow_click_left"), loader.loadTexture("texture/gui/arrow_hover_left")), 
		RIGHT(loader.loadTexture("texture/gui/arrow_right"), loader.loadTexture("texture/gui/arrow_click_right"), loader.loadTexture("texture/gui/arrow_hover_right"));
		
		public int textureButton;
		public int textureButtonClick;
		public int textureButtonHover;
		
		private ButtonDirection(int normal, int click, int hover)
		{
			textureButton = normal;
			textureButtonClick = click;
			textureButtonHover = hover;
		}
	}
}
