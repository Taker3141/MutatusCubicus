package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;

public class Octanitrocubane extends Entity implements IEdible
{
	public Octanitrocubane(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
	}

	@Override
	public FoodType getType()
	{
		return IEdible.FoodType.FUEL;
	}

	@Override
	public float getAmmount()
	{
		return 50;
	}

	@Override
	public float getEnergy()
	{
		return 0;
	}
}
