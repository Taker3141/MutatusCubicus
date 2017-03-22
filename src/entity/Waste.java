package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import renderer.models.TexturedModel;

public class Waste extends Entity implements IEdible
{
	protected float normalSize;
	
	public Waste(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		normalSize = scale;
		this.hitBox = new AABB(position, new Vector3f(scale * 3, scale * 2.0F, scale * 3), new Vector3f(-scale * 1.5F, 0, -scale * 1.5F));
	}

	@Override
	public FoodType getType()
	{
		return IEdible.FoodType.TOXIC_WASTE;
	}

	@Override
	public float getAmmount()
	{
		return normalSize * 250;
	}

	@Override
	public float getEnergy()
	{
		return normalSize * 375;
	}
}
