package gui.element;

import java.util.List;
import gui.handler.IClickHandler;
import gui.item.Item;
import gui.menu.MenuInventory;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;

public class Slot extends GuiElement implements IClickable
{
	protected static final Vector2f standardSize = new Vector2f(64, 64);
	private static int textureSlot;
	private static int textureSlotHover;
	private static int textureSlotClick;
	private static List<GuiElement> iconList;
	public Item item;
	protected Icon icon;
	protected IClickHandler handler;
	
	public static void loadAllTextures(Loader l)
	{
		textureSlot = l.loadTexture("texture/gui/inventory/slot");
		textureSlotHover = l.loadTexture("texture/gui/inventory/slot_hover");
		textureSlotClick = l.loadTexture("texture/gui/inventory/slot_click");
	}
	
	public static void setIconList(List<GuiElement> list)
	{
		iconList = list;
	}
	
	public Slot(Vector2f position, Vector2f size, MenuInventory parent)
	{
		super(textureSlot, position, size, parent);
	}
	
	public Slot(Vector2f position, MenuInventory parent)
	{
		this(position, standardSize, parent);
	}
	
	public Slot setItem(Item item)
	{
		this.item = item;
		if (this.item != null)
		{
			icon = item.getIcon();
			iconList.add(icon);
		}
		return this;
	}
	
	public Slot setClickHandler(IClickHandler h)
	{
		handler = h;
		return this;
	}

	@Override
	public void leftClick(int mouseX, int mouseY)
	{
		texture = textureSlotClick;
	}

	@Override
	public void rightClick(int mouseX, int mouseY) {}

	@Override
	public void leftReleased(int mouseX, int mouseY)
	{
		texture = textureSlotHover;
		if(handler != null) handler.click(parent);
	}

	@Override
	public void rightReleased(int mouseX, int mouseY) {}

	@Override
	public void entered(int mouseX, int mouseY)
	{
		texture = textureSlotHover;		
	}

	@Override
	public void left(int mouseX, int mouseY)
	{
		texture = textureSlot;
	}

	@Override
	public boolean isOver(int x, int y)
	{
		return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
	}
}
