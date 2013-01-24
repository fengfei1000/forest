package fengfei.forest.slice;

/**
 * Strategy
 * 
 * @author Wang Tietang
 * 
 * @param <Source>
 *            Source type
 */
public interface SliceEqualizer<Source> {

	long get(Source key, int sliceSize);

}
