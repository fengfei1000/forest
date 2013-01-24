package fengfei.forest.slice;

import java.util.Map;

public interface Plotter {

	/**
	 * 
	 * @param key
	 * @param size
	 *            slice size
	 * @return
	 */
	abstract Slice next(long seed);

	/**
	 * add slice
	 * 
	 * @param id
	 *            slice id
	 * @param master
	 *            host:port
	 * @param slaves
	 *            [host1:port, host2:port]
	 */
	void addSlice(Long id, Slice slice);

	Map<Long, Slice> getSliceMap();
}
