package fengfei.forest.slice.config.xml;

import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.config.SliceKind;

public class SliceConfig {

	@Attribute(required = false)
	protected String id;

	@Attribute(required = false, empty = "Logical")
	protected String kind;

	@Attribute(required = false)
	protected int weight = 1;

	@Attribute(required = false)
	protected String func;

	@Element(required = false, name = "sourceKey")
	private String sourceKey;

	@ElementMap(
			entry = "property",
			inline = true,
			attribute = true,
			key = "key",
			value = "value",
			required = false)
	private Map<String, String> extraInfo;

	@ElementList(entry = "slice", inline = true, required = false)
	private List<SliceConfig> subSlices;

	@Element(name = "subGroup", required = false)
	private GroupConfig subGroup;

	public SliceKind getSliceKind() {
		return SliceKind.find(kind);
	}

	public Function getFunction() {
		return Function.find(func);
	}

	public String getId() {
		return id;
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public String getKind() {
		return kind;
	}

	public int getWeight() {
		return weight;
	}

	public String getFunc() {
		return func;
	}

	public Map<String, String> getExtraInfo() {
		return extraInfo;
	}

	public List<SliceConfig> getSubSlices() {
		return subSlices;
	}

	public GroupConfig getSubGroup() {
		return subGroup;
	}

	@Override
	protected SliceConfig clone() throws CloneNotSupportedException {
		return (SliceConfig) super.clone();
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public void setKind(String kind) {
		this.kind = kind;
	}

	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	public void setFunc(String func) {
		this.func = func;
	}

	
	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	
	public void setExtraInfo(Map<String, String> extraInfo) {
		this.extraInfo = extraInfo;
	}

	
	public void setSubSlices(List<SliceConfig> subSlices) {
		this.subSlices = subSlices;
	}

	
	public void setSubGroup(GroupConfig subGroup) {
		this.subGroup = subGroup;
	}

	@Override
	public String toString() {
		return "\n		SliceConfig [id=" + id + ", kind=" + kind + ", weight=" + weight + ", sourceKey=" + sourceKey + ", func=" + func + " \n		extraInfo=" + extraInfo + ", \n		subSlices=" + subSlices + ",\n		subGroup=" + subGroup + "]";
	}

}
