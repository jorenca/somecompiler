package georgi.gaydarov.gos.compiler.translator.actionizer.mappers;

import georgi.gaydarov.gos.compiler.translator.actionizer.ActionizingException;

import java.util.HashMap;
import java.util.Map;

public class FunctionMapper {
	private static final Map<String, Byte> functionMap = new HashMap<String, Byte>();
	static
	{
		// FIXME set these values to real values when the OS is implemented.!
		functionMap.put("print!", (byte)100);
		functionMap.put("read!", (byte)101);
	}
	
	public static byte map(String functionName)
	{
		if(!functionMap.containsKey(functionName))
		{
			throw new ActionizingException("No known function ["+functionName+"].");
		}
		return functionMap.get(functionName);
	}
}
