package fengfei.forest.slice.config;

public enum SliceKind {
	Logical,
	Physical;

	public static SliceKind find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		SliceKind[] fs = values();
		for (SliceKind enumType : fs) {
			if (enumType.name().equalsIgnoreCase(name)) {
				return enumType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}
}
