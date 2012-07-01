package fengfei.forest.slice;

import java.io.InputStream;

import fengfei.forest.slice.exception.ErrorSliceConfigException;

public interface SliceReader<T> {

	public abstract T read(String filePath) throws ErrorSliceConfigException;

	public abstract T read(InputStream in) throws ErrorSliceConfigException;

}