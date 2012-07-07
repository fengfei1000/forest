package fengfei.forest.slice.impl;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.Slice;

public class EqualityLogicalSliceTest extends BaseSliceTest {

	static EqualityLogicalSlice<LongSlicePlotter> slice = new EqualityLogicalSlice<>();

	@BeforeClass
	public static void setup() {

	}

	@Test
	public void testAddSlice() {
		testMsg = "test add slice, ";
		int id = 10;
		create(slice);
		slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
		slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
		slice.addSlice(createPhysicalSlice(slice, id++), Function.Any);
		assertEquals(testMsg, 3, slice.getSliceSize());
		List<Slice> slices = slice.getSlices();
		Map<String, String> pMap = slice.getExtraInfo();
		for (int i = 0; i < slices.size(); i++) {
			Slice s = slices.get(i);
			assertNotNull(testMsg, s);
			assertEquals(testMsg, s.getSuffix(), slice.getSuffix());
			Map<String, String> exMap = s.getExtraInfo();
			assertEquals(testMsg, exMap.get(KEY_PASSWORD), pMap.get(KEY_PASSWORD));
			assertEquals(testMsg, exMap.get(KEY_USER), pMap.get(KEY_USER));
			assertEquals(testMsg, exMap.get(KEY_PORT), pMap.get(KEY_PORT));
			assertFalse(testMsg, exMap.get(KEY_HOST).equals(pMap.get(KEY_HOST)));

		}
		System.out.println(slices);

	}

	@Test
	public void testGet() {
		Slice slice1 = slice.get(1, Function.Any);
		System.out.println(slice1);
	}

	@Test
	public void testGetAny() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChildSourceFunction() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChildSource() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSuffix() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetExtraInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeight() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPhysical() {
		fail("Not yet implemented");
	}

}
