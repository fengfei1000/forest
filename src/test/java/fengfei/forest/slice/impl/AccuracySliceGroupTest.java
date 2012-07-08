package fengfei.forest.slice.impl;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import fengfei.forest.example.XmlMain;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.config.xml.XmlSliceReader;

public class AccuracySliceGroupTest {

	static SliceGroupFactory factory;
	String testMsg = "test";

	@BeforeClass
	public static void setup() {
		InputStream in = XmlMain.class.getClassLoader().getResourceAsStream(
				"config/AccuracySliceGroupTestConfig.xml");
		SliceReader<SliceGroupFactory> reader = new XmlSliceReader();
		factory = reader.read(in);
	}

	@Test
	public void testEqualityLogicalSlice() {
		SliceGroup<Long> group = factory.getSliceGroup("EqualitySliceUnit");
		System.out.println(group);
		Slice slice = group.get(1l);
		assertNotNull(slice);
		assertEquals("1", slice.getId());
	}

}
