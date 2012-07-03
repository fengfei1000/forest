package fengfei.forest.slice.config.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class GroupConfig {

	@Attribute
	private String id;
	@Element(required = false)
	private String plotterClass;
	@Attribute
	private String type;
	@Element(required = false)
	protected String over = OverType.Last.name();
	@Element
	private String unitSuffix = "_";
	@Element(required = false)
	protected String funcType;
	@Element(required = false)
	protected String navigationType = NavigationType.Floor.name();
	@Element(required = false)
	protected String algorithmType = SliceAlgorithmType.Loop.name();
	@ElementMap(attribute = true, key = "key", value = "value")
	private Map<String, String> defaultExtraInfo = new HashMap<>();
	@ElementList(name = "slices")
	private List<SliceConfig> slices = new ArrayList<>();

	public GroupConfig() {
	}

	public GroupConfig(
			String id,
			String plotterClass,
			String type,
			OverType over,
			String unitSuffix) {
		super();
		this.id = id;
		this.plotterClass = plotterClass;
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

	public List<SliceConfig> getSlices() {
		return slices;
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

	public String getPlotterClass() {
		return plotterClass;
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

	@Override
	public String toString() {
		return "\n	GroupConfig [id=" + id + ", plotterClass=" + plotterClass + ", SliceGroupType=" + type + ", SliceAlgorithmType=" + algorithmType + ", over=" + over + ", unitSuffix=" + unitSuffix + ",\n	defaultExtraInfo=" + defaultExtraInfo + ", \n	slices=" + slices + "]";
	}

}