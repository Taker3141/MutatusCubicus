package gui.menu;

import gui.element.GuiElement;
import org.lwjgl.util.vector.Vector2f;


public class LoadingScreen extends Menu
{
	public LoadingScreen()
	{
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/main_menu_background"), new Vector2f(0, -H), new Vector2f(W, 2 * H), this));
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/loading"), new Vector2f(W - 300, 100), new Vector2f(256, 64), this));
	}
	
	@Override
	public void doMenu()
	{
		render();
	}
}
