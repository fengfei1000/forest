package fengfei.forest.slice.config.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import fengfei.forest.slice.OverType;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.SliceGroupType;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.impl.NavigableSliceGroup.NavigationType;

@Root(name = "group", strict = false)
public class GroupConfig implements Cloneable {

    @Attribute
    public String id;
    @Element(required = false)
    public String equalizerClass;
    @Attribute(required = false)
    public String type;
    @Attribute(name = "extends", required = false)
    public String parentId;

    @Element(required = false)
    public String over = OverType.Last.name();
    @Element(required = false)
    public String unitSuffix = "_";
    @Element(required = false)
    public String funcType;
    @Element(required = false)
    public String navigationType = NavigationType.Floor.name();
    @Element(required = false)
    public String algorithmType = SliceAlgorithmType.Loop.name();
    @ElementMap(attribute = true, key = "key", value = "value", required = false)
    public Map<String, String> defaultExtraInfo = new HashMap<>();
    @ElementList(name = "slices", required = false)
    public Set<SliceConfig> slices = new HashSet<>();

    public GroupConfig() {
    }

    public GroupConfig(
        String id,
        String equalizerClass,
        String type,
        OverType over,
        String unitSuffix) {
        super();
        this.id = id;
        this.equalizerClass = equalizerClass;
        this.type = type;
        this.over = over.name();
        this.unitSuffix = unitSuffix;
    }

    public OverType getOverType() {
        return OverType.find(over);
    }

    public FunctionType getFunctionType() {
        return FunctionType.find(funcType);
    }

    public SliceAlgorithmType getSliceAlgorithmType() {
        return SliceAlgorithmType.find(algorithmType);
    }

    public Set<SliceConfig> getSlices() {
        return slices;
    }

    public List<SliceConfig> getSliceList() {
        return new ArrayList<>(slices);
    }

    public String getUnitSuffix() {
        return unitSuffix;
    }

    public SliceGroupType getSliceGroupType() {
        return SliceGroupType.find(type);
    }

    public NavigationType getNavigationType() {
        return NavigationType.find(navigationType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEqualizerClass() {
        return equalizerClass;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getDefaultExtraInfo() {
        return defaultExtraInfo;
    }

    public void setDefaultExtraInfo(Map<String, String> defaultExtraInfo) {
        this.defaultExtraInfo = defaultExtraInfo;
    }

    public void addDefaultExtraInfo(Map<String, String> defaultExtraInfo) {
        this.defaultExtraInfo.putAll(defaultExtraInfo);
    }

    public String getParentId() {
        return parentId;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getType() {
        return type;
    }

    public void setEqualizerClass(String equalizerClass) {
        this.equalizerClass = equalizerClass;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setUnitSuffix(String unitSuffix) {
        this.unitSuffix = unitSuffix;
    }

    public void setNavigationType(String navigationType) {
        this.navigationType = navigationType;
    }

    public String getNavigationTypeString() {
        return this.navigationType;
    }

    public void setSlices(Set<SliceConfig> slices) {
        this.slices = slices;
    }

    public void addSlices(Set<SliceConfig> slices) {
        this.slices.addAll(slices);
    }

    public void addSlices(SliceConfig slice) {
        this.slices.add(slice);
    }

    @Override
    public String toString() {
        return "\n	GroupConfig [id=" + id + ", equalizerClass=" + equalizerClass + ", SliceGroupType="
                + type + ", SliceAlgorithmType=" + algorithmType + ", over=" + over
                + ", unitSuffix=" + unitSuffix + ",\n	defaultExtraInfo=" + defaultExtraInfo
                + ", \n	slices=" + slices + "]";
    }

    @Override
    protected GroupConfig clone() throws CloneNotSupportedException {
        return (GroupConfig) super.clone();
    }

    public GroupConfig copy() throws CloneNotSupportedException {
        GroupConfig config = new GroupConfig();
        config.setAlgorithmType(getAlgorithmType());
        config.setDefaultExtraInfo(new HashMap<>(getDefaultExtraInfo()));
        config.setFuncType(getFuncType());
        config.setId(getId());
        config.setNavigationType(getNavigationTypeString());
        config.setOver(getOver());
        config.setParentId(getParentId());
        config.setEqualizerClass(getEqualizerClass());
        config.setType(getType());
        config.setSlices(new HashSet<>(getSlices()));
        config.setUnitSuffix(getUnitSuffix());

        return config;

    }

}