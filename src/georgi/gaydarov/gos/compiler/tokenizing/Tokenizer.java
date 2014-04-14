package georgi.gaydarov.gos.compiler.tokenizing;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Tokenizer {
	private static final String VAR_DECLARE_REGEX = "var";
	private static final String OPERATOR_REGEX = "[=+-/*]";
	private static final String LITERAL_REGEX = "\\d+";
	private static final String FUNCTION_REGEX = "[\\w]+!";
	private static final String END_OF_STATEMENT_REGEX = "[;\n]+";
	
	public static List<Token> tokenize(String code)
	{
		List<Token> tokens = new LinkedList<Token>();
		
		Scanner scan = new Scanner(code);
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			String[] rawTokens = line.split(" ");
			for(String rawToken : rawTokens)
			{
				if(rawToken.isEmpty()) continue;
				Token token = determineToken(rawToken);
				tokens.add(token);
			}
		}
		
		return tokens;
	}
	
	private static Token determineToken(String tokenString)
	{
		Token result = new Token(tokenString);
		
		if(tokenString.matches(VAR_DECLARE_REGEX))
		{
			result.setType(TokenType.VAR_DECLARATION);
		}
		else if(tokenString.matches(OPERATOR_REGEX))
		{
			result.setType(TokenType.OPERATION);
		}
		else if(tokenString.matches(LITERAL_REGEX))
		{
			result.setType(TokenType.LITERAL);
		}
		else if(tokenString.matches(FUNCTION_REGEX))
		{
			result.setType(TokenType.FUNCTION);
		}
		else if(tokenString.matches(END_OF_STATEMENT_REGEX))
		{
			result.setType(TokenType.STATEMENT_END);
		}
		else
		{
			result.setType(TokenType.VAR_NAME);
		}
		
		return result;
	}
}
