package inventory;

public class Inventory
{
	public final int size;
	private int selectedSlot = 0;
	
	public Inventory(int size)
	{
		this.size = size;
	}
	
	public void selectSlot(int number)
	{
		selectedSlot = number;
	}
	
	public int getSelectedSlot()
	{
		return selectedSlot;
	}
}
