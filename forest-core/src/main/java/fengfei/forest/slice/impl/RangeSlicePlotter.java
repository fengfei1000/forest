package fengfei.forest.slice.impl;

import fengfei.forest.slice.SlicePlotter;

public abstract class RangeSlicePlotter<Source> implements SlicePlotter<Source> {

    @Override
    public abstract long get(Source key);

}
