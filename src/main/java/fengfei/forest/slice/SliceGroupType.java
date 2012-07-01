package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;

public enum SliceGroupType {
	Navigable(10),
	Accuracy(20);

	private final int value;
	private static Map<Integer, SliceGroupType> cache = new HashMap<Integer, SliceGroupType>();
	static {
		for (SliceGroupType sliceType : values()) {
			cache.put(sliceType.value, sliceType);
		}
	}

	private SliceGroupType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SliceGroupType valueOf(int value) {
		return cache.get(value);
	}

	public static SliceGroupType find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		SliceGroupType[] fs = values();
		for (SliceGroupType enumType : fs) {
			if (enumType.name().equalsIgnoreCase(name)) {
				return enumType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}

}
