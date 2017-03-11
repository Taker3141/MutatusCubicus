package gui.handler;

import gui.element.Slot;
import gui.menu.Menu;
import gui.menu.MenuInventory;

public class HandlerSwapItems implements IClickHandler
{
	private Slot slot;
	
	public HandlerSwapItems(Slot parentSlot)
	{
		slot = parentSlot;
	}
	
	@Override
	public void click(Menu parent)
	{
		if (parent instanceof MenuInventory)
		{
			if (((MenuInventory)parent).mouseItem == null && slot.item != null)
			{
				((MenuInventory)parent).mouseItem = slot.item;
				slot.item = null;
				return;
			}
			if (((MenuInventory)parent).mouseItem != null && slot.item == null)
			{
				slot.item = ((MenuInventory)parent).mouseItem;
				slot.item.setPosition(slot.position);
				((MenuInventory)parent).mouseItem = null;
				return;
			}
		}
	}
}
