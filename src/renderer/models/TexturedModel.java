package renderer.models;

import renderer.textures.ModelTexture;

public class TexturedModel
{
	private SimpleModel simpleModel;
	private ModelTexture texture;
	
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
}
