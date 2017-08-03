package gui;

import inventory.Inventory;
import inventory.Item;
import main.MainGameLoop;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;

public class OverlayChemicalReactor extends Overlay
{
	protected GUIText[] texts;
	protected Inventory in;
	protected GuiElement[] itemIcons;
	
	public OverlayChemicalReactor(Inventory input)
	{
		in = input;
		position = new Vector2f(W / 2 - 256, H / 2 - 128);
		size = new Vector2f(512, 256);
		texts = new GUIText[1];
		texts[0] = new GUIText("Chemischer Reaktor", 2, font, new Vector2f(position.x + 10, position.y + size.y - 5), 1, false);
		texts[0].setColour(0.6F, 0.6F, 0.6F);
		setVisible(false);
		elements.add(new GuiElement(loader.loadTexture("texture/gui/chemistry/background"), new Vector2f(W / 2 - 256, H / 2 - 128), new Vector2f(512, 256), null));
		itemIcons = new GuiElement[in.size];
		for(int i = 0; i < itemIcons.length; i++)
		{
			itemIcons[i] = new GuiElement(Item.nullTexture, new Vector2f(position.x + 9 + 59 * i, position.y + 112), new Vector2f(64, 64), null);
			elements.add(itemIcons[i]);
		}
	}
	
	@Override
	public void leftClick(int mouseX, int mouseY)
	{
		int x = (int)(mouseX - position.x);
		int y = (int)((int)(Display.getHeight() - mouseY) - position.y);
		if(y > 116 && y < 161)
		{
			int shiftedX = x - 12;
			if(shiftedX > 0 && shiftedX < 281 && shiftedX % 59 < 48)
			{
				Item item = in.getItem(shiftedX / 59);
				Inventory inv = MainGameLoop.w.player.inv;
				if(item != null && inv.getNumberOfItems() < inv.size) 
				{
					MainGameLoop.w.player.inv.addItem(item);
					in.setItem(shiftedX / 59, null);
				}
			}
		}
	}
	
	public void update()
	{
		for(int i = 0; i < itemIcons.length; i++)
		{
			Item item = in.getItem(i);
			if(item != null) itemIcons[i].setTextureID(item.texture);
			else itemIcons[i].setTextureID(Item.nullTexture);
		}
	}
	
	@Override
	public boolean isOver(int x, int y)
	{
		boolean ret = super.isOver(x, y);
		return ret;
	}
	
	public void setVisible(boolean v)
	{
		for(GUIText text : texts)
		{
			text.isVisible = v;
		}
	}
}
