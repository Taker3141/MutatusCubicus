package level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import entity.Entity;
import world.World;

public class LevelParser
{
	public static void parseLevelFile(File levelFile, World w)
	{
		List<Line> readLines = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(levelFile)))
		{
			boolean parsing = false;
			for(String line = reader.readLine(); line != null; line = reader.readLine())
			{
				line = line.trim();
				if(line.startsWith("#")) continue;
				if(line.startsWith("init") && !parsing) 
				{
					parsing = true; continue;
				}
				if(parsing)
				{
					if(line.startsWith("{") || line.length() <= 0) continue;
					if(line.startsWith("}")) break;
					readLines.add(new Line(line));
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Unable to load level file \'" + levelFile + "\'");
			e.printStackTrace();
		}
		for(Line l : readLines)
		{
			try
			{
				l.analyzeArguments();
				l.execute(w);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private static class Line
	{
		public final String functionName;
		public final String[] argumentStrings;
		public final Object[] arguments;
		
		public Line(String line)
		{
			String[] parts = line.split(" ", 2);
			functionName = parts[0];
			String[] fragments = parts[1].split(",");
			List<String> args = new ArrayList<>();
			boolean glue = false;
			for(int i = 0; i < fragments.length; i++)
			{
				if(fragments[i].trim().startsWith("\"") && !fragments[i].trim().endsWith("\"")) 
				{
					glue = true;
					args.add(fragments[i].trim());
					continue;
				}
				if(!glue) args.add(fragments[i].trim());
				else args.set(args.size() - 1, args.get(args.size() - 1) + fragments[i]);
				if(fragments[i].trim().endsWith("\"")) glue = false;
			}
			argumentStrings = new String[args.size()];
			for(int i = 0; i < args.size(); i++)
			{
				if(args.get(i).startsWith("\"")) argumentStrings[i] = args.get(i).substring(1, args.get(i).length() - 1);
				else argumentStrings[i] = args.get(i);
			}
			arguments = new Object[argumentStrings.length];
		}
		
		public void analyzeArguments()
		{
			for(int i = 0; i < arguments.length; i++)
			{
				FunctionArgument a = FunctionArgument.match(argumentStrings[i]);
				a.load(argumentStrings[i]);
				arguments[i] = a.getData();
			}
		}
		
		public void execute(World w) throws UnknownFunctionException, EntityInstanciationException, ClassNotFoundException
		{
			switch(functionName)
			{
				case "Player": 
					w.player.position = w.hVector(((Vector2f)arguments[0]).x, ((Vector2f)arguments[0]).y);
					w.player.rotX = ((Vector3f)arguments[1]).x;
					w.player.rotY = ((Vector3f)arguments[1]).y;
					w.player.rotZ = ((Vector3f)arguments[1]).z;
					return;
				case "Entity":
					new Entity(World.createModel((String)arguments[0], (String)arguments[1], 0), 
							w.hVector(((Vector2f)arguments[2]).x, ((Vector2f)arguments[2]).y),
							((Vector3f)arguments[3]).x, ((Vector3f)arguments[3]).y, ((Vector3f)arguments[3]).z, 
							(float)arguments[4],
							w.entities);
					return;
				case "Decoration":
					w.generateDecoration(World.createModel((String)arguments[0], (String)arguments[1], 0),
							(int)(float)arguments[4],
							((Vector2f)arguments[2]).x, ((Vector2f)arguments[2]).y,
							((Vector2f)arguments[5]).x, ((Vector2f)arguments[5]).x, 
							(float)arguments[3], false);
					return;
				case "SpecialEntity":
					String entityName = (String)arguments[0];
					Class<?> targetClass = Class.forName("entity." + entityName);
					Constructor<?>[] constructors = targetClass.getConstructors();
					constructorLoop:
					for(Constructor<?> c : constructors)
					{
						Parameter[] parameters = c.getParameters();
						for(int i = 0; i < parameters.length - 1; i++)
						{
							Class<?> cType = parameters[i].getType();
							Class<?> pType = arguments[i + 1].getClass();
							if(cType.isPrimitive()) cType = Array.get(Array.newInstance(cType, 1), 0).getClass();
							if(cType != pType) continue constructorLoop;
						}
						try
						{
							Object[] instanceParameters = new Object[arguments.length];
							for(int i = 0; i < instanceParameters.length; i++)
							{
								if(i < instanceParameters.length - 1) instanceParameters[i] = arguments[i + 1];
								else instanceParameters[i] = w.entities;
							}
							c.newInstance(instanceParameters);
							return;
						}
						catch (Exception e) {throw new EntityInstanciationException(entityName);}
					}
					throw new EntityInstanciationException(entityName);
				default: throw new UnknownFunctionException(functionName);
			}
		}
	}
	
	private static class UnknownFunctionException extends Exception
	{
		private static final long serialVersionUID = 4996330661325004968L;

		public UnknownFunctionException(String function)
		{
			super("Unknown function: " + function);
		}
	}
	
	private static class EntityInstanciationException extends Exception
	{
		private static final long serialVersionUID = -8363620836452481998L;

		public EntityInstanciationException(String entityName)
		{
			super("Unable to instanciate entity: " + entityName);
		}
	}
}
