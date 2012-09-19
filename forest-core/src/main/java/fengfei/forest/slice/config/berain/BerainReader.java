package fengfei.forest.slice.config.berain;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import fengfei.berain.client.BerainClient;
import fengfei.berain.client.BerainEntry;
import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.config.xml.SliceConfig;
import fengfei.forest.slice.config.xml.Config;
import fengfei.forest.slice.config.xml.GroupConfig;
import fengfei.forest.slice.exception.ErrorSliceConfigException;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class BerainReader implements SliceReader<SliceGroupFactory> {
	private BerainClient client;

	public BerainReader(BerainClient client) {
		super();
		this.client = client;
	}

	@Override
	public SliceGroupFactory read(String path) throws ErrorSliceConfigException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SliceGroupFactory read(InputStream in)
			throws ErrorSliceConfigException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main1(String[] args) {
		Field[] fs = GroupConfig.class.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {

			String name = fs[i].getName();
			System.out.printf(
					"BerainEntry %sPe = client.getFull(tp + \"/%s\");\n", name,
					name);
			System.out.printf("config.%s =%sPe.value;\n", name, name);

		}
	}

	@Override
	public Config readConfig(String path) throws ErrorSliceConfigException {
		List<BerainEntry> chs = client.nextChildren(path);
		for (BerainEntry be : chs) {
			GroupConfig config = new GroupConfig();
			config.id = be.key;
			config.type = be.value;
			String tp = be.path;
			//
			BerainEntry plotterClassPe = client.getFull(tp + "/plotterClass");
			config.plotterClass = plotterClassPe.value;

			BerainEntry typePe = client.getFull(tp + "/type");
			config.type = typePe.value;
			BerainEntry parentIdPe = client.getFull(tp + "/parentId");
			config.parentId = parentIdPe.value;
			BerainEntry overPe = client.getFull(tp + "/over");
			config.over = overPe.value;
			BerainEntry unitSuffixPe = client.getFull(tp + "/unitSuffix");
			config.unitSuffix = unitSuffixPe.value;
			BerainEntry funcTypePe = client.getFull(tp + "/funcType");
			config.funcType = funcTypePe.value;
			BerainEntry navigationTypePe = client.getFull(tp
					+ "/navigationType");
			config.navigationType = navigationTypePe.value;
			BerainEntry algorithmTypePe = client.getFull(tp + "/algorithmType");
			config.algorithmType = algorithmTypePe.value;
			//
			List<BerainEntry> defaultExtraInfos = client.nextChildren(tp
					+ "/defaultExtraInfo");
			Map<String, String> defaultExtraInfo = new HashMap<String, String>();
			for (BerainEntry dbe : defaultExtraInfos) {
				defaultExtraInfo.put(dbe.key, dbe.value);
			}
			config.defaultExtraInfo = defaultExtraInfo;
			//

			config.slices = new HashSet<>(readSliceConfig(tp + "/slices"));
			//
			List<BerainEntry> tps = client.nextChildren(tp);

		}
		return null;
	}

	private List<SliceConfig> readSliceConfig(String path) {
		List<SliceConfig> sliceConfigs = new ArrayList<>();
		List<BerainEntry> slicesPe = client.nextChildren(path);
		for (BerainEntry sbe : slicesPe) {
			SliceConfig sliceConfig = new SliceConfig();
			String value = sbe.value;
			sliceConfig.id = sbe.key;
			sliceConfig.weight = sbe.intValue();
			BerainEntry sourceKeyBe = client.getFull(sbe.path + "/sourceKey");
			sliceConfig.sourceKey = sourceKeyBe.value;
			List<BerainEntry> subs = client.nextChildren(path + "/slice");
			for (BerainEntry sube : subs) {

			}

			sliceConfigs.add(sliceConfig);

		}
		return sliceConfigs;
	}

	private SliceConfig parseSliceConfigValue(BerainEntry sbe) {
		SliceConfig sliceConfig = new SliceConfig();
		sliceConfig.id = sbe.key;
		Map<String, String> kvs = parseSliceConfigValue(sbe.value);
		sliceConfig.weight = getInt(kvs, "weight", 1);
		sliceConfig.kind = kvs.get("kind");
		sliceConfig.func = kvs.get("func");
		sliceConfig.sourceKey = kvs.get("sourceKey");
		return sliceConfig;
	}

	private int getInt(Map<String, String> map, String key, int defalultValue) {
		String value = map.get(key);
		if (value == null) {
			return defalultValue;
		} else {
			return Integer.parseInt(value);
		}
	}

	private Map<String, String> parseSliceConfigValue(String value) {
		Map<String, String> kvs = new HashMap<>();
		String[] strs = value.split("[\r\n|\n]");
		if (strs == null || strs.length <= 0) {
			return kvs;
		}
		for (String str : strs) {
			if (str.trim().equals("")) {
				continue;
			}
			String kv[] = str.split("=");
			if (kv.length >= 2) {
				kvs.put(kv[0], kv[1]);
			}
		}
		return kvs;
	}

	public static void main(String[] args) {
		Map<String, String> kvs = new HashMap<>();
		String str = "k1=v1\nk2=v2\n\rk3=v3";
		String[] s = str.split("[\r\n|\n]");
		for (String sf : s) {
			if (sf.trim().equals("")) {
				continue;
			}
			String kv[] = sf.split("=");
			if (kv.length >= 2) {
				kvs.put(kv[0], kv[1]);
			}

		}
		System.out.println(kvs);
	}
}
