package fengfei.forest.slice.config.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root 
public class Config {

	@ElementList(name = "Units", type = GroupConfig.class, inline = true)
	private List<GroupConfig> groups = new ArrayList<>();

	private Map<String, GroupConfig> groupMap = new HashMap<>();

	public List<GroupConfig> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupConfig> groups) {
		this.groups = groups;
	}

	public Map<String, GroupConfig> getGroupMap() {
		if (groupMap == null) {
			groupMap = new HashMap<>();
		}
		for (GroupConfig group : groups) {
			groupMap.put(group.getId(), group);
		}
		return groupMap;
	}

	@Override
	public String toString() {
		return "Units [	groups=" + groups + ",\ngroupMap=" + groupMap + "]";
	}
}
