package fengfei.forest.slice.impl;

import fengfei.forest.slice.SlicePlotter;

public class LongSlicePlotter implements SlicePlotter<Long> {

	@Override
	public long get(Long key, int sliceSize) {
		return Math.abs(key % sliceSize) + 1;
	}

}
