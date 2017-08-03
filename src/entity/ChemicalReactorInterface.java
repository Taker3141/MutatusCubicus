package entity;

import inventory.Inventory;
import inventory.Item;
import java.util.List;
import main.MainGameLoop;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import terrain.Terrain;
import world.World;
import entity.Entity;
import gui.OverlayChemicalReactor;

public class ChemicalReactorInterface extends Entity
{
	private OverlayChemicalReactor overlay;
	private Inventory input;
	
	public ChemicalReactorInterface(Vector3f position, float scale, List<entity.Entity> list)
	{
		super(World.createModel("box", "texture/flames", 0), position, 0, 0, 0, scale, list);
		hitBox = new AABB(position, new Vector3f(10, 10, 10), new Vector3f(-5, 0, -5));
		input = new Inventory(5);
		input.addItem(Item.DISSOLVED_ROCK); input.addItem(Item.DISSOLVED_ROCK); input.addItem(Item.DISSOLVED_ROCK);
		overlay = new OverlayChemicalReactor(input);
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		overlay.update();
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) 
		{
			MainGameLoop.w.overlays.remove(overlay);
			overlay.setVisible(false);
		}
	}
	
	@Override
	public void click()
	{
		if(!MainGameLoop.w.overlays.contains(overlay)) 
		{
			MainGameLoop.w.overlays.add(overlay);
			overlay.setVisible(true);
		}
	}
}
