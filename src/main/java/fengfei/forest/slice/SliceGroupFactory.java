package fengfei.forest.slice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fengfei.forest.slice.NavigableSliceGroup.NavigationType;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.config.xml.Config;
import fengfei.forest.slice.config.xml.GroupConfig;
import fengfei.forest.slice.config.xml.SliceConfig;

public class SliceGroupFactory {

	private Map<String, SliceGroup<?>> sliceGroupCache = new HashMap<>();
	private Map<String, GroupConfig> groupConfigCache = new HashMap<>();
	private LogicalSliceFactory logicalSliceFactory = new LogicalSliceFactory(this);

	public void config(Config config) {
		List<GroupConfig> groups = config.getGroups();
		for (GroupConfig group : groups) {
			groupConfigCache.put(group.getId(), group);
		}
	}

	<Source> SliceGroup<Source> create(GroupConfig group) {
		SliceGroupType type = group.getSliceGroupType();
		String plotterClass = group.getPlotterClass();

		SliceGroup<Source> sliceGroup = null;
		SlicePlotter<Source> plotter = newInstance(plotterClass);
		switch (type) {
		case Navigable:
			sliceGroup = new NavigableSliceGroup<>(plotter);
			break;
		case Accuracy:
			sliceGroup = new AccuracySliceGroup<>(plotter);
			break;

		default:
			throw new IllegalArgumentException("Can't support agr.");

		}
		build(type, group, sliceGroup);
		return sliceGroup;

	}

	private <Source> void build(
			SliceGroupType sliceGroupType,
			GroupConfig group,
			SliceGroup<Source> sliceGroup) {

		FunctionType type = group.getFunctionType();
		NavigationType navigationType = group.getNavigationType();
		List<SliceConfig> sliceConfigs = group.getSlices();
		group.getUnitSuffix();
		int size = sliceConfigs.size();
		for (int i = 0; i < size; i++) {
			SliceConfig sliceConfig = sliceConfigs.get(i);
			String sourceKey = sliceConfig.getSourceKey();
			String sliceId = sliceConfig.getId();
			if (sliceId == null || "".equals(sliceId)) {
				sliceConfig.setId(String.valueOf(i));
			}
			List<Long> keys = splitSourceKey(navigationType, sliceGroupType, sourceKey);
			LogicalSlice<Source> slice = logicalSliceFactory.create(sliceConfig, type, group);

			for (Long id : keys) {
				sliceGroup.addLogicSlice(id, slice);
			}
		}

	}

	private List<Long> splitSourceKey(
			NavigationType navigationType,
			SliceGroupType sliceGroupType,
			String sourceKey) {
		List<Long> longs = new ArrayList<>();

		switch (sliceGroupType) {
		case Navigable:
			String[] sources = sourceKey.split(",| ");
			for (String sk : sources) {
				if (sk != null && !"".equals(sk.trim())) {
					String[] sks = sk.split("~|-|－|——|—|--");
					if (sks.length >= 2) {
						if (navigationType.equals(NavigationType.Floor)) {
							longs.add(Long.parseLong(sks[1]));
						} else {
							longs.add(Long.parseLong(sks[0]));
						}
					}
				}
			}

			break;
		case Accuracy:
			sources = sourceKey.split(",|，| |~|-|－|——|—|--");
			for (String sk : sources) {
				longs.add(Long.parseLong(sk));
			}
			break;
		}
		return longs;

	}

	@SuppressWarnings("unchecked")
	private <T> T newInstance(String className) {
		try {
			return (T) Class.forName(className.trim()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {

			throw new IllegalArgumentException("non-exist class: " + className);
		}

	}

	public <Source> SliceGroup<Source> getSliceGroup(String unitName) {
		@SuppressWarnings("unchecked")
		SliceGroup<Source> sliceGroup = (SliceGroup<Source>) sliceGroupCache.get(unitName);
		if (sliceGroup == null) {
			GroupConfig group = groupConfigCache.get(unitName);
			sliceGroup = create(group);
			sliceGroupCache.put(group.getId(), sliceGroup);
		}
		return sliceGroup;

	}

}
