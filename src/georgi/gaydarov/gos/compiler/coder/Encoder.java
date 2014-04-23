package georgi.gaydarov.gos.compiler.coder;

import georgi.gaydarov.gos.compiler.translator.actionizer.Instruction;
import georgi.gaydarov.gos.compiler.translator.actionizer.Operand;
import georgi.gaydarov.gos.compiler.translator.actionizer.Operation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Encodes operations to their binary representations.
 * 
 * @author Georgi Gaydarov
 *
 */
public class Encoder {
	private FileWriter writer;
	
	public Encoder(File outFile) throws IOException
	{
		writer = new FileWriter(outFile);
	}
	public Encoder(String fileName) throws IOException
	{
		this(new File(fileName));
	}
	
	public void encode(List<Operation> operations) throws IOException
	{
		for(Operation op : operations)
		{
			Instruction instruction = op.getInstruction();
			byte instructionCode = InstructionCoder.getCode(instruction);
			writer.write(instructionCode);
			
			byte operandTypes = constructOperandsDescriptor(op);
			writer.write(operandTypes);
			
			if(op.getOperand1() != Operand.L)
			{
				int highByte = op.getAddress1() & 0xff00;
				highByte >>= 4;
				writer.write(highByte);
				int lowByte = op.getAddress1() & 0x00ff;
				writer.write(lowByte);
			}
			else
			{
				writer.write(op.getLiteral1());
			}
			
			if(op.getOperand2() != Operand.L)
			{
				int highByte = op.getAddress2() & 0xff00;
				highByte >>= 4;
				writer.write(highByte);
				int lowByte = op.getAddress2() & 0x00ff;
				writer.write(lowByte);
			}
			else
			{
				writer.write(op.getLiteral2());
			}
		}
	}
	
	public void close() throws IOException
	{
		writer.close();
	}
	
	private static byte constructOperandsDescriptor(Operation operation)
	{
		byte firstOperand = 0;
		byte secondOperand = 0;
		switch(operation.getOperand1())
		{
			case A : firstOperand = 0;
				break;
			case C : firstOperand = 1;
				break;
			case L : firstOperand = 2;
				break;
		}
		switch(operation.getOperand2())
		{
			case A : secondOperand = 0;
				break;
			case C : secondOperand = 1;
				break;
			case L : secondOperand = 2;
				break;
		}
		
		firstOperand <<= 4;
		return (byte) (firstOperand | secondOperand);
	}
}
