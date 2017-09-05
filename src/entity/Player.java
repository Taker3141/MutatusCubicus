package entity;

import entity.vehicle.*;
import gui.overlay.*;
import inventory.*;
import java.util.List;
import static org.lwjgl.input.Keyboard.*;
import objLoader.OBJLoader;
import org.lwjgl.util.vector.*;
import animation.KeyframeAnimation;
import animation.KeyframeAnimation.Keyframe;
import raycasting.*;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import talk.ConversationManager;
import talk.ConversationManager.Conversation;
import terrain.Terrain;
import world.World;

public class Player extends Movable
{
	private static final float TURN_SPEED = 80;
	private Organism organism = this.new Organism();
	public Car vehicle = null;
	private Rocketship ship;
	private float dyingAnimation = 0;
	private float currentTurnSpeed = 0;
	private boolean talkFlag = true;
	
	public OverlayCommunication com;
	public OverlayOrgans organs;
	public Inventory inv;
	public final float NORMAL_SIZE;
	public final float MAX_SIZE_FACTOR = 2;
	public Inventory transferInv;
	public ConversationManager conversation;
	
	public Player(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(null, position, rotX, rotY, rotZ, scale, list, 20);
		inv = new Inventory(10);
		inv.setItem(2, Item.SLIME);
		inv.setItem(4, Item.DISSOLVED_ROCK);
		inv.addItem(Item.SLIME); inv.addItem(Item.SLIME); inv.addItem(Item.SLIME);
		inv.addItem(Item.DISSOLVED_ROCK); inv.addItem(Item.DISSOLVED_ROCK); inv.addItem(Item.DISSOLVED_ROCK);
		organs = new OverlayOrgans(this);
		com = new OverlayCommunication();
		conversation = new ConversationManager(com);
		loadModels();
		hitBox = new AABB(position, new Vector3f(0.2F, 0.3F, 0.2F), new Vector3f(-0.1F, 0.15F, -0.1F));
		NORMAL_SIZE = scale;
		model.transparencyNumber = 1;
	}
	
	@Override
	public void update(World w, Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs(delta);
		
		rotY += currentTurnSpeed * delta;
		organism.update(delta, isKeyDown(KEY_LSHIFT));
		conversation.update();
		com.update();
		super.update(w, terrain);
	}
	
	private void jump()
	{
		v.y = organism.getJumpPower();
		isInAir = true;
	}
	
