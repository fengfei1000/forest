package fengfei.forest.slice.impl;

import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SliceEqualizer;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.exception.NonExistedSliceException;

public class NavigableSliceGroup<Source> extends SliceGroup<Source> {

	public enum NavigationType {

		/**
		 * Less than or equal to
		 * 
		 * 
		 */
		Floor,
		/**
		 * Greater than or equal to
		 */
		Ceiling;

		public static NavigationType find(String name) {
			NavigationType[] fs = values();
			for (NavigationType enumType : fs) {
				if (enumType.name().equalsIgnoreCase(name)) {
					return enumType;
				}

			}
			throw new IllegalArgumentException(
					"Non-exist the enum type,error arg name:" + name);
		}

	}

	final static NavigationType DefaultNavigationType = NavigationType.Floor;
	// protected NavigableMap<Long, LogicSlice<Source>> slices = new
	// TreeMap<>();
	protected NavigableMap<Long, LogicalSlice<Source>> slices = new ConcurrentSkipListMap<>();
	protected NavigationType navigationType = DefaultNavigationType;

	public NavigableSliceGroup() {
		super();
	}

	public NavigableSliceGroup(SliceEqualizer<Source> equalizer) {
		super(equalizer);
	}

	public NavigableSliceGroup(SliceEqualizer<Source> equalizer,
			FunctionType functionType, SliceAlgorithmType algorithmType) {
		super(equalizer, functionType, algorithmType);
	}

	private Slice getSlice(Map.Entry<Long, LogicalSlice<Source>> entry,
			Source key, Function function, long id, boolean isDealOver) {
		if (slices.size() == 0) {
			throw new NonExistedSliceException("id=" + id
					+ " non-existed slice.");
		}
		if (entry == null || entry.getValue() == null) {
			return dealOver(key, null, id, isDealOver);
			// throw new NonExistedSliceException("id=" + id + " non-existed.");
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
			// throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.getAny(id);
		if (slice == null) {
			return logicSlice.getChild(key);
		}
		return slice;
	}

	public Slice first(Source key, Function function) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.firstEntry();
		return getSlice(entry, key, function, id, false);
	}

	@Override
	public Slice first(Source key) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.firstEntry();
		return getSlice(entry, key, id, false);
	}

	@Override
	public Slice last(Source key) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.lastEntry();
		return getSlice(entry, key, id, false);
	}

	public Slice last(Source key, Function function) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.lastEntry();
		return getSlice(entry, key, function, id, false);
	}

	public Slice floor(Source key, Function function) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		return getSlice(entry, key, function, id, false);
	}

	public Slice floor(Source key) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		return getSlice(entry, key, id, false);
	}

	public Slice ceiling(Source key, Function function) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.ceilingEntry(id);
		return getSlice(entry, key, function, id, true);
	}

	public Slice ceiling(Source key) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.ceilingEntry(id);
		return getSlice(entry, key, id, true);
	}

	@Override
	public Slice get(Source key, Function function) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		return getSlice(entry, key, function, id, true);
	}

	@Override
	public Slice get(Source key) {
		long id = equalizer.get(key, slices.size());
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		return getSlice(entry, key, id, true);
	}


	@Override
	public Map<Long, LogicalSlice<Source>> getSlices() {
		return slices;
	}

	public void setNavigationType(NavigationType navigationType) {
		this.navigationType = navigationType;
	}

	@Override
	public String toString() {
		return "NavigableSliceGroup [slices=" + slices
				+ ", defaultNavigationType=" + navigationType + ", equalizer="
				+ equalizer + "]";
	}

}
