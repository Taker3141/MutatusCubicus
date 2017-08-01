package inventory;

public class Inventory
{
	public final int size;
	private int selectedSlot = 0;
	private Item[] items;
	
	public Inventory(int size)
	{
		this.size = size;
		items = new Item[size];
	}
	
	public boolean addItem(Item item)
	{
		for(int i = 0; i < size; i++)
		{
			if(items[i] == null)
			{
				items[i] = item;
				return true;
			}
		}
		return false;
	}
	
	public void selectSlot(int number)
	{
		selectedSlot = number < size ? number : 0;
	}
	
	public int getSelectedSlot()
	{
		return selectedSlot;
	}
	
	public Item getItem(int index)
	{
		return index < size ? items[index] : null;
	}
	
	public Item getSelectedItem()
	{
		return items[selectedSlot];
	}
	
	public void setItem(int index, Item i)
	{
		if(index < size) items[index] = i;
	}
	
	public void setSelectedItem(Item i)
	{
		items[selectedSlot] = i;
	}
}
