package fengfei.forest.example;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.impl.LongSliceEqualizer;
import fengfei.forest.slice.impl.NavigableSliceGroup;
import fengfei.forest.slice.impl.PhysicalSlice;

public class NavigableSliceGroupExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NavigableSliceGroup<Long> group = new NavigableSliceGroup<>(
				new LongSliceEqualizer(), FunctionType.Equality,
				SliceAlgorithmType.Remainder);
		setupGroup(group);
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
		System.out.println(group.get(1001l, Function.Write));
		System.out.println(group.get(11l, Function.Write));
	}

	private static void setupGroup(SliceGroup<Long> group) {
		group.addSlice(1, new PhysicalSlice("1-1"));
		group.addSlice(100, new PhysicalSlice("2-1"));
		group.addSlice(1000, new PhysicalSlice("3-1"));
		group.addSlice(10000, new PhysicalSlice("4-1"));

	}

}
