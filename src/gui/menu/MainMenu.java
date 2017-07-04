package gui.menu;

import main.MainGameLoop;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import font.fontRendering.TextMaster;
import gui.element.Button;
import gui.element.GuiElement;
import gui.handler.HandlerChangeMenu;
import gui.handler.HandlerStartGame;
import gui.handler.MouseHandler;
import world.*;

public class MainMenu extends Menu
{
	@Override
	public void doMenu()
	{	
		{
			final int indention = W / 4;
			guiElements.add(new Button(new Vector2f(indention + 200, H - 200), buttonSize, this).setText("o!Spiel Starten", font, 1).setClickHandler(new HandlerStartGame(MoonLabWorld.class)));
			guiElements.add(new Button(new Vector2f(indention + 600, H - 200), buttonSize, this).setText("o!Weltraum", font, 1).setClickHandler(new HandlerStartGame(SpaceWorld.class)));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 250), buttonSize, this).setText("o!Einstellungen", font, 1).setClickHandler(new HandlerChangeMenu(MenuSettings.class)));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 300), buttonSize, this).setText("o!Spiel Beenden", font, 1).setClickHandler(new HandlerChangeMenu(null)));
		}
		
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/main_menu_background"), new Vector2f(0, -H), new Vector2f(W, 2 * H), null));

		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		boolean loopFlag = true;
		
		TextMaster.init(loader);
		while (loopFlag)
		{
			loopFlag = false;
			while (!Display.isCloseRequested() && !isCloseRequested)
			{
				render();
				input.poll(W, H);
			}
			isCloseRequested = false;
			if (shouldStartGame)
			{
				shouldStartGame = false;
				MainGameLoop.doGame(startWorld);
				MainManagerClass.nextMenu = MainMenu.class;
			}
		}
		cleanUp();
	}
}
