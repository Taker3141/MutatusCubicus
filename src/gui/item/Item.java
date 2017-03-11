package gui.item;

import org.lwjgl.util.vector.Vector2f;
import renderer.models.TexturedModel;
import gui.element.Icon;
import gui.element.Slot;
import gui.menu.MenuInventory;

public class Item
{
	protected Icon icon;
	protected TexturedModel model;
	public final float mass;
	
	public Item(int texture, Slot s, TexturedModel model, float mass)
	{
		icon = new Icon(texture, s.position, Icon.Size.x64, s.parent);
		this.model = model;
		this.mass = mass;
	}
	
	public Item(int texture, Vector2f position, MenuInventory parent, TexturedModel model, float mass)
	{
		icon = new Icon(texture, position, Icon.Size.x64, parent);
		this.model = model;
		this.mass = mass;
	}
	
	public void setPosition(Vector2f position)
	{
		icon.position = position;
	}
	
	public Vector2f getPosition()
	{
		return icon.position;
	}
	
	public Icon getIcon()
	{
		return icon;
	}
	
	public TexturedModel getModel()
	{
		return model;
	}
}
