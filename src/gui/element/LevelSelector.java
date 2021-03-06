package gui.element;

import java.util.List;
import level.*;
import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import font.fontMeshCreator.GUIText;
import gui.handler.IClickHandler;
import gui.menu.Menu;

public class LevelSelector extends GuiElement implements IClickable
{
	public VerticalScrollBar scroll;
	private ArrowButton leftButton, rightButton;
	private String sectionNames[];
	private GUIText sectionText;
	private int sectionIndex = 0;
	private int scrollIndex = 3;
	private LevelManager levelManager = new LevelManager();
	private LevelSlot[] levelSlots = new LevelSlot[3];
	private List<GuiElement> guiElements;
	
	public LevelSelector(Vector2f position, Vector2f size, Menu parent, List<GuiElement> list)
	{
		super(loader.loadTexture("texture/gui/story_box"), position, size, parent);
		guiElements = list;
		final float ratio = size.x / 1024;
		LevelManager.Section[] sections = levelManager.sections.toArray(new LevelManager.Section[0]);
		sectionNames = new String[sections.length];
		for(int i = 0; i < sections.length; i++) sectionNames[i] = sections[i].name;
		scroll = new VerticalScrollBar(new Vector2f(position.x + 977 * ratio, position.y + 15 * ratio), new Vector2f(32 * ratio, 354 * ratio), parent);
		scroll.setSlide(1);
		scroll.handler = new HandlerScrollChanged();
		updateSlots(true);
		leftButton = (ArrowButton)new ArrowButton(new Vector2f(position.x + 20, position.y + size.y - 84), new Vector2f(64, 64), ArrowButton.ButtonDirection.LEFT, parent).setClickHandler(new HandlerSwitchSection(-1));
		rightButton = (ArrowButton)new ArrowButton(new Vector2f(position.x + size.x - 84, position.y + size.y - 84), new Vector2f(64, 64), ArrowButton.ButtonDirection.RIGHT, parent).setClickHandler(new HandlerSwitchSection(1));
		sectionText = new GUIText(sectionNames[0], 2, font, new Vector2f(leftButton.position.x + 84, leftButton.position.y + 50), 1, false);
	}
	
	public void updateSlots(boolean forceReload)
	{
		Level[] currentLevels = levelManager.sections.get(sectionIndex).levels.toArray(new Level[0]);
		int maxScroll = currentLevels.length < 4 ? 0 : currentLevels.length - 3;
		int newScrollIndex = (int)(maxScroll * (1 - scroll.getSlide()) + 0.5F);
		if(newScrollIndex == scrollIndex && !forceReload) return;
		scrollIndex = newScrollIndex;
		final float ratio = size.x / 1024;
		for(int i = 0; i < levelSlots.length; i++)
		{
			LevelSlot s = levelSlots[i];
			if(s != null)
			{
				guiElements.remove(levelSlots[i].lock);
				levelSlots[i].numberText.remove(); levelSlots[i].name.remove();
			}
			if((i + scrollIndex) < currentLevels.length)
			{
				Level l = currentLevels[i + scrollIndex];
				LevelSlotHover hover = levelSlots[i] != null ? levelSlots[i].hover : null;
				levelSlots[i] = new LevelSlot(new Vector2f(position.x + 31 * ratio, position.y - (i * 116 * ratio) + 353 * ratio), i + scrollIndex + 1, l.name, !l.available, ratio, currentLevels[i + scrollIndex]);
				levelSlots[i].level = l;
				if(hover != null) {hover.slot = levelSlots[i]; levelSlots[i].hover = hover;}
				guiElements.add(levelSlots[i].lock);
			}
		}
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		isVisible = visible;
		scroll.setVisible(visible);
		leftButton.setVisible(visible);
		rightButton.setVisible(visible);
		sectionText.isVisible = visible;
		for(int i = 0; i < levelSlots.length; i++)
		{
			if(levelSlots[i] == null) continue;
			levelSlots[i].lock.isVisible = levelSlots[i].locked && visible;
			levelSlots[i].numberText.isVisible = visible;
			levelSlots[i].name.isVisible = visible;
		}
	}
	
