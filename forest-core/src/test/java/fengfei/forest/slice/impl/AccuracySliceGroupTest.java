package fengfei.forest.slice.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.config.xml.XmlSliceReader;

@Ignore
public class AccuracySliceGroupTest extends BaseSliceTest {

	static final String PHOST = "localhost", PPORT = "8022", PUSER = "testUser",
			PPWD = "pwd123";
	static final String KEY_HOST = "host", KEY_PORT = "port", KEY_USER = "username",
			KEY_PASSWORD = "password";
	static SliceGroupFactory factory;
	String testMsg = "test";

	@BeforeClass
	public static void setup() {
		InputStream in = AccuracySliceGroupTest.class.getClassLoader().getResourceAsStream(
				"config/AccuracySliceGroupTestConfig.xml");
		SliceReader<SliceGroupFactory> reader = new XmlSliceReader();
		factory = reader.read(in);
	}

	@Test
	public void testEqualityLogicalSlice() {
		SliceGroup<Long> group = factory.getSliceGroup("EqualitySliceUnit");
		System.out.println(group);
		Slice slice1 = group.get(1l);
		assertNotNull(slice1);
		assertNotNull(slice1.getId());
		Map<String, String> exMap = slice1.getExtraInfo();
		assertFalse(PHOST.equals(exMap.get(KEY_HOST)));
		assertTrue(PPORT.equals(exMap.get(KEY_PORT)));
		assertTrue(PUSER.equals(exMap.get(KEY_USER)));
		assertTrue(PPWD.equals(exMap.get(KEY_PASSWORD)));

		Slice slice2 = group.get(2l);
		assertNotNull(slice2);
		assertNotNull(slice2.getId());
		exMap = slice2.getExtraInfo();
		assertFalse(PHOST.equals(exMap.get(KEY_HOST)));
		assertTrue(PPORT.equals(exMap.get(KEY_PORT)));
		assertTrue(PUSER.equals(exMap.get(KEY_USER)));
		assertTrue(PPWD.equals(exMap.get(KEY_PASSWORD)));
		Slice slice3 = group.get(3l);
		assertNotNull(slice3);
		assertEquals(slice3.getSliceId(), slice2.getSliceId());
		assertFalse(slice3.getSliceId().equals(slice1.getSliceId()));
		assertNotNull(slice3.getId());
		exMap = slice3.getExtraInfo();
		assertFalse(PHOST.equals(exMap.get(KEY_HOST)));
		assertTrue(PPORT.equals(exMap.get(KEY_PORT)));
		assertTrue(PUSER.equals(exMap.get(KEY_USER)));
		assertTrue(PPWD.equals(exMap.get(KEY_PASSWORD)));

	}

}
