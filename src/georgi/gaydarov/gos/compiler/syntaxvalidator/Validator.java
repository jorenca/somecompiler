package georgi.gaydarov.gos.compiler.syntaxvalidator;

import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.TokenType;
import georgi.gaydarov.gos.compiler.translator.RawStatement;

import java.util.LinkedList;
import java.util.List;

public class Validator {
	private static final String DECLARATION_PATTERN = "DN";
	private static final String DIRECT_ASSIGNMENT_PATTERN = "NO[NL]";
	private static final String SIMPLE_ASSIGNMENT_PATTERN = "NO[NL]O[NL]";
	private static final String COMPLEX_ASSIGNMENT_PATTERN = "NO([NL]O){2,}[NL]";
	private static final String FUNCTION_CALL_PATTERN = "F[NL]";
	
	public static RawStatementType validateStatement(RawStatement statement)
	{
		String statementRepresentation = statement.getSimpleRepresentation();
		if(statementRepresentation.matches(DECLARATION_PATTERN))
		{
			return RawStatementType.DECLARATION;
		}
		else if(statementRepresentation.matches(DIRECT_ASSIGNMENT_PATTERN))
		{
			return RawStatementType.DIRECT_ASSIGNMENT;
		}
		else if(statementRepresentation.matches(SIMPLE_ASSIGNMENT_PATTERN))
		{
			return RawStatementType.SIMPLE_ASSIGNMENT;
		}
		else if(statementRepresentation.matches(COMPLEX_ASSIGNMENT_PATTERN))
		{
			return RawStatementType.COMPLEX_ASSIGNMENT;
		}
		else if(statementRepresentation.matches(FUNCTION_CALL_PATTERN))
		{
			return RawStatementType.FUNCTION_CALL;
		}
		else
		{
			String fullRepresentation = statement.getFullRepresentation();
			throw new ValidationException("Could not determine statement type. Statement is : ["+fullRepresentation+"]");
		}
	}

}
