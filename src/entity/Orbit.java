package entity;

import org.lwjgl.util.vector.Vector3f;

public class Orbit
{
	private int vaoID;
	private int vertexCount;
	public boolean visible = true;
	public Vector3f[] points;
	public Vector3f color = new Vector3f(1, 1, 1);
	
	public Orbit(int vao, int vertex)
	{
		vaoID = vao;
		vertexCount = vertex;
	}
	
	public int getVaoID()
	{
		return vaoID;
	}
	
	public int getVertexCount()
	{
		return vertexCount;
	}
}