	@Override
	public boolean isOver(int x, int y)
	{
		if(!isVisible) return false;
		boolean ret = x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
		return ret;
	}
	
	public void addComponents(List<GuiElement> list)
	{
		for(int i = 0; i < levelSlots.length; i++) if(levelSlots[i] != null) {list.add(levelSlots[i].hover); list.add(levelSlots[i].lock);}
		scroll.addComponents(list);
		list.add(leftButton);
		list.add(rightButton);
		list.add(this);
	}
	
	private class LevelSlot
	{
		public Vector2f p;
		public GUIText numberText;
		public GUIText name;
		public GuiElement lock;
		public LevelSlotHover hover;
		public Level level;
		public boolean locked;
		
		public int lockTexture;
		
		public LevelSlot(Vector2f position, int number, String n, boolean locked, float ratio, Level level)
		{
			this.level = level;
			p = position;
			numberText = new GUIText("Level " + number, 1, font, new Vector2f(position.x + 10 * ratio, position.y), 1, false);
			name = new GUIText(n, 3, font, new Vector2f(p.x + 100 * ratio, p.y), 1, false);
			this.locked = locked;
			if(locked)
			{
				numberText.setColour(0.6F, 0.6F, 0.6F);
				name.setColour(0.6F, 0.6F, 0.6F);
			}
			lockTexture = loader.loadTexture("texture/gui/lock");
			lock = new GuiElement(lockTexture, new Vector2f(position.x + 835 * ratio, position.y - 85 * ratio), new Vector2f(64, 64), null);
			lock.isVisible = locked;
			hover = new LevelSlotHover(new Vector2f(position.x, position.y - 90 * ratio), new Vector2f(923 * ratio, 88 * ratio), this);
		}
	}
	
	private class LevelSlotHover extends GuiElement implements IClickable
	{
		private int hoverTexture;
		private float clickTimer = 0;
		private LevelSlot slot;
		
		public LevelSlotHover(Vector2f position, Vector2f size, LevelSlot slot)
		{
			super(0, position, size, LevelSelector.this.parent);
			hoverTexture = loader.loadTexture("texture/gui/hover");
			this.slot = slot;
			texture = hoverTexture;
			isVisible = false;
		}

		@Override
		public void leftClick(int mouseX, int mouseY)
		{
			if(clickTimer > 0 && clickTimer < 0.5F) 
			{
				clickTimer = 0;
				parent.requestGameStart(slot.level);
			}
			else clickTimer = 0.5F;
		}

		@Override public void leftReleased(int mouseX, int mouseY) {}
		@Override public void rightClick(int mouseX, int mouseY) {}
		@Override public void rightReleased(int mouseX, int mouseY) {}
		@Override public void entered(int mouseX, int mouseY) {isVisible = LevelSelector.this.isVisible;}
		@Override public void left(int mouseX, int mouseY) {isVisible = false;}

		@Override
		public boolean isOver(int x, int y)
		{
			return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
		}
		
		@Override
		public void update()
		{
			if(clickTimer > 0) clickTimer -= DisplayManager.getFrameTimeSeconds(); else clickTimer = 0;
		}
	}
	
	private class HandlerSwitchSection implements IClickHandler
	{
		private final int offset;
		
		public HandlerSwitchSection(int offset)
		{
			this.offset = offset;
		}
		
		@Override
		public void click(Menu parent)
		{
			sectionIndex += offset;
			if(sectionIndex >= sectionNames.length) sectionIndex = 0;
			if(sectionIndex < 0) sectionIndex = sectionNames.length - 1;
			sectionText.setText(sectionNames[sectionIndex]);
			scroll.setSlide(1);
			updateSlots(true);
		}
	}
	
	private class HandlerScrollChanged implements IClickHandler
	{
		@Override
		public void click(Menu parent)
		{
			updateSlots(false);
		}
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void leftReleased(int x, int y) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
