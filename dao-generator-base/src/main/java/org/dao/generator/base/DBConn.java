package org.dao.generator.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * @date 2015-01-08
 */
public class DBConn {
	
	/**
	 * @param url
	 *            连接db url
	 * @param user
	 *            数据库用户名
	 * @param pass
	 *            数据库密码
	 * @return
	 * 
	 * @throws SQLException
	 */
	public Connection createConnection(String url, String user, String pass) throws SQLException {
		return DriverManager.getConnection(url, user, pass);
	}
}
