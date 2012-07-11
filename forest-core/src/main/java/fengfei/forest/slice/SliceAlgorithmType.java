package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;

public enum SliceAlgorithmType {
	Hash(0),
	Remainder(1),
	Loop(2);

	private final int value;
	private static Map<Integer, SliceAlgorithmType> cache = new HashMap<Integer, SliceAlgorithmType>();
	static {
		for (SliceAlgorithmType type : values()) {
			cache.put(type.value, type);
		}
	}

	private SliceAlgorithmType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SliceAlgorithmType valueOf(int value) {
		return cache.get(value);
	}

	public static SliceAlgorithmType find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		SliceAlgorithmType[] fs = values();
		for (SliceAlgorithmType enumType : fs) {
			if (enumType.name().equalsIgnoreCase(name)) {
				return enumType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}
}
