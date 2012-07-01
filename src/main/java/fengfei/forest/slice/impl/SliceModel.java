package fengfei.forest.slice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import fengfei.forest.slice.HashAlgorithms;
import fengfei.forest.slice.Slice;

public abstract class SliceModel {

	protected List<Slice> slices = new ArrayList<>();

	public SliceModel() {
	}

	public List<Slice> getSlices() {
		return slices;
	}

	public void setSlices(List<Slice> slices) {
		this.slices = slices;
	}

	public void addSlice(Slice slice) {

		slices.add(slice);
		// System.out.println("model: " + slice);
		// System.out.println("model slices: " + slices);
	}

	abstract Slice next(long seed);

}

class HashSliceModel extends SliceModel {

	public HashSliceModel() {
	}

	@Override
	public Slice next(long seed) {
		int index = HashAlgorithms.FNVHash1(String.valueOf(seed)) % slices.size();
		return slices.get(index);
	}

	@Override
	public String toString() {
		return "HashSliceModel [slices=" + slices + "]";
	}
}

class LoopSliceModel extends SliceModel {

	final AtomicInteger count = new AtomicInteger();
	private int currentIndex;

	public LoopSliceModel() {
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	@Override
	public Slice next(long seed) {
		currentIndex = count.getAndIncrement();
		int index = Math.abs(currentIndex % slices.size());
//		System.out
//				.println(seed + "=" + currentIndex + "-" + index + "-" + slices.size() + ":\n" + slices);
		return slices.get(index);
	}

	@Override
	public String toString() {
		return "LoopSliceModel [currentIndex=" + currentIndex + ", slices=" + slices + "]";
	}

}

class RemainderSliceModel extends SliceModel {

	public RemainderSliceModel() {
	}

	@Override
	public Slice next(long seed) {
		int index = Math.abs((int) (seed % slices.size()));
		return slices.get(index);
	}

	@Override
	public String toString() {
		return "RemainderSliceModel [slices=" + slices + "]";
	}
}
