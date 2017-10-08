package gui.overlay;

import java.util.*;
import entity.organism.*;
import entity.organism.Organ.OrganType;
import font.fontMeshCreator.GUIText;
import gui.element.GuiBar;
import gui.element.GuiElement;
import main.MainGameLoop;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import static entity.organism.Organ.OrganType.*;

public class OverlayOrganInfo extends Overlay
{
	protected static final Vector3f STANDARD_COLOR = new Vector3f(1F, 1F, 1F);
	protected static final Vector3f HOVER_COLOR = new Vector3f(1F, 0.6F, 0.6F);
	protected static final Vector3f SELECTED_COLOR = new Vector3f(0.4F, 0.4F, 1F);
	protected Organism o;
	protected GuiElement background;
	protected GuiElement slimeBackground;
	protected GuiElement slimeForeground;
	protected GUIText caption, organName, organInfo;
	protected GUIText[] organStatus;
	protected GuiBar[] upgradeBars;
	protected GUIText[] upgradeTexts;
	protected GuiElement upgradeBackground;
	protected Map<OrganType, GuiElement> organPictures = new HashMap<>();
	protected OrganBox[] boxes = new OrganBox[6];
	protected OrganBox selected = null;
	
	public OverlayOrganInfo(Organism organism)
	{
		super();
		o = organism;
		position = new Vector2f(W / 2 - 384, H / 2 - 192);
		super.size = new Vector2f(768, 384);
		caption = new GUIText("STATUS", 3, font, new Vector2f(position.x + 20, position.y + size.y - 20), size.x / W, false);
		organName = new GUIText("", 2.5F, font, new Vector2f(position.x + 300, position.y + size.y - 100), 450F / W, false);
		organInfo = new GUIText("", 1, font, new Vector2f(position.x + 300, position.y + size.y - 150), 450F / W, false);
		organStatus = new GUIText[3];
		for(int i = 0; i < organStatus.length; i++)
		{
			organStatus[i] = new GUIText("", 1, font, new Vector2f(position.x + 300, position.y + (80 - i * 25)), 450F / W, false);
			organStatus[i].setColour(0.2F, 0.2F, 1F);
		}
		background = new GuiElement(loader.loadTexture("texture/gui/box"), position, size, null);
		slimeBackground = new GuiElement(loader.loadTexture("texture/gui/organ/slime_background"), new Vector2f(position.x + 20, position.y + 20), new Vector2f(256, 256), null);
		slimeForeground = new GuiElement(loader.loadTexture("texture/gui/organ/slime_foreground"), slimeBackground.position, slimeBackground.size, null);
		elements.add(background);
		elements.add(slimeBackground);
		for(Map.Entry<OrganType, Organ> entry : organism.list.entrySet())
		{
			Organ organ = entry.getValue();
			if(organ.type == SLIME) continue;
			GuiElement element = new GuiElement(loader.loadTexture("texture/gui/organ/" + organ.type.name().toLowerCase()), slimeBackground.position, slimeBackground.size, null);
			organPictures.put(organ.type, element);
			elements.add(element);
		}
		organPictures.put(SLIME, slimeForeground);
		upgradeBackground = new GuiElement(loader.loadTexture("texture/gui/organ/upgrade_background"), new Vector2f(position.x + 290, position.y + 27), new Vector2f(512, 256), null);
		elements.add(upgradeBackground);
		upgradeBars = new GuiBar[6]; upgradeTexts = new GUIText[6];
		for(int i = 0; i < upgradeBars.length; i++)
		{
			OrganType type = OrganType.values()[i];
			upgradeBars[i] = new GuiBar(loader.loadTexture(OrganType.getTextureName(type)), new Vector2f(position.x + 300 + (i % 2 * 225), position.y + 223 - (75 * (i / 2))), new Vector2f(200, 50), null);
			upgradeBars[i].width = 2; upgradeBars[i].height = 0.5F;
			elements.add(upgradeBars[i]);
			upgradeTexts[i] = new GUIText(o.upgrade.getOrganCurrentLevel(type).getName() + ": ", 1, font, new Vector2f(position.x + 300 + (i % 2 * 225), position.y + 223 - (75 * (i / 2))), 1, false);
			upgradeTexts[i].isVisible = false;
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
		if(!getVisible()) 
		{
			for(int i = 0; i < upgradeBars.length; i++) upgradeTexts[i].isVisible = false;
			return;
		}
		Vector2f mouse = new Vector2f(MainGameLoop.w.input.getMouseX() - slimeBackground.position.x, (H - MainGameLoop.w.input.getMouseY()) - slimeBackground.position.y);
		boolean organColored = false;
		for(int i = 1; i < boxes.length; i++)
		{
			OrganBox box = boxes[i];
			if(!organColored && box.isInside(mouse)) 
			{
				organPictures.get(box.type).color = HOVER_COLOR;
				organColored = true;
			}
			else organPictures.get(box.type).color = STANDARD_COLOR;
		}
		if(selected != null) organPictures.get(selected.type).color = SELECTED_COLOR;
		if(selected == null || selected.type != SLIME) slimeForeground.color = STANDARD_COLOR;
		
		
		upgradeBackground.isVisible = selected == null;
		for(int i = 0; i < upgradeBars.length; i++) 
		{
			GuiBar bar = upgradeBars[i];
			OrganType type = OrganType.values()[i];
			int level = o.upgrade.getLevel(type);
			int maxLevel = o.upgrade.getMaxLevel(type);
			bar.isVisible = selected == null;
			upgradeTexts[i].isVisible = selected == null;
			upgradeTexts[i].setText(o.upgrade.getOrganCurrentLevel(type).getName() + ": " + level + "/" + maxLevel);
			if(o.upgrade.getMaxLevel(type) != 0) bar.size.x = 200 * level / maxLevel;
			else bar.size.x = 200;
			bar.width = bar.size.x / 100;
			bar.offsetX += DisplayManager.getFrameTimeSeconds() / 30;
		}
		updateInformation();
	}
	
	@Override
	public void leftClick(int x, int y)
	{
		Vector2f mouse = new Vector2f(x - slimeBackground.position.x, (H - y) - slimeBackground.position.y);
		boolean organColored = false;
		for(int i = 1; i < boxes.length; i++)
		{
			OrganBox box = boxes[i];
			if(!organColored && box.isInside(mouse)) 
			{
				selected = box;
				organColored = true;
			}
		}
		if(!organColored && boxes[0].isInside(mouse)) 
		{
			selected = boxes[0];
			organColored = true;
		}
		if(!organColored) selected = null;
	}
	
	protected void updateInformation()
	{
		if(selected != null)
		{
			Organ organ = o.list.get(selected.type);
			organName.setText(organ.getName());
			organInfo.setText(organ.getDescription());
			for(int i = 0; i < organStatus.length; i++)
			{
				if(organ.getStatus() != null && i < organ.getStatus().length) organStatus[i].setText(organ.getStatus()[i]);
				else organStatus[i].setText("");
			}
		}
		else
		{
			organName.setText("");
			organInfo.setText("");
			for(int i = 0; i < organStatus.length; i++)
			{
				organStatus[i].setText("");
			}
		}
	}

	public void setVisible(boolean v)
	{
		for(GuiElement e : elements) e.isVisible = v;
		caption.isVisible = v; organName.isVisible = v; organInfo.isVisible = v;
		selected = null;
		updateInformation();
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
