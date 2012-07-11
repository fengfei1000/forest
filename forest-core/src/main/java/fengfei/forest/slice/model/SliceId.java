package fengfei.forest.slice.model;

public class SliceId {
	private String name;
	private String unitSuffix;

	public SliceId(String name, String unitSuffix) {
		super();
		this.name = name;
		this.unitSuffix = unitSuffix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitSuffix() {
		return unitSuffix;
	}

	public void setUnitSuffix(String unitSuffix) {
		this.unitSuffix = unitSuffix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((unitSuffix == null) ? 0 : unitSuffix.hashCode());
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
		SliceId other = (SliceId) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (unitSuffix == null) {
			if (other.unitSuffix != null)
				return false;
		} else if (!unitSuffix.equals(other.unitSuffix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SliceId [id=" + name + ", unitSuffix=" + unitSuffix + "]";
	}

}
