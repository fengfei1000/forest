package fengfei.forest.slice;

import java.util.Map;

import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.LongSlicePlotter;

public abstract class SliceGroup<Source> {

    protected SlicePlotter<Source> plotter;
    protected OverType overType = OverType.Last;

    @SuppressWarnings("unchecked")
    public SliceGroup() {
        plotter = (SlicePlotter<Source>) new LongSlicePlotter();
    }

    public SliceGroup(SlicePlotter<Source> plotter) {
        this.plotter = plotter;

    }

    public abstract Slice get(Source key, Function function);

    public abstract Slice get(Source key);

    public abstract Slice first(Source key);

    public abstract Slice first(Source key, Function function);

    public abstract Slice last(Source key);

    public abstract Slice last(Source key, Function function);

    public abstract Map<Long, LogicalSlice<Source>> getSlices();

    protected Slice dealOver(Source key, Function function, long id) {

        switch (overType) {
        case First:
            return function == null ? first(key) : first(key, function);

        case Last:
            return function == null ? last(key) : last(key, function);

        case New:
            return null;

        case Exception:
            throw new NonExistedSliceException("id=" + id + " non-existed slice.");
        default:
            throw new NonExistedSliceException("id=" + id + " non-existed slice.");
        }
    }

    protected Slice getSlice(
        LogicalSlice<Source> logicSlice,
        Source key,
        Function function,
        long id) {
        if (logicSlice == null) {
            return dealOver(key, function, id);
        }
        Slice slice = logicSlice.get(id, function);
        if (slice == null) {
            return logicSlice.getChild(key, function);
        }
        return slice;
    }

    protected Slice getSlice(LogicalSlice<Source> logicSlice, Source key, long id) {
        if (logicSlice == null) {
            return dealOver(key, null, id);
        }
        Slice slice = logicSlice.getAny(id);
        if (slice == null) {
            return logicSlice.getChild(key);
        }
        return slice;
    }

    public void addLogicSlice(long id, LogicalSlice<Source> slice) {
        getSlices().put(id, slice);
    }

    public void setPlotter(SlicePlotter<Source> plotter) {
        this.plotter = plotter;
    }

    public void setOverType(OverType overType) {
        this.overType = overType;
    }
}
