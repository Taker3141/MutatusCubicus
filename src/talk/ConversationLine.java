package talk;

public class ConversationLine
{
	protected String message;
	protected Option[] options = null;
	protected Option chosenOption;
	protected ConversationLine next;
	
	public ConversationLine(String message)
	{
		this.message = message;
	}
	
	public static ConversationLine fromStringArray(String... textLines)
	{
		ConversationLine[] lines = new ConversationLine[textLines.length];
		for(int i = 0; i < lines.length; i++)
		{
			lines[i] = new ConversationLine(textLines[i]);
			if(i != 0) lines[i - 1].next = lines[i];
		}
		return lines[0];
	}
	
	public ConversationLine getNext(Option o)
	{
		chosenOption = o;
		if(chosenOption != null) 
		{
			return o.next;
		}
		return next;
	}
	
	public String getText()
	{
		return message;
	}
	
	public boolean hasOptions()
	{
		return options != null;
	}
	
	public Option[] getOptions()
	{
		return options;
	}
	
	public void setOptions(Option[] o)
	{
		options = o;
	}
	
	public static class Option
	{
		protected String text;
		public final ConversationLine next;

		public Option(String text, ConversationLine next)
		{
			this.text = text;
			this.next = next;
		}
		
		public String getText()
		{
			return text;
		}
	}
}
