package fengfei.forest.slice.impl;

import fengfei.forest.slice.HashAlgorithms;
import fengfei.forest.slice.SliceEqualizer;

public class HashSliceEqualizer<Source> implements SliceEqualizer<Source> {

	@Override
	public long get(Source key, int sliceSize) {
		String data = key.toString();
		return Math.abs(HashAlgorithms.FNVHash1(data) % sliceSize) + 1;
	}

}
