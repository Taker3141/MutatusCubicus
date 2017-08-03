package gui;

import inventory.Inventory;
import inventory.Item;
import main.MainGameLoop;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import entity.ChemicalReactorInterface;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;

public class OverlayChemicalReactor extends Overlay
{
	protected GUIText[] texts;
	protected Inventory in, out;
	protected GuiElement[] itemIcons;
	public String resultText = "";
	private boolean visible = false;
	private ChemicalReactorInterface reactor;
	
	public OverlayChemicalReactor(Inventory input, Inventory output, ChemicalReactorInterface reactor)
	{
		in = input;
		this.reactor = reactor;
		out = output;
		position = new Vector2f(W / 2 - 256, H / 2 - 128);
		size = new Vector2f(512, 256);
		texts = new GUIText[3];
		texts[0] = new GUIText("Chemischer Reaktor", 2, font, new Vector2f(position.x + 10, position.y + size.y - 5), 1, false);
		texts[0].setColour(0.6F, 0.6F, 0.6F);
		texts[1] = new GUIText("Dauer: 0s", 1, font, new Vector2f(position.x + 303, position.y + 162), 1, false);
		texts[1].setColour(0, 0.6F, 0);
		texts[2] = new GUIText("Produkt:", 1, font, new Vector2f(position.x + 10, position.y + 100), 1, false);
		texts[2].setColour(0, 0.6F, 0);
		setVisible(false);
		elements.add(new GuiElement(loader.loadTexture("texture/gui/chemistry/background"), new Vector2f(W / 2 - 256, H / 2 - 128), new Vector2f(512, 256), null));
		itemIcons = new GuiElement[in.size + out.size];
		for(int i = 0; i < itemIcons.length; i++)
		{
			if(i < in.size) itemIcons[i] = new GuiElement(Item.nullTexture, new Vector2f(position.x + 9 + 59 * i, position.y + 112), new Vector2f(64, 64), null);
			else itemIcons[i] = new GuiElement(Item.nullTexture, new Vector2f(position.x + 9 + 59 * (i - in.size), position.y + 9), new Vector2f(64, 64), null);
			elements.add(itemIcons[i]);
		}
	}
	
	@Override
	public void leftClick(int mouseX, int mouseY)
	{
		int x = (int)(mouseX - position.x);
		int y = (int)((int)(Display.getHeight() - mouseY) - position.y);
		Inventory source = y > 116 && y < 161 ? in : (y > 11 && y < 59 ? out : null);
		if(x > 450 && x < 504 && y > 116 && y < 161 && reactor.getTime() > 0)
		{
			reactor.startReaction();
		}
		else if(source != null)
		{
			int shiftedX = x - 12;
			if(shiftedX > 0 && shiftedX < 281 && shiftedX % 59 < 49)
			{
				Item item = source.getItem(shiftedX / 59);
				Inventory inv = MainGameLoop.w.player.inv;
				if(item != null && inv.getNumberOfItems() < inv.size) 
				{
					MainGameLoop.w.player.inv.addItem(item);
					source.setItem(shiftedX / 59, null);
				}
			}
		}
	}

	public void update()
	{
		for(int i = 0; i < itemIcons.length; i++)
		{
			Item item = i < in.size ? in.getItem(i) : out.getItem(i - in.size);
			if (item != null) itemIcons[i].setTextureID(item.texture);
			else itemIcons[i].setTextureID(Item.nullTexture);
		}
		if(visible)
		{
			texts[2].isVisible = texts[1].isVisible = reactor.getTime() > 0;
			texts[1].setText("Dauer: " + String.format("%.1f", reactor.getTime()) + "s");
			texts[2].setText(resultText);
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
		visible = v;
		for(GUIText text : texts)
		{
			text.isVisible = v;
		}
	}
}
