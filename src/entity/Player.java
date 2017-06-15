package entity;

import entity.vehicle.Car;
import entity.vehicle.Rocketship;
import gui.OverlayOrgans;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import raycasting.IHitBox;
import raycasting.NoHitbox;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Player extends Movable
{
	private static final float TURN_SPEED = 80;
	private Organism organism = this.new Organism();
	private Car vehicle = null;
	private Rocketship ship;
	private float dyingAnimation = 0;
	
	private float currentTurnSpeed = 0;
	
	public OverlayOrgans organs = new OverlayOrgans(this);
	public final float NORMAL_SIZE;
	public final float MAX_SIZE_FACTOR = 2;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list, 20);
		hitBox = new AABB(position, new Vector3f(0.2F, 0.3F, 0.2F), new Vector3f(-0.1F, 0.15F, -0.1F));
		NORMAL_SIZE = scale;
		model.transparencyNumber = 1;
	}
	
	@Override
	public void update(Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs(delta);
		
		rotY += currentTurnSpeed * delta;
		organism.update(delta, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT));
		super.update(terrain);
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
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				v.x = (float) (organism.getSpeed() * Math.sin(Math.toRadians(rotY)));
				v.z = (float) (organism.getSpeed() * Math.cos(Math.toRadians(rotY)));
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				v.x = (float) (-organism.getSpeed() * Math.sin(Math.toRadians(rotY)));
				v.z = (float) (-organism.getSpeed() * Math.cos(Math.toRadians(rotY)));
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;
			else currentTurnSpeed = 0;
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) jump();
		}
		if(vehicle != null)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_W) && (vehicle.v.x * vehicle.v.x + vehicle.v.z * vehicle.v.z) < 1000)
			{
				vehicle.v.x += (float) (5 * Math.sin(Math.toRadians(vehicle.rotY)));
				vehicle.v.z += (float) (5 * Math.cos(Math.toRadians(vehicle.rotY)));
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S) && (vehicle.v.x * vehicle.v.x + vehicle.v.z * vehicle.v.z) > 1)
			{
				vehicle.v.x *= 0.9F;
				vehicle.v.z *= 0.9F;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {vehicle.rotY += dt * 5 * vehicle.v.length(); rotY += dt * 5 * vehicle.v.length();}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {vehicle.rotY -= dt * 5 * vehicle.v.length(); rotY -= dt * 5 * vehicle.v.length();}
			if(Keyboard.isKeyDown(Keyboard.KEY_E)) {vehicle.passenger = null; vehicle = null; position.x += 1.5F; model.transparencyNumber = 1;}
		}
		if(ship != null)
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {ship.rotY += dt * 20; rotY += dt * 20;}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {ship.rotY -= dt * 20; rotY -= dt * 20;}
			if(Keyboard.isKeyDown(Keyboard.KEY_E)) {ship.passenger = null; ship = null;}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7) && (scale + 0.001F * dt) < NORMAL_SIZE * MAX_SIZE_FACTOR) scale += 0.001F * dt;
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) && scale > NORMAL_SIZE) scale -= 0.001F * dt;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F12)) System.out.println(position);
		if(Keyboard.isKeyDown(Keyboard.KEY_Q) && organism.eating == null && organism.digestion == 0)
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
	
	private class Organism
	{
		private float digestion = 0;
		private float energy = 110;
		private float boost = 0;
		private boolean boosting;
		private IEdible food;
		private Entity eating;
		
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
				if(digestion < 0) {digestion = 0; food = null;}
			}
			organs.update();
			if(eating != null)
			{
				eating.position = new Vector3f(position);
				eating.position.y = position.y + 10 * scale;
				if(eating.scale > 0) eating.scale -= DisplayManager.getFrameTimeSeconds() * 0.1F;
				else {eating.unregister(); eating = null;};
			}
		}
		
		public float getSpeed()
		{
			if(!boosting) return 10;
			else return 50;
		}
		
		public float getJumpPower()
		{
			if(!boosting) return 10;
			else return 25;
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
	public Matrix4f getTransformationMatrix()
	{
		if(dyingAnimation <= 1)
		{
			Matrix4f m1 = super.getTransformationMatrix();
			Matrix4f m2 = new Matrix4f();
			m2.setIdentity();
			m2.m13 = dyingAnimation * 0.08F;
			m2.m11 *= 1 - dyingAnimation * 0.5;
			return Matrix4f.mul(m1, m2, null);
		}
		return super.getTransformationMatrix();
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
		if(e instanceof Rocketship)
		{
			ship = (Rocketship)e;
			ship.passenger = this;
		}
	}
	
	@Override
	public IHitBox getHitBox()
	{
		if(vehicle == null && ship == null) return hitBox;
		else return new AABB(new Vector3f(), new Vector3f(), new Vector3f());
	}
	
	@Override
	protected float getGravity()
	{
		if(vehicle == null && ship == null) return GRAVITY;
		else return 0;
	}
}