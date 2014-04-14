package georgi.gaydarov.gos.compiler.translator.actionizer;


public class Operation {
	private Instruction instruction;
	private Operands operands;
	private short address1;
	private short address2;
	private byte literal;
	
	Operation()
	{
		
	}
	Operation(Instruction instruction) 
	{
		this.instruction = instruction;
	}
	
	public void setInstruction(Instruction instruction)
	{
		this.instruction = instruction;
	}
	public Instruction getInstruction()
	{
		return instruction;
	}

	public short getAddress1() {
		return address1;
	}

	public void setAddress1(short address1) {
		this.address1 = address1;
	}

	public short getAddress2() {
		return address2;
	}

	public void setAddress2(short address2) {
		this.address2 = address2;
	}

	public byte getLiteral() {
		return literal;
	}

	public void setLiteral(byte literal) {
		this.literal = literal;
	}
	public Operands getOperands() {
		return operands;
	}
	public void setOperands(Operands operands) {
		this.operands = operands;
	}
}
