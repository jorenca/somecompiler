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
	MOV,
	/**
	 * Adds the value in operand B to the value in address A.
	 */
	ADD;
}
