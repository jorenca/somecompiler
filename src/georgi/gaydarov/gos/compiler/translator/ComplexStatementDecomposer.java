package georgi.gaydarov.gos.compiler.translator;

import georgi.gaydarov.gos.compiler.tokenizing.Token;

import java.util.List;

public class ComplexStatementDecomposer {
	public static List<RawStatement> decompose(RawStatement statement)
	{
		// TODO implement me!
		// c = a + b becomes
		// var d ; d = a; d += b; c = d;
		
		// FIXME when variable deleting is implemented.
		return null;
	}
}
