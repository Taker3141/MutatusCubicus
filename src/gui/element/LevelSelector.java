package gui.element;

import java.util.List;
import level.LevelManager;
import org.lwjgl.util.vector.Vector2f;
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
	private LevelManager levelManager = new LevelManager();
	private LevelSlot[] levelSlots = new LevelSlot[3];
	
	public LevelSelector(Vector2f position, Vector2f size, Menu parent)
	{
		super(loader.loadTexture("texture/gui/story_box"), position, size, parent);
		final float ratio = size.x / 1024;
		LevelManager.Section[] sections = levelManager.sections.toArray(new LevelManager.Section[0]);
		sectionNames = new String[sections.length];
		for(int i = 0; i < sections.length; i++) sectionNames[i] = sections[i].name;
		LevelSlot.init();
		for(int i = 0; i < levelSlots.length; i++) levelSlots[i] = new LevelSlot(new Vector2f(position.x + 40 * ratio, position.y - (i * 116 * ratio) + 353 * ratio), i + 1, "Ein Level", i != 1, ratio);
		scroll = new VerticalScrollBar(new Vector2f(position.x + 977 * ratio, position.y + 15 * ratio), new Vector2f(32 * ratio, 354 * ratio), parent);
		scroll.setSlide(1);
		leftButton = (ArrowButton)new ArrowButton(new Vector2f(position.x + 20, position.y + size.y - 84), new Vector2f(64, 64), ArrowButton.ButtonDirection.LEFT, parent).setClickHandler(new HandlerSwitchSection(-1));
		rightButton = (ArrowButton)new ArrowButton(new Vector2f(position.x + size.x - 84, position.y + size.y - 84), new Vector2f(64, 64), ArrowButton.ButtonDirection.RIGHT, parent).setClickHandler(new HandlerSwitchSection(1));
		sectionText = new GUIText(sectionNames[0], 2, font, new Vector2f(leftButton.position.x + 84, leftButton.position.y + 50), 1, false);
	}
	
	@Override
	public void leftReleased(int x, int y)
	{
		
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
		for(int i = 0; i < levelSlots.length; i++) list.add(levelSlots[i].lock);
		scroll.addComponents(list);
		list.add(leftButton);
		list.add(rightButton);
		list.add(this);
	}
	
	private static class LevelSlot
	{
		public Vector2f position;
		public GUIText numberText;
		public GUIText name;
		public GuiElement lock;
		public boolean locked;
		
		public static int lockTexture;
		
		public LevelSlot(Vector2f position, int number, String n, boolean locked, float ratio)
		{
			this.position = position;
			numberText = new GUIText("Level " + number, 1, font, position, 1, false);
			numberText.isVisible = false;
			name = new GUIText(n, 3, font, new Vector2f(position.x + 100 * ratio, position.y), 1, false);
			name.isVisible = false;
			this.locked = locked;
			lock = new GuiElement(lockTexture, new Vector2f(position.x + 835 * ratio, position.y - 85 * ratio), new Vector2f(64, 64), null);
			lock.isVisible = locked;
		}
		
		public static void init()
		{
			lockTexture = loader.loadTexture("texture/gui/lock");
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
		}
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
