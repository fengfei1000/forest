package fengfei.forest.slice;


public abstract class LogicalSlice<Source> extends Slice {

	protected SliceGroup<Source> subSliceGroup;
	protected SliceAlgorithmType algorithmType;

	public LogicalSlice() {
		setPhysical(false);
		setAlgorithmType(SliceAlgorithmType.Hash);
	}

	public SliceGroup<Source> getSubSliceGroup() {
		return subSliceGroup;
	}

	public void setSubSliceGroup(SliceGroup<Source> subSliceGroup) {
		this.subSliceGroup = subSliceGroup;
	}

	public SliceAlgorithmType getAlgorithmType() {
		return algorithmType;
	}

	public LogicalSlice(SliceAlgorithmType algorithmType) {
		super();
		setAlgorithmType(algorithmType);
	}

	public void addSlice(Slice slice, String func) {
		Function function = Function.valueOf(func);
		addSlice(slice, function);

	}

	public abstract void addSlice(Slice slice, Function function);

	public abstract Slice get(long seed, Function function);

	public abstract Slice getAny(long seed);

	public abstract void setAlgorithmType(SliceAlgorithmType algorithmType);

	@Override
	public String toString() {
		return "LogicalSlice [subSliceGroup=" + subSliceGroup + ", algorithmType=" + algorithmType + ", id=" + id + ", suffix=" + suffix + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}

}
