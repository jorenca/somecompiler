package georgi.gaydarov.gos.compiler.coder;

import georgi.gaydarov.gos.compiler.translator.actionizer.Instruction;

import java.util.HashMap;
import java.util.Map;

class InstructionCoder {
	private static final Map<Instruction, Byte> instructionMap = new HashMap<Instruction, Byte>();
	static
	{
		byte instructionIdIterator = 0x1;
		for(Instruction instruction : Instruction.values())
		{
			instructionMap.put(instruction, instructionIdIterator);
		}
	}
	
	public static byte getCode(Instruction instruction)
	{
		byte rawCode = instructionMap.get(instruction);
		rawCode <<= 3;
		return rawCode;
	}
}
