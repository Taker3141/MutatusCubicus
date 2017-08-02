package inventory;

import java.util.LinkedList;
import java.util.List;
import main.MainManagerClass;
import renderer.Loader;

public class Item
{
	public String name;
	public String textureName;
	public int texture;
	public static int nullTexture;
	public static Loader loader = MainManagerClass.loader;
	private static List<Item> itemList = new LinkedList<Item>();
	
	public static final Item SLIME = new Item("Schleim", "slime");
	public static final Item DISSOLVED_ROCK = new Item("Zersetzter Stein", "dissolved_rock");
	public static final Item LIQUID_OXYGEN = new Item("Flüssiger Sauerstoff", "liquid_oxygen");
	public static final Item SILICON = new Item("Silizium", "silicon");
	public static final Item ALUMINIUM = new Item("Aluminium", "aluminium");
	
	protected Item(String name, String textureName)
	{
		this.name = name;
		this.textureName = textureName;
		itemList.add(this);
	}
	
	public static void init()
	{
		nullTexture = loader.loadTexture("texture/item/null");
		for(Item item : itemList)
		{
			item.reloadTexture();
		}
	}
	
	protected void reloadTexture()
	{
		this.texture = loader.loadTexture("texture/item/" + textureName);
	}
}
