package org.dao.generator.ibatis;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * 
 */
public class ClassMeta {
	private static Map<String, String> DBTYPE_TO_JAVATYPE_MAP = new HashMap<String, String>();

	private String packageStr;
	private String className;
	private String author;
	private String date;
	private String keyProperty;// 主键
	private String keyJavaType;//主键对应的java类型

	static {
		DBTYPE_TO_JAVATYPE_MAP.put("INT", "int");
		DBTYPE_TO_JAVATYPE_MAP.put("INTEGER", "int");
		DBTYPE_TO_JAVATYPE_MAP.put("BIGINT", "long");
		DBTYPE_TO_JAVATYPE_MAP.put("CHAR", "String");
		DBTYPE_TO_JAVATYPE_MAP.put("VARCHAR", "String");
		DBTYPE_TO_JAVATYPE_MAP.put("TEXT", "String");
		DBTYPE_TO_JAVATYPE_MAP.put("FLOAT", "float");
		DBTYPE_TO_JAVATYPE_MAP.put("DECIMAL", "float");
		DBTYPE_TO_JAVATYPE_MAP.put("TINYINT", "short");
	}

	public String getPackageStr() {
		return packageStr;
	}

	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getKeyProperty() {
		return keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getKeyJavaType() {
		return keyJavaType;
	}

	public void setKeyJavaType(String keyJavaType) {
		this.keyJavaType = keyJavaType;
	}

	public String getJavaType(String dbType) {
		String javaType = DBTYPE_TO_JAVATYPE_MAP.get(StringUtils.upperCase(dbType, Locale.ENGLISH));
		if (StringUtils.isBlank(javaType)) {
			return "String";
		}
		return javaType;
	}
}
