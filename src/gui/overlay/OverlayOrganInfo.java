package gui.overlay;

import java.util.*;
import entity.organism.*;
import entity.organism.Organ.OrganType;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;
import main.MainGameLoop;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import static entity.organism.Organ.OrganType.*;

public class OverlayOrganInfo extends Overlay
{
	protected Organism o;
	protected GuiElement background;
	protected GuiElement slimeBackground;
	protected GuiElement slimeForeground;
	protected GUIText caption;
	protected Map<OrganType, GuiElement> organPictures = new HashMap<>();
	protected OrganBox[] boxes = new OrganBox[6];
	
	public OverlayOrganInfo(Organism organism)
	{
		super();
		o = organism;
		position = new Vector2f(W / 2 - 384, H / 2 - 192);
		super.size = new Vector2f(768, 384);
		caption = new GUIText("STATUS", 3, font, new Vector2f(position.x + 20, position.y + size.y - 20), size.x / W, false);
		background = new GuiElement(loader.loadTexture("texture/gui/box"), position, size, null);
		slimeBackground = new GuiElement(loader.loadTexture("texture/gui/organ/slime_background"), new Vector2f(position.x + 20, position.y + 20), new Vector2f(256, 256), null);
		slimeForeground = new GuiElement(loader.loadTexture("texture/gui/organ/slime_foreground"), slimeBackground.position, slimeBackground.size, null);
		elements.add(background);
		elements.add(slimeBackground);
		for(Organ organ : organism.list)
		{
			if(organ.type == SLIME) continue;
			GuiElement element = new GuiElement(loader.loadTexture("texture/gui/organ/" + organ.type.name().toLowerCase()), slimeBackground.position, slimeBackground.size, null);
			organPictures.put(organ.type, element);
			elements.add(element);
		}
		boxes[0] = new OrganBox(SLIME, new Vector2f(30, 30), new Vector2f(204, 204));
		boxes[1] = new OrganBox(DIGESTIVE, new Vector2f(78, 76), new Vector2f(153, 124));
		boxes[2] = new OrganBox(HEART, new Vector2f(71, 104), new Vector2f(140, 158));
		boxes[3] = new OrganBox(BRAIN, new Vector2f(125, 137), new Vector2f(180, 190));
		boxes[4] = new OrganBox(SHAPER, new Vector2f(164, 84), new Vector2f(201, 118));
		boxes[5] = new OrganBox(LIVER, new Vector2f(37, 83), new Vector2f(75, 124));
		elements.add(slimeForeground);
	}
	
	public void update()
	{
		Vector2f mouse = new Vector2f(MainGameLoop.w.input.getMouseX() - slimeBackground.position.x, (H - MainGameLoop.w.input.getMouseY()) - slimeBackground.position.y);
		boolean organColored = false;
		for(int i = 1; i < boxes.length; i++)
		{
			if(!organColored && boxes[i].isInside(mouse)) 
			{
				organPictures.get(boxes[i].type).color = new Vector3f(1F, 0.5F, 0.5F);
				organColored = true;
			}
			else organPictures.get(boxes[i].type).color = new Vector3f(1, 1, 1);
		}
	}

	public void setVisible(boolean v)
	{
		for(GuiElement e : elements) e.isVisible = v;
		caption.isVisible = v;
	}

	public boolean getVisible()
	{
		return background.isVisible;
	}
	
	protected static class OrganBox
	{
		public final OrganType type;
		public Vector2f p1, p2;
		
		public OrganBox(OrganType t, Vector2f point1, Vector2f point2)
		{
			type = t;
			p1 = point1;
			p2 = point2;
		}
		
		public boolean isInside(Vector2f mouse)
		{
			return mouse.x > p1.x && mouse.x < p2.x && mouse.y > p1.y && mouse.y < p2.y;
		}
	}
}
