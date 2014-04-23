package georgi.gaydarov.gos.compiler.translator.actionizer;

/**
 * Describes the instruction operand types.
 * 
 * @author Georgi Gaydarov
 *
 */
public enum Operand
{
	/**
	 * Address in the memory.
	 */
	A,
	/**
	 * Address in the cache.
	 */
	C,
	/**
	 * Literal value.
	 */
	L;
}
