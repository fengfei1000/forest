package fengfei.forest.slice.config.xml;

import java.io.InputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SliceGroupFactory;

public class XmlMain {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		InputStream in = XmlMain.class.getClassLoader().getResourceAsStream(
				"config/config.xml");
		// System.out.println(in);

		Config config = serializer.read(Config.class, in, false);
		 System.out.println(config);
		in.close();
		SliceGroupFactory factory = new SliceGroupFactory();
		factory.config(config);
		String unitName = "unit2";
		SliceGroup<Long> group = factory.getSliceGroup(unitName);
		// System.out.println(group);
		System.out.println(group.getAny(1l));
		System.out.println(group.getAny(2l));
		System.out.println(group.getAny(1l));
		System.out.println(group.getAny(2l));
		System.out.println(group.get(1l, Function.Read));
		System.out.println(group.get(2l, Function.Read));
		System.out.println(group.get(1l, Function.Read));
		System.out.println(group.get(2l, Function.Read));
		System.out.println(group.get(1l, Function.Write));
		System.out.println(group.get(2l, Function.Write));
		System.out.println(group.get(1l, Function.Write));
		System.out.println(group.get(2l, Function.Write));

		// Config config2 = new Config();
		// config2.getGroups().add(
		// new GroupConfig("1", "class", "A", OverType.Exception, ""));
		// serializer.write(config2, new File("xx.xml"));
		// ObjectMapper mapper = new ObjectMapper();
		// mapper.writeValue(new File("xx.json"), config2);

	}

}
