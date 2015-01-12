package org.dao.generator.base.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * 
 */
public final class PropertiesUtil {
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	
	private  Properties pro = null;

	private PropertiesUtil(Properties pro) {
		super();
		this.pro = pro;
	}

	public static PropertiesUtil getClasspathProperties(String properties) {
		InputStream input = PropertiesUtil.class.getResourceAsStream(properties);
		Properties pro  = new Properties();
		try {
			pro.load(input);
		} catch (IOException e) {
			logger.error("载入资源文件失败" , e);
		}
		return new PropertiesUtil(pro);
	}
	
	public String getValue(String key){
		return pro.getProperty(key);
	}

	public  Properties getPro() {
		return this.pro;
	}

	public void setPro(Properties pro) {
		this.pro = pro;
	}
	
}
