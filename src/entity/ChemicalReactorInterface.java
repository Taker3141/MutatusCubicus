package entity;

import inventory.Inventory;
import inventory.Item;
import java.util.*;
import main.MainGameLoop;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import terrain.Terrain;
import world.World;
import entity.Entity;
import gui.OverlayChemicalReactor;
import static inventory.Item.*;

public class ChemicalReactorInterface extends Entity
{
	private OverlayChemicalReactor overlay;
	private Inventory input;
	public List<Reaction> reactions = new LinkedList<Reaction>();
	
	public ChemicalReactorInterface(Vector3f position, float scale, List<entity.Entity> list)
	{
		super(World.createModel("box", "texture/flames", 0), position, 0, 0, 0, scale, list);
		hitBox = new AABB(position, new Vector3f(10, 10, 10), new Vector3f(-5, 0, -5));
		input = new Inventory(5);
		input.addItem(DISSOLVED_ROCK); input.addItem(DISSOLVED_ROCK); input.addItem(DISSOLVED_ROCK);
		overlay = new OverlayChemicalReactor(input);
		
		reactions.add(new Reaction(new Item[]{DISSOLVED_ROCK, DISSOLVED_ROCK, DISSOLVED_ROCK}, new Item[]{ALUMINIUM, SILICON, LIQUID_OXYGEN}, 10));
	}
	
	public class Reaction
	{
		private Item[] input;
		private Item[] output;
		private float time;
		
		public Reaction(Item[] input, Item[] output, float time)
		{
			this.input = input;
			this.output = output;
			this.time = time;
		}
		
		public boolean match(Inventory currentInput)
		{
			List<Item> itemList = new ArrayList<Item>();
			for(int i = 0; i < currentInput.size; i++) itemList.add(currentInput.getItem(i));
			for(int i = 0; i < input.length; i++)
			{
				if(itemList.contains(input[i])) itemList.remove(input[i]);
				else return false;
			}
			return true;
		}
	}
	
	@Override
	public void update(World w, Terrain t)
	{
		overlay.update();
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) 
		{
			MainGameLoop.w.overlays.remove(overlay);
			overlay.setVisible(false);
			MainGameLoop.w.player.transferInv = null;
		}
		for(Reaction r : reactions)
		{
			if(r.match(input)) overlay.time = r.time;
			else overlay.time = -1;
		}
	}
	
	@Override
	public void click()
	{
		if(!MainGameLoop.w.overlays.contains(overlay)) 
		{
			MainGameLoop.w.overlays.add(overlay);
			overlay.setVisible(true);
			MainGameLoop.w.player.transferInv = input;
		}
	}
}
