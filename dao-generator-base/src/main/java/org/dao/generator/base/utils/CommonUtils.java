package org.dao.generator.base.utils;

/**
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * @date 2015-01-12
 */
@Deprecated
public final class CommonUtils {
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String strFirstCharToUppercase(String str) {
		if (str == null || "".equals(str.trim())) {
			return "";
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
