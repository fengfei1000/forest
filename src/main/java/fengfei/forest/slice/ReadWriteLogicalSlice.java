package fengfei.forest.slice;

public class ReadWriteLogicalSlice<Source> extends LogicalSlice<Source> {

    private SliceModel read;
    private SliceModel write;
    private SliceModel all;

    public ReadWriteLogicalSlice() {
        super();
    }

    public ReadWriteLogicalSlice(SliceAlgorithmType algorithmType) {
        super(algorithmType);
    }

    public void addSlice(Slice slice, Function function) {

        switch (function) {
        case Read:
            read.addSlice(slice);
            all.addSlice(slice);
            break;
        case Write:
            write.addSlice(slice);
            all.addSlice(slice);
            break;
        case ReadWrite:
            read.addSlice(slice);
            write.addSlice(slice);
            all.addSlice(slice);
            break;
        default:
            break;
        }
    }

    public void addSlice(Slice slice, String func) {
        Function function = Function.valueOf(func);
        addSlice(slice, function);
    }

    @Override
    public void setAlgorithmType(SliceAlgorithmType algorithmType) {
        switch (algorithmType) {
        case Hash:
            read = new HashSliceModel();
            write = new HashSliceModel();
            all = new HashSliceModel();
        case Remainder:
            read = new RemainderSliceModel();
            write = new RemainderSliceModel();
            all = new RemainderSliceModel();
        case Loop:
            read = new LoopSliceModel();
            write = new LoopSliceModel();
            all = new LoopSliceModel();
        default:
            read = new LoopSliceModel();
            write = new LoopSliceModel();
            all = new LoopSliceModel();
        }

    }

    public Slice get(long seed, Function function) {
        switch (function) {
        case Read:
            return read.next(seed);
        case Write:
            return write.next(seed);
        case ReadWrite:
            return all.next(seed);
        default:
            break;
        }
        return null;

    }

    @Override
    public Slice getAny(long seed) {
        return all.next(seed);
    }

	@Override
	public String toString() {
		return "ReadWriteLogicalSlice [read=" + read + ", write=" + write + ", all=" + all + ", subSliceGroup=" + subSliceGroup + ", algorithmType=" + algorithmType + ", id=" + id + ", suffix=" + suffix + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}

}
