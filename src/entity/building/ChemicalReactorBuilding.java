package entity.building;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import entity.Entity;
import world.World;

public class ChemicalReactorBuilding extends Building
{
	public ChemicalReactorBuilding(Vector3f position, float rotX, float rotY, float rotZ, List<Entity> list)
	{
		super(World.createModel("chemical_reactor", "texture/concrete", 0), position, rotX, rotY, rotZ, 10, list);
	}

	@Override
	protected void initializeWalls()
	{
		walls = new AABB[0];
	}
}
