package fengfei.forest.slice.config.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import fengfei.forest.slice.SliceType;

public class SliceConfigReader<T extends SliceConfig> {

	private Map<String, String> config;
	private SliceConfig sliceConfig;

	public static void main(String[] args) {
		SliceConfigReader<RangeConfig> reader = new SliceConfigReader<RangeConfig>();
		InputStream in = SliceConfigReader.class.getClassLoader().getResourceAsStream(
				"config/slice.json");

		RangeConfig config = reader.read(in);
		System.out.println(config);
	}

	public T read(String filePath) {

		try {
			InputStream in = new FileInputStream(filePath);
			return read(in);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public T read(InputStream in) {
		ObjectMapper mapper = new ObjectMapper();

		try {

			config = mapper.readValue(in, Map.class);
			String type = config.get("type");
			SliceType sliceType = SliceType.valueOf(type);
			String json = mapper.writeValueAsString(config);

			// switch (sliceType) {
			// case Range:
			// sliceConfig = mapper.readValue(json, RangeConfig.class);
			// break;
			// case FixedRange:
			// sliceConfig = mapper.readValue(json, FixedRangeConfig.class);
			// break;
			//
			// case MixedRange:
			// sliceConfig = mapper.readValue(json, MixedRangeConfig.class);
			// break;
			// case Hash:
			// sliceConfig = mapper.readValue(json, HashConfig.class);
			// break;
			// case List:
			// sliceConfig = mapper.readValue(json, ListConfig.class);
			// break;
			//
			// default:
			// break;
			// }

			return (T) sliceConfig;

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private T map(Map<String, String> config) {
		String type = config.get("type");
		SliceType sliceType = SliceType.valueOf(type);
		SliceConfig sliceConfig = null;
		// switch (sliceType) {
		// case Range:
		// sliceConfig = populate(RangeConfig.class, config);
		// break;
		// case FixedRange:
		// sliceConfig = populate(FixedRangeConfig.class, config);
		// break;
		//
		// case Hash:
		// sliceConfig = populate(HashConfig.class, config);
		// break;
		// case List:
		// sliceConfig = populate(ListConfig.class, config);
		// break;
		//
		// default:
		// break;
		// }
		return (T) sliceConfig;
	}

	private <T> T populate(Class<T> clazz, Map<String, String> config) {
		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, config);
			return bean;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
