package fengfei.forest.slice.impl;

import fengfei.forest.slice.SlicePlotter;

public class LongSlicePlotter implements SlicePlotter<Long> {

	@Override
	public long get(Long key) {
		return key;
	}

}
