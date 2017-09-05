package talk;

import renderer.DisplayManager;
import gui.overlay.OverlayCommunication;

public class ConversationManager
{
	public OverlayCommunication gui;
	protected Conversation conversation = null;
	protected String currentLine;
	protected float characterCounter = 0;
	
	public ConversationManager(OverlayCommunication gui)
	{
		this.gui = gui;
	}
	
	public boolean startConversation(Conversation c)
	{
		if(isRunning()) return false;
		conversation = c;
		currentLine = conversation.getCurrentLine();
		gui.show();
		characterCounter = 0;
		return true;
	}
	
	public void next()
	{
		if(!isRunning()) return;
		currentLine = conversation.getNextLine();
		if(currentLine == null) 
		{
			conversation = null;
			gui.setText("", 0);
			gui.hide();
		}
		characterCounter = 0;
	}
	
	public void update()
	{
		if(!isRunning()) return;
		characterCounter += DisplayManager.getFrameTimeSeconds() * 10;
		String substring = currentLine.substring(0, (int) (characterCounter > currentLine.length() ? currentLine.length() : characterCounter));
		gui.setText(substring == null ? "" : substring, 0);
	}
	
	public boolean isRunning()
	{
		return conversation != null;
	}
	
	public static class Conversation
	{
		protected final String[] lines;
		protected int state = 0;

		public Conversation(String[] lines)
		{
			this.lines = lines;
		}
		
		public String getCurrentLine()
		{
			return lines[state];
		}
		
		public String getNextLine()
		{
			state++;
			return state < lines.length ? lines[state] : null;
		}
	}
}
