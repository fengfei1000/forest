package fengfei.forest.slice;

import java.util.Map;

import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.EqualityLogicalSlice;
import fengfei.forest.slice.impl.LongSlicePlotter;
import fengfei.forest.slice.impl.MasterSlaveLogicalSlice;
import fengfei.forest.slice.impl.ReadWriteLogicalSlice;

public abstract class SliceGroup<Source> {

	protected SlicePlotter<Source> plotter;
	protected OverType overType = OverType.Last;
	protected FunctionType functionType;
	protected SliceAlgorithmType algorithmType;

	@SuppressWarnings("unchecked")
	public SliceGroup() {
		plotter = (SlicePlotter<Source>) new LongSlicePlotter();
	}

	public SliceGroup(SlicePlotter<Source> plotter) {
		this.plotter = plotter;

	}

	public SliceGroup(SlicePlotter<Source> plotter, FunctionType functionType,
			SliceAlgorithmType algorithmType) {
		super();
		this.plotter = plotter;
		this.functionType = functionType;
		this.algorithmType = algorithmType;
	}

	public abstract Slice get(Source key, Function function);

	public abstract Slice get(Source key);

	public abstract Slice first(Source key);

	public abstract Slice first(Source key, Function function);

	public abstract Slice last(Source key);

	public abstract Slice last(Source key, Function function);

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

	public void addSlice(long id, String suffix, Slice slice) {
		addSlice(id, suffix, slice, Function.Any);
	}

	public void addSlice(long id, Slice slice) {
		addSlice(id, String.valueOf(id), slice, Function.Any);
	}

	public void addSlice(long id, String suffix, Slice slice, Function function) {
		LogicalSlice<Source> logicalSlice = getSlices().get(id);
		if (logicalSlice == null) {
			logicalSlice = newLogicalSlice();
		}
		logicalSlice.setSuffix(suffix);
		slice.setSuffix(suffix);
		logicalSlice.addSlice(slice, function);
		getSlices().put(id, logicalSlice);
	}

	protected LogicalSlice<Source> newLogicalSlice() {
		LogicalSlice<Source> logicalSlice = null;
		switch (functionType) {
		case Equality:
			logicalSlice = new EqualityLogicalSlice<>(algorithmType);
			break;
		case MasterSlave:
			logicalSlice = new MasterSlaveLogicalSlice<>(algorithmType);
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

	public void setPlotter(SlicePlotter<Source> plotter) {
		this.plotter = plotter;
	}

	public void setOverType(OverType overType) {
		this.overType = overType;
	}

	public void setAlgorithmType(SliceAlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}

}
