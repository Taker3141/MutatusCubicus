package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import entity.ChemicalReactorInterface;
import entity.Entity;
import world.World;

public class ChemicalReactorBuilding extends Building
{
	public ChemicalReactorBuilding(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(World.createModel("chemical_reactor/chemical_reactor", "texture/concrete", 0), position, rotX, rotY, rotZ, 10, list);
		loadEntities();
	}

	@Override
	protected void initializeWalls()
	{
		walls = new AABB[0];
	}
	
	protected void loadEntities()
	{
		new ChemicalReactorInterface(Vector3f.add(new Vector3f(18, 0, -54), position, null), 10, entityList);
		new Entity(World.createModel("chemical_reactor/screen", "texture/color/black", 0.1F), Vector3f.add(new Vector3f(10, 0, -50), position, null), 0, 0, 0, 10, entityList);
		new Entity(World.createModel("chemical_reactor/decoration", "texture/reactor", 0.1F), Vector3f.add(new Vector3f(10, 8, -20), position, null), 0, 0, 0, 10, entityList);
	}
}
