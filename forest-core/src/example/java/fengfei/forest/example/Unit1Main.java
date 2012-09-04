package fengfei.forest.example;

import java.io.InputStream;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.config.xml.Config;
import fengfei.forest.slice.config.xml.XmlSliceReader;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class Unit1Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// InputStream in =
		// Unit1Main.class.getClassLoader().getResourceAsStream(
		// "config/config.xml");
		URL url = Unit1Main.class.getClassLoader().getResource(
				"config/config.xml");
		SliceReader<SliceGroupFactory> reader = new XmlSliceReader();
		SliceGroupFactory factory = new SliceGroupFactory();
		Config config = reader.readConfig(url.getFile());
		factory.config(config);
		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(config);
		System.out.println(s);
		// in.close();
		String unitName = "unit1";
		SliceGroup<Long> group = factory.getSliceGroup(unitName);
		System.out.println(group);
		System.out.println(group.get(1l));
		System.out.println(group.get(2l));
		System.out.println(group.get(1l));
		System.out.println(group.get(2l));
		System.out.println(group.get(1l, Function.Read));
		System.out.println(group.get(2l, Function.Read));
		System.out.println(group.get(1l, Function.Read));
		System.out.println(group.get(2l, Function.Read));
		System.out.println(group.get(1l, Function.Write));
		System.out.println(group.get(2l, Function.Write));
		// System.out.println(group.get(11l, Function.Write));
		// System.out.println(group.get(11l, Function.Write));

		// Config config2 = new Config();
		// config2.getGroups().add(
		// new GroupConfig("1", "class", "A", OverType.Exception, ""));
		// serializer.write(config2, new File("xx.xml"));
		// ObjectMapper mapper = new ObjectMapper();
		// mapper.writeValue(new File("xx.json"), config2);

	}

}