	private void checkInputs(float dt)
	{
		if(vehicle == null && ship == null)
		{
			if (isKeyDown(KEY_W))
			{
				v.x = (float) (organism.getSpeed() * Math.sin(Math.toRadians(rotY)));
				v.z = (float) (organism.getSpeed() * Math.cos(Math.toRadians(rotY)));
			}
			else if (isKeyDown(KEY_S))
			{
				v.x = (float) (-organism.getSpeed() * Math.sin(Math.toRadians(rotY)));
				v.z = (float) (-organism.getSpeed() * Math.cos(Math.toRadians(rotY)));
			}
			if (isKeyDown(KEY_A)) currentTurnSpeed = TURN_SPEED;
			else if (isKeyDown(KEY_D)) currentTurnSpeed = -TURN_SPEED;
			else currentTurnSpeed = 0;
			if (isKeyDown(KEY_SPACE) && !isInAir) jump();
		}
		if(vehicle != null)
		{
			rotY = vehicle.rotY;
			if(isKeyDown(KEY_W) && (vehicle.v.x * vehicle.v.x + vehicle.v.z * vehicle.v.z) < 1000)
			{
				vehicle.v.x += (float) (5 * Math.sin(Math.toRadians(vehicle.rotY)));
				vehicle.v.z += (float) (5 * Math.cos(Math.toRadians(vehicle.rotY)));
			}
			if(isKeyDown(KEY_S) && (vehicle.v.x * vehicle.v.x + vehicle.v.z * vehicle.v.z) > 1)
			{
				vehicle.v.x *= 0.9F;
				vehicle.v.z *= 0.9F;
			}
			if (isKeyDown(KEY_A)) 
			{
				vehicle.rotY += dt * 5 * vehicle.v.length(); 
				rotY += dt * 5 * vehicle.v.length();
			}
			else if (isKeyDown(KEY_D)) 
			{
				vehicle.rotY -= dt * 5 * vehicle.v.length();
				rotY -= dt * 5 * vehicle.v.length();
			}
			vehicle.curveTime = (isKeyDown(KEY_A) || isKeyDown(KEY_D) ? vehicle.curveTime + dt : 0);
			if(isKeyDown(KEY_E)) {vehicle.passenger = null; vehicle = null; position.x += 1.5F; model.transparencyNumber = 1;}
		}
		if(ship != null)
		{
			if (isKeyDown(KEY_A)) {ship.angularMomentum.y += dt * 10; ship.rotating = 1;}
			else if (isKeyDown(KEY_D)) {ship.angularMomentum.y -= dt * 10; ship.rotating = -1;}
			if (isKeyDown(KEY_NUMPAD6)) {ship.angularMomentum.x += dt * 10; ship.rotating = 1;}
			else if (isKeyDown(KEY_NUMPAD4)) {ship.angularMomentum.x -= dt * 10; ship.rotating = -1;}
			if (isKeyDown(KEY_NUMPAD8)) {ship.angularMomentum.z += dt * 10; ship.rotating = 1;}
			else if (isKeyDown(KEY_NUMPAD2)) {ship.angularMomentum.z -= dt * 10; ship.rotating = -1;}
			if (isKeyDown(KEY_NUMPAD5)) {ship.stabilizeRotation();}
			if(isKeyDown(KEY_E)) {ship.passenger = null; ship.info.setVisible(false); w.overlays.remove(ship.info); ship = null;}
			if(isKeyDown(KEY_SPACE)) ship.launch();
		}
		for (int i = 0; i < 10; i++)
		{
			if(isKeyDown(i + 2)) inv.selectSlot(i);
		}
		if(isKeyDown(KEY_SUBTRACT) && scale > NORMAL_SIZE) 
		{
			scale -= 0.01F * dt;
			if(dt * 1000 % 5 == 0) new Slime(this, entityList);
		}
		if(isKeyDown(KEY_RETURN) && inv.getSelectedItem() != null) 
		{
			if(useItem(inv.getSelectedItem())) inv.setItem(inv.getSelectedSlot(), null);
		}
		if(isKeyDown(KEY_F12)) System.out.println(position);
		if(isKeyDown(KEY_F2)) conversation.startConversation(new Conversation(new String[]{"Line 1", "Line 2", "Line 3", "Line 4", "Line 5"}));
		if(isKeyDown(KEY_F3) && talkFlag) {conversation.next(); talkFlag = false;}
		if(!isKeyDown(KEY_F3) && !talkFlag) talkFlag = true;
		if(isKeyDown(KEY_Q) && organism.eating == null && organism.digestion == 0)
		{
			final float distanceSq = 1;
			for(Entity e : entityList)
			{
				if(!(e instanceof IEdible)) continue;
				if(scale * 10 < e.scale) continue;
				float dx, dy, dz;
				dx = position.x - e.position.x; dy = position.y - e.position.y; dz = position.z - e.position.z;
				if(dx * dx + dy * dy + dz * dz < distanceSq) 
				{
					e.hitBox = new NoHitbox();
					organism.eat((IEdible)e);
					break;
				}
			}
		}
	}
	
	public boolean useItem(Item item)
	{
		if(item == Item.SLIME)
		{
			organism.addSlime(0.005F);
			return true;
		}
		return false;
	}

	private class Organism
	{
		private float digestion = 0;
		private float energy = 110;
		private float boost = 100;
		private boolean boosting;
		private IEdible food;
		private Entity eating;
		private float extraSlime = 0;
		
		public void eat(IEdible f)
		{
			if(f instanceof Entity) eating = (Entity)f;
			organism.food = (IEdible)f;
			organism.digestion = organism.food.getAmmount() < 100 ? organism.food.getAmmount() : 100;
			organs.setDigestingTexture(eating.model.getTexture().getID());
		}
		
