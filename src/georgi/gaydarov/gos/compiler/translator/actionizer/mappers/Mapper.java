package georgi.gaydarov.gos.compiler.translator.actionizer.mappers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for mapping variable names to addresses.
 * 
 * @author Georgi Gaydarov
 *
 */
public abstract class Mapper {
	private final int MEMORY_SIZE; // bytes
	private Map<String, Short> variableAddress = new HashMap<String, Short>();
	private List<Short> freedAddresses = new LinkedList<Short>();
	private short nextFreePointer;
	
	/**
	 * Constructs a memory mapper that starts mapping at a specified address.
	 * @param startAddress - the start address.
	 */
	protected Mapper(short startAddress)
	{
		MEMORY_SIZE = getMemorySize();
		nextFreePointer = startAddress;
		// TODO implement more
	}
	
	protected abstract int getMemorySize();
	
	/**
	 * Sets the memory of a variable as free and available for further allocations.
	 * @param variableName - the variable to free.
	 */
	public void freeVariable(String variableName)
	{
		// TODO IMPLEMENT var free
	}
	
	/**
	 * Allocates memory for a variable.
	 * @param variableName - the name of the variable for which memory should be allocated.
	 */
	public void allocateVariable(String variableName)
	{
		if(variableAddress.containsKey(variableName))
		{
			throw new MappingException("Variable named ["+variableName+"] already declared!");
		}
		
		if(freedAddresses.isEmpty())
		{
			if(nextFreePointer < MEMORY_SIZE)
			{
				variableAddress.put(variableName, nextFreePointer);
				nextFreePointer++;
			}
			else
			{
				throw new MappingException("Out of memory!");
			}
		}
		else
		{
			// TODO implement alloc from free
		}
	}
	
	/**
	 * Returns the memory address of a variable.
	 * @param variableName - the variable name.
	 * @return the address where the variable is stored in memory.
	 */
	public short getVariableAddress(String variableName)
	{
		if(!variableAddress.containsKey(variableName))
		{
			throw new MappingException("Variable named ["+variableName+"] was not declared!");
		}
		
		return variableAddress.get(variableName);
	}
	
	/**
	 * Checks if a variable has been declared via this mapper.
	 * 
	 * @param variableName - the variable name to be checked.
	 * @return true if such variable has been declared, false otherwise.
	 */
	public boolean isVariableDeclared(String variableName)
	{
		return variableAddress.containsKey(variableName);
	}
}
