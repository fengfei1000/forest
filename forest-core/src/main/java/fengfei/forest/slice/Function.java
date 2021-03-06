package fengfei.forest.slice;
/**
 * 
 * @author tietang
 *
 */
public enum Function {
	Normal,
	Read,
	Write,
	ReadWrite,
	Master,
	Slave;

	public static Function find(String name) {
		if (name == null || "".equals(name)) {
			return null;
		}
		Function[] fs = values();
		for (Function enumType : fs) {
			if (enumType.name().equalsIgnoreCase(name)) {
				return enumType;
			}

		}
		throw new IllegalArgumentException("Non-exist the enum type,error arg name:" + name);
	}

}
