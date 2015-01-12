package org.dao.generator.base.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.dao.generator.base.ColumnMeta;
import org.dao.generator.base.DBConn;
import org.dao.generator.base.DBHandler;
import org.dao.generator.base.TableMeta;
import org.dao.generator.base.utils.PropertiesUtil;

/**
 * mysql数据库操作
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * @date 2014-01-08
 */
public class MySqlDBHandler implements DBHandler{
	private static final PropertiesUtil PROPERTIES_UTIL = PropertiesUtil.getClasspathProperties("/db.properties");
	private static final String DB_DRIVER = PROPERTIES_UTIL.getValue("db.driver");
	private static final String DB_URL = PROPERTIES_UTIL.getValue("db.url");
	private static final String DB_USER = PROPERTIES_UTIL.getValue("db.username");
	private static final String DB_PASSWORD = PROPERTIES_UTIL.getValue("db.password");

	static {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static final Logger logger = Logger.getLogger(MySqlDBHandler.class);
	private static final String SELECT_SQL_TEMP = "select * from `%s` limit 1";
	private DBConn dbConn = new DBConn();

	public Connection getNewConnection() throws SQLException {
		return dbConn.createConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	public TableMeta getTableMeta(String tableName) {
		String sql = String.format(SELECT_SQL_TEMP, tableName);
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		ResultSetMetaData rsMetaData = null;

		try {
			conn = this.getNewConnection();
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			rsMetaData = rs.getMetaData();

			TableMeta tableMeta = new TableMeta();
			tableMeta.setTableName(tableName);
			
			int columnCount = rsMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; ++i) {
				ColumnMeta columnMeta = new ColumnMeta();
				columnMeta.setColumnName(rsMetaData.getColumnName(i));
				columnMeta.setDbType(rsMetaData.getColumnTypeName(i));
				columnMeta.setAutoIncrement(rsMetaData.isAutoIncrement(i));
				columnMeta.setNullable(rsMetaData.isNullable(i) == 1 ? true : false);
				tableMeta.addColumnMeta(columnMeta);
			}

			return tableMeta;
		} catch (SQLException e) {
			logger.error("数据库异常", e);
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("关闭异常", e);
			}
		}

		return null;
	}
	
	public static void main(String[] args) {
		MySqlDBHandler handler = new MySqlDBHandler();
		TableMeta tableMeta = handler.getTableMeta("tb_trade");
		System.out.println(tableMeta.getTableName());
	}
}
