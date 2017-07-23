package gui.menu;

import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import font.fontMeshCreator.GUIText;
import gui.element.*;
import gui.handler.IClickHandler;

public class LevelSelector extends GuiElement implements IClickable
{
	public VerticalScrollBar scroll;
	private ArrowButton leftButton, rightButton;
	private String sectionNames[] = {"Abschnitt 1", "Abschnitt 2", "Abschnitt 3", "etc."};
	private GUIText sectionText;
	private int sectionIndex = 0;
	
	public LevelSelector(Vector2f position, Vector2f size, Menu parent)
	{
		super(loader.loadTexture("texture/gui/story_box"), position, size, parent);
		final float ratio = size.x / 1024;
		scroll = new VerticalScrollBar(new Vector2f(position.x + 977 * ratio, position.y + 15 * ratio), new Vector2f(32 * ratio, 354 * ratio), parent);
		leftButton = (ArrowButton)new ArrowButton(new Vector2f(position.x + 20, position.y + size.y - 84), new Vector2f(64, 64), ArrowButton.ButtonDirection.LEFT, parent).setClickHandler(new HandlerSwitchSection(-1));
		rightButton = (ArrowButton)new ArrowButton(new Vector2f(position.x + size.x - 84, position.y + size.y - 84), new Vector2f(64, 64), ArrowButton.ButtonDirection.RIGHT, parent).setClickHandler(new HandlerSwitchSection(1));
		sectionText = new GUIText(sectionNames[0], 2, super.font, new Vector2f(leftButton.position.x + 84, leftButton.position.y + 50), 1, false);
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
		scroll.addComponents(list);
		list.add(leftButton);
		list.add(rightButton);
		list.add(this);
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
		}
	}
	
	@Override public void leftClick(int mouseX, int mouseY) {}
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
