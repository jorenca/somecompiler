import georgi.gaydarov.gos.compiler.syntaxvalidator.Validator;
import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.Tokenizer;
import georgi.gaydarov.gos.compiler.translator.Translator;
import georgi.gaydarov.gos.compiler.translator.actionizer.Action;

import java.util.List;


public class Compiler {

	public static void main(String[] args) {
		Compiler comp = new Compiler();
		
		String code = "var a ; \n a = 5 ;\n var b ; \n b = 3 ;\n var c ; c = a + b ; \n print! c ;";
		comp.compile(code);
	}
	
	
	
	public void compile(String code)
	{
		List<Token> tokens = Tokenizer.tokenize(code);
		for(Token t : tokens)
		{
			System.out.println(t.getValue() + " : "+t.getType());
		}
		
		List<Action> actions = Translator.translateTokens(tokens);
	}
}
