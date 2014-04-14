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
				
			case DIRECT_ASSIGNMENT:
				result.add(simplifyDirectAssignment(statement));
				break;
				
			case SIMPLE_ASSIGNMENT:
				result.addAll(simplifySimpleAssignment(statement));
				break;
				
				// TODO add more cases here
		}
		
		return result;
	}
	
	private static void simplifyAllocation(RawStatement statement)
	{
		// <var_keyword> <name_name>
		//assertTokenType(statement, 0, TokenType.VAR_DECLARATION);
		//assertTokenType(statement, 1, TokenType.VAR_NAME);
		
		Token nameToken = statement.get(1);
		String varName = nameToken.getValue();
		mapper.allocateVariable(varName);
	}
	
	private static Operation simplifyDirectAssignment(RawStatement statement)
	{
		// <var_name> = <var_name or literal>
		// assertTokenType(statement, 0, TokenType.VAR_NAME);
		assertTokenValue(statement, 1, "=");
		
		String varName = statement.get(0).getValue();
		short destination = mapper.getVariableAddress(varName);
		
		Token value = statement.get(2);
		String valueContent = value.getValue();
		TokenType valueType = value.getType();
		
		Operation result = new Operation(Instruction.MOV);
		result.setAddress1(destination);
		switch(valueType)
		{
			case LITERAL:
				result.setOperands(Operands.AL);
				result.setLiteral(Byte.parseByte(valueContent));
				break;
			case VAR_NAME:
				result.setOperands(Operands.AA);
				result.setAddress2(mapper.getVariableAddress(valueContent));
				break;
		}
		return result;
	}
	
	private static List<Operation> simplifySimpleAssignment(RawStatement statement)
	{
		// <var_name> = <var_name or literal> <operation> <var_name or literal>
		String resultVarName = statement.get(0).getValue();
		short resultAddress = mapper.getVariableAddress(resultVarName);
		assertTokenValue(statement, 1, "=");
		
		Token operand1 = statement.get(2);
		Token operation = statement.get(3);
		Token operand2 = statement.get(4);
		
		Operation mov = new Operation(Instruction.MOV);
		mov.setAddress1(resultAddress);
		Instruction operationInstruction = InstructionResolver.resolve(operation);
		Operation op = new Operation(operationInstruction);
		op.setAddress1(resultAddress);
		
		switch(operand1.getType())
		{
			case LITERAL:
				mov.setOperands(Operands.AL);
				mov.setLiteral(Byte.parseByte(operand1.getValue()));
				break;
			case VAR_NAME:
				mov.setOperands(Operands.AA);
				mov.setAddress2(mapper.getVariableAddress(operand1.getValue()));
				break;
		}
		
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
		
		List<Operation> result = new LinkedList<Operation>();
		result.add(mov);
		result.add(op);
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
