package entity;

import inventory.Inventory;
import inventory.Item;
import java.util.*;
import main.MainGameLoop;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import renderer.DisplayManager;
import terrain.Terrain;
import world.World;
import entity.Entity;
import gui.OverlayChemicalReactor;
import static inventory.Item.*;

public class ChemicalReactorInterface extends Entity
{
	private OverlayChemicalReactor overlay;
	private Inventory input, output;
	public List<Reaction> reactions = new LinkedList<Reaction>();
	private Reaction currentReaction;
	private boolean reactionRunning = false;
	private float time = -1;
	
	public ChemicalReactorInterface(Vector3f position, float scale, List<entity.Entity> list)
	{
		super(World.createModel("box", "texture/flames", 0), position, 0, 0, 0, scale, list);
		hitBox = new AABB(position, new Vector3f(10, 10, 10), new Vector3f(-5, 0, -5));
		input = new Inventory(5);
		output = new Inventory(5);
		overlay = new OverlayChemicalReactor(input, output, this);
		
		reactions.add(new Reaction(new Item[]{DISSOLVED_ROCK, DISSOLVED_ROCK, DISSOLVED_ROCK}, new Item[]{ALUMINIUM, SILICON, LIQUID_OXYGEN}, 10));
		reactions.add(new Reaction(new Item[]{SILICON, LIQUID_OXYGEN}, new Item[]{GLASS}, 10));
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
	
	public void startReaction()
	{
		if(currentReaction != null && output.size - output.getNumberOfItems() >= currentReaction.output.length)
		{
			reactionRunning = true;
			outerLoop:
			for(Item item : currentReaction.input) for(int i = 0; i < input.size; i++) if(input.getItem(i) == item)
			{
				input.setItem(i, null);
				continue outerLoop;
			}
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
		if(!reactionRunning) for(Reaction r : reactions)
		{
			if(r.match(input)) 
			{
				overlay.resultText = r.output.length == 1 ? "Produkt: " : "Produkte: ";
				currentReaction = r;
				time = r.time;
				for(Item item : r.output)
				{
					overlay.resultText += item.name + ", ";
				}
				break;
			}
			else 
			{
				currentReaction = null;
			}
		}
		else if(time > 0) time -= DisplayManager.getFrameTimeSeconds();
		else
		{
			time = -1; reactionRunning = false;
			for(Item item : currentReaction.output) output.addItem(item);
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
	
	public float getTime()
	{
		return time;
	}
}
