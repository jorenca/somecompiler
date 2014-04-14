package georgi.gaydarov.gos.compiler.translator.actionizer;

/**
 * Enumerates all possible instructions.
 * 
 * @author Georgi Gaydarov
 *
 */
public enum Instruction 
{
	/**
	 * Moves the value in operand B to address A.
	 */
	MOV('='),
	/**
	 * Adds the value in operand B to the value in address A.
	 */
	ADD('+'),
	/**
	 * Calls the system function, specified in A for the address B.
	 */
	CALL('\0');
	
	private char representation;
	private Instruction(char representation)
	{
		this.representation = representation;
	}
	
	public char getRepresentation()
	{
		return representation;
	}
	
	public static Instruction getByRepresentation(char representation)
	{
		for(Instruction i : Instruction.values())
		{
			if(i.getRepresentation() == representation)
			{
				return i;
			}
		}
		return null;
	}
}
