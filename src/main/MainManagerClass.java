package main;

import java.io.IOException;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;
import font.fontRendering.TextMaster;
import gui.menu.MainMenu;
import gui.menu.Menu;
import localize.Localizer;
import renderer.DisplayManager;
import renderer.Loader;
import toolbox.LogStream;

public class MainManagerClass
{
	public static Localizer localizer;
	public static final String workingPath;
	public static SettingsFile settings;
	public static Loader loader = new Loader();
	public static Class<? extends Menu> nextMenu;
	private static Audio music;
	
	public static void main(String[] args)
	{
		settings = new SettingsFile(workingPath + "/save/settings.txt");
		localizer = new Localizer(settings.language);
		DisplayManager.createDisplay(settings.resolutionX, settings.resolutionY, settings.fullscreen);
		playMusic();
		TextMaster.init(loader);
		
		new MainMenu().doMenu();
		while(nextMenu != null)
		{
			doNextMenu();
		}
		AL.destroy();
		TextMaster.cleanUp();
		System.out.println("Game successfully terminated.");
	}

	private static void playMusic()
	{
		music.playAsMusic(MainManagerClass.settings.music ? 1 : 0, 1, true);
	}
	
	static 
	{
		System.setOut(new LogStream());
		System.setProperty("file.encoding", "Cp1252");
		workingPath = System.getProperty("user.dir");
		System.out.println("Working path is: " + workingPath);
		try
		{
			music = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/main_menu.ogg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void doNextMenu()
	{
		try
		{
			Menu next = (Menu)MainManagerClass.nextMenu.newInstance();
			next.doMenu();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void update()
	{
		playMusic();
	}
}
