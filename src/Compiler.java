import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.Tokenizer;
import georgi.gaydarov.gos.compiler.translator.Translator;
import georgi.gaydarov.gos.compiler.translator.actionizer.Operands;
import georgi.gaydarov.gos.compiler.translator.actionizer.Operation;
import georgi.gaydarov.gos.compiler.translator.validator.Validator;

import java.util.List;


public class Compiler {

	public static void main(String[] args) {
		Compiler comp = new Compiler();
		
		String code = "var a ; \n a = 5 ;\n var b ; \n b = 3 ;\n var c ; c = a + b ; c + = 2 ; print! c ;";
		comp.compile(code);
		
		String code2 = "var x ; x = 9 ; x = x ; x + = 10 ; x + = x ; print! x ;";
		comp.compile(code2);
	}
	
	
	
	public void compile(String code)
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
			String instruction = String.format("%s[%s] : ", o.getInstruction().toString(), o.getOperands().toString());
			
			if(o.getOperands() == Operands.AA)
			{
				String operands = String.format("addr(%d), addr(%d)", o.getAddress1(), o.getAddress2());
				instruction += operands;
			}
			else
			{
				String operands = String.format("addr(%d), literal(%d)", o.getAddress1(), o.getLiteral());
				instruction += operands;
			}
			System.out.println(instruction);
		}
		
	}
}
