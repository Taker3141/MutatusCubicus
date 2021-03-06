package gui.element;

import java.util.List;
import gui.handler.IClickHandler;
import gui.menu.Menu;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class VerticalScrollBar extends GuiElement implements IClickable
{
	protected final int backgroundTexture = loader.loadTexture("texture/gui/scroll_bar_background");
	protected final int sliderTexture = loader.loadTexture("texture/gui/scroll_bar_slider");
	
	protected GuiElement slider;
	protected boolean mouseDown = false;
	public IClickHandler handler;
	
	public VerticalScrollBar(Vector2f position, Vector2f size, Menu parent)
	{
		super(0, position, size, parent);
		texture = backgroundTexture;
		slider = new GuiElement(sliderTexture, new Vector2f(position), new Vector2f(size.x, (size.y / 4) * 0.6875F), parent);
	}
	
	public void addComponents(List<GuiElement> list)
	{
		list.add(slider);
		list.add(this);
	}
	
	public float getSlide()
	{
		final float maxDistance = size.y - slider.size.y;
		final float distance = slider.position.y - position.y;
		return distance / maxDistance;
	}
	
	public void setSlide(float slide)
	{
		slide = slide > 1 ? 1 : slide < 0 ? 0 : slide;
		final float maxDistance = size.y - slider.size.y;
		final float distance = slide * maxDistance;
		slider.position.y = position.y + distance;
	}
	
	@Override 
	public void leftClick(int x, int y) 
	{
		mouseDown = true;
	}
	
	@Override
	public void leftReleased(int x, int y)
	{
		mouseDown = false;
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		isVisible = visible;
		slider.setVisible(visible);
	}
	
	@Override
	public boolean isOver(int x, int y)
	{
		if(!isVisible) return false;
		boolean ret = x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;

		if (mouseDown)
		{
			ret = true;
			y = Display.getHeight() - y;
			float newY = Display.getHeight() - y - slider.size.y / 2;
			if (newY < position.y) newY = position.y;
			if (newY + slider.size.y > position.y + size.y) newY = position.y + size.y - slider.size.y;
			slider.position.y = newY;
			if(handler != null) handler.click(parent);
		}
		return ret;
	}
	
	@Override public void rightClick(int mouseX, int mouseY) {}
	@Override public void rightReleased(int mouseX, int mouseY) {}
	@Override public void entered(int mouseX, int mouseY) {}
	@Override public void left(int mouseX, int mouseY) {}
}
