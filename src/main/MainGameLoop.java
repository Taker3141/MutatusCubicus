package main;

import org.lwjgl.opengl.Display;
import renderer.*;
import renderer.fbo.Fbo;
import renderer.fbo.PostProcessing;
import world.MoonLabWorld;
import world.World;

public class MainGameLoop
{
	public static World w;
	public static Fbo fbo;
	
	public static void doGame()
	{
		w = new MoonLabWorld();
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
	}
}