		public void update(float delta, boolean boost)
		{
			energy -= delta / 50;
			digest(delta);
			if(energy < 0) energy = 0;
			if(energy > 200) energy = 200;
			if(energy < 20) dyingAnimation = 1 - (energy / 20);
			else dyingAnimation = 1.1F;
			if(extraSlime > 0 && scale < NORMAL_SIZE * MAX_SIZE_FACTOR) {scale += 0.01F * delta; extraSlime -= 0.01F * delta;}
			else extraSlime = 0;
			boosting = boost && (this.boost > 0);
			if(boosting) this.boost -= delta * 10;
			if(this.boost < 0) this.boost = 0;
		}
		
		private void digest(float delta)
		{
			if(food != null)
			{
				digestion -= food.getType().digestPerSecond * delta;
				energy += (food.getEnergy() / (food.getAmmount() / food.getType().digestPerSecond)) * delta;
				if(food.getType() == IEdible.FoodType.FUEL)
				{
					boost += (50 / (food.getAmmount() / food.getType().digestPerSecond)) * delta;
					if(boost > 100) boost = 100;
				}
				if(digestion < 0) 
				{
					digestion = 0;
					inv.addItem(food.getItem());
					food = null;
				}
			}
			organs.update();
			if(eating != null)
			{
				eating.position = new Vector3f(position);
				eating.position.y = position.y + 10 * scale;
				if(eating.scale > 0) eating.scale -= DisplayManager.getFrameTimeSeconds() * 0.1F;
				else {eating.unregister(); eating = null;}
			}
		}
		
		public void addSlime(float slime)
		{
			extraSlime += slime;
		}
		
		public float getSpeed()
		{
			float standardSpeed = 3 + scale * 100;
			if(!boosting) return standardSpeed;
			else return standardSpeed * 10;
		}
		
		public float getJumpPower()
		{
			float standardJump = 3 + scale * 200;
			if(!boosting) return standardJump;
			else return standardJump * 2;
		}
		
		public float getEnergy()
		{
			return energy;
		}
		
		public float getDigestion()
		{
			return digestion;
		}
		
		public float getBoost()
		{
			return boost;
		}
	}
	
	@Override
	public Matrix4f getTransformationMatrix(boolean correct)
	{
		if(dyingAnimation <= 1)
		{
			Matrix4f m1 = super.getTransformationMatrix(correct);
			Matrix4f m2 = new Matrix4f();
			m2.setIdentity();
			m2.m13 = dyingAnimation * 0.08F;
			m2.m11 *= 1 - dyingAnimation * 0.5;
			return Matrix4f.mul(m1, m2, null);
		}
		return super.getTransformationMatrix(correct);
	}
	
	public float getEnergy()
	{
		return organism.getEnergy();
	}
	
	public float getDigestion()
	{
		return organism.getDigestion();
	}
	
	public float getBoost()
	{
		return organism.getBoost();
	}

	public void clickAt(ICollidable e, Vector3f vec)
	{
		if(e instanceof Car && Vector3f.sub(vec, position, null).length() < 4)
		{
			vehicle = (Car)e;
			vehicle.passenger = this;
			model.transparencyNumber = -1;
		}
		if(e instanceof Rocketship && ship == null)
		{
			ship = (Rocketship)e;
			ship.passenger = this;
			ship.info.setVisible(true);
			w.overlays.add(ship.info);
		}
	}
	
	@Override
	public IHitBox getHitBox()
	{
		if(vehicle == null && ship == null) return hitBox;
		else return new AABB(new Vector3f(), new Vector3f(), new Vector3f());
	}
	
	@Override
	protected float getGravityFactor()
	{
		if(vehicle == null && ship == null) return 1;
		else return 0;
	}
	
