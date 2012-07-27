package fengfei.forest.slice;

import java.io.InputStream;

import fengfei.forest.slice.config.xml.Config;
import fengfei.forest.slice.exception.ErrorSliceConfigException;

public interface SliceReader<T> {

    T read(String filePath) throws ErrorSliceConfigException;

    T read(InputStream in) throws ErrorSliceConfigException;

    Config readConfig(InputStream in) throws ErrorSliceConfigException;
}