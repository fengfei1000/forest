package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;

public enum SliceType {
	Navigable(10),
	Accuracy(20);

	private final int value;
	private static Map<Integer, SliceType> cache = new HashMap<Integer, SliceType>();
	static {
		for (SliceType sliceType : values()) {
			cache.put(sliceType.value, sliceType);
		}
	}

	private SliceType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SliceType valueOf(int value) {
		return cache.get(value);
	}

	public static SliceType find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		SliceType[] fs = values();
		for (SliceType enumType : fs) {
			if (enumType.name().equalsIgnoreCase(name)) {
				return enumType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}

}
