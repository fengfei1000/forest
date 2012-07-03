package fengfei.forest.slice;

import java.util.Map;

import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.LongSlicePlotter;

public abstract class SliceGroup<Source> {

	protected SlicePlotter<Source> plotter;
	protected OverType overType = OverType.Last;

	@SuppressWarnings("unchecked")
	public SliceGroup() {
		plotter = (SlicePlotter<Source>) new LongSlicePlotter();
	}

	public SliceGroup(SlicePlotter<Source> plotter) {
		this.plotter = plotter;

	}

	public abstract Slice get(Source key, Function function);

	public abstract Slice get(Source key);

	public abstract Slice first(Source key);

	public abstract Slice first(Source key, Function function);

	public abstract Slice last(Source key);

	public abstract Slice last(Source key, Function function);

	public abstract Map<Long, LogicalSlice<Source>> getSlices();

	protected Slice dealOver(Source key, Function function, long id) {

		switch (overType) {
		case First:
			return first(key, function);

		case Last:
			return last(key, function);

		case New:
			return null;

		case Exception:
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		default:
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
	}

	public void addLogicSlice(long id, LogicalSlice<Source> slice) {
		getSlices().put(id, slice);
	}

	public void setPlotter(SlicePlotter<Source> plotter) {
		this.plotter = plotter;
	}

	public void setOverType(OverType overType) {
		this.overType = overType;
	}
}
