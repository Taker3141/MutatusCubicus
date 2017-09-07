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
	
	public static void reloadTextures()
	{
		ButtonDirection.LEFT.loadTextures();
		ButtonDirection.RIGHT.loadTextures();
	}
	
	public enum ButtonDirection
	{
		LEFT("texture/gui/arrow_left", "texture/gui/arrow_click_left", "texture/gui/arrow_hover_left"), 
		RIGHT("texture/gui/arrow_right", "texture/gui/arrow_click_right", "texture/gui/arrow_hover_right");
		
		public int textureButton;
		public int textureButtonClick;
		public int textureButtonHover;
		
		public String textureButtonString;
		public String textureButtonClickString;
		public String textureButtonHoverString;
		
		private ButtonDirection(String normal, String click, String hover)
		{
			textureButtonString = normal;
			textureButtonClickString = click;
			textureButtonHoverString = hover;
			loadTextures();
		}
		
		private void loadTextures()
		{
			textureButton = loader.loadTexture(textureButtonString);
			textureButtonClick = loader.loadTexture(textureButtonClickString);
			textureButtonHover = loader.loadTexture(textureButtonHoverString);
		}
	}
}
