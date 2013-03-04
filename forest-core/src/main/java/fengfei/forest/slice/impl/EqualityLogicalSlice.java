package fengfei.forest.slice.impl;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;

public class EqualityLogicalSlice<Source> extends LogicalSlice<Source> {

	private SliceTribe tribe;
	protected Lock lock = new ReentrantLock();

	public EqualityLogicalSlice() {
		super();
	}

	public EqualityLogicalSlice(SliceAlgorithmType algorithmType) {
		super(algorithmType);
	}

	@Override
	public void addSlice(Slice slice, Function function) {
		mergeInheritInfoTo(slice);
		tribe.addSlice(slice);
	}

	@Override
	public Slice get(long seed, Function function) {
		return tribe.next(seed);
	}

	@Override
	public void setAlgorithmType(SliceAlgorithmType algorithmType) {
		switch (algorithmType) {
		case Hash:
			tribe = new HashSliceTribe();
		case Remainder:
			tribe = new RemainderSliceTribe();
		case Loop:
			tribe = new LoopSliceTribe();
		default:
			tribe = new LoopSliceTribe();
		}
	}


	public int getSliceSize() {
		return tribe.getAvailableSlices().size();
	}

	public List<Slice> getSlices() {
		return tribe.getAvailableSlices();
	}


	@Override
	public Slice getAny(long id) {
		return tribe.next(id);
	}

	public boolean fail(Slice slice) {
		lock.lock();
		try {
			List<Slice> availableSlices = tribe.getAvailableSlices();
			List<Slice> failSlices = tribe.getFailSlices();
			availableSlices.remove(slice);
			failSlices.add(slice);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}
	}

	public boolean recover(Slice slice) {
		lock.lock();
		try {
			List<Slice> availableSlices = tribe.getAvailableSlices();
			List<Slice> failSlices = tribe.getFailSlices();
			failSlices.remove(slice);
			availableSlices.add(slice);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		return "EqualityLogicalSlice [algorithmType=" + algorithmType + ", id=" + id + ", sliceId=" + sliceId + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}
 
}
