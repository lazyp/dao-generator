package org.dao.generator.ibatis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.dao.generator.base.DBHandler;
import org.dao.generator.base.IDaoGenerator;
import org.dao.generator.base.TableMeta;
import org.dao.generator.base.mysql.MySqlDBHandler;
import org.dao.generator.base.utils.CommonUtils;
import org.dao.generator.base.utils.PropertiesUtil;

/**
 * ibatis dao 生成器
 * 
 * @author <a href=mailto:lazy_p@163.com>lazyp</a>
 * 
 */
public class IbatisDaoGenerator implements IDaoGenerator {
	private PropertiesUtil propertiesUtil = PropertiesUtil.getClasspathProperties("/velocity.properties");
	private VelocityEngine velocityEngine = new VelocityEngine();
	private DBHandler dbHandler = new MySqlDBHandler();

	private ClassMeta classMeta;
	private String path;
	private String[] tables;

	private IbatisDaoGenerator() {
		velocityEngine.init(propertiesUtil.getPro());
	}

	public IbatisDaoGenerator(String path, String[] tables) {
		this();
		this.path = path;
		this.tables = tables;
	}

	public IbatisDaoGenerator(ClassMeta classMeta, String[] tables) {
		this();
		this.classMeta = classMeta;
		this.tables = tables;
		this.path = System.getProperty("user.dir") + File.separator + "code-template";
	}

	public void generate() {
		for (String table : tables) {
			// 获取数据库表结构信息
			TableMeta tableMeta = dbHandler.getTableMeta(table);

			// 配置一些class属性信息
			classMeta.setClassName(CommonUtils.strFirstCharToUppercase(tableMeta.getTableName()));
			// classMeta.setPackageStr("com.coin.robot.db.meta");
			// classMeta.setAuthor("lazy_p");
			classMeta.setDate(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd"));
//			classMeta.setKeyProperty("id");
//			classMeta.setKeyJavaType("long");

			this.generatePojoClassFile(classMeta, tableMeta);
			this.generateIbatisXmlFile(classMeta, tableMeta);
			this.generateIbatisDaoFile(classMeta, tableMeta);
		}
	}

	private void generateIbatisDaoFile(ClassMeta classMeta, TableMeta tableMeta) {
		Template pojoVm = velocityEngine.getTemplate("daoclass.java.vm");
		StringWriter sw = new StringWriter();
		Context vmContext = new VelocityContext();
		vmContext.put("classMeta", classMeta);
		vmContext.put("tableName", tableMeta.getTableName());
		vmContext.put("columnMetaList", tableMeta.getColumnMetaList());
		vmContext.put("commonUtils", new CommonUtils());
		pojoVm.merge(vmContext, sw);
		// System.out.println(sw.toString());
		this.outDisk(classMeta.getClassName() + "Dao.java", sw.toString());
	}

	private void generateIbatisXmlFile(ClassMeta classMeta, TableMeta tableMeta) {
		Template pojoVm = velocityEngine.getTemplate("ibatis-pojo.xml.vm");
		StringWriter sw = new StringWriter();
		Context vmContext = new VelocityContext();
		vmContext.put("classMeta", classMeta);
		vmContext.put("tableName", tableMeta.getTableName());
		vmContext.put("columnMetaList", tableMeta.getColumnMetaList());
		vmContext.put("commonUtils", new CommonUtils());
		pojoVm.merge(vmContext, sw);
		// System.out.println(sw.toString());
		this.outDisk(classMeta.getClassName() + ".xml", sw.toString());
	}

	private void generatePojoClassFile(ClassMeta classMeta, TableMeta tableMeta) {
		Template pojoVm = velocityEngine.getTemplate("Pojo.java.vm");
		StringWriter sw = new StringWriter();
		Context vmContext = new VelocityContext();
		vmContext.put("classMeta", classMeta);
		vmContext.put("columnMetaList", tableMeta.getColumnMetaList());
		vmContext.put("commonUtils", new CommonUtils());
		pojoVm.merge(vmContext, sw);

		outDisk(classMeta.getClassName() + ".java", sw.toString());
	}

	private void outDisk(String fileName, String content) {
		File dir = new File(this.path);
		dir.deleteOnExit();
		dir.mkdir();
		
		File file = new File(this.path + File.separator + fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		ClassMeta classMeta = new ClassMeta();
		classMeta.setAuthor("lazy_p");
		classMeta.setPackageStr("com.coin.robot.db.meta");
		classMeta.setKeyProperty("id");
		classMeta.setKeyJavaType("long");
		IbatisDaoGenerator daoGen = new IbatisDaoGenerator(classMeta, new String[] { "user", "order", "robot" });
		daoGen.generate();
	}
}
