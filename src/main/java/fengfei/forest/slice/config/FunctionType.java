package fengfei.forest.slice.config;

public enum FunctionType {
	ReadWrite,
	MasterSlave,
	Equality;

	public static FunctionType find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		FunctionType[] fs = values();
		for (FunctionType functionType : fs) {
			if (functionType.name().equalsIgnoreCase(name)) {
				return functionType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}
}