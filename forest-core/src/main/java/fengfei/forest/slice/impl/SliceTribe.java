package fengfei.forest.slice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import fengfei.forest.slice.HashAlgorithms;
import fengfei.forest.slice.Slice;

public abstract class SliceTribe {

	protected List<Slice> availableSlices = new ArrayList<>();
	protected List<Slice> failSlices = new ArrayList<>();


	public SliceTribe() {
	}

	public List<Slice> getAvailableSlices() {
		return availableSlices;
	}

	public void setSlices(List<Slice> slices) {
		this.availableSlices = slices;
	}

	public void addSlice(Slice slice) {
		availableSlices.add(slice);
		// System.out.println("model: " + slice);
		// System.out.println("model slices: " + slices);
	}

	public List<Slice> getFailSlices() {
		return failSlices;
	}

	abstract Slice next(long seed);
}

class HashSliceTribe extends SliceTribe {

	public HashSliceTribe() {
	}

	@Override
	public Slice next(long seed) {
		int index = HashAlgorithms.FNVHash1(String.valueOf(seed)) % availableSlices.size();
		return availableSlices.get(index);
	}

	@Override
	public String toString() {
		return "HashSliceModel [slices=" + availableSlices + "]";
	}
}

class LoopSliceTribe extends SliceTribe {

	final AtomicInteger count = new AtomicInteger();
	private int currentIndex;

	public LoopSliceTribe() {
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	@Override
	public Slice next(long seed) {
		currentIndex = count.getAndIncrement();
		int index = Math.abs(currentIndex % availableSlices.size());
		// System.out
		// .println(seed + "=" + currentIndex + "-" + index + "-" +
		// slices.size() + ":\n" + slices);
		return availableSlices.get(index);
	}

	@Override
	public String toString() {
		return "LoopSliceModel [currentIndex=" + currentIndex + ", slices=" + availableSlices + "]";
	}
}

class RemainderSliceTribe extends SliceTribe {

	public RemainderSliceTribe() {
	}

	@Override
	public Slice next(long seed) {
		int index = Math.abs((int) (seed % availableSlices.size()));
		return availableSlices.get(index);
	}

	@Override
	public String toString() {
		return "RemainderSliceModel [slices=" + availableSlices + "]";
	}
}
