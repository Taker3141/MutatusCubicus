package terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.util.vector.Vector3f;
import renderer.models.SimpleModel;
import renderer.textures.TerrainTexture;
import renderer.textures.TerrainTexturePack;

public interface Terrain
{
	public static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
	
	public SimpleModel getModel();
	public TerrainTexturePack getTexturePack();
	public TerrainTexture getBlendMap();
	public float getHeight(float x, float z);
	public Vector3f getPosition();
	
	public static float[][] readHeightMap(String heightMap, float maxHeight, boolean swapImageCoordinates)
	{
		float heights[][];
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File("res/texture/" + heightMap + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		heights = new float[image.getHeight()][image.getWidth()];
		for(int i = 0; i < image.getHeight(); i++)
		{
			for(int j = 0; j < image.getWidth(); j++)
			{
				heights[i][j] = getHeight(swapImageCoordinates ? j : i, swapImageCoordinates ? i : j, image, maxHeight);
			}
		}
		return heights;
	}
	
	public static float getHeight(int x, int y, BufferedImage image, float maxHeight)
	{
		if(x < 0 || y < 0 || x > image.getHeight() - 1 || y > image.getWidth() - 1) return 0;
		float height = image.getRGB(x, y);
		height /= MAX_PIXEL_COLOR / 2;
		height *= maxHeight;
		height += maxHeight * 2;
		return height;
	}
}
