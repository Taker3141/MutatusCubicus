package gui.overlay;

import inventory.Item;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import entity.character.Player;
import entity.organism.Organism;
import font.fontMeshCreator.GUIText;
import gui.element.*;
import gui.handler.IClickHandler;
import gui.menu.Menu;

public class OverlayOrgans extends Overlay
{
	protected Player p;
	protected Organism o;
	protected final int X = W / 2 - 512;
	protected final int Y = H / 2 - 128;
	protected GuiBar digestion, energy, cubeSize, boost;
	protected GuiElement selectedSlot;
	protected GuiElement[] itemIcons;
	protected GUIText itemText;
	protected Button organInfoButton;
	
	public OverlayOrgans(Player p)
	{
		super();
		position = new Vector2f(W / 2 - 512, 0);
		super.size = new Vector2f(1024, 256);
		this.p = p;
		o = p.organism;
		digestion = new GuiBar(loader.loadTexture("texture/cube/intestines"), new Vector2f(X + 765, 74), new Vector2f(198, 48), null);
		energy = new GuiBar(loader.loadTexture("texture/cube/storage_cone"), new Vector2f(X + 765, 12), new Vector2f(198, 48), null);
		cubeSize = new GuiBar(loader.loadTexture("texture/gui/organ/slime"), new Vector2f(X + 633, 11), new Vector2f(48, 98), null);
		boost = new GuiBar(loader.loadTexture("texture/gui/organ/boost"), new Vector2f(X + 381, 11), new Vector2f(198, 48), null);
		selectedSlot = new GuiElement(loader.loadTexture("texture/gui/inventory/slot_selected"), new Vector2f(X + 8, 72), new Vector2f(64, 64), null);
		organInfoButton = new Button(new Vector2f(position.x + 524, 71), new Vector2f(52, 52), null);
		organInfoButton.setTextures("info");
		organInfoButton.setClickHandler(new OrganInfoHandler());
		elements.add(new GuiElement(loader.loadTexture("texture/gui/organ/background"), position, size, null));
		elements.add(digestion);
		elements.add(energy);
		elements.add(cubeSize);
		elements.add(boost);
		elements.add(organInfoButton);
		itemIcons = new GuiElement[p.inv.size];
		for(int i = 0; i < itemIcons.length; i++)
		{
			itemIcons[i] = new GuiElement(Item.nullTexture, new Vector2f(X + 8 + 62 * (i % 5), 71 - 62 * (i / 5)), new Vector2f(64, 64), null);
			elements.add(itemIcons[i]);
		}
		elements.add(selectedSlot);
		itemText = new GUIText("", 1, font, new Vector2f(X + 320, 110), 1, false);
		itemText.setColour(0.4F, 1, 0.4F);
	}
	
	public void update()
	{
		digestion.width = 2F * o.getDigestion() / 100;
		digestion.size.x = 198 * o.getDigestion() / 100;
		digestion.offsetX -= DisplayManager.getFrameTimeSeconds() / 10;
		digestion.offsetY = digestion.offsetX;
		if(digestion.offsetX < 1) {digestion.offsetX += 1; digestion.offsetY += 1;}
		
		energy.width = 2F * o.getEnergy() / o.getMaxEnergy();
		energy.size.x = 198 * o.getEnergy() / o.getMaxEnergy();
		energy.offsetX -= DisplayManager.getFrameTimeSeconds() / 40;
		energy.offsetY = energy.offsetX;
		if(energy.offsetX < 1) {energy.offsetX += 1; energy.offsetY += 1;}
		
		float sizeFactor = (p.scale - p.NORMAL_SIZE) / (o.getMaxSize() - p.NORMAL_SIZE);
		cubeSize.height = sizeFactor;
		cubeSize.size.y = sizeFactor  * 98;
		cubeSize.offsetX = cubeSize.offsetY = ((float)Math.sin(DisplayManager.getTime() * 5) - 0.5F) / 2;
		
		boost.width = o.getBoost() / 100;
		boost.size.x = 198 * o.getBoost() / 100;
		boost.offsetY -= DisplayManager.getFrameTimeSeconds() * 4;
		if(boost.offsetY < 1) boost.offsetY += 1;
		
		for(int i = 0; i < itemIcons.length; i++)
		{
			Item item = p.inv.getItem(i);
			if(item != null) itemIcons[i].setTextureID(item.texture);
			else itemIcons[i].setTextureID(Item.nullTexture);
		}
		
		itemText.setText(p.inv.getSelectedItem() != null ? p.inv.getSelectedItem().name : "");
		
		selectedSlot.position.x = X + 8 + 62 * (p.inv.getSelectedSlot() % 5);
		selectedSlot.position.y = 71 - 62 * (p.inv.getSelectedSlot() / 5);
	}
	
	@Override
	public void leftClick(int mouseX, int mouseY)
	{
		int x = (int)(mouseX - position.x);
		int y = (int)((int)(Display.getHeight() - mouseY) - position.y);
		if((y > 13 && y < 61) || (y > 74 && y < 122))
		{
			int shiftedX = x - 11;
			if(shiftedX > 0 && shiftedX < 296 && shiftedX % 62 < 49)
			{
				int index = shiftedX / 59 + 5 * ((122 - y) / 48);
				Item item = p.inv.getItem(index);
				if(item != null && p.transferInv != null && p.transferInv.getNumberOfItems() < p.transferInv.size) 
				{
					p.transferInv.addItem(item);
					p.inv.setItem(index, null);
				}
			}
		}
	}
	
	protected class OrganInfoHandler implements IClickHandler
	{
		@Override
		public void click(Menu parent)
		{
			p.organism.overlay.setVisible(!p.organism.overlay.getVisible());
		}
	}
	
	public void setDigestingTexture(int id)
	{
		digestion.setTexture(id);
	}
}
