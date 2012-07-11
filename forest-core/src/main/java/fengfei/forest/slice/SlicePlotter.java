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

    long get(Source key);
}
