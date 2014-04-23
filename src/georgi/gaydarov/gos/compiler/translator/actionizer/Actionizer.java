package georgi.gaydarov.gos.compiler.translator.actionizer;

import java.util.LinkedList;
import java.util.List;

import georgi.gaydarov.gos.compiler.tokenizing.Token;
import georgi.gaydarov.gos.compiler.tokenizing.TokenType;
import georgi.gaydarov.gos.compiler.translator.RawStatement;
import georgi.gaydarov.gos.compiler.translator.RawStatementType;
import georgi.gaydarov.gos.compiler.translator.actionizer.mappers.CacheMapper;
import georgi.gaydarov.gos.compiler.translator.actionizer.mappers.FunctionMapper;
import georgi.gaydarov.gos.compiler.translator.actionizer.mappers.MemoryMapper;

/**
 * Converts a {@link RawStatement} instance (high level code) to a {@link List<Operation>} 
 * that, when executed, behave as expected.
 * 
 * @author Georgi Gaydarov
 *
 */
public class Actionizer {
	private static final MemoryMapper memory = new MemoryMapper((short)0);
	private static final CacheMapper cache = new CacheMapper((short)0);
	
	
	
	public static List<Operation> simplify(RawStatementType statementType, RawStatement statement)
	{
		List<Operation> result = new LinkedList<Operation>();
		
		switch(statementType)
		{
			case DECLARATION:
				simplifyAllocation(statement);
				break;

			case DIRECT_OPERATION:
				result.add(simplifyDirectOperation(statement));
				break;
				
			case DIRECT_ASSIGNMENT:
				result.add(simplifyDirectAssignment(statement));
				break;
				
			case OPERATION_ASSIGNMENT:
				result.addAll(simplifyOperationAssignment(statement));
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
		TokenType allocationType = statement.get(0).getType();
		Token nameToken = statement.get(1);
		String varName = nameToken.getValue();
		
		if(allocationType == TokenType.MEMORY_VAR_DECLARATION)
		{
			memory.allocateVariable(varName);
		}
		else
		{
			cache.allocateVariable(varName);
		}
	}
	
	private static Operation simplifyDirectOperation(RawStatement statement)
	{
		// <var_name> <operation> = <var_name or literal>
		String resultVarName = statement.get(0).getValue();
		short resultAddress;
		Operand resultOperandType;
		if(memory.isVariableDeclared(resultVarName))
		{
			resultAddress = memory.getVariableAddress(resultVarName);
			resultOperandType = Operand.A;
		}
		else
		{
			resultAddress = cache.getVariableAddress(resultVarName);
			resultOperandType = Operand.C;
		}
		
		Token operation = statement.get(1);
		char operationRepresentation = operation.getValue().charAt(0);
		assertTokenValue(statement, 2, "=");
		Token operand2 = statement.get(3);

		Instruction operationInstruction = Instruction.getByRepresentation(operationRepresentation);
		Operation op = new Operation();
		op.setInstruction(operationInstruction);
		op.setAddress1(resultAddress);
		op.setOperand1(resultOperandType);
		
		String operand2Value = operand2.getValue();
		switch(operand2.getType())
		{
			case LITERAL:
				op.setOperand2(Operand.L);
				op.setLiteral2(Byte.parseByte(operand2Value));
				break;
			case VAR_NAME:
				short op2Address;
				Operand op2Type;
				if(memory.isVariableDeclared(operand2Value))
				{
					op2Address = memory.getVariableAddress(operand2Value);
					op2Type = Operand.A;
				}
				else
				{
					op2Address = cache.getVariableAddress(operand2Value);
					op2Type = Operand.C;
				}
				op.setOperand2(op2Type);
				op.setAddress2(op2Address);
				break;
		}
		
		return op;
	}
	
	private static Operation simplifyDirectAssignment(RawStatement statement)
	{
		// <var_name> = <var_name or literal>
		assertTokenValue(statement, 1, "=");
		
		RawStatement movStatement = new RawStatement();
		movStatement.add(statement.get(0));
		movStatement.add(new Token(String.valueOf(Instruction.MOV.getRepresentation()), TokenType.OPERATION));
		movStatement.add(statement.get(1));
		movStatement.add(statement.get(2));
		
		return simplifyDirectOperation(movStatement);
	}
	
	private static List<Operation> simplifyOperationAssignment(RawStatement statement)
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
		Operation op = simplifyDirectOperation(opStatement);
		
		
		List<Operation> result = new LinkedList<Operation>();
		result.add(mov);
		result.add(op);
		return result;
	}
	
	private static Operation simplifyFunctionInvocation(RawStatement statement)
	{
		// <function_name>! <var_name or literal>
		Token functionName = statement.get(0);
		byte functionId = FunctionMapper.map(functionName.getValue());
		Token operand = statement.get(1);
		
		Operation result = new Operation(Instruction.CALL);
		result.setOperand1(Operand.L);
		result.setLiteral1(functionId);
		String operandValue = operand.getValue();
		switch(operand.getType())
		{
			case LITERAL:
				result.setOperand2(Operand.L);
				result.setLiteral2(Byte.parseByte(operandValue));
				break;
			case VAR_NAME:
				short opAddress;
				Operand opType;
				if(memory.isVariableDeclared(operandValue))
				{
					opAddress = memory.getVariableAddress(operandValue);
					opType = Operand.A;
				}
				else
				{
					opAddress = cache.getVariableAddress(operandValue);
					opType = Operand.C;
				}
				result.setOperand2(opType);
				result.setAddress2(opAddress);
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
}
