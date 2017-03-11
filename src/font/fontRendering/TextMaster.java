package font.fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import font.fontMeshCreator.FontType;
import font.fontMeshCreator.GUIText;
import font.fontMeshCreator.TextMeshData;
import renderer.Loader;

public class TextMaster
{
	private static Loader loader;
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static FontRenderer renderer;
	
	public static void init(Loader l)
	{
		renderer = new FontRenderer();
		loader = l;
	}
	
	public static void render()
	{
		renderer.render(texts);
	}
	
	public static void loadText(GUIText text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public static void removeText(GUIText text)
	{
		if (text != null)
		{
			List<GUIText> textBatch = texts.get(text.getFont());
			textBatch.remove(text);
			if (textBatch.isEmpty())
			{
				texts.remove(text.getFont());
			}
		}
	}
	
	public static void cleanUp()
	{
		renderer.cleanUp();
		texts.clear();
	}
	
	public static void clear()
	{
		texts.clear();
	}
}
