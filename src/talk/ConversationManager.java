package talk;

import renderer.DisplayManager;
import gui.overlay.OverlayCommunication;

public class ConversationManager
{
	public OverlayCommunication gui;
	protected ConversationLine currentLine;
	protected float characterCounter = 0;
	
	public ConversationManager(OverlayCommunication gui)
	{
		this.gui = gui;
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
		currentLine = currentLine.getNext(null);
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
	}
	
	public boolean isRunning()
	{
		return currentLine != null;
	}
}
