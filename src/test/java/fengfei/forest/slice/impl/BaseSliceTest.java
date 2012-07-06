package fengfei.forest.slice.impl;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.model.Status;

public class BaseSliceTest {

    static int id = 1;
    String testMsg = "test";

    public static void create(LogicalSlice<LongSlicePlotter> slice) {
        slice.setAlgorithmType(SliceAlgorithmType.Loop);
        slice.setId("1");
        slice.setStatus(Status.Normal);
        slice.setSuffix("_1");
        slice.setWeight(1);
        slice.addExtraInfo("host", "localhost");
        slice.addExtraInfo("port", "8022");
        slice.addExtraInfo("user", "name");
        slice.addExtraInfo("pwd", "pwd123");

        slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
        slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
        slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);

    }

    private static
        PhysicalSlice
        createPhysicalSlice(LogicalSlice<LongSlicePlotter> pslice, int id) {
        PhysicalSlice slice = new PhysicalSlice();
        slice.setId(String.valueOf(id));
        slice.setStatus(Status.Normal);
        slice.setWeight(1);
        slice.addExtraInfo("host", "host" + id);
        return slice;
    }

}
