package entity.organism;

import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import world.World;
import entity.Entity;
import entity.SubEntity;
import entity.character.Player;

public class OrganSlime extends Organ
{
	public OrganSlime(Organism organism)
	{
		super(organism, OrganType.SLIME);
	}
	
	@Override
	public String getName()
	{
		return "Schleimh�lle";
	}
	
	@Override
	public String getDescription()
	{
		return "Die aus siliziumbasierten Polymeren bestehende Schleimh�lle sch�tzt die anderen Organe vor �u�eren Einfl�ssen und h�lt den Druck im Inneren des W�rfels konstant, kann wenn n�tig aber auch Gegenst�nde und Nahrung durchlassen, um diese aufzunehmen. Sie wird vom Shaper durch Mikrovibrationen in Form gehalten und zur Fortbewegung und zum Springen manipuliert.";
	}
	
	@Override
	public void loadModels(Player p)
	{
		TexturedModel outerModel = new TexturedModel(OBJLoader.loadOBJModel("cube/outer_cube"), new ModelTexture(MainManagerClass.loader.loadTexture("texture/cube/outer_cube"), true));
		new SubEntity(outerModel, new Vector3f(), 0, 0, 0, 1, Entity.w.entities, p);
		outerModel.transparencyNumber = 1;
		TexturedModel veins = World.createModel("cube/veins", "texture/cube/veins", 0.5F);
		new SubEntity(veins, new Vector3f(-4.93F, 7.37F, -2.71F), -12.01F, 15.67F, 0, 1, Entity.w.entities, p);
		new SubEntity(veins, new Vector3f(-2.48F, 9.43F, 1.48F), 14.2F, -4.72F, 0, 0.8F, Entity.w.entities, p);
		new SubEntity(veins, new Vector3f(5.31F, 9.28F, 0.79F), 36.21F, 0, 0, 1, Entity.w.entities, p);
		new SubEntity(veins, new Vector3f(3.03F, 8.7F, 0.27F), 30.38F, 45, 0, 1, Entity.w.entities, p);
		new SubEntity(veins, new Vector3f(2.17F, 12.21F, -2.38F), 36.21F, -94.14F, 0, 0.7F, Entity.w.entities, p);
	}
}
