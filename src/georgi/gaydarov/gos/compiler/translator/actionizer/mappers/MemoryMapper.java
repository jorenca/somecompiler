package georgi.gaydarov.gos.compiler.translator.actionizer.mappers;

/**
 * Class responsible for mapping variable names to memory addresses.
 * 
 * @author Georgi Gaydarov
 *
 */
public class MemoryMapper extends Mapper {
	private final static int MEMORY_SIZE = 1024;
	
	public MemoryMapper(short startAddress) {
		super(startAddress);
	}

	@Override
	protected int getMemorySize() {
		return MEMORY_SIZE;
	}
}
