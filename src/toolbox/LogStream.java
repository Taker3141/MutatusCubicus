package toolbox;

import java.io.PrintStream;

public class LogStream extends PrintStream
{
	public LogStream()
	{
		super(System.out);
	}
	
	@Override
	public void println(String s)
	{
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		String[] className = e.getClassName().split("\\.");
		super.println("[" + className[className.length - 1] + "] says: " + s);
	}
}
