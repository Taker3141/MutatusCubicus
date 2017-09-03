package entity;

import inventory.Item;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import world.World;

public class OreRock extends Rock
{
	public OreRock(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(position, rotX, rotY, rotZ, scale, list);
		model = World.createModel("rock", "texture/moon_crater", 0.1F);
	}
	
	@Override
	public Item getItem()
	{
		return Item.DISSOLVED_ORE;
	}
}
