package fengfei.forest.slice;

import java.util.Map;

public abstract class SliceGroup<Source> {

	protected SlicePlotter<Source> plotter;

	public SliceGroup(SlicePlotter<Source> plotter) {
		this.plotter = plotter;

	}

	public abstract Slice get(Source key, Function function);

	public abstract Slice getAny(Source key);

	public abstract Map<Long, LogicalSlice<Source>> getSlices();

	public void addLogicSlice(long id, LogicalSlice<Source> slice) {
		getSlices().put(id, slice);
	}
}
