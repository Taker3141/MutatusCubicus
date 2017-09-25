package entity.organism;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import world.World;
import entity.*;
import entity.character.Player;

public class OrganBrain extends Organ
{
	public OrganBrain(List<Organ> list)
	{
		super(list);
	}
	
	@Override
	public String getName()
	{
		return "Gehirn";
	}
	
	@Override
	public String getDescription()
	{
		return "";
	}
	
	@Override
	public void loadModels(Player p)
	{
		new SubEntity(World.createModel("brain", "texture/cube/brain", 0.5F), new Vector3f(5, 12.87F, -4), 0, 0, 0, 1, Entity.w.entities, p);
	}
}
