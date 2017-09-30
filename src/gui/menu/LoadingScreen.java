package gui.menu;

import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.element.GuiElement;
import main.MainGameLoop;
import org.lwjgl.util.vector.Vector2f;


public class LoadingScreen extends Menu
{
	protected GUIText progress;
	
	public LoadingScreen()
	{
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/main_menu_background"), new Vector2f(0, -H), new Vector2f(W, 2 * H), this));
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/loading"), new Vector2f(W - 300, 100), new Vector2f(256, 64), this));
		progress = new GUIText("0%", 3, font, new Vector2f(10, 100), 1, false);
		progress.setColour(0, 0.7F, 0);
	}
	
	@Override
	public void doMenu()
	{
		progress.setText(MainGameLoop.loadingProgress + "%");
		render();
	}
	
	@Override
	public void cleanUp()
	{
		TextMaster.removeText(progress);
		MainGameLoop.loadingProgress = 0;
	}
}
