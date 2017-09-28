package renderer.models;

public class DummyModel extends TexturedModel
{
	public DummyModel()
	{
		super(null, null);
	}
	
	@Override
	public int compareTo(TexturedModel o)
	{
		return 0;
	}
}
