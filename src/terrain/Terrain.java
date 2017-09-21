package terrain;

import renderer.models.SimpleModel;
import renderer.textures.TerrainTexture;
import renderer.textures.TerrainTexturePack;

public interface Terrain
{
	public SimpleModel getModel();
	public TerrainTexturePack getTexturePack();
	public TerrainTexture getBlendMap();
	public float getHeight(float x, float z);
	public float getX();
	public float getZ();
}
