package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;

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

	public Slice getChild(Source key, Function function) {
		if (subSliceGroup == null) {
			return null;
		}
		return subSliceGroup.get(key, function);
	}

	public Slice getChild(Source key) {
		if (subSliceGroup == null) {
			return null;
		}
		return subSliceGroup.get(key);
	}

	protected void mergeInheritInfoTo(Slice slice) {
		Map<String, String> extraInfo = new HashMap<String, String>(slice.getExtraInfo());
		slice.addExtraInfo(getExtraInfo());
		slice.addExtraInfo(extraInfo);
	}

	public abstract void addSlice(Slice slice, Function function);

	public abstract Slice get(long seed, Function function);

	public abstract Slice getAny(long seed);

	public abstract void setAlgorithmType(SliceAlgorithmType algorithmType);

	public abstract boolean fail(Slice slice);

	public abstract boolean recover(Slice slice);

	 
}
