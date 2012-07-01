package fengfei.forest.slice.config.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fengfei.forest.slice.OverType;
import fengfei.forest.slice.SliceType;

public class SliceConfig {

    protected String name;

    protected OverType over = OverType.Last;
    private String unitSuffix;
    private Map<String, String> defaultExtraInfo = new HashMap<String, String>();
    private Set<PartitionConfig<Long>> partitions = new HashSet<PartitionConfig<Long>>();

    public Set<PartitionConfig<Long>> getPartitions() {
        return partitions;
    }

    public void setPartitions(Set<PartitionConfig<Long>> partitions) {
        this.partitions = partitions;
    }

    public void addPartition(PartitionConfig<Long> partition) {
        this.partitions.add(partition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public OverType getOver() {
        return over;
    }

    public void setOver(OverType over) {
        this.over = over;
    }
}
