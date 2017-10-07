package entity.organism;

import org.lwjgl.util.vector.Vector3f;
import world.World;
import entity.*;
import entity.character.Player;

public class OrganBrain extends Organ
{
	public OrganBrain(Organism organism)
	{
		super(organism, OrganType.BRAIN);
	}
	
	@Override
	public String getName()
	{
		return "Gehirn";
	}
	
	@Override
	public String getDescription()
	{
		return "Dieses leistungsf�hige und kompakte Gehirn aus k�nstlichen Neuronen ist die zentrale Kontrolleinheit des Organismus. Es ist au�erdem mit optischen und akustischen Sensoren so wie Antennen ausgestattet, um seine Umgebung wahrnehmen zu k�nnen.";
	}
	
	@Override
	public void loadModels(Player p)
	{
		new SubEntity(World.createModel("brain", "texture/cube/brain", 0.5F), new Vector3f(5, 12.87F, -4), 0, 0, 0, 1, Entity.w.entities, p);
	}
}
