package georgi.gaydarov.gos.compiler.translator;

import georgi.gaydarov.gos.compiler.tokenizing.Token;

import java.util.LinkedList;

/**
 * Represents a sequence of tokens that make a statement.
 * 
 * @author Georgi Gaydarov
 *
 */
public class RawStatement extends LinkedList<Token>{

	/**
	 * auto-gen serialization id
	 */
	private static final long serialVersionUID = -1268517468866751216L;

	public String getSimpleRepresentation()
	{
		StringBuilder builder = new StringBuilder();
		for(Token token : this)
		{
			builder.append(token.getType().getRepresentation());
		}
		
		return builder.toString();
	}
	
	public String getFullRepresentation()
	{
		StringBuilder fullStatementBuilder = new StringBuilder();
		for(Token token : this)
		{
			fullStatementBuilder.append(token.getType());
			fullStatementBuilder.append(' ');
		}
		return fullStatementBuilder.toString();
	}
	
	/**
	 * @return the full statement soruce code.
	 */
	public String getFullStatement()
	{
		StringBuilder fullStatementBuilder = new StringBuilder();
		for(Token token : this)
		{
			fullStatementBuilder.append(token.getValue());
			fullStatementBuilder.append(' ');
		}
		return fullStatementBuilder.toString();
	}
}
