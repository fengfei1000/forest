package fengfei.forest.slice.config.json;

import java.util.HashSet;
import java.util.Set;

import fengfei.forest.slice.SliceType;

public class RangeConfig extends SliceConfig {

	private Set<PartitionConfig<Long>> partitions = new HashSet<PartitionConfig<Long>>();

	public RangeConfig() {

	}

	public Set<PartitionConfig<Long>> getPartitions() {
		return partitions;
	}

	public void setPartitions(Set<PartitionConfig<Long>> partitions) {
		this.partitions = partitions;
	}

	public void addPartition(PartitionConfig<Long> partition) {
		this.partitions.add(partition);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partitions == null) ? 0 : partitions.hashCode());
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
		RangeConfig other = (RangeConfig) obj;
		if (partitions == null) {
			if (other.partitions != null)
				return false;
		} else if (!partitions.equals(other.partitions))
			return false;
		return true;
	}
}
