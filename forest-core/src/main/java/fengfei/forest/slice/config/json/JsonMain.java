package fengfei.forest.slice.config.json;

import java.io.File;
import java.io.InputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import fengfei.forest.slice.config.xml.Config;

public class JsonMain {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Serializer serializer = new Persister();
		InputStream in = JsonMain.class.getClassLoader().getResourceAsStream(
				"config/config.xml");
		// System.out.println(in);

		Config config = serializer.read(Config.class, in, false);
		 System.out.println(config);
		in.close();
	 
		 

		// Config config2 = new Config();
		// config2.getGroups().add(
		// new GroupConfig("1", "class", "A", OverType.Exception, ""));
		// serializer.write(config2, new File("xx.xml"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File("xx.json"), config);

	}

}
