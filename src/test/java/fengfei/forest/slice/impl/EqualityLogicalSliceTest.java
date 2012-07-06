package fengfei.forest.slice.impl;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.model.Status;

public class EqualityLogicalSliceTest {

    static EqualityLogicalSlice<LongSlicePlotter> slice = new EqualityLogicalSlice<>();
    static int id = 1;

    @BeforeClass
    public static void setup() {
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

    private static PhysicalSlice createPhysicalSlice(
        EqualityLogicalSlice<LongSlicePlotter> pslice,
        int id) {
        PhysicalSlice slice = new PhysicalSlice();
        slice.setId(String.valueOf(id));
        slice.setStatus(Status.Normal);
        slice.setWeight(1);
        slice.addExtraInfo("host", "host" + id);
        return slice;
    }

    public void testAddSlice() {
        int id = 10;
        slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
        slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
        slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);

    }

    @Test
    public void testGet() {
        Slice slice1 = slice.get(1, Function.Any);
        System.out.println(slice1);
    }

    @Test
    public void testGetAny() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetChildSourceFunction() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetChildSource() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSuffix() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetExtraInfo() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetWeight() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetStatus() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsPhysical() {
        fail("Not yet implemented");
    }

}
