package fengfei.forest.slice;

import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import fengfei.forest.slice.exception.NonExistedSliceException;

public class NavigableSliceGroup<Source> extends SliceGroup<Source> {

	public enum NavigationType {

		/**
		 * Min
		 */
		First,

		/**
		 * Max
		 */
		Last,

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

	// protected NavigableMap<Long, LogicSlice<Source>> slices = new
	// TreeMap<>();
	protected NavigableMap<Long, LogicalSlice<Source>> slices = new ConcurrentSkipListMap<>();
	protected NavigationType defaultNavigationType = NavigationType.Floor;

	public NavigableSliceGroup(SlicePlotter<Source> plotter) {
		super(plotter);
	}

	public Slice first(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.firstEntry();
		if (entry == null || entry.getValue() == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.get(id, function);
		return slice;
	}

	public Slice last(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.lastEntry();
		if (entry == null || entry.getValue() == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.get(id, function);
		return slice;
	}

	public Slice floor(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		if (entry == null || entry.getValue() == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.get(id, function);
		return slice;
	}

	public Slice ceiling(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.ceilingEntry(id);
		if (entry == null || entry.getValue() == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.get(id, function);
		return slice;
	}

	@Override
	public Slice get(Source key, Function function) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		if (entry == null || entry.getValue() == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.get(id, function);
		return slice;
	}

	@Override
	public Slice getAny(Source key) {
		long id = plotter.get(key);
		Map.Entry<Long, LogicalSlice<Source>> entry = slices.floorEntry(id);
		if (entry == null || entry.getValue() == null) {
			throw new NonExistedSliceException("id=" + id + " non-existed.");
		}
		LogicalSlice<Source> logicSlice = entry.getValue();
		Slice slice = logicSlice.getAny(id);
		return slice;
	}

	@Override
	public Map<Long, LogicalSlice<Source>> getSlices() {
		return slices;
	}

	@Override
	public String toString() {
		return "NavigableSliceGroup [slices=" + slices + ", defaultNavigationType=" + defaultNavigationType + ", plotter=" + plotter + "]";
	}

}
