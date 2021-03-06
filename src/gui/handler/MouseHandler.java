package gui.handler;

import gui.element.GuiElement;
import gui.element.IClickable;
import gui.overlay.Overlay;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class MouseHandler implements MouseListener
{
	private List<IClickable> elements;
	
	public MouseHandler(List<GuiElement> list)
	{
		elements = new ArrayList<IClickable>();
		for(GuiElement e : list)
		{
			if(e instanceof IClickable)
			{
				elements.add((IClickable)e);
			}
		}
	}
	
	public MouseHandler(List<Overlay> list, boolean b)
	{
		elements = new ArrayList<IClickable>();
		addOverlays(list);
	}
	
	public void updateList(List<Overlay> list)
	{
		elements = new ArrayList<IClickable>();
		addOverlays(list);
	}
	
	private void addOverlays(List<Overlay> list)
	{
		for(Overlay e : list)
		{
			elements.add((IClickable)e);
			for(GuiElement subElement : e.getElements()) if(subElement instanceof IClickable)
			{
				elements.add((IClickable)subElement);
			}
		}
	}
	
	@Override public void inputEnded() {}
	@Override public void inputStarted() {}
	@Override public boolean isAcceptingInput() { return true; }
	@Override public void setInput(Input arg0) {}
	@Override public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void mouseDragged(int oldX, int oldY, int newX, int newY) 
	{
		mouseMoved(oldX, oldY, newX, newY);
	}

	@Override
	public void mouseMoved(int oldX, int oldY, int newX, int newY)
	{
		for(IClickable e : elements)
		{
			if(!e.isOver(oldX, Display.getHeight() - oldY) && e.isOver(newX, Display.getHeight() - newY)) e.entered(newX, newY);
			if(e.isOver(oldX, Display.getHeight() - oldY) && !e.isOver(newX, Display.getHeight() - newY)) e.left(newX, newY);
		}
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		for(IClickable e : elements)
		{
			if(e.isOver(x, Display.getHeight() - y))
			{
				if(button == 0) e.leftClick(x, y);
				if(button == 1) e.rightClick(x, y);
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		for(IClickable e : elements)
		{
			if(e.isOver(x, Display.getHeight() - y))
			{
				if(button == 0) e.leftReleased(x, y);
				if(button == 1) e.rightReleased(x, y);
			}
		}
	}

	@Override
	public void mouseWheelMoved(int arg0)
	{
		
	}
}
