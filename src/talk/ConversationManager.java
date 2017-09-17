package talk;

import org.lwjgl.opengl.Display;
import renderer.DisplayManager;
import world.World;
import gui.overlay.OverlayCommunication;

public class ConversationManager
{
	public OverlayCommunication gui;
	protected ConversationLine currentLine;
	protected float characterCounter = 0;
	protected World w;
	
	public ConversationManager(OverlayCommunication gui, World world)
	{
		this.gui = gui;
		w = world;
	}
	
	public boolean startConversation(ConversationLine startLine)
	{
		if(isRunning()) return false;
		currentLine = startLine;
		gui.show();
		characterCounter = 0;
		return true;
	}
	
	public void next()
	{
		if(!isRunning()) return;
		if(currentLine.options == null) currentLine = currentLine.getNext(null);
		else currentLine = currentLine.getNext(currentLine.getOptions()[gui.getSelectedIndex()]);
		gui.reset();
		if(currentLine == null) 
		{
			gui.setText("", 0);
			gui.hide();
		}
		characterCounter = 0;
	}
	
	public void update()
	{
		if(!isRunning()) return;
		characterCounter += DisplayManager.getFrameTimeSeconds() * 10;
		int length = currentLine.getText().length();
		String substring = currentLine.getText().substring(0, (int)(characterCounter > length ? length : characterCounter));
		gui.setText(substring == null ? "" : substring, 0);
		gui.setPictureTexture(currentLine.getCharacter().getFaceTexture());
		if(currentLine.getOptions() != null && characterCounter > length)
		{
			for(int i = 0; i < currentLine.getOptions().length; i++)
			{
				gui.setText("> " + currentLine.getOptions()[i].getText(), i + 1);
			}
		}
		
		gui.activateInfo(w.input.getMouseX(), Display.getHeight() - w.input.getMouseY(), currentLine.getCharacter());
	}
	
	public boolean isRunning()
	{
		return currentLine != null;
	}
}
