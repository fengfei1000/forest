package fengfei.forest.slice;

/**
 * Strategy
 * 
 * @author Wang Tietang
 * 
 * @param <Source>
 *            Source type
 */
public interface SlicePlotter<Source> {

	 Slice next(long seed);
}
