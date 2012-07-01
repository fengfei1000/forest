package fengfei.forest.slice;

import java.util.Map;

import fengfei.forest.slice.impl.LongSlicePlotter;

public abstract class SliceGroup<Source> {

	protected SlicePlotter<Source> plotter;

	@SuppressWarnings("unchecked")
	public SliceGroup() {
		plotter = (SlicePlotter<Source>) new LongSlicePlotter();
	}

	public SliceGroup(SlicePlotter<Source> plotter) {
		this.plotter = plotter;

	}

	public abstract Slice get(Source key, Function function);

	public abstract Slice getAny(Source key);

	public abstract Map<Long, LogicalSlice<Source>> getSlices();

	public void addLogicSlice(long id, LogicalSlice<Source> slice) {
		getSlices().put(id, slice);
	}

	public void setPlotter(SlicePlotter<Source> plotter) {
		this.plotter = plotter;
	}
}
