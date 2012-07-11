package fengfei.forest.slice.impl;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;

public class MasterSlaveLogicalSlice<Source> extends LogicalSlice<Source> {

	private SliceModel master;
	private SliceModel slave;
	private SliceModel all;

	public MasterSlaveLogicalSlice() {
		super();
	}

	public MasterSlaveLogicalSlice(SliceAlgorithmType algorithmType) {
		super(algorithmType);
	}

	public void addSlice(Slice slice, Function function) {
	    mergeInheritInfoTo(slice);
		switch (function) {
		case Master:
			master.addSlice(slice);
			all.addSlice(slice);
			break;
		case Slave:
			slave.addSlice(slice);
			all.addSlice(slice);
			break;
		case Any:
			master.addSlice(slice);
			slave.addSlice(slice);
			all.addSlice(slice);
			break;
		default:
			break;
		}
	}

	public void addSlice(Slice slice, String func) {
		Function function = Function.valueOf(func);
		addSlice(slice, function);
	}

	@Override
	public void setAlgorithmType(SliceAlgorithmType algorithmType) {
		switch (algorithmType) {
		case Hash:
			master = new HashSliceModel();
			slave = new HashSliceModel();
			all = new HashSliceModel();
		case Remainder:
			master = new RemainderSliceModel();
			slave = new RemainderSliceModel();
			all = new RemainderSliceModel();
		case Loop:
			master = new LoopSliceModel();
			slave = new LoopSliceModel();
			all = new LoopSliceModel();
		default:
			master = new LoopSliceModel();
			slave = new LoopSliceModel();
			all = new LoopSliceModel();
		}

	}

	public Slice get(long seed, Function function) {
		switch (function) {
		case Master:
			return master.next(seed);
		case Slave:
			return slave.next(seed);
		case Any:
			return all.next(seed);
		default:
			break;
		}
		return null;

	}

	@Override
	public Slice getAny(long seed) {
		return all.next(seed);
	}

	@Override
	public String toString() {
		return "MasterSlaveLogicalSlice [master=" + master + ", slave=" + slave + ", all=" + all + ", subSliceGroup=" + subSliceGroup + ", algorithmType=" + algorithmType + ", id=" + id + ", suffix=" + suffix + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}

}
