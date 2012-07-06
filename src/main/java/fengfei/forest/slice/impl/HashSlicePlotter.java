package fengfei.forest.slice.impl;

import fengfei.forest.slice.HashAlgorithms;
import fengfei.forest.slice.SlicePlotter;

public class HashSlicePlotter<Source> implements SlicePlotter<Source> {

    @Override
    public long get(Source key) {
        String data = key.toString();
        return HashAlgorithms.FNVHash1(data);
    }

}
