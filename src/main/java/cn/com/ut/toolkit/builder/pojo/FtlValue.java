package cn.com.ut.toolkit.builder.pojo;

import java.util.Map;

public class FtlValue {

	private String className;
	private String pkg;
	private Map<String, ColumnType> columnTypes;

	public String getPkg() {

		return pkg;
	}

	public void setPkg(String pkg) {

		this.pkg = pkg;
	}

	public String getClassName() {

		return className;
	}

	public void setClassName(String className) {

		this.className = className;
	}

	public Map<String, ColumnType> getColumnTypes() {

		return columnTypes;
	}

	public void setColumnTypes(Map<String, ColumnType> columnTypes) {

		this.columnTypes = columnTypes;
	}

}
