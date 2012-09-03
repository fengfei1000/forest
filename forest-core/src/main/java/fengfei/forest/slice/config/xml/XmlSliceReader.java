package fengfei.forest.slice.config.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.exception.ErrorSliceConfigException;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class XmlSliceReader implements SliceReader<SliceGroupFactory> {

	static Logger logger = LoggerFactory.getLogger(XmlSliceReader.class);
	private Serializer serializer = new Persister();

	@Override
	public SliceGroupFactory read(String filePath)
			throws ErrorSliceConfigException {
		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			return read(in);
		} catch (FileNotFoundException e) {
			logger.error("Slice config file(" + filePath + ") not found.", e);
			throw new ErrorSliceConfigException("Slice config file(" + filePath
					+ ") not found.", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("Slice config error.", e);
			}
		}

	}

	@Override
	public SliceGroupFactory read(InputStream in)
			throws ErrorSliceConfigException {
		try {
			Config config = serializer.read(Config.class, in, false);
			SliceGroupFactory factory = new SliceGroupFactory();
			factory.config(config);
			System.out.println(config);

			return factory;
		} catch (Exception e) {
			logger.error("Slice config error.", e);
			throw new ErrorSliceConfigException("Slice config error.", e);
		}
	}

	@Override
	public Config readConfig(String path) throws ErrorSliceConfigException {
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			Config config = serializer.read(Config.class, in, false);
			return config;
		} catch (Exception e) {
			logger.error("Slice config error.", e);
			throw new ErrorSliceConfigException("Slice config error.", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("Slice config error.", e);
			}
		}
	}

}
