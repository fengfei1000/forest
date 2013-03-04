package fengfei.forest.slice.impl;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.exception.NoSupportedException;

public class ReadWriteLogicalSlice<Source> extends LogicalSlice<Source> {

	protected Lock lock = new ReentrantLock();
	private SliceTribe read;
	private SliceTribe write;
	private SliceTribe all;

	public ReadWriteLogicalSlice() {
		super();
	}

	public ReadWriteLogicalSlice(SliceAlgorithmType algorithmType) {
		super(algorithmType);
	}

	public void addSlice(Slice slice, Function function) {
		mergeInheritInfoTo(slice);
		switch (function) {
		case Read:
			read.addSlice(slice);
			all.addSlice(slice);
			break;
		case Write:
			write.addSlice(slice);
			all.addSlice(slice);
			break;
		case ReadWrite:
			read.addSlice(slice);
			write.addSlice(slice);
			all.addSlice(slice);
			break;
		default:
			throw new NoSupportedException("Don't supported the function: " + function.name());
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
			read = new HashSliceTribe();
			write = new HashSliceTribe();
			all = new HashSliceTribe();
		case Remainder:
			read = new RemainderSliceTribe();
			write = new RemainderSliceTribe();
			all = new RemainderSliceTribe();
		case Loop:
			read = new LoopSliceTribe();
			write = new LoopSliceTribe();
			all = new LoopSliceTribe();
		default:
			read = new LoopSliceTribe();
			write = new LoopSliceTribe();
			all = new LoopSliceTribe();
		}
	}

	public Slice get(long seed, Function function) {
		switch (function) {
		case Read:
			return read.next(seed);
		case Write:
			return write.next(seed);
		case ReadWrite:
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

	public boolean fail(Slice slice) {
		lock.lock();
		try {
			return (fail(read, slice) || fail(write, slice)) && fail(all, slice);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}
	}

	public boolean fail(SliceTribe tribe, Slice slice) {
		List<Slice> availableSlices = tribe.getAvailableSlices();
		List<Slice> failSlices = tribe.getFailSlices();
		availableSlices.remove(slice);
		failSlices.add(slice);
		return true;
	}

	public boolean recover(Slice slice) {
		lock.lock();
		try {
			return (recover(read, slice) || recover(write, slice)) && recover(all, slice);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}
	}

	public boolean recover(SliceTribe tribe, Slice slice) {
		List<Slice> availableSlices = tribe.getAvailableSlices();
		List<Slice> failSlices = tribe.getFailSlices();
		failSlices.remove(slice);
		availableSlices.add(slice);
		return true;
	}

	@Override
	public String toString() {
		return "ReadWriteLogicalSlice [read=" + read + ", write=" + write + ", all=" + all + ", subSliceGroup=" + subSliceGroup + ", algorithmType=" + algorithmType + ", id=" + id + ", sliceId=" + sliceId + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}
}
