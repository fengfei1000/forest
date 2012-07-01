package fengfei.forest.slice.config.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartitionConfig<Key> {

	private Key sourceKey;
	private String name;
	private String unitSuffix;
	private Set<PartitionConfig<Key>> subPatitions = new HashSet<PartitionConfig<Key>>();
	private Map<String, String> extraInfo = new HashMap<String, String>();

	public PartitionConfig() {
	}

	public PartitionConfig(Key sourceKey, String name, String unitSuffix) {
		super();
		this.sourceKey = sourceKey;
		this.name = name;
		this.unitSuffix = unitSuffix;
	}

	public PartitionConfig(Key sourceKey, String name, String unitSuffix,
			Set<PartitionConfig<Key>> subPatitions) {
		super();
		this.sourceKey = sourceKey;
		this.name = name;
		this.unitSuffix = unitSuffix;
		this.subPatitions = subPatitions;
	}

	public Map<String, String> getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(Map<String, String> extraInfo) {
		this.extraInfo = extraInfo;
	}

	public void addExtraInfo(String key, String value) {
		this.extraInfo.put(key, value);
	}

	public Set<PartitionConfig<Key>> getSubPatitions() {
		return subPatitions;
	}

	public void setSubPatitions(Set<PartitionConfig<Key>> subPatitions) {
		this.subPatitions = subPatitions;
	}

	public void addSubPatition(PartitionConfig<Key> subPatition) {

		this.subPatitions.add(subPatition);
	}

	public Key getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(Key sourceKey) {
		this.sourceKey = sourceKey;
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
	public String toString() {
		return "PartitionConfig [sourceKey=" + sourceKey + ", name=" + name + ", unitSuffix=" + unitSuffix + ", subPatitions=" + subPatitions + ", extraInfo=" + extraInfo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((sourceKey == null) ? 0 : sourceKey.hashCode());
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
		PartitionConfig other = (PartitionConfig) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceKey == null) {
			if (other.sourceKey != null)
				return false;
		} else if (!sourceKey.equals(other.sourceKey))
			return false;
		if (unitSuffix == null) {
			if (other.unitSuffix != null)
				return false;
		} else if (!unitSuffix.equals(other.unitSuffix))
			return false;
		return true;
	}

}
