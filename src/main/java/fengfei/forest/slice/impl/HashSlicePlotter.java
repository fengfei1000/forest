package fengfei.forest.slice.impl;

import fengfei.forest.slice.HashAlgorithms;
import fengfei.forest.slice.SlicePlotter;

public abstract class HashSlicePlotter<Source> implements SlicePlotter<Source> {

    @Override
    public long get(Source key) {
        String data = toString(key);
        return HashAlgorithms.FNVHash1(data);
    }

    public abstract String toString(Source key);
}
