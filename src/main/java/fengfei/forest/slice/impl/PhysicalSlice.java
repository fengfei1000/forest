package fengfei.forest.slice.impl;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.Slice;

public class PhysicalSlice extends Slice {

	private Function function;

	public PhysicalSlice() {
		setPhysical(true);
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@Override
	public String toString() {
		return "PhysicalSlice [function=" + function + ", id=" + id + ", suffix=" + suffix + ", extraInfo=" + extraInfo + ", weight=" + weight + ", status=" + status + ", isPhysical=" + isPhysical + "]";
	}
}