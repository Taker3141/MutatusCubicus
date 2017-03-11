package gui.item;

public class Inventory
{
	public String name;
	protected Item[] items;
	public final int size;
	
	public Inventory(int size)
	{
		this.size = size;
		items = new Item[size];
	}
	
	public Item getItemAt(int index)
	{
		return items[index];
	}
	
	public void setItem(int index, Item item)
	{
		items[index] = item;
	}
}
