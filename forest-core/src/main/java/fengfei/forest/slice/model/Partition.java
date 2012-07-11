package fengfei.forest.slice.model;

public class Partition<Key> {
	private String unitId;
	private Key sourceKey;
	private SliceId sliceId;

	public Partition(String unitId, Key sourceKey, SliceId sliceId) {
		super();
		this.unitId = unitId;
		this.sourceKey = sourceKey;
		this.sliceId = sliceId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Key getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(Key sourceKey) {
		this.sourceKey = sourceKey;
	}

	public SliceId getSliceId() {
		return sliceId;
	}

	public void setSliceId(SliceId sliceId) {
		this.sliceId = sliceId;
	}
}
