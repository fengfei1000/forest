package fengfei.forest.slice.impl;

import fengfei.forest.slice.SliceEqualizer;

public class LongSliceEqualizer implements SliceEqualizer<Long> {

	@Override
	public long get(Long key, int sliceSize) {
		return Math.abs(key % sliceSize) + 1;
	}

}
