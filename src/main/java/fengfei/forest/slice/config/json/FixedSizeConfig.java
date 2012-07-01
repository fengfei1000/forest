package fengfei.forest.slice.config.json;


public class FixedSizeConfig extends SliceConfig {

    private int rangeSize = 1;
    private int partitionSize = 1;
    private String unitSuffix = "_";

    public FixedSizeConfig() {

    }

    public int getRangeSize() {
        return rangeSize;
    }

    public void setRangeSize(int rangeSize) {
        this.rangeSize = rangeSize;
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
