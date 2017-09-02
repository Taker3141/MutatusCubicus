package level;

import org.lwjgl.util.vector.*;

abstract class FunctionArgument
{
	public static FunctionArgument match(String s)
	{
		String[] fragments = s.split(" ");
		if(fragments.length == 1)
		{
			try {Float.parseFloat(fragments[0]); return new NumberArgument();}
			catch(Exception e) {}
		}
		if(fragments.length > 1)
		{
			try 
			{
				for(int i = 0; i < fragments.length; i++) Float.parseFloat(fragments[i]);
				return new VectorArgument();
			}
			catch(Exception e) {}
		}
		return new StringArgument();
	}
	
	public abstract void load(String s);
	public abstract Object getData();
	
	static class StringArgument extends FunctionArgument
	{
		private String data;
		
		@Override
		public void load(String s)
		{
			data = s;
		}

		@Override
		public Object getData()
		{
			return data;
		}
	}
	
	static class NumberArgument extends FunctionArgument
	{
		private float data;
		
		@Override
		public void load(String s)
		{
			data = Float.parseFloat(s);
		}

		@Override
		public Object getData()
		{
			return data;
		}
	}
	
	static class VectorArgument extends FunctionArgument
	{
		private float[] data;
		
		@Override
		public void load(String s)
		{
			String[] numbers = s.split(" ");
			data = new float[numbers.length];
			for(int i = 0; i < numbers.length; i++) data[i] = Float.parseFloat(numbers[i]);
		}

		@Override
		public Object getData()
		{
			switch(data.length)
			{
				case 2: return new Vector2f(data[0], data[1]);
				case 3: return new Vector3f(data[0], data[1], data[2]);
				case 4: return new Vector4f(data[0], data[1], data[2], data[3]);
				default: return null;
			}
		}
	}
}
