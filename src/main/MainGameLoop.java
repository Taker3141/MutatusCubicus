package main;

import level.Level;
import gui.menu.LoadingScreen;
import org.lwjgl.opengl.Display;
import font.fontRendering.TextMaster;
import renderer.*;
import renderer.fbo.Fbo;
import renderer.fbo.PostProcessing;
import world.World;

public class MainGameLoop
{
	public static World w;
	public static Fbo fbo;
	public static int loadingProgress = 0;
	public static LoadingScreen loading;
	
	public static String doGame(WorldCreator creator)
	{
		TextMaster.clear();
		try
		{
			loading = new LoadingScreen();
			loading.doMenu();
			w = creator.createWorld();
			loading.cleanUp();
			Loader loader = MainManagerClass.loader;
			w.updateRaycaster();
			
			fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
			PostProcessing.init(loader);
			while(!Display.isCloseRequested())
			{
				if(!w.tick()) break;
				DisplayManager.updateDisplay();
			}
			PostProcessing.cleanUp();
			fbo.cleanUp();
			w.cleanUp();
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getCause() + " at " + e.getStackTrace()[0].toString();
		}
	}
	
	public static void reportProgress(int progress)
	{
		loadingProgress = progress;
		loading.doMenu();
	}
	
	public static String startLevel(Level level)
	{
		return doGame(level);
	}
	
	public static interface WorldCreator
	{
		public abstract World createWorld();
	}
}
