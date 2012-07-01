package fengfei.forest.slice.config.xml;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.TreeMap;

public class MAIN2 {

	static int num = 500000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		TreeMap<String, String> treeMap = new TreeMap<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			treeMap.put("key-" + i, "value" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("treeMap put: " + (end - start));
		start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			String value = treeMap.get("key-" + i);
		}
		end = System.currentTimeMillis();
		System.out.println("treeMap get: " + (end - start));
		start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Entry<String, String> value = treeMap.floorEntry("key-" + i);
		}
		end = System.currentTimeMillis();
		System.out.println("treeMap floorEntry: " + (end - start));
	}

	private static void test2() {
		HashMap<String, String> map = new HashMap<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			map.put("key-" + i, "value" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("HashMap put: " + (end - start));
		start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			String value = map.get("key-" + i);
		}
		end = System.currentTimeMillis();
		System.out.println("HashMap get: " + (end - start));

	}

}
