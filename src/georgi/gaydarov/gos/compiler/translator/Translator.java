package georgi.gaydarov.gos.compiler.translator;

import georgi.gaydarov.gos.compiler.syntaxvalidator.RawStatementType;
import georgi.gaydarov.gos.compiler.syntaxvalidator.ValidationException;
import georgi.gaydarov.gos.compiler.syntaxvalidator.Validator;
import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.TokenType;
import georgi.gaydarov.gos.compiler.translator.actionizer.Action;
import georgi.gaydarov.gos.compiler.translator.actionizer.Actionizer;

import java.util.LinkedList;
import java.util.List;

public class Translator {
	public static List<Action> translateTokens(List<Token> tokens) {
		List<Action> result = new LinkedList<Action>();
		
		RawStatement rawStatement = new RawStatement();
		for(Token token : tokens)
		{
			if(token.getType() != TokenType.STATEMENT_END)
			{
				rawStatement.add(token);
			}
			else
			{
				if(rawStatement.isEmpty()) continue;
				List<Action> translatedStatement = translateStatement(rawStatement);
				result.addAll(translatedStatement);
				rawStatement.clear();
			}
		}
		
		if(rawStatement.size() > 0)
		{
			throw new ValidationException("Unexpected end of file.");
		}
		
		return result;
	}
	
	private static List<Action> translateStatement(RawStatement statement)
	{
		List<Action> result = new LinkedList<Action>();
		
		RawStatementType statementType = Validator.validateStatement(statement);
		
		if(statementType != RawStatementType.COMPLEX_ASSIGNMENT){
			result.addAll(determineActions(statementType, statement));
		}
		else
		{
			List<RawStatement> simpleStatements = ComplexStatementDecomposer.decompose(statement);
			for(RawStatement simpleStatement : simpleStatements)
			{
				result.addAll(translateStatement(simpleStatement));
			}
		}
		
		
		return result;
	}
	
	private static List<Action> determineActions(RawStatementType type, RawStatement statement)
	{
		return Actionizer.simplify(type, statement);
	}
}
