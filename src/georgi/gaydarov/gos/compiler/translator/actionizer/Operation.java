package georgi.gaydarov.gos.compiler.translator.actionizer;


/**
 * Describes a processor operation ({@link Instruction} and operands).
 * @author Georgi Gaydarov
 *
 */
public class Operation {
	private Instruction instruction;
	private Operand operand1;
	private Operand operand2;
	private short address1;
	private short address2;
	private byte literal1;
	private byte literal2;
	
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
	public Operand getOperand1() {
		return operand1;
	}
	public void setOperand1(Operand operand1) {
		this.operand1 = operand1;
	}
	public Operand getOperand2() {
		return operand2;
	}
	public void setOperand2(Operand operand2) {
		this.operand2 = operand2;
	}
	public byte getLiteral1() {
		return literal1;
	}
	public void setLiteral1(byte literal1) {
		this.literal1 = literal1;
	}
	public byte getLiteral2() {
		return literal2;
	}
	public void setLiteral2(byte literal2) {
		this.literal2 = literal2;
	}

	/**
	 * Constructs a readable representation of this operation.
	 * 
	 * @return the readable string.
	 */
	public String getReadable()
	{
		StringBuilder resultBuilder = new StringBuilder();
		String instrString = String.format("%s[%s/%s] : ", instruction.toString(), operand1.toString(), operand2.toString());
		resultBuilder.append(instrString);
		
		String opr = "UNKNOWN";
		switch(operand1)
		{
			case L:
				opr = String.format("literal(%d)", literal1);
				break;
			case A:
			case C:
				opr = String.format("addr(%d)", address1);
				break;
		}
		resultBuilder.append(opr);
		resultBuilder.append(", ");
		
		opr = "UNKNOWN";
		switch(operand2)
		{
			case L:
				opr = String.format("literal(%d)", literal2);
				break;
			case A:
			case C:
				opr = String.format("addr(%d)", address2);
				break;
		}

		resultBuilder.append(opr);
		
		return resultBuilder.toString();
	}
}
