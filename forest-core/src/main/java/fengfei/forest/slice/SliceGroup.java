package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.EqualityLogicalSlice;
import fengfei.forest.slice.impl.LongSliceEqualizer;
import fengfei.forest.slice.impl.ReadWriteLogicalSlice;

public abstract class SliceGroup<Source> {

	protected SliceEqualizer<Source> equalizer;
	protected OverType overType = OverType.Last;
	protected FunctionType functionType;
	protected SliceAlgorithmType algorithmType;
	protected Map<String, String> defaultExtraInfo = new HashMap<>();

	@SuppressWarnings("unchecked")
	public SliceGroup() {
		equalizer = (SliceEqualizer<Source>) new LongSliceEqualizer();
	}

	public SliceGroup(SliceEqualizer<Source> equalizer) {
		this.equalizer = equalizer;

	}

	public SliceGroup(SliceEqualizer<Source> equalizer,
			FunctionType functionType, SliceAlgorithmType algorithmType) {
		super();
		this.equalizer = equalizer;
		this.functionType = functionType;
		this.algorithmType = algorithmType;
	}

	/**
	 * get slice by function
	 * 
	 * @param key
	 *            the key is Source key type of Sliceequalizer
	 * @param function
	 * @return
	 */
	public abstract Slice get(Source key, Function function);

	/**
	 * get any slice
	 * 
	 * @param key
	 * @return
	 */
	public abstract Slice get(Source key);

	/**
	 * get first slice of all slices
	 * 
	 * @param key
	 * @return
	 */
	public abstract Slice first(Source key);

	/**
	 * get first slice of all slices by function
	 * 
	 * @param key
	 * @return
	 */
	public abstract Slice first(Source key, Function function);

	/**
	 * get first slice of last slices
	 * 
	 * @param key
	 * @return
	 */
	public abstract Slice last(Source key);

	/**
	 * get first slice of all slices by function
	 * 
	 * @param key
	 * @return
	 */
	public abstract Slice last(Source key, Function function);

	/**
	 * get all slices
	 * 
	 * @return
	 */
	public abstract Map<Long, LogicalSlice<Source>> getSlices();

	protected Slice dealOver(Source key, Function function, long id,
			boolean isDealOver) {
		if (!isDealOver) {
			throw new NonExistedSliceException("id=" + id
					+ " non-existed slice.");
		}

		switch (overType) {
		case First:
			return function == null ? first(key) : first(key, function);

		case Last:
			return function == null ? last(key) : last(key, function);

		case New:
			return null;

		case Exception:
			throw new NonExistedSliceException("id=" + id
					+ " non-existed slice.");
		default:
			throw new NonExistedSliceException("id=" + id
					+ " non-existed slice.");
		}
	}

	/**
	 * get a special function slice of logicslice
	 * 
	 * @param logicSlice
	 * @param key
	 * @param function
	 * @param id
	 * @param isDealOver
	 * @return
	 */
	protected Slice getSlice(LogicalSlice<Source> logicSlice, Source key,
			Function function, long id, boolean isDealOver) {

		if (logicSlice == null) {
			return dealOver(key, function, id, isDealOver);
		}
		Slice slice = logicSlice.get(id, function);
		if (slice == null) {
			return logicSlice.getChild(key, function);
		}
		return slice;
	}

	protected Slice getSlice(LogicalSlice<Source> logicSlice, Source key,
			long id, boolean isDealOver) {
		if (logicSlice == null) {
			return dealOver(key, null, id, isDealOver);
		}
		Slice slice = logicSlice.getAny(id);
		if (slice == null) {
			return logicSlice.getChild(key);
		}
		return slice;
	}

	public void addLogicSlice(long id, LogicalSlice<Source> slice) {
		getSlices().put(id, slice);
	}

	protected AtomicLong ids = new AtomicLong();

	public void addSlice(String suffix, Slice slice) {
		long id = ids.getAndIncrement();
		addSlice(id, suffix, slice, Function.Normal);
	}

	public void addSlice(long id, String suffix, Slice slice) {
		addSlice(id, suffix, slice, Function.Normal);
	}

	/**
	 * add a new slice to the group, suffix=id, and function is Function.Any
	 * 
	 * 
	 * @see addSlice(long, String, Slice, Function)
	 * @param id
	 * @param slice
	 */
	public void addSlice(long id, Slice slice) {
		addSlice(id, String.valueOf(id), slice, Function.Normal);
	}

	public void addSlice(Slice slice) {
		long id = ids.getAndIncrement();
		addSlice(id, String.valueOf(id), slice, Function.Normal);
	}

	public void addSlice(long id, Slice slice, Function function) {
		addSlice(id, String.valueOf(id), slice, function);
	}

	public void addSlice(Slice slice, Function function) {
		long id = ids.getAndIncrement();
		addSlice(id, String.valueOf(id), slice, function);
	}

	/**
	 * add a new slice to the LogicSlice. if the LogicSlice is non-exists, then
	 * new a LogicSlice. slice is a PhysicalSlice or LogicSlice instance
	 * 
	 * @param id
	 *            logic slice id
	 * @param suffix
	 *            the slice mark
	 * @param slice
	 *            slice is a PhysicalSlice or LogicSlice instance,
	 * @param function
	 *            function type of silce
	 */
	public void addSlice(long id, String suffix, Slice slice, Function function) {
		LogicalSlice<Source> logicalSlice = getSlices().get(id);
		if (logicalSlice == null) {
			logicalSlice = newLogicalSlice();
		}
		logicalSlice.setSliceId(suffix);
		slice.installSliceId(suffix);
		slice.setFunction(function);
		Map<String, String> extraInfo = new HashMap<>(getDefaultExtraInfo());
		extraInfo.putAll(logicalSlice.getExtraInfo());
		extraInfo.putAll(slice.getExtraInfo());
		slice.addExtraInfo(extraInfo);
		logicalSlice.addSlice(slice, function);
		getSlices().put(id, logicalSlice);
	}

	/**
	 * create LogicSlice instance by functionType
	 * 
	 * @return this instance
	 */
	protected LogicalSlice<Source> newLogicalSlice() {
		LogicalSlice<Source> logicalSlice = null;
		switch (functionType) {
		case Equality:
			logicalSlice = new EqualityLogicalSlice<>(algorithmType);
			break;
	 
		case ReadWrite:
			logicalSlice = new ReadWriteLogicalSlice<>(algorithmType);
			break;

		default:
			logicalSlice = new EqualityLogicalSlice<>(algorithmType);
			break;
		}
		return logicalSlice;
	}

	public void setFunctionType(FunctionType functionType) {
		this.functionType = functionType;
	}

	public void setEqualizer(SliceEqualizer<Source> equalizer) {
		this.equalizer = equalizer;
	}

	public void setOverType(OverType overType) {
		this.overType = overType;
	}

	public void setAlgorithmType(SliceAlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}

	public Map<String, String> getDefaultExtraInfo() {
		return defaultExtraInfo;
	}

	public void setDefaultExtraInfo(Map<String, String> defaultExtraInfo) {
		this.defaultExtraInfo = defaultExtraInfo;
	}

}
