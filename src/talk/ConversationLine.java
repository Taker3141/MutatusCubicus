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
	
	public static class Option
	{
		protected String text;
		public final int id;
		public final ConversationLine next;

		public Option(String text, int id, ConversationLine next)
		{
			this.text = text;
			this.id = id;
			this.next = next;
		}
		
		public String getText()
		{
			return text;
		}
	}
}
