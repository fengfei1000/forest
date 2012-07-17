package fengfei.forest.slice.database;

import java.util.Map;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SlicePlotter;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class DatabaseSliceGroup<Source> extends SliceGroup<Source> {

    private SliceGroup<Source> sliceGroup;

    public DatabaseSliceGroup(SliceGroupFactory factory, String unitName) {
        this.sliceGroup = factory.getSliceGroup(unitName);
    }

    public DatabaseSliceGroup(
        SliceGroupFactory factory,
        SlicePlotter<Source> plotter,
        String unitName) {
        this.sliceGroup = factory.getSliceGroup(plotter, unitName);
    }

    @Override
    public ServerSlice get(Source key, Function function) {
        return new ServerSlice(sliceGroup.get(key, function));
    }

    @Override
    public ServerSlice get(Source key) {
        return new ServerSlice(sliceGroup.get(key));
    }

    @Override
    public ServerSlice first(Source key) {
        return new ServerSlice(sliceGroup.first(key));
    }

    @Override
    public ServerSlice first(Source key, Function function) {
        return new ServerSlice(sliceGroup.first(key, function));
    }

    @Override
    public ServerSlice last(Source key) {
        return new ServerSlice(sliceGroup.last(key));
    }

    @Override
    public ServerSlice last(Source key, Function function) {
        return new ServerSlice(sliceGroup.last(key, function));
    }

    @Override
    public Map<Long, LogicalSlice<Source>> getSlices() {
        return sliceGroup.getSlices();
    }

}
