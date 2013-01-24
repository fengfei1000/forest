package fengfei.forest.example;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.SliceAlgorithmType;
import fengfei.forest.slice.SliceGroup;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.impl.AccuracySliceGroup;
import fengfei.forest.slice.impl.HashSliceEqualizer;
import fengfei.forest.slice.impl.PhysicalSlice;

public class AccuracySliceGroupExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AccuracySliceGroup<Long> group = new AccuracySliceGroup<>(
				new HashSliceEqualizer<Long>(), FunctionType.ReadWrite,
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
		System.out.println(group.get(11l, Function.Write));
		System.out.println(group.get(11l, Function.Write));
	}

	private static void setupGroup(SliceGroup<Long> group) {
		group.addSlice(1, new PhysicalSlice("1-1", "1"), Function.Read);
		group.addSlice(2, new PhysicalSlice("2-1", "2"), Function.Read);
		group.addSlice(3, new PhysicalSlice("3-1", "3"), Function.Read);
		group.addSlice(4, new PhysicalSlice("4-1", "4"), Function.Read);
		//
		group.addSlice(1, new PhysicalSlice("1-2", "1"), Function.Write);
		group.addSlice(2, new PhysicalSlice("2-2", "2"), Function.Write);
		group.addSlice(3, new PhysicalSlice("3-2", "3"), Function.Write);
		group.addSlice(4, new PhysicalSlice("4-2", "4"), Function.Write);

	}

}
