package georgi.gaydarov.gos.compiler.translator.actionizer;

import java.util.HashMap;
import java.util.Map;

public class MemoryMapper {
	private final static int MEMORY_SIZE = 16000; // bytes
	private Map<String, Short> variableAddress = new HashMap<String, Short>();
	public MemoryMapper(short startAddress)
	{
		// TODO implement
	}
	
	public void freeVariable(String variableName)
	{
		// TODO IMPLEMENT var free
	}
	public void allocateVariable(String variableName)
	{
		if(variableAddress.containsKey(variableName))
		{
			throw new MemoryMappingException("Variable named ["+variableName+"] already declared!");
		}
		
		// TODO alloc
	}
	public short getVariableAddress(String variableName)
	{
		if(!variableAddress.containsKey(variableName))
		{
			throw new MemoryMappingException("Variable named ["+variableName+"] was not declared!");
		}
		
		return variableAddress.get(variableName);
	}
}
