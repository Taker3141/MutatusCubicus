package gui.menu;

import java.util.ArrayList;
import java.util.List;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.element.Button;
import gui.element.Checkbox;
import gui.element.CycleButton;
import gui.element.GuiElement;
import gui.handler.HandlerChangeMenu;
import gui.handler.MouseHandler;
import localize.Localizer;
import main.MainManagerClass;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import renderer.DisplayManager;

public class MenuSettings extends Menu
{
	private CycleButton langButton;
	private CycleButton screenButton;
	private Checkbox fullscreenBox;
	private Checkbox musicBox;
	
	@Override
	public void doMenu()
	{
		final int buttonIndention = W / 4;
		new GUIText("o!Einstellungen", 3, font, new Vector2f(0, H - 32), 1, true).setColour(1, 1, 1);
		//guiElements.add(new GuiElement(loader.loadTexture("texture/gui/banner_settings"), new Vector2f(0, H - 128), new Vector2f(W, 128), this));
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/main_menu_background"), new Vector2f(0, -H), new Vector2f(W, 2 * H), this));
		{
			langButton = (CycleButton)new CycleButton(new Vector2f(buttonIndention, H - 200), buttonSize, this).setTextList(new String[]{"o!Deutsch"}, font, 1);
			//langButton.index = MainManagerClass.settings.language.compareTo("en_US") == 0 ? 1 : 0;
			//langButton.updateText();
			new GUIText("o!Sprache", 1, font, new Vector2f(langButton.position.x - 100, langButton.position.y + (langButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(langButton);
		}
		{
			fullscreenBox = new Checkbox(new Vector2f(buttonIndention * 2.2F, H - 225), checkboxSize, this);
			fullscreenBox.setChecked(MainManagerClass.settings.fullscreen);
			new GUIText("o!Vollbild", 1, font, new Vector2f(fullscreenBox.position.x - 100, fullscreenBox.position.y + (fullscreenBox.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(fullscreenBox);
		}
		{
			screenButton = (CycleButton)new CycleButton(new Vector2f(buttonIndention, H - 250), buttonSize, this).setTextList(getDisplayModes(), font, 1);
			for(int i = 0; i < screenButton.list.length; i++)
			{
				if(screenButton.list[i].compareTo("o!" + MainManagerClass.settings.resolutionX + "x" + MainManagerClass.settings.resolutionY) == 0)
				{
					screenButton.index = i;
					break;
				}
			}
			screenButton.updateText();
			new GUIText("o!Auflösung", 1, font, new Vector2f(screenButton.position.x - 100, screenButton.position.y + (screenButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(screenButton);
		}
		{
			musicBox = new Checkbox(new Vector2f(buttonIndention * 2.2F, H - 300), checkboxSize, this);
			musicBox.setChecked(MainManagerClass.settings.music);
			new GUIText("o!Musik", 1, font, new Vector2f(musicBox.position.x - 100, musicBox.position.y + (musicBox.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(musicBox);
		}
		
		guiElements.add(new Button(new Vector2f(200, 100), buttonSize, this).setText("o!Zurück", font, 1).setClickHandler(new HandlerChangeMenu(MainMenu.class)));
		
		
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		TextMaster.init(loader);
		while(!Display.isCloseRequested() && !isCloseRequested)
		{
			render();
			input.poll(W, H);
		}
		cleanUp();
	}
	
	@Override
	public void requestClose(Class<? extends Menu> next)
	{
		super.requestClose(next);
		
		switch (langButton.getCurrent())
		{
			case "lang.german":
			{
				MainManagerClass.localizer = new Localizer("de_DE");
				MainManagerClass.settings.language = "de_DE";
				break;
			}
			case "lang.english":
			{
				MainManagerClass.localizer = new Localizer("en_US");
				MainManagerClass.settings.language = "en_US";
				break;
			}
		}
		
		String[] resolution = screenButton.getCurrent().substring(2).split("x");
		int width = Integer.parseInt(resolution[0]);
		int height = Integer.parseInt(resolution[1]);
		boolean fullscreen = fullscreenBox.isChecked();
		if(Display.getWidth() != width || Display.getHeight() != height || fullscreen != MainManagerClass.settings.fullscreen)
		{
			Mouse.setCursorPosition(0, 0);
			DisplayManager.recreateDisplay(width, height, fullscreen);
		}
		MainManagerClass.settings.resolutionX = width;
		MainManagerClass.settings.resolutionY = height;
		MainManagerClass.settings.fullscreen = fullscreen;
		MainManagerClass.settings.music = musicBox.isChecked();
		MainManagerClass.settings.writeFile();
	}
	
	public String[] getDisplayModes()
	{
		List<String> ret = new ArrayList<String>();
		try
		{
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for(int i = 0; i < modes.length; i++)
			{
				if (modes[i].getBitsPerPixel() == 32 && modes[i].getHeight() >= 600)
				{
					ret.add("o!" + modes[i].getWidth() + "x" + modes[i].getHeight());
				}
			}
			String[] array = new String[ret.size()];
			for(int i = 0; i < array.length; i++)
			{
				array[i] = ret.get(i);
			}
			return array;
		}
		catch (LWJGLException e)
		{
			System.out.println("Couldn't get Display Modes!");
			e.printStackTrace();
		}
		return null;
	}
}
