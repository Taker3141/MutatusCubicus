package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import gui.item.Item;

public class EntityItem extends Movable
{
	protected Vector3f rotOffset = new Vector3f(0, 90, 0);
	protected Vector3f offset = new Vector3f(0, -1.5F, 0);
	public EntityItem(Item i, Vector3f position, List<Entity> list)
	{
		super(i.getModel(), position, 0, 0, 0, 0.1F, list, 0.1F);
	}
}
