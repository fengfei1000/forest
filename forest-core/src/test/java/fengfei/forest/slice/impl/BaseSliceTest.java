package fengfei.forest.slice.impl;

import org.junit.Ignore;

import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.model.Status;
@Ignore
public class BaseSliceTest {

    static final String PHOST = "localhost", PPORT = "8022", PUSER = "testUser", PPWD = "pwd123";
    static final String KEY_HOST = "host", KEY_PORT = "port", KEY_USER = "username",
            KEY_PASSWORD = "password";
    static int id = 1;
    String testMsg = "test";

    public static void create(LogicalSlice<LongSliceEqualizer> slice) {
        slice.setAlgorithmType(SliceAlgorithmType.Loop);
        slice.setId("1");
        slice.setStatus(Status.Normal);
        slice.setSuffix("_1");
        slice.setWeight(1);
        slice.addExtraInfo(KEY_HOST, PHOST);
        slice.addExtraInfo(KEY_PORT, PPORT);
        slice.addExtraInfo(KEY_USER, PUSER);
        slice.addExtraInfo(KEY_PASSWORD, PUSER);
    }

    public static PhysicalSlice createPhysicalSlice(LogicalSlice<LongSliceEqualizer> pslice, int id) {
        PhysicalSlice slice = new PhysicalSlice();
        slice.setId(String.valueOf(id));
        slice.setStatus(Status.Normal);
        slice.setWeight(1);
        slice.addExtraInfo(KEY_HOST, "host" + id);
        return slice;
    }

}
