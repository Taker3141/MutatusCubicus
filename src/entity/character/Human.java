package entity.character;

import java.io.File;
import java.util.List;
import loader.AnimatedModelLoader;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.AnimatedModel;
import entity.Entity;
import entity.Movable;

public class Human extends Movable implements ICharacter
{
	public Human(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, float mass)
	{
		super(loadModel(), position, rotX, rotY, rotZ, scale, list, mass);
	}
	
	private static AnimatedModel loadModel()
	{
		return AnimatedModelLoader.loadEntity(new File("res/models/human.dae"), new File("texture/test"));
	}
	
	@Override
	public String getID()
	{
		return null;
	}
	
	@Override
	public int[] getBirthday()
	{
		return null;
	}
	
	@Override
	public String getFirstName()
	{
		return null;
	}
	
	@Override
	public String getLastName()
	{
		return null;
	}
	
	@Override
	public Gender getGender()
	{
		return null;
	}
	
	@Override
	public String getProfession()
	{
		return null;
	}
	
	@Override
	public int getFaceTexture()
	{
		return 0;
	}
}
