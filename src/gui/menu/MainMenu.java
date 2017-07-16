package gui.menu;

import main.MainGameLoop;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import font.fontRendering.TextMaster;
import gui.element.*;
import gui.handler.HandlerChangeMenu;
import gui.handler.IClickHandler;
import gui.handler.MouseHandler;

public class MainMenu extends Menu
{
	private WorldSelector selector;
	private boolean freePlayVisible = false;
	
	@Override
	public void doMenu()
	{	
		{
			final int indention = 200;
			guiElements.add(new Button(new Vector2f(indention, H - 200), buttonSize, this).setText("Story", font, 1));
			guiElements.add(new Button(new Vector2f(indention, H - 250), buttonSize, this).setText("Freies Spiel", font, 1).setClickHandler(new HandlerToggleFreePlayGui()));
			guiElements.add(new Button(new Vector2f(indention, H - 300), buttonSize, this).setText("Einstellungen", font, 1).setClickHandler(new HandlerChangeMenu(MenuSettings.class)));
			guiElements.add(new Button(new Vector2f(indention, H - 350), buttonSize, this).setText("Spiel Beenden", font, 1).setClickHandler(new HandlerChangeMenu(null)));
			selector = new WorldSelector(new Vector2f(indention - 128, H - 700), new Vector2f(512, 256), this);
			for(GuiElement e : selector.worldIcons) guiElements.add(e);
			selector.setVisible(false);
			guiElements.add(selector);
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
	
	private class HandlerToggleFreePlayGui implements IClickHandler
	{
		@Override
		public void click(Menu parent)
		{
			freePlayVisible = !freePlayVisible;
			selector.setVisible(freePlayVisible);
		}
	}
}
