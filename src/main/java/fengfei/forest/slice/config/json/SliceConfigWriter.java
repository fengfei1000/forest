package fengfei.forest.slice.config.json;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SliceConfigWriter {

	public static void main(String[] args)
			throws JsonGenerationException,
			JsonMappingException,
			IOException {
		range();
		// fixtedRange();
		// fixtedSliceSize();
		// list();
		// hash();
		all();
	}

	private static void range()
			throws JsonGenerationException,
			JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		RangeConfig config = new RangeConfig();

		PartitionConfig<Long> partition = new PartitionConfig<Long>(20L, "", "");
		PartitionConfig<Long> subPartition = new PartitionConfig<Long>(20L, "", "");
		partition.addSubPatition(subPartition);
		config.addPartition(partition);
		//

		byte[] bs = mapper.writeValueAsBytes(config);
		System.out.println(new String(bs));
	}

	private static void hash()
			throws JsonGenerationException,
			JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		HashConfig config = new HashConfig();

		byte[] bs = mapper.writeValueAsBytes(config);
		System.out.println(new String(bs));
	}

	private static void all()
			throws JsonGenerationException,
			JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		Set<SliceConfig> sets = new HashSet<SliceConfig>();
		HashConfig config = new HashConfig();

		byte[] bs = mapper.writeValueAsBytes(sets);
		System.out.println(new String(bs));
		Set s = mapper.readValue(bs, Set.class);

	}

}
