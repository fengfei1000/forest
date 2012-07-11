package fengfei.forest.slice.model;

import java.util.Map;

public class SliceInfo {
	private SliceId id;
	private Status status;
	private Map<String, String> extraInfo;

	public SliceInfo(SliceId id, Status status, Map<String, String> extraInfo) {
		super();
		this.id = id;
		this.status = status;
		this.extraInfo = extraInfo;
	}

	public SliceInfo(SliceId id, Status status) {
		super();
		this.id = id;
		this.status = status;
	}

	public void addExtraInfo(String key, String value) {
		this.extraInfo.put(key, value);
	}

	public Map<String, String> getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(Map<String, String> extraInfo) {
		this.extraInfo = extraInfo;
	}

	public SliceId getId() {
		return id;
	}

	public void setId(SliceId id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((extraInfo == null) ? 0 : extraInfo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SliceInfo other = (SliceInfo) obj;
		if (status != other.status)
			return false;
		if (extraInfo == null) {
			if (other.extraInfo != null)
				return false;
		} else if (!extraInfo.equals(other.extraInfo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SliceInfo [id=" + id + ", status=" + status + ", extraInfo="
				+ extraInfo + "]";
	}
}
