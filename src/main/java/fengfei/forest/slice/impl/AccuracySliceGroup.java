package fengfei.forest.slice.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SlicePlotter;
import fengfei.forest.slice.exception.NonExistedSliceException;

public class AccuracySliceGroup<Source> extends SliceGroup<Source> {

	protected Map<Long, LogicalSlice<Source>> slices = new ConcurrentHashMap<>();

	public AccuracySliceGroup() {
		super();
	}

	public AccuracySliceGroup(SlicePlotter<Source> plotter) {
		super(plotter);
	}

	@Override
	public Slice get(Source key, Function function) {
		long id = plotter.get(key);
		LogicalSlice<Source> logicSlice = slices.get(id);
		if (logicSlice == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		Slice slice = logicSlice.get(id, function);
		return slice;
	}

	@Override
	public Slice getAny(Source key) {
		long id = plotter.get(key);
		LogicalSlice<Source> logicSlice = slices.get(id);
		// System.out.println(logicSlice);
		if (logicSlice == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		Slice slice = logicSlice.getAny(id);
		return slice;

	}

	@Override
	public Map<Long, LogicalSlice<Source>> getSlices() {
		return slices;
	}

	@Override
	public String toString() {
		return "AccuracySliceGroup [slices=" + slices + ", plotter=" + plotter + "]";
	}

}
