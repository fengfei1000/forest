package fengfei.forest.slice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.config.SliceKind;
import fengfei.forest.slice.config.xml.GroupConfig;
import fengfei.forest.slice.config.xml.SliceConfig;

class LogicalSliceFactory {

	private PhysicalSliceFactory physicalSliceFactory = new PhysicalSliceFactory();
	private SliceGroupFactory sliceGroupFactory;

	public LogicalSliceFactory(SliceGroupFactory sliceGroupFactory) {
		super();
		this.sliceGroupFactory = sliceGroupFactory;
	}

	public <Source> LogicalSlice<Source> create(
			SliceConfig sliceConfig,
			FunctionType functionType,
			GroupConfig group) {
		Map<String, String> defaultExtraInfo = group.getDefaultExtraInfo();
		SliceKind sliceKind = sliceConfig.getSliceKind();
		LogicalSlice<Source> logicalSlice = null;
		if (sliceKind == SliceKind.Physical) {
			logicalSlice = new EqualityLogicalSlice<>();
		} else {
			switch (functionType) {
			case ReadWrite:
				logicalSlice = new ReadWriteLogicalSlice<>();
				break;
			case Equality:
				logicalSlice = new EqualityLogicalSlice<>();
				break;
			default:
				throw new IllegalArgumentException("Can't support agr.");
			}
		}
		logicalSlice.setAlgorithmType(group.getSliceAlgorithmType());
		GroupConfig subGroup = sliceConfig.getSubGroup();
		if (subGroup != null) {
			subGroup.setParentId(group.getId());
			SliceGroup<Source> sliceGroup = sliceGroupFactory.create(subGroup);
			logicalSlice.setSubSliceGroup(sliceGroup);
		}
		logicalSlice.addExtraInfo(defaultExtraInfo);
		logicalSlice.addExtraInfo(sliceConfig.getExtraInfo());
		logicalSlice.setId(sliceConfig.getId());
		logicalSlice.setPhysical(false);
		logicalSlice.setSliceId(group.getUnitSuffix() + sliceConfig.getId());
		logicalSlice.setWeight(sliceConfig.getWeight());
		List<SliceConfig> subSliceConfigs = null;
		if (sliceKind == SliceKind.Physical) {
			subSliceConfigs = new ArrayList<>();
			subSliceConfigs.add(sliceConfig);
		} else {
			subSliceConfigs = sliceConfig.getSubSlices();
		}
		buildSliceConfig(subSliceConfigs, logicalSlice, functionType);
		return logicalSlice;
	}

	private <Source> void buildSliceConfig(
			List<SliceConfig> subSliceConfigs,
			LogicalSlice<Source> logicalSlice,
			FunctionType functionType) {
		int size = subSliceConfigs.size();
		for (int i = 0; i < size; i++) {
			SliceConfig sliceConfig = subSliceConfigs.get(i);
			String id = sliceConfig.getId();
			PhysicalSlice physicalSlice = physicalSliceFactory.create(
					sliceConfig,
					logicalSlice,
					functionType);
			if (id == null || "".equals(id) || logicalSlice.getId().equals(id)) {
				String pid = logicalSlice.getId();
				physicalSlice
						.setId((pid == null || "".equals(pid)) ? String.valueOf(i) : (pid + "_" + i));
			}
			Function function = sliceConfig.getFunction();
			logicalSlice.addSlice(physicalSlice, function);
		}
	}
}
