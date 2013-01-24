package fengfei.forest.slice.impl;

import fengfei.forest.slice.SliceEqualizer;

public abstract class RangeSliceEqualizer<Source> implements SliceEqualizer<Source> {

	@Override
	public abstract long get(Source key, int sliceSize);

}
