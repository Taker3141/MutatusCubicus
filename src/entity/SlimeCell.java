package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;

public class SlimeCell extends Entity
{
	protected boolean isOpen = false;
	
	public SlimeCell(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
	}
	
	public void setOpen(boolean open)
	{
		isOpen = open;
	}
}
