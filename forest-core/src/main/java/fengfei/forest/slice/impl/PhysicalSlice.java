package fengfei.forest.slice.impl;

import java.util.HashMap;
import java.util.Map;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.model.Status;

public class PhysicalSlice extends Slice {


	public PhysicalSlice() {
		setPhysical(true);
	}

	public PhysicalSlice(String id, String suffix, Map<String, String> extraInfo) {
		super(id, suffix, new HashMap<String, String>(extraInfo), 1,
				Status.Normal, true);
	}

	public PhysicalSlice(String id, String suffix) {
		super(id, suffix, new HashMap<String, String>(), 1, Status.Normal, true);
	}

	public PhysicalSlice(String id, String suffix, Function function) {
		super(id, suffix, new HashMap<String, String>(), 1, Status.Normal, true);
		this.function = function;
	}

	public PhysicalSlice(String id) {
		super(id, null, new HashMap<String, String>(), 1, Status.Normal, true);
	}

	public PhysicalSlice(String id, Function function) {
		super(id, null, new HashMap<String, String>(), 1, Status.Normal, true);
		this.function = function;
	}



	@Override
	public String toString() {
		return "PhysicalSlice [function=" + function + ", id=" + id
				+ ", suffix=" + suffix + ", extraInfo=" + extraInfo
				+ ", weight=" + weight + ", status=" + status + ", isPhysical="
				+ isPhysical + "]";
	}

}
