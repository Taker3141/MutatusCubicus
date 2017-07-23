package gui.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import main.MainManagerClass;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import font.fontMeshCreator.FontType;
import font.fontRendering.TextMaster;
import gui.element.GuiElement;
import renderer.GuiRenderer;
import renderer.Loader;
import world.World;

public abstract class Menu
{
	protected int W;
	protected int H;
	protected final Vector2f buttonSize = new Vector2f(256, 32);
	protected final Vector2f checkboxSize = new Vector2f(64, 64);
	
	
	protected Loader loader = new Loader();
	public FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
	protected List<GuiElement> guiElements = new ArrayList<GuiElement>();
	protected List<GuiElement> guiElementsBackground = new ArrayList<GuiElement>();
	protected List<GuiElement> guiElementsForeground = new ArrayList<GuiElement>();
	protected GuiRenderer gRenderer = new GuiRenderer(loader);
	protected boolean isCloseRequested = false;
	protected boolean shouldStartGame = false;
	protected Class<? extends World> startWorld;
	
	public Menu()
	{
		TextMaster.clear();
		W = Display.getWidth();
		H = Display.getHeight();
		GL11.glViewport(0, 0, W, H);
	}
	
	public abstract void doMenu();
	
	protected void render()
	{
		List<GuiElement> renderList = new ArrayList<GuiElement>();
		renderList.addAll(guiElementsForeground);
		renderList.addAll(guiElements);
		renderList.addAll(guiElementsBackground);
		gRenderer.render(renderList, true);
		TextMaster.render();
		Display.update();
	}
	
	protected void cleanUp()
	{
		TextMaster.cleanUp();
		gRenderer.cleanUp();
	}
	
	public void requestClose(Class<? extends Menu> next)
	{
		isCloseRequested = true;
		MainManagerClass.nextMenu = next;
	}
	
	public void requestGameStart(Class<? extends World> world)
	{
		startWorld = world;
		isCloseRequested = true;
		shouldStartGame = true;
	}
}
