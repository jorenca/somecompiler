package georgi.gaydarov.gos.compiler.translator.actionizer;

import java.util.HashMap;
import java.util.Map;

public class FunctionMapper {
	private static final Map<String, Short> functionMap = new HashMap<String, Short>();
	static
	{
		// FIXME set these values to real values when the OS is implemented.!
		functionMap.put("print!", (short)100);
		functionMap.put("read!", (short)101);
	}
	
	public static short map(String functionName)
	{
		if(!functionMap.containsKey(functionName))
		{
			throw new ActionizingException("No known function ["+functionName+"].");
		}
		return functionMap.get(functionName);
	}
}
