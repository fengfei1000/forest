package fengfei.forest.slice.config.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.slice.SliceGroupFactory;
import fengfei.forest.slice.SliceReader;
import fengfei.forest.slice.exception.ErrorSliceConfigException;

public class XmlSliceReader implements SliceReader<SliceGroupFactory> {

	static Logger logger = LoggerFactory.getLogger(XmlSliceReader.class);
	private Serializer serializer = new Persister();

	@Override
	public SliceGroupFactory read(String filePath) throws ErrorSliceConfigException {
		try {
			InputStream in = new FileInputStream(filePath);
			return read(in);
		} catch (FileNotFoundException e) {
			logger.error("Slice config file(" + filePath + ") not found.", e);
			throw new ErrorSliceConfigException(
					"Slice config file(" + filePath + ") not found.",
					e);
		}

	}

	@Override
	public SliceGroupFactory read(InputStream in) throws ErrorSliceConfigException {
		try {
			Config config = serializer.read(Config.class, in, false);
			SliceGroupFactory factory = new SliceGroupFactory();
			factory.config(config);
			in.close();
			return factory;
		} catch (Exception e) {
			logger.error("Slice config error.", e);
			throw new ErrorSliceConfigException("Slice config error.", e);
		}
	}

}