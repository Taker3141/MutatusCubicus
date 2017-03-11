package gui.element;

import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class Icon extends GuiElement
{
	public static enum Size
	{
		x16,
		x32,
		x64;
		
		public int size()
		{
			if(this == x16) return 16;
			if(this == x32) return 32;
			if(this == x64) return 64;
			return 0;
		}
	}
	
	public final Size s;	
	
	public Icon(int texture, Vector2f position, Size s, Menu parent)
	{
		super(texture, position, new Vector2f(s.size(), s.size()), parent);
		this.s = s;
	}
}
