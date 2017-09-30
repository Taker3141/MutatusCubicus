package renderer.models;

import renderer.textures.ModelTexture;

public class TexturedModel implements IModel, Comparable<TexturedModel>
{
	private SimpleModel simpleModel;
	private ModelTexture texture;
	public int transparencyNumber = 0;
	
	public TexturedModel(SimpleModel model, ModelTexture tex)
	{
		simpleModel = model;
		texture = tex;
	}

	public SimpleModel getModel()
	{
		return simpleModel;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}

	@Override
	public int compareTo(TexturedModel o)
	{
		return Integer.compare(transparencyNumber, o.transparencyNumber);
	}
}
