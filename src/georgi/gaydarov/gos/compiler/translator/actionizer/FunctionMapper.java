package georgi.gaydarov.gos.compiler.translator.actionizer;

import java.util.HashMap;
import java.util.Map;

public class FunctionMapper {
	private static final Map<String, Short> functionMap = new HashMap<String, Short>();
	static
	{
		functionMap.put("print!", (short)100);
	}
	
	public static short map(String functionName)
	{
		return functionMap.get(functionName);
	}
}
