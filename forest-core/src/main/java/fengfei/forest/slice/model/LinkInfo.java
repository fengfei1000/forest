package fengfei.forest.slice.model;

public class LinkInfo {

	private SliceId upId;
	private SliceId downId;
	private int weight;

	public LinkInfo(SliceId upId, SliceId downId, int weight) {
		super();
		this.upId = upId;
		this.downId = downId;
		this.weight = weight;
	}

	public SliceId getUpId() {
		return upId;
	}

	public void setUpId(SliceId upId) {
		this.upId = upId;
	}

	public SliceId getDownId() {
		return downId;
	}

	public void setDownId(SliceId downId) {
		this.downId = downId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((downId == null) ? 0 : downId.hashCode());
		result = prime * result + ((upId == null) ? 0 : upId.hashCode());
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkInfo other = (LinkInfo) obj;
		if (downId == null) {
			if (other.downId != null)
				return false;
		} else if (!downId.equals(other.downId))
			return false;
		if (upId == null) {
			if (other.upId != null)
				return false;
		} else if (!upId.equals(other.upId))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LinkInfo [upId=" + upId + ", downId=" + downId + ", weight="
				+ weight + "]";
	}

}
