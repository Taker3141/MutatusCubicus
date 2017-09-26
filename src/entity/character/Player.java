package entity.character;

import entity.*;
import entity.organism.Organism;
import entity.vehicle.*;
import gui.overlay.*;
import inventory.*;
import java.util.List;
import static org.lwjgl.input.Keyboard.*;
import org.lwjgl.util.vector.*;
import raycasting.*;
import renderer.DisplayManager;
import renderer.models.DummyModel;
import talk.*;
import talk.ConversationLine.Option;
import terrain.Terrain;
import world.World;

public class Player extends Movable implements ICharacter
{
	private static final float TURN_SPEED = 80;
	public Organism organism = new Organism(this);
	public Car vehicle = null;
	private Rocketship ship;
	public float dyingAnimation = 0;
	private float currentTurnSpeed = 0;
	private boolean talkFlag = true;
	
	public OverlayCommunication com;
	public OverlayOrgans organs;
	public Inventory inv;
	public final float NORMAL_SIZE;
	public final float MAX_SIZE_FACTOR = 2;
	public final int faceTexture;
	public Inventory transferInv;
	public ConversationManager conversation;
	
	public Player(Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(new DummyModel(), position, rotX, rotY, rotZ, scale, list, 20);
		ICharacter.super.register();
		inv = new Inventory(10);
		inv.setItem(2, Item.SLIME);
		inv.setItem(4, Item.DISSOLVED_ROCK);
		inv.addItem(Item.SLIME); inv.addItem(Item.SLIME); inv.addItem(Item.SLIME);
		inv.addItem(Item.DISSOLVED_ROCK); inv.addItem(Item.DISSOLVED_ROCK); inv.addItem(Item.DISSOLVED_ROCK);
		organs = new OverlayOrgans(this);
		com = new OverlayCommunication(w.characterInfo);
		conversation = new ConversationManager(com, w);
		faceTexture = loader.loadTexture("texture/gui/communication/mutatus_cubicus");
		hitBox = new AABB(position, new Vector3f(0.2F, 0.3F, 0.2F), new Vector3f(-0.1F, 0.15F, -0.1F));
		NORMAL_SIZE = scale;
	}
	
	@Override
	public void update(World w, Terrain terrain)
	{
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs(delta);
		
		rotY += currentTurnSpeed * delta;
		organism.update(delta, isKeyDown(KEY_LSHIFT));
		conversation.update();
		com.update(false);
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
		if(isKeyDown(KEY_RETURN) && talkFlag) 
		{
			if(conversation.isRunning())
			{
				{conversation.next(); talkFlag = false;}
			}
			else if(inv.getSelectedItem() != null && useItem(inv.getSelectedItem())) inv.setItem(inv.getSelectedSlot(), null);
		}
		if(!isKeyDown(KEY_RETURN) && !talkFlag) talkFlag = true;
		if(isKeyDown(KEY_F12)) System.out.println(position);
		if(isKeyDown(KEY_F2)) 
		{
			ConversationLine startLine = (ConversationLine.fromStringArray(this, "Line 1", "Line 2", "Line 3", "Line 4", "Line 5"));
			startLine.setOptions(new Option[]{new Option("Go to line 2", startLine.getNext(null)), new Option("Go to line 3", startLine.getNext(null).getNext(null)), new Option("Go to line 5", startLine.getNext(null).getNext(null).getNext(null).getNext(null))});
			this.conversation.startConversation(startLine);
		}
		if(isKeyDown(KEY_Q) && organism.getEating() == null && organism.getDigestion() == 0)
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
	
	@Override
	public String getID()
	{
		return "mutatus_cubicus";
	}

	@Override
	public String getFirstName()
	{
		return "Mutatus";
	}
	
	@Override
	public String getLastName()
	{
		return "Cubicus";
	}
	
	@Override
	public int getFaceTexture()
	{
		return faceTexture;
	}
	
	@Override
	public int[] getBirthday()
	{
		//20th June 2121
		return new int[]{20, 7, 2121};
	}
	
	@Override
	public Gender getGender()
	{
		return Gender.OTHER;
	}
	
	@Override
	public String getProfession()
	{
		return "Cubicus-Prototyp";
	}
}
