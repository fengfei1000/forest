package fengfei.forest.slice.impl;

import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SlicePlotter;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.exception.NonExistedSliceException;

public class AccuracySliceGroup<Source> extends SliceGroup<Source> {

	protected Map<Long, LogicalSlice<Source>> slices = new ConcurrentHashMap<>();
	protected NavigableMap<Long, LogicalSlice<Source>> sortedSlices = new ConcurrentSkipListMap<>();

	public AccuracySliceGroup() {
		super();
	}

	public AccuracySliceGroup(SlicePlotter<Source> plotter) {
		super(plotter);
	}

	public AccuracySliceGroup(SlicePlotter<Source> plotter,
			FunctionType functionType, SliceAlgorithmType algorithmType) {
		super(plotter, functionType, algorithmType);
	}

	private Slice getSlice(Map.Entry<Long, LogicalSlice<Source>> entry,
			Source key, Function function, long id, boolean isDealOver) {
		if (slices.size() == 0) {
			throw new NonExistedSliceException("id=" + id
					+ " non-existed slice.");
		}
		if (entry == null || entry.getValue() == null) {
			return dealOver(key, function, id, isDealOver);
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.get(id, function);
		if (slice == null) {
			return logicSlice.getChild(key, function);
		}
		return slice;
	}

	private Slice getSlice(Map.Entry<Long, LogicalSlice<Source>> entry,
			Source key, long id, boolean isDealOver) {
		if (slices.size() == 0) {
			throw new NonExistedSliceException("id=" + id
					+ " non-existed slice.");
		}
		if (entry == null || entry.getValue() == null) {
			return dealOver(key, null, id, isDealOver);
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.getAny(id);
		if (slice == null) {
			return logicSlice.getChild(key);
		}
		return slice;
	}

	@Override
	public Slice get(Source key, Function function) {
		long id = plotter.get(key);
		LogicalSlice<Source> logicSlice = slices.get(id);
		return getSlice(logicSlice, key, function, id,true);
	}

	@Override
	public Slice get(Source key) {
		long id = plotter.get(key);
		LogicalSlice<Source> logicSlice = slices.get(id);
		return getSlice(logicSlice, key, id,true);

	}

	public Slice first(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = sortedSlices.firstEntry();
		return getSlice(entry, key, function, id,false);
	}

	@Override
	public Slice first(Source key) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = sortedSlices.firstEntry();
		return getSlice(entry, key, id,false);
	}

	@Override
	public Slice last(Source key) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = sortedSlices.lastEntry();
		return getSlice(entry, key, id,false);
	}

	public Slice last(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = sortedSlices.lastEntry();
		return getSlice(entry, key, function, id,false);
	}

	@Override
	public Map<Long, LogicalSlice<Source>> getSlices() {
		return slices;
	}

	public void addLogicSlice(long id, LogicalSlice<Source> slice) {
		getSlices().put(id, slice);
		sortedSlices.put(id, slice);
	}

	@Override
	public String toString() {
		return "AccuracySliceGroup [slices=" + slices + ", plotter=" + plotter
				+ ", overType=" + overType + "]";
	}

}
