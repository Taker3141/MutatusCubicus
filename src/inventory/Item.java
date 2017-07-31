package inventory;

import renderer.Loader;

public class Item
{
	public String name;
	public int texture;
	public static int nullTexture;
	
	public Item(String name, int texture)
	{
		this.name = name;
		this.texture = texture;
	}
	
	public static void init(Loader loader)
	{
		nullTexture = loader.loadTexture("texture/item/null");
	}
}
