import georgi.gaydarov.gos.compiler.coder.Encoder;
import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.Tokenizer;
import georgi.gaydarov.gos.compiler.translator.Translator;
import georgi.gaydarov.gos.compiler.translator.actionizer.Operand;
import georgi.gaydarov.gos.compiler.translator.actionizer.Operation;
import georgi.gaydarov.gos.compiler.translator.validator.Validator;

import java.io.IOException;
import java.util.List;


public class Compiler {

	public static void main(String[] args) throws IOException {
		Compiler comp = new Compiler();
		
		String code = "cvar a ; \n a = 5 ;\n var b ; \n b = 3 ;\n var c ; c = a + b ; c + = 2 ; print! c ;";
		comp.compile(code);
		
		String code2 = "cvar x ; x = 9 ; x = x ; x + = 10 ; x + = x ; print! x ;";
		//comp.compile(code2);
	}
	
	
	
	public void compile(String code) throws IOException
	{
		List<Token> tokens = Tokenizer.tokenize(code);
		//for(Token t : tokens)
		//{
		//	System.out.println(t.getValue() + " : "+t.getType());
		//}
		
		List<Operation> operations = Translator.translateTokens(tokens);
		System.out.println("Operations are :");
		for(Operation o : operations)
		{
			System.out.println(o.getReadable());
		}
		
		Encoder encoder = new Encoder("out.gex");
		encoder.encode(operations);
		encoder.close();
		
	}
}
