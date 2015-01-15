说明
======

* dao-generator-base模块是基础公共模块。
* ibatis-dao-generator模块是ibatis dao模板代码生成模块。


使用
======

* 默认生成的代码和xml文件在System.getProperty("user.dir")/code-template目录下面 *

```java
    ClassMeta classMeta = new ClassMeta();
    classMeta.setAuthor("lazy_p");
	classMeta.setPackageStr("com.coin.robot.db.meta");
	classMeta.setKeyProperty("id");
	classMeta.setKeyJavaType("long");
	IbatisDaoGenerator daoGen = new IbatisDaoGenerator(classMeta, new String[] { "user", "order", "robot" });
	daoGen.generate();
```