	protected void loadModels()
	{
		model = new TexturedModel(OBJLoader.loadOBJModel("outer_cube"), new ModelTexture(loader.loadTexture("texture/cube/outer_cube"), true));
		
		new SubEntity(World.createModel("brain", "texture/cube/brain", 0.5F), new Vector3f(5, 12.87F, -4), 0, 0, 0, 1, entityList, this);
		{
			TexturedModel heart = World.createModel("heart", "texture/cube/heart", 0.5F);
			SubEntity heart1 = new SubEntity(heart, new Vector3f(-0.35F, 10, -2.58F), 0, 0, 0, 1, entityList, this);
			SubEntity heart2 = new SubEntity(heart, new Vector3f(-2.33F, 10, -2.58F), 90, 0, 0, 1, entityList, this);
			Keyframe[] k1 = 
				{
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.2F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.1F), 
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.1F, 0.1F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.8F)
				};
			heart1.a = new KeyframeAnimation(heart1, k1);
			Keyframe[] k2 = 
				{
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.1F), 
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.1F, 0.1F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
				};
			heart2.a = new KeyframeAnimation(heart2, k2);
		}
		{
			SubEntity shaper = new SubEntity(World.createModel("shaper", "texture/cube/shaper", 0.5F), new Vector3f(5.47F, 6.76F, 3.12F), 0, 0, 0, 1, entityList, this);
			Keyframe[] k = 
				{
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.2F),
					new Keyframe(new Vector3f(0, 0.2F, 0), new Vector3f(0, 0, 0), 0, 0.2F),
					new Keyframe(new Vector3f(0, 0.2F, 0.2F), new Vector3f(0, 0, 0), 0, 0.2F),
					new Keyframe(new Vector3f(0, 0, 0.2F), new Vector3f(0, 0, 0), 0, 0.2F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 0.01F),
				};
			shaper.a = new KeyframeAnimation(shaper, k);
		}
		ModelTexture digestive = new ModelTexture(loader.loadTexture("texture/cube/intestines"));			
		TexturedModel upperIntestine = new TexturedModel(OBJLoader.loadOBJModel("upper_intestine"), digestive);
		new SubEntity(upperIntestine, new Vector3f(-2.97F, 5.9F, 3.42F), 0, 0, 0, 1, entityList, this);
		{
			SubEntity liver = new SubEntity(World.createModel("liver", "texture/cube/storage_cone", 0.5F), new Vector3f(-5.8F, 6.18F, -6.18F), 0, 0, 0, 1, entityList, this);
			Keyframe[] k = 
				{
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 20F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 90, 0), 0, 20F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 180, 0), 0, 20F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 270, 0), 0, 20F),
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 360, 0), 0, 0.01F)
				};
			liver.a = new KeyframeAnimation(liver, k);
		}
		TexturedModel lowerIntestine = new TexturedModel(OBJLoader.loadOBJModel("lower_intestine"), digestive);
		new SubEntity(lowerIntestine, new Vector3f(-2.7F, 7.56F, 3.42F), 0, 0, 0, 1, entityList, this);
		{
			TexturedModel stomachModel = new TexturedModel(OBJLoader.loadOBJModel("stomach"), digestive);
			SubEntity stomach = new SubEntity(stomachModel, new Vector3f(-2.97F, 9.2F, 3.42F), 0, 0, 0, 1, entityList, this);
			Keyframe[] k = 
				{
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1), 
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.2F, 2), 
					new Keyframe(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0, 1)
				};
			stomach.a = new KeyframeAnimation(stomach, k);
		}
		
		TexturedModel veins = World.createModel("veins", "texture/cube/veins", 0.5F);
		new SubEntity(veins, new Vector3f(-4.93F, 7.37F, -2.71F), -12.01F, 15.67F, 0, 1, entityList, this);
		new SubEntity(veins, new Vector3f(-2.48F, 9.43F, 1.48F), 14.2F, -4.72F, 0, 0.8F, entityList, this);
		new SubEntity(veins, new Vector3f(5.31F, 9.28F, 0.79F), 36.21F, 0, 0, 1, entityList, this);
		new SubEntity(veins, new Vector3f(3.03F, 8.7F, 0.27F), 30.38F, 45, 0, 1, entityList, this);
		new SubEntity(veins, new Vector3f(2.17F, 12.21F, -2.38F), 36.21F, -94.14F, 0, 0.7F, entityList, this);
	}
}