package gui.overlay;

import java.util.*;
import entity.organism.*;
import entity.organism.Organ.OrganType;
import font.fontMeshCreator.GUIText;
import gui.element.GuiElement;
import org.lwjgl.util.vector.Vector2f;
import static entity.organism.Organ.OrganType.*;

public class OverlayOrganInfo extends Overlay
{
	protected Organism o;
	protected GuiElement background;
	protected GuiElement slimeBackground;
	protected GuiElement slimeForeground;
	protected GUIText caption;
	protected Map<OrganType, GuiElement> organPictures = new HashMap<>();
	
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
		elements.add(slimeForeground);
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
}
