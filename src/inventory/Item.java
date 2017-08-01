package inventory;

import main.MainManagerClass;
import renderer.Loader;

public class Item
{
	public String name;
	public int texture;
	public static int nullTexture;
	public static Loader loader = MainManagerClass.loader;
	
	public static final Item SLIME = new Item("Schleim", loader.loadTexture("texture/item/slime"));
	public static final Item DISSOLVED_ROCK = new Item("Zersetzter Stein", loader.loadTexture("texture/item/dissolved_rock"));
	
	protected Item(String name, int texture)
	{
		this.name = name;
		this.texture = texture;
	}
	
	public static void init()
	{
		nullTexture = loader.loadTexture("texture/item/null");
	}
}
