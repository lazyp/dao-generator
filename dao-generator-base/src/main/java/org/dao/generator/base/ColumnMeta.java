package org.dao.generator.base;

/**
 * 数据库列元数据
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * @date 2014-01-08
 */
public class ColumnMeta {
	private String columnName;
	private String dbType;
	private boolean isKey;
	private boolean isAutoIncrement;
	private boolean isNullable;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

}
