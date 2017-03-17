package gui;

import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import renderer.Loader;
import main.MainManagerClass;
import entity.Player;
import gui.element.*;

public class OverlayOrgans extends Overlay
{
	protected Player p;
	protected Loader loader = MainManagerClass.loader;
	protected final int X = W / 2 - 512;
	protected GuiBar digestion, energy, size;
	
	public OverlayOrgans(Player p)
	{
		super();
		this.p = p;
		digestion = new GuiBar(loader.loadTexture("texture/cube/intestines"), new Vector2f(X + 765, 74), new Vector2f(198, 48), null);
		energy = new GuiBar(loader.loadTexture("texture/cube/storage_cone"), new Vector2f(X + 765, 11), new Vector2f(198, 48), null);
		size = new GuiBar(loader.loadTexture("texture/gui/organ/slime"), new Vector2f(X + 633, 11), new Vector2f(48, 98), null);
		elements.add(digestion);
		elements.add(energy);
		elements.add(size);
		elements.add(new GuiElement(loader.loadTexture("texture/gui/organ/background"), new Vector2f(W / 2 - 512, 0), new Vector2f(1024, 256), null));
	}
	
	public void update()
	{
		digestion.width = 2F * p.digestion / 100;
		digestion.size.x = 198 * p.digestion / 100;
		digestion.offset -= DisplayManager.getFrameTimeSeconds() / 10;
		if(digestion.offset < 1) digestion.offset += 1;
		
		energy.width = 2F * p.energy / 200;
		energy.size.x = 198 * p.energy / 200;
		energy.offset -= DisplayManager.getFrameTimeSeconds() / 40;
		if(energy.offset < 1) energy.offset += 1;
		
		float sizeFactor = (p.scale - p.NORMAL_SIZE) / ((p.NORMAL_SIZE * p.MAX_SIZE_FACTOR) - p.NORMAL_SIZE);
		size.height = sizeFactor;
		size.size.y = sizeFactor  * 98;
		size.offset = ((float)Math.sin(DisplayManager.getTime() * 5) - 0.5F) / 2;
	}
}
