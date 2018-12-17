package cn.com.ut.toolkit.builder.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import cn.com.ut.toolkit.builder.pojo.ColumnType;
import cn.com.ut.toolkit.builder.pojo.FtlValue;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class FreemarkerUtil implements InitializingBean {

	private Configuration configuration;

	public Configuration getConfiguration() {

		return configuration;
	}

	public void gen(FtlValue ftlValue, OutputStream output, String tmpName)
			throws IOException, TemplateException {

		Template tmp = getConfiguration().getTemplate(tmpName);
		Writer out = new OutputStreamWriter(output);
		tmp.process(ftlValue, out);

	}

	public void genByFileName(FtlValue ftlValue, OutputStream output, String fileName)
			throws IOException, TemplateException {

		if (fileName.endsWith(ftlValue.getClassName() + ".java")) {
			gen(ftlValue, output, "entity.ftl");

		} else if (fileName.endsWith(ftlValue.getClassName() + ".json")) {
			gen(ftlValue, output, "json.ftl");

		} else if (fileName.endsWith(ftlValue.getClassName() + "DAO.java")) {
			gen(ftlValue, output, "DAO.ftl");

		} else if (fileName.endsWith(ftlValue.getClassName() + "DAOImpl.java")) {
			gen(ftlValue, output, "DAOImpl.ftl");

		} else if (fileName.endsWith(ftlValue.getClassName() + "ServiceImpl.java")) {
			gen(ftlValue, output, "ServiceImpl.ftl");

		} else if (fileName.endsWith(ftlValue.getClassName() + "Service.java")) {
			gen(ftlValue, output, "Service.ftl");

		} else if (fileName.endsWith(ftlValue.getClassName() + "Controller.java")) {

			gen(ftlValue, output, "Controller.ftl");
		} else {

		}

	}

	public FtlValue buildModel(Map<String, ColumnType> columnTypeMap, String className,
			String pkg) {

		FtlValue ftlValue = new FtlValue();
		ftlValue.setClassName(className);
		ftlValue.setPkg(pkg);
		ftlValue.setColumnTypes(columnTypeMap);
		return ftlValue;

	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);
		cfg.setClassLoaderForTemplateLoading(FreemarkerUtil.class.getClassLoader(), "template");
		cfg.setDefaultEncoding("UTF-8");
		this.configuration = cfg;

	}

}
