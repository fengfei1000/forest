package fengfei.forest.slice.impl;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceAlgorithmType;

public class EqualityLogicalSlice<Source> extends LogicalSlice<Source> {

    private SliceModel model;

    public EqualityLogicalSlice() {
        super();
    }

    public EqualityLogicalSlice(SliceAlgorithmType algorithmType) {
        super(algorithmType);
    }

    @Override
    public void addSlice(Slice slice, Function function) {
        model.addSlice(slice);
    }

    @Override
    public Slice get(long seed, Function function) {
        return model.next(seed);
    }

    @Override
    public void setAlgorithmType(SliceAlgorithmType algorithmType) {
        switch (algorithmType) {
        case Hash:
            model = new HashSliceModel();
        case Remainder:
            model = new RemainderSliceModel();
        case Loop:
            model = new LoopSliceModel();
        default:
            model = new LoopSliceModel();
        }

    }

    @Override
    public Slice getAny(long id) {

        return model.next(id);
    }

	@Override
	public String toString() {
		return "EqualityLogicalSlice [model=" + model + ", subSliceGroup=" + subSliceGroup + ", algorithmType=" + algorithmType + ", id=" + id + ", suffix=" + suffix + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}

}
