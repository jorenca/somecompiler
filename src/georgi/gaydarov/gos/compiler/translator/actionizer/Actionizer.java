package georgi.gaydarov.gos.compiler.translator.actionizer;

import java.util.LinkedList;
import java.util.List;

import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.TokenType;
import georgi.gaydarov.gos.compiler.translator.RawStatement;
import georgi.gaydarov.gos.compiler.translator.RawStatementType;

public class Actionizer {
	private static final MemoryMapper mapper = new MemoryMapper((short)0);
	
	
	
	public static List<Operation> simplify(RawStatementType statementType, RawStatement statement)
	{
		List<Operation> result = new LinkedList<Operation>();
		
		switch(statementType)
		{
			case DECLARATION:
				simplifyAllocation(statement);
				break;

			case SIMPLE_OPERATION:
				result.add(simplifySimpleOperation(statement));
				break;
				
			case DIRECT_ASSIGNMENT:
				result.add(simplifyDirectAssignment(statement));
				break;
				
			case SIMPLE_ASSIGNMENT:
				result.addAll(simplifySimpleAssignment(statement));
				break;
				
			case FUNCTION_CALL:
				result.add(simplifyFunctionInvocation(statement));
				break;
				
				// TODO add more cases here
		}
		
		return result;
	}
	
	private static void simplifyAllocation(RawStatement statement)
	{
		// <var_keyword> <name_name>
		Token nameToken = statement.get(1);
		String varName = nameToken.getValue();
		mapper.allocateVariable(varName);
	}
	
	private static Operation simplifySimpleOperation(RawStatement statement)
	{
		// <var_name> <operation> = <var_name or literal>
		String resultVarName = statement.get(0).getValue();
		short resultAddress = mapper.getVariableAddress(resultVarName);
		
		Token operation = statement.get(1);
		char operationRepresentation = operation.getValue().charAt(0);
		
		assertTokenValue(statement, 2, "=");
		
		Token operand2 = statement.get(3);

		Instruction operationInstruction = Instruction.getByRepresentation(operationRepresentation);
		Operation op = new Operation();
		op.setInstruction(operationInstruction);
		op.setAddress1(resultAddress);
		switch(operand2.getType())
		{
			case LITERAL:
				op.setOperands(Operands.AL);
				op.setLiteral(Byte.parseByte(operand2.getValue()));
				break;
			case VAR_NAME:
				op.setOperands(Operands.AA);
				op.setAddress2(mapper.getVariableAddress(operand2.getValue()));
				break;
		}
		
		return op;
	}
	
	private static Operation simplifyDirectAssignment(RawStatement statement)
	{
		// <var_name> = <var_name or literal>
		Token destination = statement.get(0);
		assertTokenValue(statement, 1, "=");
		Token operand = statement.get(2);
		
		RawStatement movStatement = new RawStatement();
		movStatement.add(destination);
		movStatement.add(new Token(String.valueOf(Instruction.MOV.getRepresentation()), TokenType.OPERATION));
		movStatement.add(new Token("=", TokenType.OPERATION));
		movStatement.add(operand);
		
		return simplifySimpleOperation(movStatement);
	}
	
	private static List<Operation> simplifySimpleAssignment(RawStatement statement)
	{
		// <var_name> = <var_name or literal> <operation> <var_name or literal>
		Token destination = statement.get(0);
		assertTokenValue(statement, 1, "=");
		Token operand1 = statement.get(2);
		Token operation = statement.get(3);
		Token operand2 = statement.get(4);
		
		RawStatement movStatement = new RawStatement();
		movStatement.add(destination);
		movStatement.add(new Token("=", TokenType.OPERATION));
		movStatement.add(operand1);
		Operation mov = simplifyDirectAssignment(movStatement);
		
		RawStatement opStatement = new RawStatement();
		opStatement.add(destination);
		opStatement.add(operation);
		opStatement.add(new Token("=", TokenType.OPERATION));
		opStatement.add(operand2);
		Operation op = simplifySimpleOperation(opStatement);
		
		
		List<Operation> result = new LinkedList<Operation>();
		result.add(mov);
		result.add(op);
		return result;
	}
	
	private static Operation simplifyFunctionInvocation(RawStatement statement)
	{
		// <function_name>! <var_name or literal>
		Token functionName = statement.get(0);
		short functionAddress = FunctionMapper.map(functionName.getValue());
		Token operand = statement.get(1);
		
		Operation result = new Operation(Instruction.CALL);
		result.setAddress1(functionAddress);
		switch(operand.getType())
		{
			case LITERAL:
				result.setOperands(Operands.AL);
				result.setLiteral(Byte.parseByte(operand.getValue()));
				break;
			case VAR_NAME:
				result.setOperands(Operands.AA);
				result.setAddress2(mapper.getVariableAddress(operand.getValue()));
				break;
		}
		return result;
	}
	
	
	private static void assertTokenValue(RawStatement statement, int index, String expectedValue)
	{
		Token token = statement.get(index);
		String tokenValue = token.getValue();
		if(!tokenValue.equals(expectedValue))
		{
			String statementString = statement.getFullStatement();
			StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("Statement syntax is not acceptable.\n");
			messageBuilder.append(statementString);
			messageBuilder.append("\nfor token '");
			messageBuilder.append(tokenValue);
			messageBuilder.append("' [");
			messageBuilder.append(token.getType());
			messageBuilder.append("] at index [");
			messageBuilder.append(index);
			messageBuilder.append("].\nExpected [");
			messageBuilder.append(expectedValue);
			messageBuilder.append("].");
			throw new ActionizingException(messageBuilder.toString());
		}
	}
	
	private static void assertTokenType(RawStatement statement, int index, TokenType expectedType)
	{
		Token token = statement.get(index);
		TokenType tokenType = token.getType();
		if(!tokenType.equals(expectedType))
		{
			String statementString = statement.getFullStatement();
			StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("Statement syntax is not acceptable.\n");
			messageBuilder.append(statementString);
			messageBuilder.append("\nfor token '");
			messageBuilder.append(token.getValue());
			messageBuilder.append("' [");
			messageBuilder.append(tokenType);
			messageBuilder.append("] at index [");
			messageBuilder.append(index);
			messageBuilder.append("].\nExpected [");
			messageBuilder.append(expectedType);
			messageBuilder.append("].");
			throw new ActionizingException(messageBuilder.toString());
		}
	}
}
