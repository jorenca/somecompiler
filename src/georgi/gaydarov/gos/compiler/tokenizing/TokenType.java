package georgi.gaydarov.gos.compiler.tokenizing;

public enum TokenType {
	MEMORY_VAR_DECLARATION('D'),
	CACHE_VAR_DECLARATION('D'),
	VAR_NAME('N'),
	LITERAL('L'),
	OPERATION('O'),
	FUNCTION('F'),
	STATEMENT_END('E');
	
	private char representation;
	private TokenType(char representation)
	{
		this.representation = representation;
	}
	
	public char getRepresentation()
	{
		return representation;
	}
}
