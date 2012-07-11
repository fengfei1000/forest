package fengfei.forest.slice.config.json;


public class HashConfig extends SliceConfig {

	private int partitionSize = 1;
	private String unitSuffix = "_";

	public HashConfig() {

	}

	public int getPartitionSize() {
		return partitionSize;
	}

	public void setPartitionSize(int partitionSize) {
		this.partitionSize = partitionSize;
	}

	public String getUnitSuffix() {
		return unitSuffix;
	}

	public void setUnitSuffix(String unitSuffix) {
		this.unitSuffix = unitSuffix;
	}

}
