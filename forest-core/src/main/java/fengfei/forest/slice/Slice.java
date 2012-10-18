package fengfei.forest.slice;

import java.util.HashMap;
import java.util.Map;

import fengfei.forest.slice.model.Status;

public abstract class Slice {

	protected String id;
	protected String suffix;
	protected Map<String, String> extraInfo = new HashMap<>();
	protected int weight;
	protected Status status;
	protected boolean isPhysical;
	protected Function function = Function.Normal;

	public Slice() {
	}

	public Slice(String id, String suffix, Map<String, String> extraInfo,
			int weight, Status status, boolean isPhysical) {
		super();
		this.id = id;
		this.suffix = suffix;
		this.extraInfo = extraInfo;
		this.weight = weight;
		this.status = status;
		this.isPhysical = isPhysical;
	}

	public void addExtraInfo(String key, String value) {
		extraInfo.put(key, value);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Map<String, String> getExtraInfo() {
		return extraInfo;
	}

	public void addExtraInfo(Map<String, String> extraInfo) {
		if (extraInfo == null) {
			return;
		}
		this.extraInfo.putAll(new HashMap<>(extraInfo));
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isPhysical() {
		return isPhysical;
	}

	public void setPhysical(boolean isPhysical) {
		this.isPhysical = isPhysical;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@Override
	public String toString() {
		return "Slice [id=" + id + ", suffix=" + suffix + ", extraInfo="
				+ extraInfo + ", weight=" + weight + ", status=" + status
				+ ", isPhysical=" + isPhysical + ", function=" + function + "]";
	}

}
