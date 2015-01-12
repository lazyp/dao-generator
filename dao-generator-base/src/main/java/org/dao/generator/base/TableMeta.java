package org.dao.generator.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 表元数据
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * @date 2014-01-08
 */
public class TableMeta {
	private String tableName;
	private List<ColumnMeta> columnMetaList = new ArrayList<ColumnMeta>(64);

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnMeta> getColumnMetaList() {
		return columnMetaList;
	}

	public void addColumnMeta(ColumnMeta columnMeta) {
		this.columnMetaList.add(columnMeta);
	}
}
