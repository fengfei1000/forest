package fengfei.forest.slice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SliceGroupType;
import fengfei.forest.slice.SliceEqualizer;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.config.xml.Config;
import fengfei.forest.slice.config.xml.GroupConfig;
import fengfei.forest.slice.config.xml.SliceConfig;
import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.NavigableSliceGroup.NavigationType;

public class SliceGroupFactory {

	protected Map<String, SliceGroup<?>> sliceGroupCache = new HashMap<>();
	protected Map<String, GroupConfig> groupConfigCache = new HashMap<>();
	protected LogicalSliceFactory logicalSliceFactory = new LogicalSliceFactory(
			this);

	public void config(Config config) {
		List<GroupConfig> groups = config.getGroups();
		for (GroupConfig group : groups) {
			groupConfigCache.put(group.getId(), group);
		}
		for (GroupConfig group : groups) {
			try {
				GroupConfig groupConfig = inherit(group);
				groupConfigCache.put(group.getId(), groupConfig);
			} catch (CloneNotSupportedException e) {

				e.printStackTrace();
			}
		}

	}

	private GroupConfig inherit(GroupConfig group)
			throws CloneNotSupportedException {
		String parentId = group.getParentId();
		if (parentId == null) {
			return group;
		} else {
			GroupConfig parentGroup = groupConfigCache.get(parentId);
			if (parentGroup == null) {
				return group;
			} else {
				GroupConfig config = inherit(parentGroup);
				// FIXME copy
				config = config.copy();
				config.setId(group.getId());
				config.setAlgorithmType(group.getAlgorithmType() == null ? config
						.getAlgorithmType() : group.getAlgorithmType());
				config.setFuncType(group.getFuncType() == null ? config
						.getFuncType() : group.getFuncType());
				config.setNavigationType(group.getNavigationTypeString() == null ? config
						.getNavigationTypeString() : group
						.getNavigationTypeString());
				config.setOver(group.getOver() == null ? config.getOver()
						: group.getOver());
				config.setEqualizerClass(group.getEqualizerClass() == null ? config
						.getEqualizerClass() : group.getEqualizerClass());
				config.setType(group.getType() == null ? config.getType()
						: group.getType());
				config.setUnitSuffix(group.getUnitSuffix() == null ? config
						.getUnitSuffix() : group.getUnitSuffix());
				config.addDefaultExtraInfo(new HashMap<>(group
						.getDefaultExtraInfo()));
				config.setSlices(group.getSlices());

				return config;

			}

		}

	}

	@SuppressWarnings("unchecked")
	<Source> SliceGroup<Source> create(GroupConfig group) {
		SliceGroupType type = group.getSliceGroupType();
		String equalizerClass = group.getEqualizerClass();

		SliceGroup<Source> sliceGroup = null;
		SliceEqualizer<Source> equalizer = null;
		if (equalizerClass == null || "".equals(equalizerClass)) {
			equalizer = (SliceEqualizer<Source>) new LongSliceEqualizer();
		} else {
			equalizer = newInstance(equalizerClass);
		}

		switch (type) {
		case Navigable:
			sliceGroup = new NavigableSliceGroup<>(equalizer);
			break;
		case Accuracy:
			sliceGroup = new AccuracySliceGroup<>(equalizer);
			break;

		default:
			throw new IllegalArgumentException("Can't support agr.");

		}
		sliceGroup.setOverType(group.getOverType());
		build(type, group, sliceGroup);
		return sliceGroup;

	}

	private <Source> void build(SliceGroupType sliceGroupType,
			GroupConfig group, SliceGroup<Source> sliceGroup) {

		FunctionType type = group.getFunctionType();
		NavigationType navigationType = group.getNavigationType();
		List<SliceConfig> sliceConfigs = group.getSliceList();

		int size = sliceConfigs.size();
		for (int i = 0; i < size; i++) {
			SliceConfig sliceConfig = sliceConfigs.get(i);
			String sourceKey = sliceConfig.getSourceKey();
			String sliceId = sliceConfig.getId();
			if (sliceId == null || "".equals(sliceId)) {
				sliceConfig.setId(String.valueOf(i));
			}
			List<Long> keys = splitSourceKey(navigationType, sliceGroupType,
					sourceKey);
			LogicalSlice<Source> slice = logicalSliceFactory.create(
					sliceConfig, type, group);

			for (Long id : keys) {
				sliceGroup.addLogicSlice(id, slice);
			}
		}

	}

	private List<Long> splitSourceKey(NavigationType navigationType,
			SliceGroupType sliceGroupType, String sourceKey) {
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
					} else {
						longs.add(Long.parseLong(sks[0]));
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
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {

			throw new IllegalArgumentException("non-exist class: " + className);
		}

	}

	public <Source> SliceGroup<Source> getSliceGroup(String unitName) {
		@SuppressWarnings("unchecked")
		SliceGroup<Source> sliceGroup = (SliceGroup<Source>) sliceGroupCache
				.get(unitName);
		if (sliceGroup == null) {
			GroupConfig group = groupConfigCache.get(unitName);
			System.out.println("1:  " + group);
			sliceGroup = create(group);
			sliceGroupCache.put(group.getId(), sliceGroup);
		}
		if (sliceGroup == null) {
			throw new NonExistedSliceException("unitName=" + unitName);
		}
		return sliceGroup;

	}

	public <Source> SliceGroup<Source> getSliceGroup(
			SliceEqualizer<Source> equalizer, String unitName) {
		SliceGroup<Source> sliceGroup = getSliceGroup(unitName);
		if (equalizer != null) {
			sliceGroup.setEqualizer(equalizer);
		}

		return sliceGroup;

	}

}
