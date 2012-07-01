package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;

public enum OverType {
	Ignore(0),
	Last(10),
	New(20),
	Scattering(30),
	Exception(40);

	private final int value;
	private static Map<Integer, OverType> cache = new HashMap<Integer, OverType>();
	static {
		for (OverType type : values()) {
			cache.put(type.value, type);
		}
	}

	private OverType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static OverType valueOf(int value) {
		return cache.get(value);
	}

	public static OverType find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		OverType[] fs = values();
		for (OverType enumType : fs) {
			if (enumType.name().equalsIgnoreCase(name)) {
				return enumType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}
}
