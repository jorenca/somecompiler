package georgi.gaydarov.gos.compiler.translator.actionizer.mappers;

/**
 * Class responsible for mapping variable names to cache addresses.
 * 
 * @author Georgi Gaydarov
 *
 */
public class CacheMapper extends Mapper {
	private final static int MEMORY_SIZE = 16000;
	
	public CacheMapper(short startAddress) {
		super(startAddress);
	}

	@Override
	protected int getMemorySize() {
		return MEMORY_SIZE;
	}

}
