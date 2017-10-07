package entity.organism;

import main.MainManagerClass;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import world.World;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import entity.Entity;
import entity.IEdible;
import entity.SubEntity;
import entity.character.Player;

public class OrganDigestiveSystem extends Organ
{
	protected float capacity = 100;
	protected float digestion = 0;
	protected IEdible food;
	protected Entity eating;
	protected final int level;
	
	public OrganDigestiveSystem(int level, Organism organism)
	{
		super(organism, OrganType.DIGESTIVE);
		this.level = level;
	}
	
	public void eat(IEdible f, Player p)
	{
		if(f instanceof Entity) eating = (Entity)f;
		food = (IEdible)f;
		digestion = food.getAmmount() < capacity ? food.getAmmount() : capacity;
		p.organs.setDigestingTexture(eating.model.getTexture().getID());
	}
	
	@Override
	public void update(float delta, Player p)
	{
		if(food != null)
		{
			digestion -= food.getType().digestPerSecond * delta;
			o.liver.energy += (food.getEnergy() / (food.getAmmount() / food.getType().digestPerSecond)) * delta;
			if(food.getType() == IEdible.FoodType.FUEL)
			{
				o.liver.boost += (50 / (food.getAmmount() / food.getType().digestPerSecond)) * delta;
			}
			if(digestion < 0) 
			{
				digestion = 0;
				p.inv.addItem(food.getItem());
				food = null;
			}
		}
		if(eating != null)
		{
			eating.position = new Vector3f(p.position);
			eating.position.y = p.position.y + 10 * p.scale;
			if(eating.scale > 0) eating.scale -= DisplayManager.getFrameTimeSeconds() * 0.1F;
			else {eating.unregister(); eating = null;}
		}
	}
	
	@Override
	public String[] getStatus()
	{
		if(digestion > 0 ) return new String[]{"Magenfüllstand: " + (int)digestion + "%", "Nahrung: " + food.getName()};
		else return new String[]{"Magen leer"};
	}
	
	@Override
	public String getName()
	{
		return "Verdauungssystem";
	}
	
	@Override
	public String getDescription()
	{
		return "Das Verdauungssystem kann verschiedene Nahrung verarbeiten, indem es diese zuerst im Magen mit Flusssäure auflöst und anschließend im Darm mithilfe von Enzymen weiterverarbeitet, um Energie zu gewinnen.";
	}
	
	@Override
	public void loadModels(Player p)
	{
		if(level < 1) return;
		ModelTexture digestive = new ModelTexture(MainManagerClass.loader.loadTexture("texture/cube/intestines"), false, 0.2F);			
		SubEntity stomach = new SubEntity(new TexturedModel(OBJLoader.loadOBJModel("stomach"), digestive), new Vector3f(-2.97F, 9.2F, 3.42F), 0, 0, 0, 1, Entity.w.entities, p);
		Keyframe[] k = 
			{
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1), 
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.2F, 2), 
				new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
			};
		stomach.a = new KeyframeAnimation(stomach, k);
		if(level < 2) return;
		new SubEntity(World.createModel("gallbladder", "texture/cube/gallbladder", 0.2F), new Vector3f(0, 10, 4.79F), 0, 0, 0, 1, Entity.w.entities, p);
		if(level < 3) return;
		new SubEntity(new TexturedModel(OBJLoader.loadOBJModel("upper_intestine"), digestive), new Vector3f(-2.97F, 5.9F, 3.42F), 0, 0, 0, 1, Entity.w.entities, p);
		if(level < 4) return;
		new SubEntity(new TexturedModel(OBJLoader.loadOBJModel("lower_intestine"), digestive), new Vector3f(-2.7F, 7.56F, 3.42F), 0, 0, 0, 1, Entity.w.entities, p);
	}
}
