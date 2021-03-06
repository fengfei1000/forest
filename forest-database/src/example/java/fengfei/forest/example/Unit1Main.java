package fengfei.forest.example;

import java.net.URL;

import javax.sql.DataSource;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.config.xml.Config;
import fengfei.forest.slice.config.xml.XmlSliceReader;
import fengfei.forest.slice.database.DatabaseSliceGroupFactory;
import fengfei.forest.slice.database.PoolableServerSlice;
import fengfei.forest.slice.database.PoolableSliceGroup;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class Unit1Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		URL url = Unit1Main.class.getClassLoader().getResource(
				"config/config.xml");
		SliceReader<SliceGroupFactory> reader = new XmlSliceReader();
		SliceGroupFactory factory = new SliceGroupFactory();
		Config config = reader.readConfig(url.getFile());
		System.out.println(config);
		String unitName = "unit1";
		DatabaseSliceGroupFactory groupFactory = new DatabaseSliceGroupFactory();
		groupFactory.config(config);
		PoolableSliceGroup<Long> group = groupFactory
				.getPoolableSliceGroup(unitName);
		System.out.println(group);
		PoolableServerSlice slice = group.getPoolableServerSlice(1l);
		System.out.println(slice.getDataSource());
		System.out.println(slice.getDataSource().getConnection());
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
