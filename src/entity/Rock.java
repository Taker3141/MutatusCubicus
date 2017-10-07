package entity;

import inventory.Item;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import world.World;

public class Rock extends Entity implements IEdible
{
	protected float normalSize;
	
	public Rock(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(World.createModel("rock", "texture/moon_dust", 0.1F), position, rotX, rotY, rotZ, scale, list);
		normalSize = scale;
		this.hitBox = new AABB(position, new Vector3f(scale * 4, scale * 2.0F, scale * 4), new Vector3f(-scale * 2.0F, 0, -scale * 2.0F));
	}

	@Override
	public FoodType getType()
	{
		return IEdible.FoodType.ROCK;
	}

	@Override
	public float getAmmount()
	{
		return normalSize * 250;
	}

	@Override
	public float getEnergy()
	{
		return normalSize * 125;
	}
	
	@Override
	public Item getItem()
	{
		return Item.DISSOLVED_ROCK;
	}

	@Override
	public String getName()
	{
		return "Mondgestein";
	}
}
